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

    private void initializePanels() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        entryListModel = new DefaultListModel<>();
        entryJList = new JList<>(entryListModel);
        JScrollPane scrollPane = new JScrollPane(entryJList);

        JPanel buttons = new JPanel(new GridLayout(5, 2));
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

        JButton viewBtn = new JButton("View Entries");
        viewBtn.addActionListener(e -> refreshList());

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
        buttons.add(viewBtn);
        buttons.add(saveBtn);
        buttons.add(loadBtn);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttons, BorderLayout.SOUTH);

        add(panel);
    }

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

    private void updateEntryTitle() {
        // stub
    }

    private void updateEntryArtist() {
        // stub
    }

    private void updateEntryDate() {
        // stub
    }

    private void updateEntryMood() {
        // stub
    }

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

    private void refreshList() {
        entryListModel.clear();
        for (Entry e : journal.getAllEntries()) {
            entryListModel.addElement(formatEntry(e));
        }
    }

    private String formatEntry(Entry e) {
        return "ID: " + e.getId()
                + " | \"" + e.getSongName() + "\""
                + " by " + e.getSongArtist()
                + " on " + e.getDate()
                + " | Mood: " + e.getMood();
    }

    public static void main(String[] args) {
        new JournalGUI();
    }
}
