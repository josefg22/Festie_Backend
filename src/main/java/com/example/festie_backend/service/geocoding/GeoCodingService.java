package com.example.festie_backend.service.geocoding;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

@Service
public class GeoCodingService {

    private static final String GEOCODING_API_URL = "https://maps.googleapis.com/maps/api/geocode/json";

    @Value("${geocoding.api.key}")
    private String API_KEY;

    public String[] getCoordinates(String location) {
        try {
            // URL de la solicitud a la API de geocodificación
            String url = GEOCODING_API_URL + "?address=" + location.replace(" ", "+") + "&key=" + API_KEY;

            // Realiza la solicitud HTTP
            RestTemplate restTemplate = new RestTemplate();
            GeoCodingResponse response = restTemplate.getForObject(url, GeoCodingResponse.class);

            // Obtiene las coordenadas del primer resultado
            if (response != null && !response.getResults().isEmpty()) {
                GeoCodingResponse.Location locationResult = response.getResults().get(0).getGeometry().getLocation();
                return new String[]{String.valueOf(locationResult.getLat()), String.valueOf(locationResult.getLng())};
            }
        } catch (Exception e) {
            throw new RuntimeException("Error obteniendo coordenadas: " + e.getMessage());
        }
        throw new RuntimeException("No se encontraron coordenadas para la ubicación: " + location);
    }
}
