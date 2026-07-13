package com.jiosaavn.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import java.util.List;

@Data
public class Playlist {
    private String id;

    @SerializedName("listname")
    private String listName;

    private String firstname, image, description;

    @SerializedName("song_count")
    private String songCount;

    private List<Song> songs;
}