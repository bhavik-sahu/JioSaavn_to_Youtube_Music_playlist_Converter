package com.jiosaavn;
import com.jiosaavn.model.Playlist;
import com.jiosaavn.model.Song;
import com.jiosaavn.service.SaavnService;
import com.jiosaavn.service.YouTubeClient;
import com.jiosaavn.service.PlaylistConverter;
import java.util.List;
import java.util.Scanner;

public class PlaylistFetcher {
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║  JioSaavn → YouTube Playlist Converter ║");
        System.out.println("╚════════════════════════════════════════╝\n");
        
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter playlist ID or URL: ");
        String input = sc.nextLine().trim();
        System.out.print("Convert to YouTube Music? (y/N): ");
        boolean convert = sc.nextLine().trim().equalsIgnoreCase("y");
        
        try {
            System.out.println("\nFetching from JioSaavn...\n");
            String id = SaavnService.getPlaylistId(input);
            Playlist p = SaavnService.getPlaylist(id, false);
            
            if (p == null) {
                System.out.println("Playlist not found!");
                return;
            }
            
            System.out.println("┌────────────────────────────────────────┐");
            System.out.println("│  PLAYLIST: " + pad(p.getListName(), 25) + "│");
            System.out.println("│  Total Songs: " + pad(p.getSongCount(), 22) + "│");
            System.out.println("└────────────────────────────────────────┘\n");
            
            List<Song> songs = p.getSongs();
            for (int i = 0; i < songs.size(); i++) {
                Song s = songs.get(i);
                System.out.println((i + 1) + ". " + s.getSong());
                if (s.getPrimaryArtists() != null) System.out.println("   Artists: " + s.getPrimaryArtists());
                System.out.println();
            }
            System.out.println("✓ Retrieved " + songs.size() + " songs!");
            
            if (convert) {
                YouTubeClient yt = new YouTubeClient();
                PlaylistConverter converter = new PlaylistConverter(yt);
                String ytPlaylistId = converter.convertToYouTube(p);
                System.out.println("\n✓ YouTube Playlist Created: " + ytPlaylistId);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            sc.close();
        }
    }
    
    private static String pad(String s, int len) {
        return String.format("%-" + len + "s", s == null ? "" : s);
    }
}
