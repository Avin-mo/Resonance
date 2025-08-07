# Resonance
### Your personal music diary

**Resonance** is a desktop application that connects to your Spotify account to create a personal music diary.  
Import your listening history and add personal notes to capture memories, thoughts, and feelings about the songs that soundtrack your life.

**🎵 Spotify Integration** - Connect your Spotify account to automatically import your recently played tracks and add personal notes to create a comprehensive music diary. 

This app is designed for anyone who enjoys journaling, loves music, treasures memories, or wants to track their life through the lens of the songs they listen to.

I chose this project because music is a big part of my daily life, I’m almost always listening to something, and certain periods of my life are deeply tied to the music I was into at the time. For example, when I think back to summer 2018, the songs I listened to then immediately bring back the mood and memories of that time. Until now, I’ve kept these notes in a generic notes app, but I wanted to create something more intentional and personal, a journal made specifically for preserving memories through music.

<b>Note:<b> This is a copy of my CPSC 210 personal project that I've further worked on and enhanced :)

## Instructions

### Getting Started:
1. **Connect to Spotify**: Click "Connect Spotify" to authenticate with your Spotify account. Follow the authentication dialog to log in and authorize the app.
2. **Import Your Listening History**: After connecting, click "Import Listening History" to load your recently played songs. Choose how many tracks to load (1-50), then select tracks to add to your diary.
3. **Add Personal Notes**: Select any entry and click "Add Notes" to add your thoughts, memories, or feelings about the song.

### Managing Your Diary:
- **Filter by Date**: Click "Filter by Date" to see entries from a specific date
- **Filter by Artist**: Click "Filter by Artist" to see all entries from a particular artist
- **Remove Entries**: Select an entry and click "Remove Entry" to delete it from your diary
- **Save Your Diary**: Click "Save Journal" to preserve your music memories
- **Load Your Diary**: Click "Load Journal" to restore your previously saved diary

**Note**: For detailed Spotify setup instructions, see [SPOTIFY_SETUP.md](SPOTIFY_SETUP.md).

## Building and Running

### Prerequisites
- Java 11 or higher
- Spotify account (for Spotify integration features)

### Build and Run
```bash
# Build the project
./gradlew build

# Run the application
./gradlew run
```

### Spotify Setup
Before using Spotify features, you need to:
1. Create a Spotify app at [Spotify Developer Dashboard](https://developer.spotify.com/dashboard)
2. Update the credentials in `src/main/model/SpotifyService.java`
3. See [SPOTIFY_SETUP.md](SPOTIFY_SETUP.md) for detailed instructions
