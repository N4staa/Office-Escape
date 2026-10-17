package game;

import java.io.*;

/**
 * Persists the all-time high score to a plain-text file so it survives
 * between play sessions.
 */
public class HighScoreManager {

    private static final String FILE = "data/highscore.txt";

    /** Reads the saved high score, returning 0 if the file is missing or unreadable. */
    public static int load() {
        try (BufferedReader r = new BufferedReader(new FileReader(FILE))) {
            return Integer.parseInt(r.readLine().trim());
        } catch (Exception e) {
            return 0;
        }
    }

    /** Writes the given score to disk, creating the file if needed. */
    public static void save(int score) {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(FILE))) {
            w.write(String.valueOf(score));
        } catch (IOException e) {
            System.out.println("Could not save high score: " + e.getMessage());
        }
    }
}
