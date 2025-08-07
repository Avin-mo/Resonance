package ui;

import model.SpotifyService;
import model.SpotifyTrack;
import model.Entry;
import model.Journal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class SpotifyImportDialog extends JDialog {
    private SpotifyService spotifyService;
    private Journal journal;
    private JList<SpotifyTrack> trackList;
    private DefaultListModel<SpotifyTrack> trackListModel;
    private JTextArea notesArea;

    private JSpinner limitSpinner;
    private JButton importButton;
    private JButton refreshButton;
    private JLabel statusLabel;
    
    public SpotifyImportDialog(JFrame parent, SpotifyService spotifyService, Journal journal) {
        super(parent, "Import Your Listening History", true);
        this.spotifyService = spotifyService;
        this.journal = journal;
        
        setSize(700, 500);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        initializeComponents();
        layoutComponents();
    }
    
    private void initializeComponents() {
        trackListModel = new DefaultListModel<>();
        trackList = new JList<>(trackListModel);
        trackList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        trackList.setCellRenderer(new SpotifyTrackListCellRenderer());
        
        notesArea = new JTextArea(5, 30);
        notesArea.setLineWrap(true);
        notesArea.setWrapStyleWord(true);
        
        limitSpinner = new JSpinner(new SpinnerNumberModel(20, 1, 50, 1));
        
        importButton = new JButton("Add to Diary");
        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                importSelectedTrack();
            }
        });
        
        refreshButton = new JButton("Refresh Tracks");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTracks();
            }
        });
        
        statusLabel = new JLabel("Click 'Refresh Tracks' to load your recently played songs");
        statusLabel.setForeground(Color.BLUE);
    }
    
    private void layoutComponents() {
        setLayout(new BorderLayout());
        
        // Top panel with controls
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Number of tracks to load:"));
        topPanel.add(limitSpinner);
        topPanel.add(refreshButton);
        
        // Center panel with track list and details
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        
        // Left side - track list
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("Recently Played Tracks"));
        leftPanel.add(new JScrollPane(trackList), BorderLayout.CENTER);
        
        // Right side - track details and import
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("Add to Your Music Diary"));
        
        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.add(new JLabel("Add your thoughts about this song:"), BorderLayout.NORTH);
        detailsPanel.add(new JScrollPane(notesArea), BorderLayout.CENTER);
        
        rightPanel.add(detailsPanel, BorderLayout.CENTER);
        rightPanel.add(importButton, BorderLayout.SOUTH);
        
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);
        splitPane.setDividerLocation(400);
        
        // Bottom panel with status
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(statusLabel, BorderLayout.CENTER);
        
        add(topPanel, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void refreshTracks() {
        try {
            int limit = (Integer) limitSpinner.getValue();
            List<SpotifyTrack> tracks = spotifyService.getRecentlyPlayedTracks(limit);
            
            trackListModel.clear();
            for (SpotifyTrack track : tracks) {
                trackListModel.addElement(track);
            }
            
            statusLabel.setText("Loaded " + tracks.size() + " tracks from Spotify");
            statusLabel.setForeground(Color.GREEN);
            
        } catch (Exception e) {
            statusLabel.setText("Error loading tracks: " + e.getMessage());
            statusLabel.setForeground(Color.RED);
        }
    }
    
    private void importSelectedTrack() {
        SpotifyTrack selectedTrack = trackList.getSelectedValue();
        if (selectedTrack == null) {
            JOptionPane.showMessageDialog(this, 
                "Please select a track to import", 
                "No Track Selected", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String notes = notesArea.getText().trim();
        
        // Convert played date to LocalDate
        LocalDate playedDate = selectedTrack.getPlayedAt().toLocalDate();
        
        // Create new entry with default mood (HAPPY)
        Entry entry = new Entry(
            selectedTrack.getName(),
            selectedTrack.getArtist(),
            playedDate,
            Entry.Mood.HAPPY,
            selectedTrack.getId()
        );
        
        if (!notes.isEmpty()) {
            entry.updateNotes(notes);
        }
        
        journal.addEntry(entry);
        
        statusLabel.setText("Added \"" + selectedTrack.getName() + "\" to your diary");
        statusLabel.setForeground(Color.GREEN);
        
        // Clear the notes area
        notesArea.setText("");
        
        JOptionPane.showMessageDialog(this, 
            "Successfully added to your music diary!", 
            "Added to Diary", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    // Custom cell renderer for Spotify tracks
    private static class SpotifyTrackListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, 
                                                     int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            
            if (value instanceof SpotifyTrack) {
                SpotifyTrack track = (SpotifyTrack) value;
                setText("<html><b>" + track.getName() + "</b><br>" +
                       "by " + track.getArtist() + "<br>" +
                       "<i>" + track.getAlbum() + "</i><br>" +
                       "Played: " + track.getPlayedAt().toLocalDate() + "</html>");
            }
            
            return this;
        }
    }
} 