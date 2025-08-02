package ui;

import model.Entry;
import model.Journal;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

public class JournalGUI extends JFrame {
    private static final String JSON_STORE = "./data/journal.json";
    private Journal journal;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private boolean journalSaved = true;

    private DefaultListModel<String> entryListModel;
    private JList<String> entryJList;

    // EFFECTS: constructs the GUI window, initializes components, loads journal into memory, 
    //          and sets up the window closing behavior.
    public JournalGUI() {
        super("Resonance - Music Mood Journal");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        journal = new Journal();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                handleExit();
            }
        });

        initializePanels();
        setVisible(true);
    }


    // MODIFIES: this
    // EFFECTS: initializes all panels, buttons, and the journal list in the GUI.
    private void initializePanels() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        entryListModel = new DefaultListModel<>();
        entryJList = new JList<>(entryListModel);
        JScrollPane scrollPane = new JScrollPane(entryJList);

        JPanel buttons = new JPanel(new GridLayout(6, 2));
        JButton addBtn = new JButton("Add Entry");
        addBtn.addActionListener(e -> addEntry());

        JButton removeBtn = new JButton("Remove Entry");
        removeBtn.addActionListener(e -> removeEntry());

        JButton updateTitleBtn = new JButton("Update Title");
        updateTitleBtn.addActionListener(e -> updateEntryTitle());

        JButton updateArtistBtn = new JButton("Update Artist");
        updateArtistBtn.addActionListener(e -> updateEntryArtist());

        JButton updateDateBtn = new JButton("Update Date");
        updateDateBtn.addActionListener(e -> updateEntryDate());

        JButton updateMoodBtn = new JButton("Update Mood");
        updateMoodBtn.addActionListener(e -> updateEntryMood());

        JButton viewAllBtn = new JButton("View All");
        viewAllBtn.addActionListener(e -> refreshList());

        JButton viewByTitle = new JButton("Filter by Title");
        viewByTitle.addActionListener(e -> filterByTitle());

        JButton viewByArtist = new JButton("Filter by Artist");
        viewByArtist.addActionListener(e -> filterByArtist());

        JButton viewByDate = new JButton("Filter by Date");
        viewByDate.addActionListener(e -> filterByDate());

        JButton viewByMood = new JButton("Filter by Mood");
        viewByMood.addActionListener(e -> filterByMood());

        JButton saveBtn = new JButton("Save Journal");
        saveBtn.addActionListener(e -> saveJournal());

        JButton loadBtn = new JButton("Load Journal");
        loadBtn.addActionListener(e -> loadJournal());

        buttons.add(addBtn);
        buttons.add(removeBtn);
        buttons.add(updateTitleBtn);
        buttons.add(updateArtistBtn);
        buttons.add(updateDateBtn);
        buttons.add(updateMoodBtn);
        buttons.add(viewAllBtn);
        buttons.add(viewByTitle);
        buttons.add(viewByArtist);
        buttons.add(viewByDate);
        buttons.add(viewByMood);
        buttons.add(saveBtn);
        buttons.add(loadBtn);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttons, BorderLayout.SOUTH);

        add(panel);
    }


    // MODIFIES: this
    // EFFECTS: if journal is unsaved, prompts the user to optionally save changes;
    //          exits the application unless cancelled.
    private void handleExit() {
        if (!journalSaved) {
            int option = JOptionPane.showConfirmDialog(this,
                    "You have unsaved changes. Would you like to save before exiting?",
                    "Unsaved Changes",
                    JOptionPane.YES_NO_CANCEL_OPTION);

            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.YES_OPTION) {
                saveJournal();
            }
        }
        dispose();
    }


    // REQUIRES: valid user input through dialogs for title, artist, date, and mood
    // MODIFIES: this, journal
    // EFFECTS: adds a new entry to the journal and updates the display; shows error if input is invalid
    private void addEntry() {
        String title = JOptionPane.showInputDialog(this, "Enter song title:");
        String artist = JOptionPane.showInputDialog(this, "Enter artist:");
        String dateStr = JOptionPane.showInputDialog(this, "Enter date (YYYY-MM-DD):");
        String moodStr = JOptionPane.showInputDialog(this, "Enter mood (HAPPY, SAD, CALM, ANGRY, EXCITED):");

        try {
            LocalDate date = LocalDate.parse(dateStr);
            Entry.Mood mood = Entry.Mood.valueOf(moodStr.toUpperCase());
            Entry entry = new Entry(title, artist, date, mood);
            journal.addEntry(entry);
            journalSaved = false;
            refreshList();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Entry not added.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    // REQUIRES: a journal entry to be selected
    // MODIFIES: this, journal
    // EFFECTS: removes the selected entry from the journal and updates the display; 
    //           shows warning if no entry is selected
    private void removeEntry() {
        int index = entryJList.getSelectedIndex();
        if (index != -1) {
            Entry toRemove = journal.getAllEntries().get(index);
            journal.removeEntry(toRemove);
            journalSaved = false;
            refreshList();
        } else {
            JOptionPane.showMessageDialog(this, "Please select an entry to remove.", "No selection", JOptionPane.WARNING_MESSAGE);
        }
    }



    // REQUIRES: a journal entry to be selected and a valid new title input
    // MODIFIES: this, journal
    // EFFECTS: updates the title of the selected entry; shows warning if no entry is selected
    private void updateEntryTitle() {
        int index = entryJList.getSelectedIndex();
        if (index != -1) {
            Entry entry = journal.getAllEntries().get(index);
            String newTitle = JOptionPane.showInputDialog(this, "Enter new title:", entry.getSongName());
            if (newTitle != null && !newTitle.isBlank()) {
                entry.updateSongTitle(newTitle);
                journalSaved = false;
                refreshList();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an entry to update.", "No selection", JOptionPane.WARNING_MESSAGE);
        }
    }


    // REQUIRES: a journal entry to be selected and a valid new artist input
    // MODIFIES: this, journal
    // EFFECTS: updates the artist of the selected entry; shows warning if no entry is selected
    private void updateEntryArtist() {
        int index = entryJList.getSelectedIndex();
        if (index != -1) {
            Entry entry = journal.getAllEntries().get(index);
            String newArtist = JOptionPane.showInputDialog(this, "Enter new artist:", entry.getSongArtist());
            if (newArtist != null && !newArtist.isBlank()) {
                entry.updateSongArtist(newArtist);
                journalSaved = false;
                refreshList();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an entry to update.", "No selection", JOptionPane.WARNING_MESSAGE);
        }
    }


    // REQUIRES: a journal entry to be selected and a valid new date input
    // MODIFIES: this, journal
    // EFFECTS: updates the date of the selected entry; shows warning if no entry is selected
    private void updateEntryDate() {
        int index = entryJList.getSelectedIndex();
        if (index != -1) {
            Entry entry = journal.getAllEntries().get(index);
            String newDateStr = JOptionPane.showInputDialog(this, "Enter new date (YYYY-MM-DD):", entry.getDate().toString());
            try {
                LocalDate newDate = LocalDate.parse(newDateStr);
                entry.updateDate(newDate);
                journalSaved = false;
                refreshList();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid date format.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an entry to update.", "No selection", JOptionPane.WARNING_MESSAGE);
        }
    }


    // REQUIRES: a journal entry to be selected and a valid new mood input
    // MODIFIES: this, journal
    // EFFECTS: updates the mood of the selected entry; shows warning if no entry is selected
    private void updateEntryMood() {
        int index = entryJList.getSelectedIndex();
        if (index != -1) {
            Entry entry = journal.getAllEntries().get(index);
            String newMoodStr = JOptionPane.showInputDialog(this, "Enter new mood (HAPPY, SAD, CALM, ANGRY, EXCITED):", entry.getMood().toString());
            try {
                Entry.Mood newMood = Entry.Mood.valueOf(newMoodStr.toUpperCase());
                entry.updateMood(newMood);
                journalSaved = false;
                refreshList();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid mood.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an entry to update.", "No selection", JOptionPane.WARNING_MESSAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS: filters and displays entries with matching song title
    private void filterByTitle() {
        String title = JOptionPane.showInputDialog(this, "Enter song title to filter by:");
        if (title != null) {
            displayFilteredEntries(journal.getEnteriesBySongTitle(title));
        }
    }


    // MODIFIES: this
    // EFFECTS: filters and displays entries with matching song artist
    private void filterByArtist() {
        String artist = JOptionPane.showInputDialog(this, "Enter artist to filter by:");
        if (artist != null) {
            displayFilteredEntries(journal.getEnteriesBySongArtist(artist));
        }
    }


    // MODIFIES: this
    // EFFECTS: filters and displays entries with matching date
    private void filterByDate() {
        String dateStr = JOptionPane.showInputDialog(this, "Enter date to filter by (YYYY-MM-DD):");
        try {
            LocalDate date = LocalDate.parse(dateStr);
            displayFilteredEntries(journal.getEnteriesByDate(date));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid date.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS: filters and displays entries with matching mood
    private void filterByMood() {
        String moodStr = JOptionPane.showInputDialog(this, "Enter mood to filter by (HAPPY, SAD, CALM, ANGRY, EXCITED):");
        try {
            Entry.Mood mood = Entry.Mood.valueOf(moodStr.toUpperCase());
            displayFilteredEntries(journal.getEnteriesByMood(mood));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid mood.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    // REQUIRES: entries is not null
    // MODIFIES: this
    // EFFECTS: displays the provided entries in the GUI list
    private void displayFilteredEntries(java.util.List<Entry> entries) {
        entryListModel.clear();
        for (Entry e : entries) {
            entryListModel.addElement(formatEntry(e));
        }
    }


    // MODIFIES: this, file system
    // EFFECTS: saves journal to file specified by JSON_STORE; shows error if file is not found
    private void saveJournal() {
        try {
            jsonWriter.open();
            jsonWriter.write(journal);
            jsonWriter.close();
            journalSaved = true;
            JOptionPane.showMessageDialog(this, "Journal saved successfully.");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Unable to write to file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    // MODIFIES: this, journal
    // EFFECTS: loads journal from file specified by JSON_STORE; shows error if file is not readable
    private void loadJournal() {
        try {
            journal = jsonReader.read();
            journalSaved = true;
            refreshList();
            JOptionPane.showMessageDialog(this, "Journal loaded successfully.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Unable to read from file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    // MODIFIES: this
    // EFFECTS: updates the displayed entry list with all current entries from the journal
    private void refreshList() {
        entryListModel.clear();
        for (Entry e : journal.getAllEntries()) {
            entryListModel.addElement(formatEntry(e));
        }
    }

    // REQUIRES: e is not null
    // EFFECTS: returns a formatted string version of the entry
    private String formatEntry(Entry e) {
        return "ID: " + e.getId()
                + " | \"" + e.getSongName() + "\""
                + " by " + e.getSongArtist()
                + " on " + e.getDate()
                + " | Mood: " + e.getMood();
    }

    // EFFECTS: Main method to launch the GUI application.
    public static void main(String[] args) {
        new JournalGUI();
    }
}
