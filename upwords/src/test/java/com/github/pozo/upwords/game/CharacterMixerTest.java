package com.github.pozo.upwords.game;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CharacterMixerTest {

    @Test
    public void raffleCharacters() throws Exception {

    }

    @Test
    public void replaceTestForA() throws Exception {
        // GIVEN
        HashMap<String, Integer> testCharacterSet = new HashMap<>();
        testCharacterSet.put("a", 2);
        testCharacterSet.put("b", 1);

        CharacterProvider characterProvider = mock(CharacterProvider.class);
        when(characterProvider.getRandomCharacterFrom(anyMap())).thenReturn("a");

        final CharacterMixer characterMixer = new CharacterMixer(testCharacterSet, characterProvider);
        // WHEN
        String newCharacter = characterMixer.replace("a");
        // THEN
        int expectedAQuantity = 2;
        int actualAQuantity = testCharacterSet.get("a");
        assertEquals(expectedAQuantity, actualAQuantity);

        int expectedBQuantity = 1;
        int actualBQuantity = testCharacterSet.get("b");
        assertEquals(expectedBQuantity, actualBQuantity);
    }

    @Test
    public void replaceTestForB() throws Exception {
        // GIVEN
        HashMap<String, Integer> testCharacterSet = new HashMap<>();
        testCharacterSet.put("a", 2);
        testCharacterSet.put("b", 1);

        CharacterProvider characterProvider = mock(CharacterProvider.class);
        when(characterProvider.getRandomCharacterFrom(anyMap())).thenReturn("b");

        final CharacterMixer characterMixer = new CharacterMixer(testCharacterSet, characterProvider);
        // WHEN
        String newCharacter = characterMixer.replace("a");
        // THEN
        int expectedAQuantity = 3;
        int actualAQuantity = testCharacterSet.get("a");
        assertEquals(expectedAQuantity, actualAQuantity);

        int expectedBQuantity = 0;
        int actualBQuantity = testCharacterSet.get("b");
        assertEquals(expectedBQuantity, actualBQuantity);
    }
    @Test
    public void getCharacters() throws Exception {

    }

    @Test
    public void remainingCharactersAsList() throws Exception {

    }

    @Test
    public void remainingCharacters() throws Exception {

    }

    @Test
    public void remainingCharacterNumber() throws Exception {

    }

}