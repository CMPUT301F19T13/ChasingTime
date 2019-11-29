package com.example.soulbook;


import com.example.soulbook.ui.dashboard.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class FriendListTest {
    ArrayList<String> nicknames = new ArrayList<>();

    private friendListAdapter mockFriend() {
        return new friendListAdapter(nicknames);
    }

    @Test
    void TestMsgList(){
        friendListAdapter testedFriends = mockFriend();
        nicknames.add("tester1");
        // one tester in the list
        assertEquals(1, testedFriends.getCount());
        nicknames.add("tester2");
        nicknames.add("tester3");
        // three testers in the list
        assertEquals(3, testedFriends.getCount());
    }

}
