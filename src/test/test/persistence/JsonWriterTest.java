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
class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyJournal() {
        try {
            Journal journal = new Journal();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyJournal.json");
            writer.open();
            writer.write(journal);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyJournal.json");
            journal = reader.read();
            assertEquals(0, journal.getAllEntries().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralJournal() {
        try {
            Journal journal = new Journal();
            journal.addEntry(new Entry("Hello", "Adele", LocalDate.of(2025, 7, 12), Entry.Mood.SAD));
            journal.addEntry(new Entry("good 4 u", "Olivia Rodrigo", LocalDate.of(2024, 9, 24), Entry.Mood.ANGRY));

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralJournal.json");
            writer.open();
            writer.write(journal);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralJournal.json");
            journal = reader.read();
            List<Entry> entries = journal.getAllEntries();
            assertEquals(2, entries.size());
            checkEntry("Hello", "Adele", LocalDate.of(2025, 7, 12), Entry.Mood.SAD, entries.get(0));
            checkEntry("good 4 u", "Olivia Rodrigo", LocalDate.of(2024, 9, 24), Entry.Mood.ANGRY, entries.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterJournalWithEmptyFields() {
        try {
            Journal journal = new Journal();
            journal.addEntry(new Entry("", "", LocalDate.now(), Entry.Mood.CALM));

            JsonWriter writer = new JsonWriter("./data/testWriterJournalWithEmptyFields.json");
            writer.open();
            writer.write(journal);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterJournalWithEmptyFields.json");
            journal = reader.read();
            assertEquals(1, journal.getAllEntries().size());
            checkEntry("", "", LocalDate.now(), Entry.Mood.CALM, journal.getAllEntries().get(0));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterJournalWithDuplicates() {
        try {
            Journal journal = new Journal();
            Entry entry = new Entry("Song", "Artist", LocalDate.of(2025, 1, 1), Entry.Mood.HAPPY);
            journal.addEntry(entry);
            journal.addEntry(entry);

            JsonWriter writer = new JsonWriter("./data/testWriterJournalWithDuplicates.json");
            writer.open();
            writer.write(journal);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterJournalWithDuplicates.json");
            journal = reader.read();
            assertEquals(2, journal.getAllEntries().size());
            checkEntry("Song", "Artist", LocalDate.of(2025, 1, 1), Entry.Mood.HAPPY, journal.getAllEntries().get(0));
            checkEntry("Song", "Artist", LocalDate.of(2025, 1, 1), Entry.Mood.HAPPY, journal.getAllEntries().get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterJournalWithAllMoods() {
        try {
            Journal journal = new Journal();
            for (Entry.Mood mood : Entry.Mood.values()) {
                journal.addEntry(new Entry("Song", "Artist", LocalDate.now(), mood));
            }

            JsonWriter writer = new JsonWriter("./data/testWriterJournalWithAllMoods.json");
            writer.open();
            writer.write(journal);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterJournalWithAllMoods.json");
            journal = reader.read();
            assertEquals(Entry.Mood.values().length, journal.getAllEntries().size());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
