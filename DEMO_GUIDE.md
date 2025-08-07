# Demo Guide - Testing Your Music Diary

## 🎵 How to Test the App Right Now

### Step 1: Run the App
```bash
./gradlew run
```

### Step 2: Connect to Spotify (Demo Mode)
1. Click **"Connect Spotify"** button
2. Click **"Open Browser for Authentication"** 
   - This will open Spotify's API documentation page
3. In the **"Authorization Code"** field, enter any code (like `demo123`)
4. Click **"Complete Authentication"**
5. You should see: "Successfully connected to Spotify (Demo Mode)!"

### Step 3: Import Mock Listening History
1. Click **"Import Listening History"** button
2. Choose how many tracks to load (1-10 available)
3. Click **"Refresh Tracks"**
4. You'll see 10 popular songs like:
   - "Blinding Lights" by The Weeknd
   - "Shape of You" by Ed Sheeran
   - "Dance Monkey" by Tones and I
   - And more...

### Step 4: Add Songs to Your Diary
1. Select any song from the list
2. Add your personal thoughts in the notes area
3. Click **"Add to Diary"**
4. The song will appear in your main diary list

### Step 5: Manage Your Diary
- **Add Notes**: Select any entry → Click "Add Notes" → Write your thoughts
- **Filter by Date**: Click "Filter by Date" → Enter a date
- **Filter by Artist**: Click "Filter by Artist" → Enter artist name
- **Remove Entries**: Select entry → Click "Remove Entry"
- **Save/Load**: Use "Save Journal" and "Load Journal" buttons

## 🎯 What You're Testing

✅ **Spotify Connection Flow** (Demo)
✅ **Import Listening History** (Mock Data)
✅ **Add Personal Notes** to songs
✅ **Filter and Search** functionality
✅ **Save/Load** your music diary
✅ **Clean, Focused Interface**

## 🔄 Next Steps for Real Spotify

When you want real Spotify data:

1. **Create Spotify App** at [Spotify Developer Dashboard](https://developer.spotify.com/dashboard)
2. **Get Real Credentials** (Client ID & Secret)
3. **Replace in SpotifyService.java**:
   ```java
   private static final String CLIENT_ID = "your_real_client_id";
   private static final String CLIENT_SECRET = "your_real_client_secret";
   ```
4. **Uncomment Real API Code** (I can help with this)

## 🎉 Enjoy Your Music Diary!

The app is fully functional with mock data. You can:
- Create entries with personal notes
- Organize by date and artist
- Save your music memories
- Experience the complete diary workflow

**The demo shows exactly how the real app will work!** 🎵✨ 