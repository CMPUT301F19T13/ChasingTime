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
public class LogInTest {
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
     * Try login with exist account
     */
    public void tryLogIn(){
        solo.assertCurrentActivity("Wrong Activity", LogInPage.class);
        solo.enterText((EditText) solo.getView(R.id.userEmail), "dxzero@gmail.com");//enter email
        solo.enterText((EditText) solo.getView(R.id.password), "qq452010");//enter password
        solo.clickOnButton("Sign in");

        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
    }

    @Test
    /**
     * Login with wrong password
     */
    public void wrongPassword(){
        solo.assertCurrentActivity("Wrong Activity", LogInPage.class);
        solo.enterText((EditText) solo.getView(R.id.userEmail), "dxzero@gmail.com");//enter email
        solo.enterText((EditText) solo.getView(R.id.password), "x");//enter password
        solo.clickOnButton("Sign in");

        assertTrue(solo.waitForText("Log in fail"));
    }

    @Test
    /**
     * Login with wrong email
     */
    public void wrongEmail(){
        solo.assertCurrentActivity("Wrong Activity", LogInPage.class);
        solo.enterText((EditText) solo.getView(R.id.userEmail), "xxxxxxxxxx");//enter email
        solo.enterText((EditText) solo.getView(R.id.password), "x");//enter password
        solo.clickOnButton("Sign in");

        assertTrue(solo.waitForText("Log in fail"));
    }
}
