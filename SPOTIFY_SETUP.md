# Spotify Integration Setup Guide

## Overview
This app integrates with Spotify to create your personal music diary:
- Connect your Spotify account
- Import your recently played tracks
- Add personal notes to capture memories and thoughts about each song
- Create a meaningful diary of your music journey

## Setup Instructions

### 1. Create a Spotify App
1. Go to [Spotify Developer Dashboard](https://developer.spotify.com/dashboard)
2. Log in with your Spotify account
3. Click "Create App"
4. Fill in the app details:
   - App name: "Resonance Music Journal" (or any name you prefer)
   - App description: "A music mood journal app"
   - Redirect URI: `http://localhost:8888/callback`
   - Website: (optional)
5. Accept the terms and create the app

### 2. Get Your Credentials
1. In your Spotify app dashboard, you'll see your Client ID and Client Secret
2. Copy these values - you'll need them for the next step

### 3. Update the App Configuration
1. Open `src/main/model/SpotifyService.java`
2. Replace the placeholder values:
   ```java
   private static final String CLIENT_ID = "YOUR_ACTUAL_CLIENT_ID";
   private static final String CLIENT_SECRET = "YOUR_ACTUAL_CLIENT_SECRET";
   ```

**Note**: The current version includes mock data for testing. To use real Spotify data, you'll need to implement the full Spotify API integration.

### 4. Install Dependencies
Run the following command to download the Spotify Web API Java wrapper:
```bash
./gradlew build
```

### 5. Run the Application
```bash
./gradlew run
```

## How to Use

### Connecting to Spotify
1. Click the "Connect Spotify" button
2. A dialog will open with authentication instructions
3. Click "Open Browser for Authentication"
4. Log in to your Spotify account and authorize the app
5. Copy the authorization code from the URL
6. Paste it in the dialog and click "Complete Authentication"

### Importing Tracks
1. After connecting to Spotify, click "Import Listening History"
2. Choose how many tracks to load (1-50)
3. Click "Refresh Tracks" to load your recently played songs
4. Select a track from the list
5. Add your personal thoughts and memories about the song
6. Click "Add to Diary"

### Adding Notes to Existing Entries
1. Select any entry in your journal
2. Click "Add Notes"
3. Enter your thoughts, memories, or feelings about the song
4. Click OK to save

## Features

### Features:
- **Spotify Authentication**: Secure OAuth2 connection to Spotify
- **Listening History Import**: Import your recently played tracks from Spotify
- **Personal Notes**: Add your thoughts, memories, and feelings about each song
- **Date-based Organization**: View your music diary organized by date
- **Artist Filtering**: Filter entries by artist to see your listening patterns
- **Local Storage**: Save your music diary locally to preserve your memories

## Security Notes
- Your Spotify credentials are stored locally in the app
- The app only requests permission to read your recently played tracks
- No data is sent to external servers
- All your journal data is stored locally on your computer

## Troubleshooting

### "Not authenticated with Spotify" error
- Make sure you've completed the Spotify authentication process
- Try clicking "Connect Spotify" again

### "Unable to load tracks" error
- Check your internet connection
- Verify your Spotify app credentials are correct
- Make sure you have recently played tracks in your Spotify account

### Build errors
- Make sure you have Java 11 or higher installed
- Run `./gradlew clean build` to clean and rebuild
- Check that all dependencies are downloaded correctly

## Technical Details

### Dependencies Added:
- Spotify Web API Java wrapper (se.michaelthelin.spotify:spotify-web-api-java:8.5.0)
- Gradle build system for dependency management

### New Classes:
- `SpotifyService`: Handles Spotify API authentication and requests
- `SpotifyTrack`: Represents a track from Spotify with metadata
- `SpotifyAuthDialog`: GUI for Spotify authentication
- `SpotifyImportDialog`: GUI for importing Spotify tracks

### Modified Classes:
- `Entry`: Added notes field and Spotify track ID
- `Journal`: Enhanced to work with Spotify tracks
- `JournalGUI`: Added Spotify integration buttons and functionality 