package com.github.pozo.upwords.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CharacterMixer {
    public static final int MAX_CHARACTER_NUMBER = 7;

    private final Map<String, Integer> currentCharacters;

    private static final Map<String, Integer> HUNGARIAN_CHARACTERS = new HashMap<String, Integer>() {
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
    private final CharacterProvider characterProvider;

    public CharacterMixer() {
        this(HUNGARIAN_CHARACTERS, CharacterProvider.instance());
    }

    CharacterMixer(Map<String, Integer> characterSet, CharacterProvider characterProvider) {
        this.currentCharacters = characterSet;
        this.characterProvider = characterProvider;
    }

    public List<String> raffleCharacters() {
        return getCharacters(MAX_CHARACTER_NUMBER);
    }

    String replace(String character) {
        final String randomCharacter = getOneCharacter();

        int currentValue = currentCharacters.get(character);
        currentCharacters.put(character, currentValue + 1);

        return randomCharacter;
    }

    List<String> getCharacters(int numberOfRequestedCharacters) {
        if (numberOfRequestedCharacters < 1 || numberOfRequestedCharacters > MAX_CHARACTER_NUMBER) {
            throw new IllegalArgumentException("The number of the requested characters must be between 1 and " + MAX_CHARACTER_NUMBER);
        }
        final int remainingCharacterNumber = remainingCharacterNumber();
        //boolean hasOnlyLessThanSevenCharacter = remainingCharacterNumber < MAX_CHARACTER_NUMBER;
        boolean hasOnlyLessThanRequestedCharacter = remainingCharacterNumber < numberOfRequestedCharacters;

        if (hasOnlyLessThanRequestedCharacter) {
            return remainingCharactersAsList();
        } else {
            return ruffleCharacters(numberOfRequestedCharacters);
        }
    }

    private String getOneCharacter() {
        List<String> character = getCharacters(1);
        if (character.size() == 1) {
            return character.get(0);
        } else {
            throw new IllegalArgumentException("There is not enough characters!");
        }
    }

    private List<String> ruffleCharacters(int numberOfCharacters) {
        final ArrayList<String> ruffledCharacters = new ArrayList<>();

        for (int i = 0; i < numberOfCharacters; i++) {
            final Map<String, Integer> remainingCharacters = remainingCharacters();
            final String randomCharacter = characterProvider.getRandomCharacterFrom(remainingCharacters);

            ruffledCharacters.add(randomCharacter);

            int currentValue = currentCharacters.get(randomCharacter);
            currentCharacters.put(randomCharacter, currentValue - 1);
        }

        return ruffledCharacters;
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
