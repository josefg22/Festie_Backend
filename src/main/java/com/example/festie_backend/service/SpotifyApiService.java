package com.example.festie_backend.service;

import okhttp3.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class SpotifyApiService {

    private static final String BASE_URL = "https://api.spotify.com/v1";

    private final OkHttpClient client;
    private final ObjectMapper objectMapper;

    public SpotifyApiService() {
        this.client = new OkHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Busca el ID del artista por su nombre.
     * @param accessToken Token de acceso.
     * @param artistName Nombre del artista.
     * @return ID del artista.
     * @throws IOException En caso de error con la API.
     */
    public String getArtistId(String accessToken, String artistName) throws IOException {
        HttpUrl url = HttpUrl.parse(BASE_URL + "/search")
                .newBuilder()
                .addQueryParameter("q", artistName)
                .addQueryParameter("type", "artist")
                .addQueryParameter("limit", "1")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Error al buscar el artista: " + response);
            }

            // Parsear la respuesta de la API
            Map<String, Object> data = objectMapper.readValue(response.body().string(), Map.class);

            // Obtener la lista de artistas desde "artists.items"
            Map<String, Object> artists = (Map<String, Object>) data.get("artists");
            List<Map<String, Object>> items = (List<Map<String, Object>>) artists.get("items");

            // Asegurarse de que la lista no esté vacía
            if (items.isEmpty()) {
                throw new IOException("No se encontraron artistas con el nombre proporcionado.");
            }

            // Tomar el primer artista de la lista
            Map<String, Object> artist = items.get(0);

            // Retornar el ID del artista
            return (String) artist.get("id");
        }
    }

    /**
     * Busca la playlist "This Is" de un artista por su ID.
     * @param accessToken Token de acceso.
     * @param artistId ID del artista.
     * @return URL de la playlist "This Is".
     * @throws IOException En caso de error con la API.
     */
    public String getThisIsPlaylist(String accessToken, String artistName) throws IOException {
        HttpUrl url = HttpUrl.parse(BASE_URL + "/search")
                .newBuilder()
                .addQueryParameter("q", "This Is " + artistName.trim()) // Búsqueda por nombre del artista
                .addQueryParameter("type", "playlist")
                .addQueryParameter("limit", "5") // Limitar a 5 resultados
                .build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body() != null ? response.body().string() : "{}";
            System.out.println("Respuesta de la API: " + responseBody);

            if (!response.isSuccessful()) {
                throw new IOException("Error al buscar la playlist: " + responseBody);
            }

            // Parsear la respuesta JSON
            Map<String, Object> data = objectMapper.readValue(responseBody, Map.class);

            // Extraer el nodo 'playlists'
            Map<String, Object> playlists = (Map<String, Object>) data.get("playlists");
            if (playlists == null) {
                throw new IOException("No se encontraron playlists en la respuesta.");
            }

            // Extraer la lista de 'items'
            List<Map<String, Object>> items = (List<Map<String, Object>>) playlists.get("items");
            if (items == null || items.isEmpty()) {
                return "No se encontraron playlists para 'This Is " + artistName + "'.";
            }

            // Filtrar elementos nulos en la lista
            items = items.stream().filter(item -> item != null).toList();

            if (items.isEmpty()) {
                return "No se encontraron playlists válidas para 'This Is " + artistName + "'.";
            }

            // Obtener la primera playlist válida
            Map<String, Object> playlist = items.get(0);
            Map<String, String> externalUrls = (Map<String, String>) playlist.get("external_urls");

            if (externalUrls == null || !externalUrls.containsKey("spotify")) {
                throw new IOException("La playlist no contiene una URL válida.");
            }

            // Retornar la URL de Spotify de la primera playlist válida
            return externalUrls.get("spotify");
        }
    }

}

