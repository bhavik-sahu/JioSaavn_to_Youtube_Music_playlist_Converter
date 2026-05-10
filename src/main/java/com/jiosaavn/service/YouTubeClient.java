package com.jiosaavn.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;

public class YouTubeClient {

    private final YouTube youtube;

    public YouTubeClient() throws Exception {

        GoogleClientSecrets secrets =
                GoogleClientSecrets.load(
                        GsonFactory.getDefaultInstance(),
                        new FileReader("src/main/resources/client_server.json"));

        GsonFactory jsonFactory = GsonFactory.getDefaultInstance();

        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        GoogleNetHttpTransport.newTrustedTransport(),
                        jsonFactory,
                        secrets,
                        Collections.singletonList("https://www.googleapis.com/auth/youtube")
                )
                        .setAccessType("offline")
                        .build();

        Credential credential =
                new AuthorizationCodeInstalledApp(
                        flow,
                        new LocalServerReceiver.Builder().setPort(8888).build()
                ).authorize("user");

        youtube = new YouTube.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                jsonFactory,
                credential
        )
                .setApplicationName("Project SYC")
                .build();
    }

    // SEARCH VIDEO
    public String searchVideo(String query) throws IOException {

        YouTube.Search.List search =
                youtube.search().list(Collections.singletonList("id,snippet"));

        search.setQ(query);
        search.setMaxResults(1L);
        search.setType(Collections.singletonList("video"));

        SearchListResponse response = search.execute();

        if (response.getItems().isEmpty()) {
            return null;
        }

        return response.getItems()
                .get(0)
                .getId()
                .getVideoId();
    }

    // CREATE PLAYLIST
    public String createPlaylist(String title) throws IOException {

        Playlist playlist = new Playlist();

        PlaylistSnippet snippet = new PlaylistSnippet();
        snippet.setTitle(title);

        PlaylistStatus status = new PlaylistStatus();
        status.setPrivacyStatus("private");

        playlist.setSnippet(snippet);
        playlist.setStatus(status);

        Playlist inserted = youtube.playlists()
                .insert(Collections.singletonList("snippet,status"), playlist)
                .execute();

        return inserted.getId();
    }

    // ADD VIDEO TO PLAYLIST
    public void addToPlaylist(String playlistId, String videoId) throws IOException {

        PlaylistItem item = new PlaylistItem();

        PlaylistItemSnippet snippet = new PlaylistItemSnippet();
        snippet.setPlaylistId(playlistId);

        ResourceId resourceId = new ResourceId();
        resourceId.setKind("youtube#video");
        resourceId.setVideoId(videoId);

        snippet.setResourceId(resourceId);
        item.setSnippet(snippet);

        youtube.playlistItems()
                .insert(Collections.singletonList("snippet"), item)
                .execute();
    }
}
