package ui;

import model.Entry;
import model.Journal;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.time.LocalDate;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class JournalApp {
    private Journal journal;
    private Scanner input;
    // CITATION: usage of the following fields was inspired by CPSC 210
    // JsonSerializationDemo
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/journal.json";
    private boolean journalSaved;

    // MODIFIES: this
    // EFFECTS: starts the journal app
    public JournalApp() throws FileNotFoundException {
        // CITATION: the following code was inspired/taken by CPSC 210
        // JsonSerializationDemo
        input = new Scanner(System.in);
        journal = new Journal();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runJournal();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runJournal() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            mainMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                if (!journalSaved) {
                    askToSave();
                } else {
                    keepGoing = false;
                }
            } else {
                executeCommand(command);
            }
        }

        System.out.println("Thanks for using Resonance!");

        printEventLog();
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
    

    // EFFECTS: asks the user whether they want to save
    private void askToSave() {
        System.out.println("\nAre you sure you want to clsoe the app without saving the new changes?");
        System.out.println("\ty -> yes");
        System.out.println("\tn -> no");
        input.nextLine(); // clear leftover newline
        String choice = input.nextLine();

        if (choice.equals("y")) {
            saveJournal();
            System.out.println("Thanks for using Resonance!");
            System.exit(0);
        } else {
            System.out.println("Thanks for using Resonance!");
            System.exit(0);
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes journal
    private void init() {
        journal = new Journal();
        input = new Scanner(System.in);
        journalSaved = true;
    }

    // EFFECTS: displays the main menu of options for the user
    private void mainMenu() {
        System.out.println("\nPlease select from the options below:");
        System.out.println("\ta -> add entry");
        System.out.println("\tv -> view entries");
        System.out.println("\tr -> remove entry");
        System.out.println("\tu -> update entry");
        System.out.println("\ts -> save journal to a file");
        System.out.println("\tl -> load journal from a file");
        System.out.println("\tq -> quit");
        System.out.print("Select an option: ");
    }

    // MODIFIES: this
    // EFFECTS: executes user commands from the main menu; if input not valid
    // returns to mainMenu()
    private void executeCommand(String command) {
        if (command.equals("a")) {
            addEntry();
        } else if (command.equals("v")) {
            viewEntries();
        } else if (command.equals("r")) {
            removeEntry();
        } else if (command.equals("u")) {
            updateEntryOption();
        } else if (command.equals("s")) {
            saveJournal();
        } else if (command.equals("l")) {
            loadJournal();
        } else {
            System.out.println("Selection not valid.");
            mainMenu();
        }
    }

    // MODIFEIS: this
    // EFFECTS: prompts user for entry details and adds a new entry to the journal
    private void addEntry() {
        input.nextLine(); // clear newline
        System.out.print("Enter song title: ");
        String title = input.nextLine();

        System.out.print("Enter artist: ");
        String artist = input.nextLine();

        LocalDate date = promptForDate("Enter date (YYYY-MM-DD): ");
        Entry.Mood mood = promptForMood("Enter mood (HAPPY, SAD, CALM, ANGRY, EXCITED): ");

        journal.addEntry(new Entry(title, artist, date, mood));
        journalSaved = false;
        System.out.println("Entry added successfully!");
    }

    // EFFECTS: prompts user until they enter a valid mood
    private Entry.Mood promptForMood(String promptMessage) {
        Entry.Mood mood = null;
        while (mood == null) {
            System.out.println(promptMessage);
            String moodStr = input.nextLine();
            try {
                mood = Entry.Mood.valueOf(moodStr.toUpperCase());
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter one of (HAPPY, SAD, CALM, ANGRY, EXCITED).");
            }
        }
        return mood;
    }

    // EFFECTS: prompts user until they enter a valid date in YYYY-MM-DD format
    private LocalDate promptForDate(String promptMessage) {
        LocalDate date = null;
        while (date == null) {
            System.out.print(promptMessage);
            String dateStr = input.nextLine();
            try {
                date = LocalDate.parse(dateStr);
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }
        return date;
    }

    // EFFECTS: displays options for entry viewing
    private void viewEntries() {
        System.out.println("\nView entries:");
        System.out.println("\tva -> view all");
        System.out.println("\tvt -> filter by song title");
        System.out.println("\tvr -> filter by artist");
        System.out.println("\tvd -> filter by date");
        System.out.println("\tvm -> filter by mood");
        System.out.print("Select an option: ");
        input.nextLine(); // clear leftover newline
        String choice = input.nextLine();

        if (choice.equals("va")) {
            viewAllEntries();
        } else if (choice.equals("vt")) {
            filterByTitle();
        } else if (choice.equals("vr")) {
            filterByArtist();
        } else if (choice.equals("vd")) {
            filterByDate();
        } else if (choice.equals("vm")) {
            filterByMood();
        } else {
            System.out.println("Invalid option.");
        }

    }

    // EFFECTS: displays all journal entries
    private void viewAllEntries() {
        if (journal.getAllEntries().isEmpty()) {
            System.out.println("No entries found.");
        } else {
            System.out.println("\nYour journal entries:");
            for (Entry e : journal.getAllEntries()) {
                System.out.println(formatEntry(e));
            }
        }
    }

    // EFFECTS: displays entries filtered by song title
    private void filterByTitle() {
        String title = prompt("Enter song title to search for: ");
        var matchTitle = journal.getEnteriesBySongTitle(title);

        System.out.println("\nEntries with title \"" + title + "\":");
        for (Entry e : matchTitle) {
            System.out.println(formatEntry(e));
        }
    }

    // EFFECTS: displays entries filtered by artist
    private void filterByArtist() {
        String artist = prompt("Enter song artist to search for: ");
        var matchArtist = journal.getEnteriesBySongArtist(artist);

        System.out.println("\nEntries with artist \"" + artist + "\":");
        for (Entry e : matchArtist) {
            System.out.println(formatEntry(e));
        }
    }

    // EFFECTS: displays entries filtered by date
    private void filterByDate() {
        LocalDate date = null;
        while (date == null) {
            System.out.print("Enter date (YYYY-MM-DD): ");
            String dateStr = input.nextLine();
            try {
                date = LocalDate.parse(dateStr);
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }
        var matchDate = journal.getEnteriesByDate(date);

        System.out.println("\nEntries with date \"" + date + "\":");
        for (Entry e : matchDate) {
            System.out.println(formatEntry(e));
        }
    }

    // EFFECTS: displays entries filtered by mood
    private void filterByMood() {
        Entry.Mood mood = null;
        while (mood == null) {
            System.out.println("Enter mood to search for (HAPPY, SAD, CALM, ANGRY, EXCITED): ");
            String moodString = input.nextLine();
            try {
                mood = Entry.Mood.valueOf(moodString.toUpperCase());
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter one of (HAPPY, SAD, CALM, ANGRY, EXCITED).");
            }
        }
        var matchMood = journal.getEnteriesByMood(mood);

        System.out.println("\nEntries with mood \"" + mood + "\":");
        for (Entry e : matchMood) {
            System.out.println(formatEntry(e));
        }
    }

    // MODIFIES: this
    // EFFECTS: displays entries, then removes entry by ID if found, else prints
    // error
    // if the journal is empty returns to mainMenu()
    private void removeEntry() {
        if (journal.getAllEntries().isEmpty()) {
            System.out.println("Your journal is empty. Nothing to remove.");
            mainMenu();
        }

        System.out.println("\nCurrent Entries:");
        viewAllEntries();

        System.out.println("Enter ID of entry to remove: ");
        int id = input.nextInt();

        Entry toRemove = journal.getEntryById(id);
        if (toRemove != null) {
            journal.removeEntry(toRemove);
            System.out.println("Entry removed successfully.");
        } else {
            System.out.println("No entry found with ID: " + id);
        }

        journalSaved = false;
    }

    // MODIFIES: this
    // EFFECTS: lets the user select an entry update option; if the journal is empty
    // returns to mainMenu()
    private void updateEntryOption() {
        if (journal.getAllEntries().isEmpty()) {
            System.out.println("Your journal is empty. Nothing to update.");
        } else {
            System.out.println("\nUpdate entries:");
            System.out.println("\tut -> update by song title");
            System.out.println("\tur -> update by artist");
            System.out.println("\tud -> update by date");
            System.out.println("\tum -> update by mood");
            System.out.print("Select an option: ");

            updateEntry();
        }
    }

    // MODIFIES: this
    // EFFECTS: prints all the enteris for the user to see;
    // updates the entry based on the option selected by the user
    // if choice is invalid returns to updateEntryOption();
    private void updateEntry() {
        System.out.println("\nCurrent Entries:");
        viewAllEntries();

        input.nextLine();
        String choice = input.nextLine();

        if (choice.equals("ut")) {
            updateTheTitle();
        } else if (choice.equals("ur")) {
            updateTheArtist();
        } else if (choice.equals("ud")) {
            updateTheDate();
        } else if (choice.equals("um")) {
            updateTheMood();
        } else {
            System.out.println("Invalid option.");
            updateEntryOption();
        }

        journalSaved = false;
    }

    // MODIFIES: this
    // EFFECTS: updates the song title of the spesified entry; if id not found
    // returns to updateEntryOption();
    private void updateTheTitle() {
        System.out.println("Enter ID of entry to remove: ");
        int id = input.nextInt();

        Entry toUpdate = journal.getEntryById(id);
        if (toUpdate != null) {
            String oldTitle = prompt("Enter the current song title: ");
            String newTitle = prompt("Enter the new song title: ");
            journal.updateSongTitle(oldTitle, newTitle);
        } else {
            System.out.println("No entry found with ID: " + id);
            updateEntryOption();
        }

        System.out.println("Update completed.");
    }

    // MODIFIES: this
    // EFFECTS: updates the song artist of the spesified entry; if id nto found
    // returns to updateEntryOption();
    private void updateTheArtist() {
        System.out.println("Enter ID of entry to remove: ");
        int id = input.nextInt();

        Entry toUpdate = journal.getEntryById(id);

        if (toUpdate != null) {
            String oldArtist = prompt("Enter the current song artist: ");
            String newArtist = prompt("Enter the new song artist: ");
            journal.updateSongArtist(oldArtist, newArtist);
        } else {
            System.out.println("No entry found with ID: " + id);
            updateEntryOption();
        }

        System.out.println("Update completed.");
    }

    // MODIFIES: this
    // EFFECTS: updates the date of the spesified entry; if id not found returns to
    // updateEntryOption();
    private void updateTheDate() {
        System.out.println("Enter ID of entry to remove: ");
        int id = input.nextInt();

        Entry toUpdate = journal.getEntryById(id);
        if (toUpdate != null) {
            LocalDate oldDate = promptForDate("Enter the current entry date: ");
            LocalDate newDate = promptForDate("Enter the new entry date: ");
            journal.updateDate(oldDate, newDate);
            System.out.println("Update completed.");
        } else {
            System.out.println("No entry found with ID: " + id);
            updateEntryOption();
        }
    }

    // MODIFIES: this
    // EFFECTS: updates the mood of the spesified entry; if id nto found returns to
    // updateEntryOption();
    private void updateTheMood() {
        System.out.println("Enter ID of entry to remove: ");
        int id = input.nextInt();

        Entry toUpdate = journal.getEntryById(id);
        if (toUpdate != null) {
            Entry.Mood oldMood = promptForMood("Enter the current mood (HAPPY, SAD, CALM, ANGRY, EXCITED): ");
            Entry.Mood newMood = promptForMood("Enter the new mood (HAPPY, SAD, CALM, ANGRY, EXCITED): ");
            journal.updateMood(oldMood, newMood);
            System.out.println("Update completed.");
        } else {
            System.out.println("No entry found with ID: " + id);
            updateEntryOption();
        }
    }

    // EFFECTS: prompts user and returns their input
    private String prompt(String message) {
        System.out.print(message);
        return input.nextLine();
    }

    // EFFECTS: returns formatted string of entry
    private String formatEntry(Entry e) {
        return "ID: " + e.getId()
                + " | \"" + e.getSongName() + "\""
                + " by " + e.getSongArtist()
                + " on " + e.getDate()
                + " | Mood: " + e.getMood();

    }

    // CITATION: the following code was inspired/taken by CPSC 210
    // JsonSerializationDemo
    // EFFECTS: saves journal to a file
    private void saveJournal() {
        try {
            jsonWriter.open();
            jsonWriter.write(journal);
            jsonWriter.close();
            System.out.println("Saved your journal to" + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }

        journalSaved = true;
    }

    // CITATION: the following code was inspired/taken by CPSC 210
    // JsonSerializationDemo
    // EFFECTS: loads journal from a file
    private void loadJournal() {
        try {
            journal = jsonReader.read();
            System.out.println("Loaded your journal from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
        journalSaved = true;
    }
}
