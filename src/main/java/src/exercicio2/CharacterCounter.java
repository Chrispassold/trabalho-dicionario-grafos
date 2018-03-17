package src.exercicio2;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CharacterCounter {
    private final Map<Character, Integer> counts = new HashMap<Character, Integer>();

    /**
     * @param c the character to count
     */
    public void increment(Character c) {
        Integer freq = counts.get(c);
        if (freq == null) {
            counts.put(c, 1);
        } else {
            counts.put(c, freq + 1);
        }
    }

    /**
     * @param text contains the characters to count
     */
    public void increment(String text) {
        for (char c : text.toCharArray()) {
            increment(c);
        }
    }

    /**
     * @param bufferedReader reader
     */
    public void load(BufferedReader bufferedReader) throws IOException {
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            increment(line);
        }
    }

    /**
     * @return set containing each character seen while counting
     */
    public Set<Character> getCharacters() {
        return counts.keySet();
    }

    /**
     * @return set containing each character seen while counting and its count
     */
    public Set<Map.Entry<Character, Integer>> getCharacterCounts() {
        return counts.entrySet();
    }
}