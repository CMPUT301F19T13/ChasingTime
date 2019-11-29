package com.example.soulbook;


import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class MoodListTest {
    private ArrayList<mood> mockMoodList() {
        ArrayList<mood> moods = new ArrayList<>();
        moods.add(mockMood());

        return moods;
    }

    private mood mockMood() {
        return new mood("tested mood", "tested poster",null, "Anger", 12345, null);
    }

    @Test
    void TestAddMood() {
        ArrayList<mood> moods = mockMoodList();
        // test content
        assertEquals("tested mood", mockMood().getContent());
        assertEquals("tested mood", moods.get(0).getContent());

        // test poster
        assertEquals("tested poster", mockMood().getPoster());
        assertEquals("tested poster", moods.get(0).getPoster());

        // test emotion
        assertEquals("Anger", mockMood().getEmotion());
        assertEquals("Anger", moods.get(0).getEmotion());

        // test phone number
        assertEquals(12345, mockMood().getPhotonumber());
        assertEquals(12345, moods.get(0).getPhotonumber());
    }

}
