package com.github.pozo.upwords.game;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

class CharacterProvider {
    String getRandomCharacterFrom(Map<String, Integer> remainingCharacters) {
        final Random random = new Random();
        int randomCharacterIndex = random.nextInt(remainingCharacters.size());

        final ArrayList<Map.Entry<String, Integer>> listOfEntries = new ArrayList<Map.Entry<String, Integer>>(remainingCharacters.entrySet());
        final Map.Entry<String, Integer> entry = listOfEntries.get(randomCharacterIndex);

        return entry.getKey();
    }
    static CharacterProvider instance() {
        return new CharacterProvider();
    }
}