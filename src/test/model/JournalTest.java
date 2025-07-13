package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Entry.Mood;

import java.time.LocalDate;

public class JournalTest {
    Journal journal;
    Entry e1;
    Entry e2;
    Entry e3;

    @BeforeEach
    public void setup() {
        journal = new Journal();

        e1 = new Entry("Heather", "Conan Gray", LocalDate.of(2025, 5, 14), Mood.SAD);
        e2 = new Entry("good 4 u", "Olivia Rodrigo", LocalDate.of(2025, 5, 14), Mood.ANGRY);
        e3 = new Entry("Vampire", "Olivia Rodrigo", LocalDate.of(2025, 6, 18), Mood.SAD);
    }

    @Test
    public void addOneEntryTest() {
        journal.addEntry(e1);
        assertEquals(1, journal.getAllEntries().size());
        assertTrue(journal.getAllEntries().contains(e1));
    }

    @Test
    public void addMultipleEntriesTest() {
        journal.addEntry(e1);
        journal.addEntry(e2);
        assertEquals(2, journal.getAllEntries().size());
        assertTrue(journal.getAllEntries().contains(e1));
        assertTrue(journal.getAllEntries().contains(e2));
    }

    @Test
    public void removeEntryTest() {
        journal.addEntry(e1);
        journal.addEntry(e2);
        journal.removeEntry(e1);
        assertEquals(1, journal.getAllEntries().size());
        assertFalse(journal.getAllEntries().contains(e1));
        assertTrue(journal.getAllEntries().contains(e2));
    }

    @Test
    public void removeNonexistentEntryTest() {
        journal.addEntry(e1);
        journal.removeEntry(e2); // e2 was never added
        assertEquals(1, journal.getAllEntries().size());
        assertTrue(journal.getAllEntries().contains(e1));
    }

    @Test
    public void getAllEntriesTest() {
        journal.addEntry(e1);
        journal.addEntry(e2);

        assertTrue(journal.getAllEntries().contains(e1));
        assertTrue(journal.getAllEntries().contains(e2));
    }

    @Test
    public void get1EntryByDateTest() {
        journal.addEntry(e1);
        journal.addEntry(e2);
        journal.addEntry(e3);

        var june18 = journal.getEnteriesByDate(LocalDate.of(2025, 6, 18));
        assertEquals(1, june18.size());
        assertTrue(june18.contains(e3));
    }

    @Test
    public void getMultipleEntriesByDateTest() {
        journal.addEntry(e1);
        journal.addEntry(e2);
        journal.addEntry(e3);

        var may14 = journal.getEnteriesByDate(LocalDate.of(2025, 5, 14));
        assertEquals(2, may14.size());
        assertTrue(may14.contains(e1));
        assertTrue(may14.contains(e2));
    }

    @Test
    public void getNoEntriesByDateTest() {
        journal.addEntry(e1);
        journal.addEntry(e2);
        journal.addEntry(e3);

        var noMatch = journal.getEnteriesByDate(LocalDate.of(2024, 1, 1));
        assertTrue(noMatch.isEmpty());
    }

    @Test
    public void getMultipleEntriesByMoodTest() {
        journal.addEntry(e1);
        journal.addEntry(e2);
        journal.addEntry(e3);

        var sad = journal.getEnteriesByMood(Mood.SAD);
        assertEquals(2, sad.size());
        assertTrue(sad.contains(e1));
        assertTrue(sad.contains(e3));
    }

    @Test
    public void get1EntryByMoodTest() {
        journal.addEntry(e1);
        journal.addEntry(e2);
        journal.addEntry(e3);

        var angry = journal.getEnteriesByMood(Mood.ANGRY);
        assertEquals(1, angry.size());
        assertTrue(angry.contains(e2));
    }

    @Test
    public void getNoEntriesByMoodTest() {
        journal.addEntry(e1);
        journal.addEntry(e2);
        journal.addEntry(e3);

        var calm = journal.getEnteriesByMood(Mood.CALM);
        assertTrue(calm.isEmpty());
    }

