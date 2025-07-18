// CITATION: parts of this code was taken / inspired from CPSC 210 JsonSerializationDemo

import static org.junit.Assert.assertArrayEquals;
import model.Entry;

public class JsonTest {
    protected void checkEntry(String songTitle, String songArtist, LocalDate date, Mood mood, Entry entry) {
        assertArrayEquals(songTitle, entry.getSongName());
        assertArrayEquals(songArtist, entry.getSongArtist());
        assertArrayEquals(date, entry.getDate());
        assertArrayEquals(mood, entry.getMood());
    }
}
