package com.github.pozo.upwords.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class CharacterMixer {
    private static final int MAX_CHARACTER_NUMBER = 7;

    private final Map<String, Integer> currentCharacters = new HashMap<String, Integer>() {
        {
            put("A", 6);
            put("Á", 4);
            put("B", 3);
            put("C", 2);
            put("CS", 1);
            put("D", 3);
            put("E", 6);
            put("É", 3);
            put("F", 2);
            put("G", 3);
            put("GY", 2);
            put("H", 2);
            put("I", 3);
            put("Í", 1);
            put("J", 2);
            put("K", 6);
            put("L", 4);
            put("LY", 1);
            put("M", 3);
            put("N", 4);
            put("NY", 1);
            put("O", 3);
            put("Ó", 3);
            put("Ö", 2);
            put("Ő", 2);
            put("P", 2);
            put("R", 4);
            put("S", 3);
            put("SZ", 2);
            put("T", 5);
            put("TY", 1);
            put("U", 2);
            put("Ú", 1);
            put("Ü", 2);
            put("Ű", 1);
            put("V", 2);
            put("Z", 2);
            put("ZS", 1);
        }
    };

    public List<String> raffleCharacters() {
        return getCharacters(MAX_CHARACTER_NUMBER);
    }

    List<String> getCharacters(int numberOfCharacters) {
        if (numberOfCharacters < 1 || numberOfCharacters > MAX_CHARACTER_NUMBER) {
            throw new IllegalArgumentException("The number of the requested characters must be between 1 and " + MAX_CHARACTER_NUMBER);
        }
        boolean hasOnlyLessThanSevenCharacter = remainingCharacterNumber() < MAX_CHARACTER_NUMBER;

        if (hasOnlyLessThanSevenCharacter) {
            return remainingCharactersAsList();
        } else {
            return ruffleCharacters(numberOfCharacters);
        }
    }

    private List<String> ruffleCharacters(int numberOfCharacters) {
        final ArrayList<String> ruffledCharacters = new ArrayList<>();

        for (int i = 0; i < numberOfCharacters; i++) {
            final Map<String, Integer> remainingCharacters = remainingCharacters();
            final String randomCharacter = getRandomCharacterFrom(remainingCharacters);

            ruffledCharacters.add(randomCharacter);

            int currentValue = currentCharacters.get(randomCharacter);
            currentCharacters.put(randomCharacter, currentValue - 1);
        }

        return ruffledCharacters;
    }

    private String getRandomCharacterFrom(Map<String, Integer> remainingCharacters) {
        final Random random = new Random();
        int randomCharacterIndex = random.nextInt(remainingCharacters.size());

        final ArrayList<Map.Entry<String, Integer>> listOfEntries = new ArrayList<>(remainingCharacters.entrySet());
        final Map.Entry<String, Integer> entry = listOfEntries.get(randomCharacterIndex);

        return entry.getKey();
    }

    public ArrayList<String> remainingCharactersAsList() {
        final ArrayList<String> remainingCharactersAsList = new ArrayList<>();
        final Map<String, Integer> remainingCharacters = remainingCharacters();

        remainingCharacters.forEach((key, value) -> {
            for (int i = 0; i < value; i++) {
                remainingCharactersAsList.add(key);
            }
        });

        return remainingCharactersAsList;
    }

    public Map<String, Integer> remainingCharacters() {
        return currentCharacters.entrySet()
                .stream()
                .filter(stringIntegerEntry -> stringIntegerEntry.getValue() > 0)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public int remainingCharacterNumber() {
        return currentCharacters.values()
                .stream()
                .mapToInt(quantity -> quantity)
                .sum();
    }
}
