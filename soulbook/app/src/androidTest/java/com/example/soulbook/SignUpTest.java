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
public class SignUpTest {
    private Solo solo;
    String email = "dxzero@gmail.com";//email address for sign up
    String NickName = "zerodx"; //nick name for sign up

    @Rule
    public ActivityTestRule<LogInPage> rule =
            new ActivityTestRule<>(LogInPage.class, true, true);

    @Before
    public void setUp() throws Exception{

        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
    }

    @Test
    /**
     * Handle the password that you enter and confirm for sign up are different
     */
    public void checkPassword(){
        solo.assertCurrentActivity("Wrong Activity", LogInPage.class);

        solo.clickOnButton("Sign up");

        solo.assertCurrentActivity("Wrong Activity", SignUpPage.class);
        solo.enterText((EditText) solo.getView(R.id.newEmail), email); //enter email
        solo.enterText((EditText) solo.getView(R.id.newPassword), "12345"); //enter password
        solo.enterText((EditText) solo.getView(R.id.confirmPassword), "qwer"); //confirm password
        solo.enterText((EditText) solo.getView(R.id.newNickname), NickName); //enter nick name

        solo.clickOnButton("Sign up");

        assertTrue(solo.waitForText("passwords are different"));
    }

    @Test
    /**
     * Handle the email address that enter for sign up already exist
     */
    public void checkEmail(){
        solo.assertCurrentActivity("Wrong Activity", LogInPage.class);

        solo.clickOnButton("Sign up");
        solo.enterText((EditText) solo.getView(R.id.newEmail), email); //enter email
        solo.enterText((EditText) solo.getView(R.id.newPassword), "12345"); //enter password
        solo.enterText((EditText) solo.getView(R.id.confirmPassword), "12345"); //confirm password
        solo.enterText((EditText) solo.getView(R.id.newNickname), NickName); //enter nick name

        solo.clickOnButton("Sign up");

        assertTrue(solo.waitForText("This email has been used"));
    }

    @Test
    /**
     * Sign Up a new account
     * Pass this test need change email
     */
    public void trySignUp() throws InterruptedException {
        // Asserts that the current activity is the LogInPage. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong Activity", LogInPage.class);

        solo.clickOnButton("Sign up");//click SIGN UP

        // Asserts that the current activity is the SignUpPage. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong Activity", SignUpPage.class);

        solo.enterText((EditText) solo.getView(R.id.newEmail), "dxdx123@gmail.com"); //enter email
        solo.enterText((EditText) solo.getView(R.id.newPassword), "qq123"); //enter password
        solo.enterText((EditText) solo.getView(R.id.confirmPassword), "qq123"); //confirm password
        solo.enterText((EditText) solo.getView(R.id.newNickname), "newuser"); //enter nick name
        solo.clickOnButton("Sign up");//click SIGN UP

        // Asserts that the current activity is the LogInPage. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong Activity", LogInPage.class);

        solo.enterText((EditText) solo.getView(R.id.userEmail), email);//enter email
        solo.enterText((EditText) solo.getView(R.id.password), "12345");//enter password

        solo.clickOnButton("Log in");//click LOG IN
        // Asserts that the current activity is the MainActivity. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
    }
}
