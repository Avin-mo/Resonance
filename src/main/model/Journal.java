package model;

import java.time.LocalDate;
import java.util.ArrayList;

// Represents a list of enteries 
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
        entries.add(entry);
    }

    // REQURIES: entry is not null
    // MODIFIES: this
    // EFFECTS: removes the given entry to the journal
    public void removeEntry(Entry entry) {
        entries.remove(entry);
    }

    // EFFECTS: returns a list of all entries; list may be empty
    public ArrayList<Entry> getAllEntries() {
        return new ArrayList<>(entries);
    }

    // REQUIRES: date is not null
    // EFFECTS: returns a list of entries logged on the specified date; list may be
    // empty
    public ArrayList<Entry> getEnteriesByDate(LocalDate date) {
        ArrayList<Entry> result = new ArrayList<>();
        for (Entry e : entries) {
            if (e.getDate().equals(date)) {
                result.add(e);
            }
        }
        return result;
    }

    // REQUIRES: mood is not null
    // EFFECTS: returns a list of entries logged on the specified mood; list may be
    // empty
    public ArrayList<Entry> getEnteriesByMood(Entry.Mood mood) {
        ArrayList<Entry> result = new ArrayList<>();
        for (Entry e : entries) {
            if (e.getMood() == mood) {
                result.add(e);
            }
        }
        return result;
    }

    // REQUIRES: songTitle is not null
    // EFFECTS: returns a list of entries logged on the specified song title; list
    // may be empty
    public ArrayList<Entry> getEnteriesBySongTitle(String songTitle) {
        ArrayList<Entry> result = new ArrayList<>();
        for (Entry e : entries) {
            if (e.getSongName().equalsIgnoreCase(songTitle)) {
                result.add(e);
            }
        }
        return result;
    }

    // REQUIRES: songArtist is not null
    // EFFECTS: returns a list of entries logged on the specified song artist; list
    // may be empty
    public ArrayList<Entry> getEnteriesBySongArtist(String songArtist) {
        ArrayList<Entry> result = new ArrayList<>();
        for (Entry e : entries) {
            if (e.getSongArtist().equalsIgnoreCase(songArtist)) {
                result.add(e);
            }
        }
        return result;
    }


    // REQUIRES: id is not null
    // EFFECTS: returns the Entry with the given id, or null if not found
    public Entry getEntryById(int id) {
        for (Entry e : entries) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    // MODIFIES: this
    // EFFECTS: if any entry in the journal has a song title equal to oldSongTitle
    // (case-insensitive),
    // updates its song title to newSongTitle
    public void updateSongTitle(String oldSongTitle, String newSongTitle) {
        for (Entry e : entries) {
            if (e.getSongName().equalsIgnoreCase(oldSongTitle)) {
                e.updateSongTitle(newSongTitle);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: if any entry in the journal has a song artist equal to oldSongArtist
    // (case-insensitive),
    // updates its artist to newSongArtist
    public void updateSongArtist(String oldSongArtist, String newSongArtist) {
        for (Entry e : entries) {
            if (e.getSongArtist().equalsIgnoreCase(oldSongArtist)) {
                e.updateSongArtist(newSongArtist);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: if any entry in the journal has a date equal to oldDate
    // (case-insensitive),
    // updates its artist to newDate
    public void updateDate(LocalDate oldDate, LocalDate newDate) {
        for (Entry e : entries) {
            if (e.getDate().equals(oldDate)) {
                e.updateDate(newDate);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: if any entry in the journal has a mood equal to oldMood
    // (case-insensitive),
    // updates its artist to newMood
    public void updateMood(Entry.Mood oldMood, Entry.Mood newMood) {
        for (Entry e : entries) {
            if (e.getMood() == oldMood) {
                e.updateMood(newMood);
            }
        }
    }

}
