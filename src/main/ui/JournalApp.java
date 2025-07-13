package ui;

public class JournalApp {
    
    // MODIFIES: this
    // EFFECTS: starts the journal app
    public JournalApp() {
        runJournal();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runJournal() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: initializes journal
    private void init() {
        // stub
    }

    // EFFECTS: displays the main menu of options for the user
    private void mainMenu() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: executes user commands from the main menu
    private void executeCommand(String command) {
        // stub
    }

    // MODIFEIS: this
    // EFFECTS: prompts user for entry details and adds a new entry to the journal
    private void addEntry() {
        // stub
    }

    // EFFECTS: displays options for entry viewing
    private void viewEntries() {
        // stub
    }

    // EFFECTS: displays all journal entries
    private void viewAllEntries() {
        // stub
    }

    // EFFECTS: displays entries filtered by song title
    private void filterByTitle() {
        // stub
    }

    // EFFECTS: displays entries filtered by artist
    private void filterByArtist() {
        // stub
    }

    // EFFECTS: displays entries filtered by date
    private void filterByDate() {
        // stub
    }

    // EFFECTS: displays entries filtered by mood
    private void filterByMood() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: displays entries, then removes entry by ID if found, else prints
    // error
    private void removeEntry() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: lets the user select an entry and update one of its fields;
    // does nothing if journal is empty or user input is invalid
    private void updateEntry() {
        // stub
    }

    // EFFECTS: prompts user and returns their input
    private String prompt(String message) {
        // stub
    }

    // EFFECTS: returns formatted string of entry
    private String formatEntry(Entry e) {
        // stub

    }
}
