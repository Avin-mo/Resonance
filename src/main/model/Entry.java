package model;

import java.time.LocalDate;


// Represents a single journal entry with a song title, song artist, date, mood, and id
public class Entry {
    private static int nextEntryId = 1;   // tracks id of next account created
    private int id;                       // entry id
    private String songTitle;             // entry name or the song title
    private String songArtist;            // song's artist
    private LocalDate date;               // date entry
    private Mood mood;                    // mood entry


    // Represents possible moods
    public enum Mood {
        HAPPY, 
        SAD, 
        CALM, 
        ANGRY, 
        EXCITED
    }


    // REQUIRES: songTitle has a non-zero length
    // EFFECTS: creates a journal entry with a date and a song title;
    //          account id is a positive integer not assigned to any other account;
    public Entry(String songTitle, String songArtist, LocalDate date, Mood mood) {
        // stub
    }





    // REQUIRES: songTitle has a non-zero length
    // MODIFIES: this
    // EFFECTS: updates the entry with the new songTitle
    public void updateSongTitle(int id, String songTitle) {
        // stub
    }


    // REQUIRES: songArtist has a non-zero length
    // MODIFIES: this
    // EFFECTS: updates the entry with the new songArtist
    public void updateSongArtist(int id, String songArtist) {
        // stub
    }

    // REQUIRES: date cannot be null
    // MODIFIES: this
    // EFFECTS: updates the entry with the new date
    public void updateDate(int id, LocalDate date) {
        // stub
    }

    // REQUIRES: mood cannot be null
    // MODIFIES: this
    // EFFECTS: updates the entry with the new mood
    public void updateMood(int id, Mood mood) {
        // stub
    }




    // EFFECTS: returns entry id
    public int getId() {
        return 0; // stub
    }

    // EFFECTS: returns song name of the entry
    public String getSongName() {
        return null; // stub
    }

    // EFFECTS: returns song artist of the entry
    public String getSongArtist() {
        return null; // stub
    }


    // EFFECTS: returns date of the entery
    public LocalDate getDate() {
        return null; // stub
    } 

    // EFFECTS: returns mood of the entry
    public String getMood() {
        return null; // stub
    }

    // EFFECTS: returns the color name corresponding to the mood
    public String getColor() {
        return null; // stub
    }

}
