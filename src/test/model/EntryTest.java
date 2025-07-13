package model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Entry.Mood;

import java.time.LocalDate;

public class EntryTest {
    Entry e;

    @BeforeEach
    public void setup() {
        p = new Entry("Hello", "Adele", LocalDate.of(2025, 7, 12), Mood.SAD);
    }

    @Test
    void constructorTest() {
        assertEquals(e.getSongName(), "Hello");
        assertEquals(e.getSongArtist(), "Adele");
        assertEquals(e.getDate(), LocalDate.of(2025, 7, 12));
        assertEquals(e.getMood(), Mood.SAD);
        assertTrue(e.getId() > 0);
    }
}
