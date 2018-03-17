package src.exercicio2;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Keeps a running count of how many times each unique character is seen.
 */
public class CharacterCounter {
    private final Map<Character, Integer> counts = new HashMap<Character, Integer>();

    /**
     * Increments the count of the given character,
     * setting it to one on first appearance.
     *
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
     * Increments the count of each character in the given text.
     *
     * @param text contains the characters to count
     */
    public void increment(String text) {
        for (char c : text.toCharArray()) {
            increment(c);
        }
    }

    /**
     * Returns the set of characters seen.
     *
     * @return set containing each character seen while counting
     */
    public Set<Character> getCharacters() {
        return counts.keySet();
    }

    /**
     * Returns the set of characters seen along with their counts.
     *
     * @return set containing each character seen while counting and its count
     */
    public Set<Map.Entry<Character, Integer>> getCharacterCounts() {
        return counts.entrySet();
    }
}