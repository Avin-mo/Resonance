package model;

import java.time.LocalDateTime;

/**
 * Represents a track from Spotify with metadata
 */
public class SpotifyTrack {
    private String id;
    private String name;
    private String artist;
    private String album;
    private LocalDateTime playedAt;
    
    public SpotifyTrack(String id, String name, String artist, String album, LocalDateTime playedAt) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.playedAt = playedAt;
    }
    
    // Getters
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getArtist() {
        return artist;
    }
    
    public String getAlbum() {
        return album;
    }
    
    public LocalDateTime getPlayedAt() {
        return playedAt;
    }
    
    @Override
    public String toString() {
        return "\"" + name + "\" by " + artist + " (" + album + ")";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        SpotifyTrack that = (SpotifyTrack) obj;
        return id.equals(that.id);
    }
    
    @Override
    public int hashCode() {
        return id.hashCode();
    }
} 