package com.jiosaavn.service;

import com.jiosaavn.model.Song;
import com.jiosaavn.model.Playlist;
import java.io.IOException;
import java.util.List;

public class PlaylistConverter {
    private YouTubeClient youTubeClient;
    
    public PlaylistConverter(YouTubeClient youTubeClient) {
        this.youTubeClient = youTubeClient;
    }
    
    public String convertToYouTube(Playlist playlist) throws IOException {
        System.out.println("\n🔄 Converting to YouTube Music...");
        
        String ytPlaylistId = youTubeClient.createPlaylist(playlist.getListName());
        System.out.println("✓ Created YouTube Playlist: " + ytPlaylistId);
        
        List<Song> songs = playlist.getSongs();
        int added = 0, failed = 0;
        
        for (int i = 0; i < songs.size(); i++) {
            Song s = songs.get(i);
            String query = (s.getSong() != null ? s.getSong() : "") + " " +
                          (s.getPrimaryArtists() != null ? s.getPrimaryArtists() : "");
            
            try {
                String videoId = youTubeClient.searchVideo(query.trim());
                if (videoId != null) {
                    youTubeClient.addToPlaylist(ytPlaylistId, videoId);
                    System.out.println("  [" + (i+1) + "/" + songs.size() + "] ✓ " + s.getSong());
                    added++;
                } else {
                    System.out.println("  [" + (i+1) + "/" + songs.size() + "] ✗ Not found: " + s.getSong());
                    failed++;
                }
            } catch (Exception e) {
                System.out.println("  [" + (i+1) + "/" + songs.size() + "] ✗ Error: " + s.getSong());
                failed++;
            }
        }
        
        System.out.println("\n✓ Conversion Complete: " + added + " added, " + failed + " failed");
        return ytPlaylistId;
    }
}
