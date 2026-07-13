package com.jiosaavn.model;
import com.google.gson.annotations.SerializedName;

public class Song {
    private String id, song, album, singers, image, lyrics, duration, year, language;
    @SerializedName("primary_artists") private String primaryArtists;
    @SerializedName("encrypted_media_url") private String encryptedMediaUrl;
    @SerializedName("media_url") private String mediaUrl;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getSong() { return song; }

    public void setSong(String song) { this.song = song; }

    public String getAlbum() { return album; }

    public void setAlbum(String album) { this.album = album; }

    public String getPrimaryArtists() { return primaryArtists; }

    public void setPrimaryArtists(String primaryArtists) { this.primaryArtists = primaryArtists; }

    public String getSingers() { return singers; }

    public void setSingers(String singers) { this.singers = singers; }

    public String getImage() { return image; }


    public void setImage(String image) { this.image = image; }

    public String getMediaUrl() { return mediaUrl; }

    public void setMediaUrl(String mediaUrl) { this.mediaUrl = mediaUrl; }

    public String getLyrics() { return lyrics; }

    public void setLyrics(String lyrics) { this.lyrics = lyrics; }

    public String getDuration() { return duration; }

    public void setDuration(String duration) { this.duration = duration; }

    public String getYear() { return year; }

    public void setYear(String year) { this.year = year; }

    public String getLanguage() { return language; }

    public void setLanguage(String language) { this.language = language; }

    public String getEncryptedMediaUrl() { return encryptedMediaUrl; }

    public void setEncryptedMediaUrl(String encryptedMediaUrl) { this.encryptedMediaUrl = encryptedMediaUrl; }
}
