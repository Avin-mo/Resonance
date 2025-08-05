package model;

import java.time.LocalDate;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

// Represents a list of enteries 
public class Journal implements Writable {

    private ArrayList<Entry> entries;

    // MODIFIES: this
    // EFFECTS: creates an empty list of enteries
    public Journal() {
        entries = new ArrayList<>();
    }

    // REQURIES: entry is not null
    // MODIFIES: this
    // EFFECTS: adds the given entry to the journal
    public void addEntry(Entry entry) {
        entries.add(entry);
        EventLog.getInstance().logEvent(new Event(
            "Added entry (ID: " + entry.getId() + ") - \"" +
            entry.getSongName() + "\" by " + entry.getSongArtist() +
            ", Date: " + entry.getDate() + ", Mood: " + entry.getMood()
        ));
    }

    // REQURIES: entry is not null
    // MODIFIES: this
    // EFFECTS: removes the given entry to the journal
    public void removeEntry(Entry entry) {
        entries.remove(entry);
        EventLog.getInstance().logEvent(new Event(
            "Removed entry (ID: " + entry.getId() + ") - \"" +
            entry.getSongName() + "\" by " + entry.getSongArtist()
        ));
    }

    // EFFECTS: returns a list of all entries; list may be empty
    public ArrayList<Entry> getAllEntries() {
        EventLog.getInstance().logEvent(new Event(
            "Viewed all entries (" + entries.size() + " total)"
        ));
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
        EventLog.getInstance().logEvent(new Event(
            "Filtered entries by date: " + date + " (" + result.size() + " match(es))"
        ));
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
        EventLog.getInstance().logEvent(new Event(
            "Filtered entries by mood: " + mood + " (" + result.size() + " match(es))"
        ));
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
        EventLog.getInstance().logEvent(new Event(
            "Filtered entries by song title: \"" + songTitle + "\" (" + result.size() + " match(es))"
        ));
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
        EventLog.getInstance().logEvent(new Event(
            "Filtered entries by artist: \"" + songArtist + "\" (" + result.size() + " match(es))"
        ));
        return result;
    }

    // REQUIRES: id is not null
    // EFFECTS: returns the Entry with the given id, or null if not found
    public Entry getEntryById(int id) {
        for (Entry e : entries) {
            if (e.getId() == id) {
                EventLog.getInstance().logEvent(new Event(
                    "Retrieved entry by ID: " + id + " → \"" + e.getSongName() + "\" by " + e.getSongArtist()
                ));
                return e;
            }
        }
        EventLog.getInstance().logEvent(new Event(
            "Attempted to retrieve entry by ID: " + id + " → Not found"
        ));
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
                EventLog.getInstance().logEvent(new Event(
                    "Updated title for entry (ID: " + e.getId() + ") - from \"" +
                    oldSongTitle + "\" to \"" + newSongTitle + "\""
                ));
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
                EventLog.getInstance().logEvent(new Event(
                    "Updated artist for entry (ID: " + e.getId() + ") - from \"" +
                    oldSongArtist + "\" to \"" + newSongArtist + "\""
                ));
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
                EventLog.getInstance().logEvent(new Event(
                    "Updated date for entry (ID: " + e.getId() + ") - from " +
                    oldDate + " to " + newDate
                ));
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
                EventLog.getInstance().logEvent(new Event(
                    "Updated mood for entry (ID: " + e.getId() + ") - from " +
                    oldMood + " to " + newMood
                ));
            }
        }
    }

    // CITATION: part of this code was insipired by CPSC 210 JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("entries", entriesToJson());
        return json;
    }

    // EFFECTS: returns enteries in the journal as json arrays
    public JSONArray entriesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Entry e : entries) {
            jsonArray.put(e.toJson());
        }

        return jsonArray;
    }

}
