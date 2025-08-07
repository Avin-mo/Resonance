# Resonance
### Your personal music diary

**Resonance** is a desktop application that connects to your Spotify account to create a personal music diary.  
Import your listening history and add personal notes to capture memories, thoughts, and feelings about the songs that soundtrack your life.

**🎵 Spotify Integration** - Connect your Spotify account to automatically import your recently played tracks and add personal notes to create a comprehensive music diary. 

This app is designed for anyone who enjoys journaling, loves music, treasures memories, or wants to track their life through the lens of the songs they listen to.

I chose this project because music is a big part of my daily life, I’m almost always listening to something, and certain periods of my life are deeply tied to the music I was into at the time. For example, when I think back to summer 2018, the songs I listened to then immediately bring back the mood and memories of that time. Until now, I’ve kept these notes in a generic notes app, but I wanted to create something more intentional and personal, a journal made specifically for preserving memories through music.


## User Stories

- As a user, I want to connect my Spotify account to automatically import my listening history.
- As a user, I want to add personal notes to any song entry to capture memories and thoughts.
- As a user, I want to view my music diary entries organized by date.
- As a user, I want to filter my entries by artist to see my listening patterns.
- As a user, I want to remove entries from my diary if needed.
- As a user, I want to save my music diary to preserve my memories.
- As a user, I want to load my previously saved music diary.
- As a user, I want to be reminded to save my diary before closing the app.

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

### Phase 4: Task 2
Tue Aug 05 14:37:48 PDT 2025 → Added entry (ID: 1) - "aaa" by ccccc, Date: 2020-04-05, Mood: HAPPY
Tue Aug 05 14:37:48 PDT 2025 → Added entry (ID: 2) - "Heather" by Conan Gray, Date: 2025-04-03, Mood: ANGRY
Tue Aug 05 14:37:48 PDT 2025 → Viewed all entries (2 total)
Tue Aug 05 14:38:15 PDT 2025 → Added entry (ID: 3) - "Love Story" by Taylor Swift, Date: 2025-04-06, Mood: HAPPY
Tue Aug 05 14:38:15 PDT 2025 → Viewed all entries (3 total)
Tue Aug 05 14:38:20 PDT 2025 → Viewed all entries (3 total)
Tue Aug 05 14:38:25 PDT 2025 → Viewed all entries (3 total)
Tue Aug 05 14:38:33 PDT 2025 → Filtered entries by mood: HAPPY (2 match(es))
Tue Aug 05 14:38:35 PDT 2025 → Viewed all entries (3 total)