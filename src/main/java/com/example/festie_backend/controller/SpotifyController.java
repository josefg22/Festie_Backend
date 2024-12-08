package com.example.festie_backend.controller;

import com.example.festie_backend.service.SpotifyApiService;
import com.example.festie_backend.service.SpotifyAuthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpotifyController {

    private final SpotifyAuthService spotifyAuthService;
    private final SpotifyApiService spotifyApiService;

    public SpotifyController(SpotifyAuthService spotifyAuthService, SpotifyApiService spotifyApiService) {
        this.spotifyAuthService = spotifyAuthService;
        this.spotifyApiService = spotifyApiService;
    }

    /**
     * Endpoint para obtener información del artista por su nombre.
     * @param artistName Nombre del artista.
     * @return ID del artista en Spotify.
     */
    @GetMapping("/spotify/artist")
    public String getArtist(@RequestParam String artistName) {
        try {
            String accessToken = spotifyAuthService.getAccessToken();
            String artistId = spotifyApiService.getArtistId(accessToken, artistName);
            return "Artista encontrado: " + artistId;
        } catch (Exception e) {
            return "Error al obtener el artista: " + e.getMessage();
        }
    }

    /**
     * Endpoint para obtener la playlist "This Is" de un artista.
     * @param artistName Nombre del artista.
     * @return URL de la playlist "This Is" o un mensaje indicando que no se encontró.
     */
    @GetMapping("/spotify/playlist/this-is")
    public String getThisIsPlaylist(@RequestParam String artistName) {
        try {
            String accessToken = spotifyAuthService.getAccessToken();
            String artistId = spotifyApiService.getArtistId(accessToken, artistName);
            String playlistUrl = spotifyApiService.getThisIsPlaylist(accessToken, artistName);
            return playlistUrl != null ? "Playlist encontrada: " + playlistUrl : "No se encontró la playlist 'This Is' para este artista.";
        } catch (Exception e) {
            return "Error al obtener la playlist: " + e.getMessage();
        }
    }
}

