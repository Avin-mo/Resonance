package ui;

import model.Entry;
import model.Journal;

import java.time.LocalDate;
import java.util.Scanner;

public class JournalApp {
    private Journal journal;
    private Scanner input;

    // MODIFIES: this
    // EFFECTS: starts the journal app
    public JournalApp() {
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
                keepGoing = false;
            } else {
                executeCommand(command);
            }
        }

        System.out.println("Thanks for using Resonance!");
    }

    // MODIFIES: this
    // EFFECTS: initializes journal
    private void init() {
        journal = new Journal();
        input = new Scanner(System.in);
    }

    // EFFECTS: displays the main menu of options for the user
    private void mainMenu() {
        System.out.println("\nPlease select from the options below:");
        System.out.println("\ta -> add entry");
        System.out.println("\tv -> view entries");
        System.out.println("\tr -> remove entry");
        System.out.println("\tu -> update entry");
        System.out.println("\tq -> quit");

    }

    // MODIFIES: this
    // EFFECTS: executes user commands from the main menu
    private void executeCommand(String command) {
        if (command.equals("a")) {
            addEntry();
        } else if (command.equals("v")) {
            viewEntries();
        } else if (command.equals("r")) {
            removeEntry();
        } else if (command.equals("u")) {
            updateEntryOption();
        } else {
            System.out.println("Selection not valid...");
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

        System.out.print("Enter date (YYYY-MM-DD): ");
        String dateStr = input.nextLine();
        LocalDate date = LocalDate.parse(dateStr);

        System.out.print("Enter mood (HAPPY, SAD, CALM, ANGRY, EXCITED): ");
        String moodStr = input.nextLine();
        Entry.Mood mood = Entry.Mood.valueOf(moodStr.toUpperCase());

        Entry entry = new Entry(title, artist, date, mood);
        journal.addEntry(entry);
        System.out.println("Entry added successfully!");
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
        String dateString = prompt("Enter date to search for (YYYY-MM-DD)): ");

        LocalDate date = LocalDate.parse(dateString);
        var matchDate = journal.getEnteriesByDate(date);

        System.out.println("\nEntries with date \"" + date + "\":");
        for (Entry e : matchDate) {
            System.out.println(formatEntry(e));
        }
    }

    // EFFECTS: displays entries filtered by mood
    private void filterByMood() {
        String moodString = prompt("Enter mood to search for (HAPPY, SAD, CALM, ANGRY, EXCITED): ").toUpperCase();

        Entry.Mood mood = Entry.Mood.valueOf(moodString);
        var matchMood = journal.getEnteriesByMood(mood);

        System.out.println("\nEntries with mood \"" + mood + "\":");
        for (Entry e : matchMood) {
            System.out.println(formatEntry(e));
        }
    }

    // MODIFIES: this
    // EFFECTS: displays entries, then removes entry by ID if found, else prints
    //          error
    private void removeEntry() {
        if (journal.getAllEntries().isEmpty()) {
            System.out.println("Your journal is empty. Nothing to remove.");
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
    }

    // MODIFIES: this
    // EFFECTS: lets the user select an entry update option
    private void updateEntryOption() {
        System.out.println("\nUpdate entries:");
        System.out.println("\tut -> update by song title");
        System.out.println("\tur -> update by artist");
        System.out.println("\tud -> update by date");
        System.out.println("\tum -> update by mood");
        System.out.print("Select an option: ");

        updateEntry();

        System.out.println("Update completed.");
    }



    // MODIFIES: this
    // EFFECTS: updates the entry based on the option selected by the user
    private void updateEntry() {
        input.nextLine();
        String choice = input.nextLine();
        
        if (choice.equals("ut")) {
            String oldTitle = prompt("Enter the current song title: ");
            String newTitle = prompt("Enter the new song title: ");
            journal.updateSongTitle(oldTitle, newTitle);
        } else if (choice.equals("ur")) {
            String oldArtist = prompt("Enter the current song artist: ");
            String newArtist = prompt("Enter the new song artist: ");
            journal.updateSongArtist(oldArtist, newArtist);
        } else if (choice.equals("ud")) {
            LocalDate oldDate = LocalDate.parse(prompt("Enter the current entry date: "));
            LocalDate newDate = LocalDate.parse(prompt("Enter the new entry date: "));
            journal.updateDate(oldDate, newDate);
        } else if (choice.equals("um")) {
            String oldMoodString = prompt("Enter the current mood (HAPPY, SAD, CALM, ANGRY, EXCITED): ").toUpperCase();
            String newMoodString = prompt("Enter the new mood (HAPPY, SAD, CALM, ANGRY, EXCITED): ").toUpperCase();
            Entry.Mood oldMood = Entry.Mood.valueOf(oldMoodString);
            Entry.Mood newMood = Entry.Mood.valueOf(newMoodString);
            journal.updateMood(oldMood, newMood);
        } else {
            System.out.println("Invalid option.");
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
                + " | Mood: " + e.getMood()
                + " (" + e.getColor() + ")";

    }
}
