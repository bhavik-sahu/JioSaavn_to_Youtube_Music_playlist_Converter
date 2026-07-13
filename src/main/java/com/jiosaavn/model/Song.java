package com.jiosaavn.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Song {
    private String id, song, album, singers, image, lyrics, duration, year, language;

    @SerializedName("primary_artists")
    private String primaryArtists;

    @SerializedName("encrypted_media_url")
    private String encryptedMediaUrl;

    @SerializedName("media_url")
    private String mediaUrl;
}