package test.persistence;

import model.Entry;
import model.Journal;
import persistence.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// CITATION: parts of this code was taken / inspired from CPSC 210 JsonSerializationDemo
class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyJournal() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyJournal.json");
        try {
            Journal journal = reader.read();
            assertEquals(0, journal.getAllEntries().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralJournal() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralJournal.json");
        try {
            Journal journal = reader.read();
            List<Entry> entries = journal.getAllEntries();
            assertEquals(2, entries.size());
            checkEntry("Hello", "Adele", LocalDate.of(2025, 7, 12), Entry.Mood.SAD, entries.get(0));
            checkEntry("good 4 u", "Olivia Rodrigo", LocalDate.of(2024, 9, 24), Entry.Mood.ANGRY, entries.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
