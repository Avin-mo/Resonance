package ui;

import model.SpotifyService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class SpotifyAuthDialog extends JDialog {
    private SpotifyService spotifyService;
    private JTextArea authUrlArea;
    private JTextField codeField;
    private JButton openBrowserButton;
    private JButton completeAuthButton;
    private JLabel statusLabel;
    private boolean authSuccessful = false;
    
    public SpotifyAuthDialog(JFrame parent, SpotifyService spotifyService) {
        super(parent, "Spotify Authentication", true);
        this.spotifyService = spotifyService;
        
        setSize(500, 300);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        initializeComponents();
        layoutComponents();
    }
    
    private void initializeComponents() {
        authUrlArea = new JTextArea();
        authUrlArea.setEditable(false);
        authUrlArea.setLineWrap(true);
        authUrlArea.setWrapStyleWord(true);
        
        codeField = new JTextField(20);
        
        openBrowserButton = new JButton("Open Browser for Authentication");
        openBrowserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openBrowserForAuth();
            }
        });
        
        completeAuthButton = new JButton("Complete Authentication");
        completeAuthButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                completeAuthentication();
            }
        });
        
        statusLabel = new JLabel("Click 'Open Browser' to start authentication");
        statusLabel.setForeground(Color.BLUE);
    }
    
    private void layoutComponents() {
        setLayout(new BorderLayout());
        
        // Top panel with instructions
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createTitledBorder("Instructions"));
        JLabel instructionLabel = new JLabel(
            "<html><b>Demo Mode - Spotify Connection:</b><br>" +
            "1. Click 'Open Browser' to see Spotify's API documentation<br>" +
            "2. For demo purposes, enter any code (like 'demo123') below<br>" +
            "3. Click 'Complete Authentication' to proceed with mock data<br>" +
            "<br><i>Note: This is a demo version with mock data. For real Spotify integration, you'll need to set up your own Spotify app.</i></html>"
        );
        topPanel.add(instructionLabel, BorderLayout.CENTER);
        
        // Center panel with auth URL and code field
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createTitledBorder("Authorization"));
        
        JPanel urlPanel = new JPanel(new BorderLayout());
        urlPanel.add(new JLabel("Authorization URL:"), BorderLayout.NORTH);
        urlPanel.add(new JScrollPane(authUrlArea), BorderLayout.CENTER);
        urlPanel.add(openBrowserButton, BorderLayout.SOUTH);
        
        JPanel codePanel = new JPanel(new BorderLayout());
        codePanel.add(new JLabel("Authorization Code:"), BorderLayout.NORTH);
        codePanel.add(codeField, BorderLayout.CENTER);
        codePanel.add(completeAuthButton, BorderLayout.SOUTH);
        
        centerPanel.add(urlPanel, BorderLayout.NORTH);
        centerPanel.add(codePanel, BorderLayout.CENTER);
        
        // Bottom panel with status
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(statusLabel, BorderLayout.CENTER);
        
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void openBrowserForAuth() {
        try {
            String authUrl = spotifyService.getAuthorizationUrl();
            authUrlArea.setText(authUrl);
            
            // Try to open the browser
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new java.net.URI(authUrl));
                statusLabel.setText("Browser opened to Spotify API docs. For demo, enter any code below.");
                statusLabel.setForeground(Color.GREEN);
            } else {
                statusLabel.setText("Please manually copy and paste the URL above into your browser.");
                statusLabel.setForeground(Color.ORANGE);
            }
        } catch (Exception e) {
            statusLabel.setText("Error: " + e.getMessage());
            statusLabel.setForeground(Color.RED);
        }
    }
    
    private void completeAuthentication() {
        String code = codeField.getText().trim();
        if (code.isEmpty()) {
            statusLabel.setText("Please enter the authorization code");
            statusLabel.setForeground(Color.RED);
            return;
        }
        
        try {
            boolean success = spotifyService.completeAuthorization(code);
            if (success) {
                authSuccessful = true;
                statusLabel.setText("Authentication successful!");
                statusLabel.setForeground(Color.GREEN);
                JOptionPane.showMessageDialog(this, 
                    "Successfully connected to Spotify (Demo Mode)! You can now import mock listening history.", 
                    "Authentication Complete", 
                    JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                statusLabel.setText("Authentication failed. Please try again.");
                statusLabel.setForeground(Color.RED);
            }
        } catch (Exception e) {
            statusLabel.setText("Error: " + e.getMessage());
            statusLabel.setForeground(Color.RED);
        }
    }
    
    public boolean isAuthSuccessful() {
        return authSuccessful;
    }
} 