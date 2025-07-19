package persistence;

import model.Entry;
import model.Journal;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads journal from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        // stub
    }

    // EFFECTS: reads journal from file and returns it;
    // throws IOException if an error occurs reading data from file
    public WorkRoom read() throws IOException {
        // stub
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        // stub
    }

    // EFFECTS: parses journal from JSON object and returns it
    private Journal parseJournal(JSONObject jsonObject) {
        // stub
    }

    // MODIFIES: journal
    // EFFECTS: parses entries from JSON object and adds them to journal
    private void addEntries(Journal journal, JSONObject jsonObject) {
        // stub
    }

    // MODIFIES: journal
    // EFFECTS: parses a single entry from JSON object and adds it to journal
    private void addEntry(Journal journal, JSONObject jsonObject) {
        // stub
    }
}
