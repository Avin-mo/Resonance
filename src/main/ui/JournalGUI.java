package ui;


import model.Entry;
import model.Journal;
import model.SpotifyService;
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
    private SpotifyService spotifyService;
    private boolean journalSaved = true;

    private DefaultListModel<String> entryListModel;
    private JList<String> entryJList;

    // EFFECTS: constructs the GUI window, initializes components, loads journal
    // into memory,
    // and sets up the window closing behavior.
    public JournalGUI() {
        super("Resonance - Music Diary (Demo Mode)");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        journal = new Journal();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        spotifyService = new SpotifyService();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                handleExit();
            }
        });

        initializePanels();
        setVisible(true);
    }

    // REQUIRES: label is not null or empty, action is not null
    // EFFECTS: creates a JButton with the given label and associates it with the
    // provided action.
    // When the button is clicked, the action is executed.
    private JButton createButton(String label, Runnable action) {
        JButton button = new JButton(label);
        button.addActionListener(e -> action.run());
        return button;
    }

    // MODIFIES: this
    // EFFECTS: initializes the main panel layout, creates the scrollable journal
    // list,
    // and adds the button panel to the frame.
    private void initializePanels() {
        JPanel panel = new JPanel(new BorderLayout());

        entryListModel = new DefaultListModel<>();
        entryJList = new JList<>(entryListModel);
        JScrollPane scrollPane = new JScrollPane(entryJList);

        JPanel buttons = createButtonPanel();

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttons, BorderLayout.SOUTH);

        add(panel);
    }

    // MODIFIES: this
    // EFFECTS: creates and returns a panel containing all functional buttons for
    // the GUI.
    // Each button is labeled and connected to its corresponding action.
    private JPanel createButtonPanel() {
        JPanel buttons = new JPanel(new GridLayout(4, 2));
        buttons.add(createButton("Connect Spotify", this::connectSpotify));
        buttons.add(createButton("Import Listening History", this::importSpotifyTracks));
        buttons.add(createButton("Add Notes", this::addNotes));
        buttons.add(createButton("Remove Entry", this::removeEntry));
        buttons.add(createButton("Filter by Date", this::filterByDate));
        buttons.add(createButton("Filter by Artist", this::filterByArtist));
        buttons.add(createButton("Save Journal", this::saveJournal));
        buttons.add(createButton("Load Journal", this::loadJournal));
        return buttons;
    }

    // MODIFIES: this
    // EFFECTS: if journal is unsaved, prompts the user to optionally save changes;
    // exits the application unless cancelled.
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
        printEventLog();
        dispose();
    }

    // REQUIRES: EventLog has been populated with events during the program's
    // execution
    // MODIFIES: none
    // EFFECTS: prints all events in the EventLog to the console in the order they
    // were logged
    private void printEventLog() {
        for (model.Event e : model.EventLog.getInstance()) {
            System.out.println(e.getDate() + " → " + e.getDescription());
        }
    }    



    // REQUIRES: a journal entry to be selected
    // MODIFIES: this, journal
    // EFFECTS: removes the selected entry from the journal and updates the display;
    // shows warning if no entry is selected
    private void removeEntry() {
        int index = entryJList.getSelectedIndex();
        if (index != -1) {
            Entry toRemove = journal.getAllEntries().get(index);
            journal.removeEntry(toRemove);
            journalSaved = false;
            refreshList();
        } else {
            JOptionPane.showMessageDialog(this, "Please select an entry to remove.", "No selection",
                    JOptionPane.WARNING_MESSAGE);
        }
    }


    
    // REQUIRES: a journal entry to be selected
    // MODIFIES: this, journal
    // EFFECTS: adds or updates notes for the selected entry; shows warning if no entry is selected
    private void addNotes() {
        int index = entryJList.getSelectedIndex();
        if (index != -1) {
            Entry entry = journal.getAllEntries().get(index);
            String currentNotes = entry.getNotes();
            String newNotes = JOptionPane.showInputDialog(this, "Enter notes for this song:", currentNotes);
            if (newNotes != null) {
                entry.updateNotes(newNotes);
                journalSaved = false;
                refreshList();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an entry to add notes to.", "No selection",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
    
    // MODIFIES: this
    // EFFECTS: opens Spotify authentication dialog
    private void connectSpotify() {
        SpotifyAuthDialog authDialog = new SpotifyAuthDialog(this, spotifyService);
        authDialog.setVisible(true);
        
        if (authDialog.isAuthSuccessful()) {
            JOptionPane.showMessageDialog(this, 
                "Successfully connected to Spotify (Demo Mode)! You can now import mock listening history.", 
                "Spotify Connected", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    // MODIFIES: this
    // EFFECTS: opens Spotify import dialog to import tracks from Spotify
    private void importSpotifyTracks() {
        if (!spotifyService.isAuthenticated()) {
            JOptionPane.showMessageDialog(this, 
                "Please connect to Spotify first using the 'Connect Spotify' button.", 
                "Not Connected", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        SpotifyImportDialog importDialog = new SpotifyImportDialog(this, spotifyService, journal);
        importDialog.setVisible(true);
        journalSaved = false;
        refreshList();
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
    // EFFECTS: saves journal to file specified by JSON_STORE; shows error if file
    // is not found
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
    // EFFECTS: loads journal from file specified by JSON_STORE; shows error if file
    // is not readable
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
    // EFFECTS: updates the displayed entry list with all current entries from the
    // journal
    private void refreshList() {
        entryListModel.clear();
        for (Entry e : journal.getAllEntries()) {
            entryListModel.addElement(formatEntry(e));
        }
    }

    // REQUIRES: e is not null
    // EFFECTS: returns a formatted string version of the entry
    private String formatEntry(Entry e) {
        String base = e.getDate() + " | \"" + e.getSongName() + "\" by " + e.getSongArtist();
        
        if (!e.getNotes().isEmpty()) {
            base += " | 📝 " + e.getNotes();
        }
        
        return base;
    }

    // EFFECTS: Main method to launch the GUI application.
    public static void main(String[] args) {
        new JournalGUI();
    }
}