    @Test
    public void get1EntryBySongTitleTest() {
        journal.addEntry(e1);
        journal.addEntry(e2);
        journal.addEntry(e3);

        var vampire = journal.getEnteriesBySongTitle("Vampire");
        assertEquals(1, vampire.size());
        assertTrue(vampire.contains(e3));
    }

    @Test
    public void getMultipleEntriesBySongTitleTest() {
        journal.addEntry(e1);
        journal.addEntry(e2);
        journal.addEntry(e3);

        var heather = journal.getEnteriesBySongTitle("Heather");
        assertEquals(1, heather.size());
        assertTrue(heather.contains(e1));
    }

    @Test
    public void getNoEntriesBySongTitleTest() {
        journal.addEntry(e1);
        journal.addEntry(e2);
        journal.addEntry(e3);

        var unknown = journal.getEnteriesBySongTitle("Unknown Song");
        assertTrue(unknown.isEmpty());
    }

    @Test
    public void get1EntryBySongArtistTest() {
        journal.addEntry(e1);
        journal.addEntry(e2);
        journal.addEntry(e3);

        var conan = journal.getEnteriesBySongArtist("Conan Gray");
        assertEquals(1, conan.size());
        assertTrue(conan.contains(e1));
    }

    @Test
    public void getMultipleEntriesBySongArtistTest() {
        journal.addEntry(e1);
        journal.addEntry(e2);
        journal.addEntry(e3);

        var olivia = journal.getEnteriesBySongArtist("Olivia Rodrigo");
        assertEquals(2, olivia.size());
        assertTrue(olivia.contains(e2));
        assertTrue(olivia.contains(e3));
    }

    @Test
    public void getNoEntriesBySongArtistTest() {
        journal.addEntry(e1);
        journal.addEntry(e2);
        journal.addEntry(e3);

        var unknown = journal.getEnteriesBySongArtist("Unknown Artist");
        assertTrue(unknown.isEmpty());
    }

    @Test
    public void updateSongTitleTest() {
        journal.addEntry(e1);
        journal.addEntry(e2);
        journal.addEntry(e3);

        journal.updateSongTitle("Heather", "Never Ending Song");
        assertEquals("Never Ending Song", e1.getSongName());

        // Others remain unchanged
        assertEquals("good 4 u", e2.getSongName());
        assertEquals("Vampire", e3.getSongName());
    }

    @Test
    public void updateSongArtistTest() {
        journal.addEntry(e1);
        journal.addEntry(e2);
        journal.addEntry(e3);

        journal.updateSongArtist("Olivia Rodrigo", "Taylor Swift");

        // Only e2 and e3 updated
        assertEquals("Conan Gray", e1.getSongArtist());
        assertEquals("Taylor Swift", e2.getSongArtist());
        assertEquals("Taylor Swift", e3.getSongArtist());
    }

    @Test
    public void updateDateTest() {
        journal.addEntry(e1);
        journal.addEntry(e2);
        journal.addEntry(e3);

        LocalDate newDate = LocalDate.of(2025, 12, 25);
        journal.updateDate(LocalDate.of(2025, 5, 14), newDate);

        // Only e1 and e2 updated
        assertEquals(newDate, e1.getDate());
        assertEquals(newDate, e2.getDate());
        assertEquals(LocalDate.of(2025, 6, 18), e3.getDate());
    }

    @Test
    public void updateMoodTest() {
        journal.addEntry(e1);
        journal.addEntry(e2);
        journal.addEntry(e3);

        journal.updateMood(Mood.SAD, Mood.HAPPY);

        // e1 and e3 moods updated
        assertEquals(Mood.HAPPY, e1.getMood());
        assertEquals(Mood.ANGRY, e2.getMood());
        assertEquals(Mood.HAPPY, e3.getMood());
    }

    @Test
    public void getEntryByIdTest() {
        journal.addEntry(e1);
        journal.addEntry(e2);
        journal.addEntry(e3);

        Entry e = journal.getEntryById(e1.getId());

        assertEquals(e, e1);
    }
}
