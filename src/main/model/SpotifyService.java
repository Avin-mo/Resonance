package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SpotifyService {
    // For testing, we'll use a demo client ID that works with Spotify's demo flow
    private static final String CLIENT_ID = "demo_client_id"; 
    private static final String CLIENT_SECRET = "demo_client_secret";
    
    private boolean isAuthenticated = false;
    
    public SpotifyService() {
        // Initialize without actual Spotify API for now
    }
    
    /**
     * Gets the authorization URL for Spotify login
     * @return The authorization URL as a string
     */
    public String getAuthorizationUrl() {
        // For demo purposes, we'll use a working Spotify demo URL
        return "https://developer.spotify.com/documentation/web-api/tutorials/code-flow";
    }
    
    /**
     * Completes the authorization process using the authorization code
     * @param code The authorization code received from Spotify
     * @return true if authentication was successful, false otherwise
     */
    public boolean completeAuthorization(String code) {
        // For testing purposes, accept any non-empty code
        if (code != null && !code.trim().isEmpty()) {
            isAuthenticated = true;
            return true;
        }
        return false;
    }
    
    /**
     * Fetches the user's recently played tracks (mock data for testing)
     * @param limit The number of tracks to fetch (max 50)
     * @return List of SpotifyTrack objects representing recently played tracks
     */
    public List<SpotifyTrack> getRecentlyPlayedTracks(int limit) {
        if (!isAuthenticated) {
            throw new IllegalStateException("Not authenticated with Spotify");
        }
        
        List<SpotifyTrack> tracks = new ArrayList<>();
        
        // Mock data for testing
        String[] mockSongs = {
            "Blinding Lights", "Shape of You", "Dance Monkey", "Uptown Funk", "Thinking Out Loud",
            "See You Again", "Sugar", "Shake It Off", "All of Me", "Counting Stars"
        };
        
        String[] mockArtists = {
            "The Weeknd", "Ed Sheeran", "Tones and I", "Mark Ronson ft. Bruno Mars", "Ed Sheeran",
            "Wiz Khalifa ft. Charlie Puth", "Maroon 5", "Taylor Swift", "John Legend", "OneRepublic"
        };
        
        String[] mockAlbums = {
            "After Hours", "÷ (Divide)", "The Kids Are Coming", "Uptown Special", "× (Multiply)",
            "Furious 7", "V", "1989", "Love in the Future", "Native"
        };
        
        for (int i = 0; i < Math.min(limit, mockSongs.length); i++) {
            SpotifyTrack track = new SpotifyTrack(
                "mock_id_" + i,
                mockSongs[i],
                mockArtists[i],
                mockAlbums[i],
                LocalDateTime.now().minusHours(i)
            );
            tracks.add(track);
        }
        
        return tracks;
    }
    
    /**
     * Checks if the service is authenticated with Spotify
     * @return true if authenticated, false otherwise
     */
    public boolean isAuthenticated() {
        return isAuthenticated;
    }
    
    /**
     * Sets the access token directly (useful for testing or if you have a stored token)
     * @param accessToken The Spotify access token
     */
    public void setAccessToken(String accessToken) {
        isAuthenticated = true;
    }
} 