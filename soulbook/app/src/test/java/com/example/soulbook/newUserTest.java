package com.example.soulbook;


import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class newUserTest {
    private User mockUser() {
        return new User("123@test.com","tester","123456");
    }

    @Test
    public void TestUser() {
       User testUser = mockUser();
        // test nickname
        assertEquals("tester", testUser.getNickname());
        // test email address
        assertEquals("123@test.com", testUser.getEmail());
        // test password
        assertEquals("123456", testUser.getPassword());
    }

}
