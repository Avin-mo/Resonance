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
        this.id = nextEntryId++;
        this.songTitle = songTitle;
        this.songArtist = songArtist;
        this.date = date;
        this.mood = mood;
    }





    // REQUIRES: songTitle has a non-zero length
    // MODIFIES: this
    // EFFECTS: updates the entry with the new songTitle
    public void updateSongTitle(String newsongTitle) {
        this.songTitle = newsongTitle;
    }


    // REQUIRES: songArtist has a non-zero length
    // MODIFIES: this
    // EFFECTS: updates the entry with the new songArtist
    public void updateSongArtist(String songArtist) {
        this.songArtist = songArtist;
    }

    // REQUIRES: date cannot be null
    // MODIFIES: this
    // EFFECTS: updates the entry with the new date
    public void updateDate(LocalDate date) {
        this.date = date;
    }

    // REQUIRES: mood cannot be null
    // MODIFIES: this
    // EFFECTS: updates the entry with the new mood
    public void updateMood(Mood mood) {
        this.mood = mood;
    }




    // EFFECTS: returns entry id
    public int getId() {
        return id;
    }

    // EFFECTS: returns song name of the entry
    public String getSongName() {
        return songTitle;
    }

    // EFFECTS: returns song artist of the entry
    public String getSongArtist() {
        return songArtist;
    }


    // EFFECTS: returns date of the entery
    public LocalDate getDate() {
        return date;
    } 

    // EFFECTS: returns mood of the entry
    public String getMood() {
        return mood.toString();
    }

    // EFFECTS: returns the color name corresponding to the mood
    public String getColor() {
        switch (mood) {
            case HAPPY: return "Yellow";
            case SAD: return "Blue";
            case CALM: return "Green";
            case ANGRY: return "Red";
            case EXCITED: return "Orange";
            default: return "Gray";
        }
    }
}
