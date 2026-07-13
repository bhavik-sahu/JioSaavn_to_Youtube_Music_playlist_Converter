package com.jiosaavn.service;

import com.google.gson.*;
import com.jiosaavn.model.Playlist;
import com.jiosaavn.model.Song;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class SaavnService {
    private static final String BASE = "https://www.jiosaavn.com/api.php";
    private static final Gson gson = new Gson();

    public static String getPlaylistId(String input) {
        if (!input.contains("http")) return input;
        try {
            // Reverted to string-splitting: the response here is not plain JSON
            // (it's a JSONP-style/prefixed payload), so JsonParser fails on it.
            String res = request(input);
            try {
                return res.split("\"type\":\"playlist\",\"id\":\"")[1].split("\"")[0];
            } catch (Exception e) {
                return res.split("\"page_id\",\"")[1].split("\",\"")[0];
            }
        } catch (Exception e) {
            throw new RuntimeException("Error extracting ID", e);
        }
    }

    public static Playlist getPlaylist(String id, boolean lyrics) {
        try {
            String url = BASE + "?__call=playlist.getDetails&_format=json&cc=in&_marker=0%3F_marker%3D0&listid=" + id;
            JsonObject json = JsonParser.parseString(request(url)).getAsJsonObject();

            Playlist p = new Playlist();
            p.setId(safe(json, "id", "Unknown"));
            p.setListName(format(safe(json, "listname", "Unknown")));
            p.setFirstname(format(safe(json, "firstname", "")));
            if (json.has("image")) p.setImage(safe(json, "image", "").replace("150x150", "500x500"));

            List<Song> songs = new ArrayList<>();
            if (json.has("songs")) {
                for (JsonElement e : json.getAsJsonArray("songs")) {
                    Song s = gson.fromJson(e, Song.class);
                    if (s.getEncryptedMediaUrl() != null)
                        s.setMediaUrl(CryptoService.decrypt(s.getEncryptedMediaUrl()));
                    if (s.getImage() != null)
                        s.setImage(s.getImage().replace("150x150", "500x500"));
                    songs.add(s);
                }
            }
            p.setSongs(songs);
            p.setSongCount(String.valueOf(songs.size()));
            return p;
        } catch (Exception e) {
            throw new RuntimeException("Error fetching playlist", e);
        }
    }

    private static String safe(JsonObject j, String key, String def) {
        return j.has(key) && !j.get(key).isJsonNull() ? j.get(key).getAsString() : def;
    }

    private static String format(String s) {
        return s == null ? "" : s.replace("&quot;", "'").replace("&amp;", "&").replace("&#039;", "'");
    }

    private static String request(String url) throws Exception {
        java.net.URLConnection conn = new URL(url).openConnection();
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");
        conn.setConnectTimeout(10000);
        try (BufferedReader r = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) sb.append(line);
            return sb.toString();
        }
    }
}