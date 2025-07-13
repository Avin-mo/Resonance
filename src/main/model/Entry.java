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


    // EFFECTS: returns the color name corresponding to the mood
    public String getColor() {
        // stub
    }

    // REQUIRES: songTitle has a non-zero length
    // EFFECTS: creates a journal entry with a date and a song title;
    //          account id is a positive integer not assigned to any other account;
    public Entry(String songTitle, String songArtist, LocalDate date, Mood mood) {
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

}
