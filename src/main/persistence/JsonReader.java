package persistence;

import model.Entry;
import model.Journal;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.stream.Stream;

import org.json.*;

// CITATION: parts of this code was taken / inspired from CPSC 210 JsonSerializationDemo
// Represents a reader that reads journal from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads journal from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Journal read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseJournal(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses journal from JSON object and returns it
    private Journal parseJournal(JSONObject jsonObject) {
        Journal journal = new Journal();
        addEntries(journal, jsonObject);
        return journal;
    }

    // MODIFIES: journal
    // EFFECTS: parses entries from JSON object and adds them to journal
    private void addEntries(Journal journal, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("entries");
        for (Object json : jsonArray) {
            JSONObject nextEntry = (JSONObject) json;
            addEntry(journal, nextEntry);
        }
    }

    // MODIFIES: journal
    // EFFECTS: parses a single entry from JSON object and adds it to journal
    private void addEntry(Journal journal, JSONObject jsonObject) {
        String songTitle = jsonObject.getString("songTitle");
        String songArtist = jsonObject.getString("songArtist");
        LocalDate date = LocalDate.parse(jsonObject.getString("date"));
        Entry.Mood mood = Entry.Mood.valueOf(jsonObject.getString("mood"));

        Entry entry = new Entry(songTitle, songArtist, date, mood);
        journal.addEntry(entry);
    }
}
