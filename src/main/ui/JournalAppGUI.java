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

public class JournalAppGUI extends JFrame {
    private static final String JSON_STORE = "./data/journal.json";

    private Journal journal;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private JTextField titleField;
    private JTextField artistField;
    private JComboBox<String> moodBox;
    private JTextField dateField;
    private JTextArea displayArea;

    private boolean journalSaved = true;

    public JournalAppGUI() {
        super("Resonance – Music Mood Journal");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(600, 500);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        journal = new Journal();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        initUI();
        initWindowListener();
        setVisible(true);
    }

    private void initUI() {
        JPanel inputPanel = new JPanel(new GridLayout(5, 2));

        inputPanel.add(new JLabel("Song Title:"));
        titleField = new JTextField();
        inputPanel.add(titleField);

        inputPanel.add(new JLabel("Artist:"));
        artistField = new JTextField();
        inputPanel.add(artistField);

        inputPanel.add(new JLabel("Mood:"));
        moodBox = new JComboBox<>(new String[]{"HAPPY", "SAD", "CALM", "ANGRY", "EXCITED"});
        inputPanel.add(moodBox);

        inputPanel.add(new JLabel("Date (YYYY-MM-DD):"));
        dateField = new JTextField();
        inputPanel.add(dateField);

        JButton addButton = new JButton("Add Entry");
        addButton.addActionListener(e -> addEntry());
        inputPanel.add(addButton);

        add(inputPanel, BorderLayout.NORTH);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();

        JButton saveButton = new JButton("Save Journal");
        saveButton.addActionListener(e -> saveJournal());
        controlPanel.add(saveButton);

        JButton loadButton = new JButton("Load Journal");
        loadButton.addActionListener(e -> loadJournal());
        controlPanel.add(loadButton);

        JButton removeButton = new JButton("Remove All Entries");
        removeButton.addActionListener(e -> removeAllEntries());
        controlPanel.add(removeButton);

        add(controlPanel, BorderLayout.SOUTH);
    }

    private void initWindowListener() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (!journalSaved) {
                    int option = JOptionPane.showConfirmDialog(
                            JournalAppGUI.this,
                            "You have unsaved changes. Do you want to save your journal before exiting?",
                            "Unsaved Changes",
                            JOptionPane.YES_NO_CANCEL_OPTION
                    );

                    if (option == JOptionPane.YES_OPTION) {
                        saveJournal();
                        dispose();
                    } else if (option == JOptionPane.NO_OPTION) {
                        dispose();
                    } // else CANCEL -> do nothing
                } else {
                    dispose();
                }
            }
        });
    }

    private void addEntry() {
        String title = titleField.getText();
        String artist = artistField.getText();
        String moodStr = (String) moodBox.getSelectedItem();
        String dateStr = dateField.getText();

        try {
            LocalDate date = LocalDate.parse(dateStr);
            Entry.Mood mood = Entry.Mood.valueOf(moodStr);
            Entry entry = new Entry(title, artist, date, mood);
            journal.addEntry(entry);
            displayArea.append(formatEntry(entry) + "\n");
            journalSaved = false;
            clearFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please check the date format and mood.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeAllEntries() {
        journal.getAllEntries().clear();
        displayArea.setText("");
        journalSaved = false;
    }

    private void saveJournal() {
        try {
            jsonWriter.open();
            jsonWriter.write(journal);
            jsonWriter.close();
            journalSaved = true;
            JOptionPane.showMessageDialog(this, "Journal saved successfully!", "Saved", JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Unable to write to file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadJournal() {
        try {
            journal = jsonReader.read();
            displayArea.setText("");
            for (Entry e : journal.getAllEntries()) {
                displayArea.append(formatEntry(e) + "\n");
            }
            journalSaved = true;
            JOptionPane.showMessageDialog(this, "Journal loaded successfully!", "Loaded", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Unable to read from file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        titleField.setText("");
        artistField.setText("");
        dateField.setText("");
        moodBox.setSelectedIndex(0);
    }

    private String formatEntry(Entry e) {
        return "[ID: " + e.getId() + "] "
                + e.getSongName() + " by " + e.getSongArtist()
                + " | " + e.getDate() + " | Mood: " + e.getMood();
    }

    public static void main(String[] args) {
        new JournalAppGUI();
    }
}
