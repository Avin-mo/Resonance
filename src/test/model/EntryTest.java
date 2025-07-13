package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Entry.Mood;

import java.time.LocalDate;

public class EntryTest {
    private Entry e;

    @BeforeEach
    public void setup() {
        e = new Entry("Hello", "Adele", LocalDate.of(2025, 7, 12), Mood.SAD);
    }

    @Test
    public void constructorTest() {
        assertEquals(e.getSongName(), "Hello");
        assertEquals(e.getSongArtist(), "Adele");
        assertEquals(e.getDate(), LocalDate.of(2025, 7, 12));
        assertEquals(e.getMood(), Mood.SAD);
        assertTrue(e.getId() > 0);
    }


    @Test
    public void updateSongTitleTest() {
        e.updateSongTitle(e.getId(), "Someone Like You");
        assertEquals("Someone Like You", e.getSongName());
    }

    @Test
    public void updateSongArtistTest() {
        e.updateSongArtist(e.getId(), "Lionel Richie");
        assertEquals("Lionel Richie", e.getSongArtist());
    }

    @Test
    public void updateDateTest() {
        e.updateDate(e.getId(), LocalDate.of(2024, 3, 25));
        assertEquals(LocalDate.of(2024, 3, 25), e.getDate());
    }

    @Test
    public void updateMoodTest() {
        e.updateMood(e.getId(), Mood.CALM);
        assertEquals(Mood.CALM, e.getMood());
    }

}
