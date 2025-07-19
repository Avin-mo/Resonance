package test.persistence;

import static org.junit.Assert.assertEquals;
import model.Entry;
import model.Entry.Mood;
import java.time.LocalDate;


// CITATION: parts of this code was taken / inspired from CPSC 210 JsonSerializationDemo
public class JsonTest {
    protected void checkEntry(String songTitle, String songArtist, LocalDate date, Mood mood, Entry entry) {
        assertEquals(songTitle, entry.getSongName());
        assertEquals(songArtist, entry.getSongArtist());
        assertEquals(date, entry.getDate());
        assertEquals(mood, entry.getMood());
    }
}

