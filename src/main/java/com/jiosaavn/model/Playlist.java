package com.jiosaavn.model;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Playlist {
    private String id;
    @SerializedName("listname") private String listName;
    private String firstname, image, description;
    @SerializedName("song_count") private String songCount;
    private List<Song> songs;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getListName() { return listName; }

    public void setListName(String listName) { this.listName = listName; }

    public String getFirstname() { return firstname; }

    public void setFirstname(String firstname) { this.firstname = firstname; }

    public String getImage() { return image; }

    public void setImage(String image) { this.image = image; }

    public List<Song> getSongs() { return songs; }

    public void setSongs(List<Song> songs) { this.songs = songs; }

    public String getSongCount() { return songCount; }

    public void setSongCount(String songCount) { this.songCount = songCount; }
}
