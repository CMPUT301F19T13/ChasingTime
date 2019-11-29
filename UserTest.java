package com.example.soulbook;
import android.app.Activity;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;


import android.widget.EditText;

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
public class UserTest {
    private Solo solo;
    @Rule
    public ActivityTestRule<LogInPage> rule =
            new ActivityTestRule<>(LogInPage.class, true, true);

    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
    }
    @Test
    /**
     * Try to change nick name
     */
    public void tryChangeNickName(){
        solo.assertCurrentActivity("Wrong Activity", LogInPage.class);
        solo.enterText((EditText) solo.getView(R.id.userEmail), "xliu2@ualberta.ca");//enter email
        solo.enterText((EditText) solo.getView(R.id.password), "123456");//enter password
        solo.clickOnButton("Sign in");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnMenuItem("Setting");
        solo.clickOnText("account");
        solo.clickOnText("Set new nickname");
        solo.enterText((EditText) solo.getView(R.id.setnickname), "testName");
        solo.clickOnButton("OK");

        assertTrue(solo.waitForText("change saved"));
    }

    @Test
    /**
     * Try with an invalid nickname
     */
    public void tryEmptyNickName(){
        solo.assertCurrentActivity("Wrong Activity", LogInPage.class);
        solo.enterText((EditText) solo.getView(R.id.userEmail), "xliu2@ualberta.ca");//enter email
        solo.enterText((EditText) solo.getView(R.id.password), "123456");//enter password
        solo.clickOnButton("Sign in");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnMenuItem("Setting");
        solo.clickOnText("account");
        solo.clickOnText("Set new nickname");
        solo.enterText((EditText) solo.getView(R.id.setnickname), "");
        solo.clickOnButton("OK");

        assertTrue(solo.waitForText("invalid nickname"));
    }
}
