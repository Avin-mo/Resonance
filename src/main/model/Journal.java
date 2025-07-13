package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Journal {
    
    private ArrayList<Entry> entries;


    // MODIFIES: this
    // EFFECTS: creates an empty list of enteries
    public Journal() {
        entries = new ArrayList<>(); // stub
    }

    // REQURIES: entry is not null
    // MODIFIES: this
    // EFFECTS: adds the given entry to the journal
    public void addEntry(Entry entry) {
        // stub
    }


    // REQURIES: entry is not null
    // MODIFIES: this
    // EFFECTS: removes the given entry to the journal
    public void removeEntry(Entry entry) {
        // stub
    }


    // MODIFIES: this
    // EFFECTS: updates the specified entry with the new details if it exists
    public void updateEntry(Entry entry) {
        // stub
    }


    // EFFECTS: returns a list of all entries; list may be empty
    public ArrayList<Entry> getAllEntries() {
        return null; // stub
    }


    // REQUIRES: date is not null
    // EFFECTS: returns a list of entries logged on the specified date; list may be empty
    public ArrayList<Entry> getEnteriesByDate(LocalDate date) {
        return null; // stub
    }


    // REQUIRES: mood is not null
    // EFFECTS: returns a list of entries logged on the specified mood; list may be empty
    public ArrayList<Entry> getEnteriesByMood(Entry.Mood mood) {
        return null; // stub
    }


    // REQUIRES: songTitle is not null
    // EFFECTS: returns a list of entries logged on the specified song title; list may be empty
    public ArrayList<Entry> getEnteriesBySongTitle(String songTitle) {
        return null; // stub
    }


    // REQUIRES: songArtist is not null
    // EFFECTS: returns a list of entries logged on the specified song artist; list may be empty
    public ArrayList<Entry> getEnteriesBySongArtist(String songArtist) {
        return null; // stub
    }
}
