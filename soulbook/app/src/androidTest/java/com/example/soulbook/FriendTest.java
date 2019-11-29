package com.example.soulbook;
import android.app.Activity;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;


import android.widget.EditText;

import com.example.soulbook.ui.dashboard.DashboardFragment;
import com.example.soulbook.ui.dashboard.friend;
import com.example.soulbook.ui.home.HomeFragment;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
@RunWith(AndroidJUnit4.class)
public class FriendTest {
    private Solo solo;
    @Rule
    public ActivityTestRule<LogInPage> rule =
            new ActivityTestRule<>(LogInPage.class, true, true);

    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    @Test
    /**
     * Try to add a friend
     */
    public void TryAddFriend() {
        solo.assertCurrentActivity("Wrong Activity", LogInPage.class);
        solo.enterText((EditText) solo.getView(R.id.userEmail), "123@gg.com");//enter email
        solo.enterText((EditText) solo.getView(R.id.password), "123456");//enter password
        solo.clickOnButton("Sign in");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnText("Friend");
        solo.clickOnText("friends");
        solo.clickOnImageButton(0);
        solo.enterText((EditText) solo.getView(R.id.searchpage_content), "xliu@ualberta.ca");//enter email
        solo.clickOnButton("OK");
        solo.enterText((EditText) solo.getView(R.id.add_friend_message), "dwaedwq");
        solo.clickOnButton("OK");
    }

    @Test
    /**
     * Try add a friend with invalid email address
     */
    public void TryInvalidFriend() {
        solo.assertCurrentActivity("Wrong Activity", LogInPage.class);
        solo.enterText((EditText) solo.getView(R.id.userEmail), "123@gg.com");//enter email
        solo.enterText((EditText) solo.getView(R.id.password), "123456");//enter password
        solo.clickOnButton("Sign in");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnText("Friend");
        solo.clickOnText("friends");
        solo.clickOnImageButton(0);
        solo.enterText((EditText) solo.getView(R.id.searchpage_content), "x");//enter email
        solo.clickOnButton("OK");

        assertTrue(solo.waitForText("please enter a correct email address"));
    }
}