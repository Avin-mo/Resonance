package test.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Entry;
import model.Entry.Mood;

import java.time.LocalDate;

public class EntryTest {
    private Entry testEntry;

    @BeforeEach
    public void setup() {
        testEntry = new Entry("Hello", "Adele", LocalDate.of(2025, 7, 12), Mood.SAD);
    }

    @Test
    public void constructorTest() {
        assertEquals(testEntry.getSongName(), "Hello");
        assertEquals(testEntry.getSongArtist(), "Adele");
        assertEquals(testEntry.getDate(), LocalDate.of(2025, 7, 12));
        assertEquals(testEntry.getMood(), Mood.SAD);
        assertTrue(testEntry.getId() > 0);
    }

    @Test
    public void updateSongTitleTest() {
        testEntry.updateSongTitle("Skyfall");
        assertEquals("Skyfall", testEntry.getSongName());
    }

    @Test
    public void updateSongArtistTest() {
        testEntry.updateSongArtist("Beyoncé");
        assertEquals("Beyoncé", testEntry.getSongArtist());
    }

    @Test
    public void updateDateTest() {
        LocalDate newDate = LocalDate.of(2025, 7, 13);
        testEntry.updateDate(newDate);
        assertEquals(newDate, testEntry.getDate());
    }

    @Test
    public void updateMoodTest() {
        testEntry.updateMood(Mood.HAPPY);
        assertEquals(Mood.HAPPY, testEntry.getMood());
    }
}
