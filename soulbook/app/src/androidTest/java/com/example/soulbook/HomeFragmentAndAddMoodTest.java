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
public class HomeFragmentAndAddMoodTest {
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
     * Test to add a new mood
     */
    public void TestAddMood(){
        solo.assertCurrentActivity("Wrong Activity", LogInPage.class);
        solo.enterText((EditText) solo.getView(R.id.userEmail), "dxzero@gmail.com");//enter email
        solo.enterText((EditText) solo.getView(R.id.password), "qq452010");//enter password
        solo.clickOnButton("Log in");

        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnImageButton(0);//click add in homefragment
        solo.assertCurrentActivity("Wrong Activity", AddMoodActivity.class);
        solo.enterText((EditText) solo.getView(R.id.addmoodpage_content), "Happy");
        solo.clickOnImageButton(1);//click add in add mood activity
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnButton("detail");
        assertTrue(solo.waitForText("Happy"));
    }

    @Test
    /**
     * Test to delete a exist mood
     */
    public void TestDeleteMood(){
        solo.assertCurrentActivity("Wrong Activity", LogInPage.class);
        solo.enterText((EditText) solo.getView(R.id.userEmail), "dxzero@gmail.com");//enter email
        solo.enterText((EditText) solo.getView(R.id.password), "qq452010");//enter password
        solo.clickOnButton("Log in");

        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        solo.clickOnImageButton(1);//click delete
        solo.clickOnButton("detail");
        assertFalse(solo.waitForText("Happy"));
    }
    @Test
    /**
     * Test to select mood emoji and create a new mood with this emoji
     */
    public void testSelectMood(){
        solo.assertCurrentActivity("Wrong Activity", LogInPage.class);
        solo.enterText((EditText) solo.getView(R.id.userEmail), "dxzero@gmail.com");//enter email
        solo.enterText((EditText) solo.getView(R.id.password), "qq452010");//enter password
        solo.clickOnButton("Log in");

        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnImageButton(0);
        solo.assertCurrentActivity("Wrong Activity", AddMoodActivity.class);
        solo.pressSpinnerItem(0,1);//click spinner and choose Fear
        solo.waitForText("Fear");
        solo.clickOnImageButton(1);//click add
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnButton("detail");
        assertTrue(solo.waitForText("Fear"));
    }
}
