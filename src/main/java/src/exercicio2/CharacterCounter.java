package src.exercicio2;

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
     * @param stringBuilder text
     */
    public void load(StringBuilder stringBuilder) {
        String string = stringBuilder.toString();
        for (char c : string.toCharArray()) {
            increment(c);
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