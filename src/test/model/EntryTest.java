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
        e.updateSongTitle("Skyfall");
        assertEquals("Skyfall", e.getSongName());
    }

    @Test
    public void updateSongArtistTest() {
        e.updateSongArtist("Beyoncé");
        assertEquals("Beyoncé", e.getSongArtist());
    }

    @Test
    public void updateDateTest() {
        LocalDate newDate = LocalDate.of(2025, 7, 13);
        e.updateDate(newDate);
        assertEquals(newDate, e.getDate());
    }

    @Test
    public void updateMoodTest() {
        e.updateMood(Mood.HAPPY);
        assertEquals(Mood.HAPPY, e.getMood());
    }

    @Test
    public void getColorTest() {
        // initial mood: SAD
        assertEquals("Blue", e.getColor());

        e.updateMood(Mood.HAPPY);
        assertEquals("Yellow", e.getColor());

        e.updateMood(Mood.CALM);
        assertEquals("Green", e.getColor());

        e.updateMood(Mood.ANGRY);
        assertEquals("Red", e.getColor());

        e.updateMood(Mood.EXCITED);
        assertEquals("Orange", e.getColor());
    }

}
