package com.example.festie_backend.service;

import okhttp3.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;

@Service
public class SpotifyAuthService {
    private static final String TOKEN_URL = "https://accounts.spotify.com/api/token";

    @Value("${spotify.client-id}")
    private String clientId;

    @Value("${spotify.client-secret}")
    private String clientSecret;

    private String accessToken;
    private Instant tokenExpiration;

    public String getAccessToken() throws IOException {
        // Verifica si el token actual es válido
        if (accessToken != null && tokenExpiration != null && Instant.now().isBefore(tokenExpiration)) {
            return accessToken;
        }

        // Si no es válido, solicita un nuevo token
        OkHttpClient client = new OkHttpClient();
        String credentials = Credentials.basic(clientId, clientSecret);

        RequestBody formBody = new FormBody.Builder()
                .add("grant_type", "client_credentials")
                .build();

        Request request = new Request.Builder()
                .url(TOKEN_URL)
                .post(formBody)
                .addHeader("Authorization", credentials)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Failed to get access token: " + response);
            }

            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> responseData = mapper.readValue(response.body().string(), Map.class);

            // Guarda el token y el tiempo de expiración
            accessToken = (String) responseData.get("access_token");
            int expiresIn = (int) responseData.get("expires_in");
            tokenExpiration = Instant.now().plusSeconds(expiresIn);

            return accessToken;
        }
    }
}

