package com.olq.timemanager.activities

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.*
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.olq.timemanager.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by olq on 21.10.17.
 */

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule<MainActivity>(MainActivity::class.java)


    @Test
    fun onAddBtnClick_checkEmptyEditText(){
        onView(withId(R.id.myAddButton)).perform(click())

        onView(withId(R.id.myTaskText)).check(doesNotExist())
    }

    @Test
    fun onAddBtnClick_checkIsManagerActivityDisplayed(){
        val demoText = "Tested Task"
        onView(withId(R.id.myEditText)).perform(typeText(demoText), closeSoftKeyboard())
        onView(withId(R.id.myAddButton)).perform(click())

        onView(withId(R.id.myTaskText)).check(matches(isDisplayed()))
    }

    @Test
    fun onAddBtnClick_checkTaskTextValue(){
        val demoText = "Tested Task"
        onView(withId(R.id.myEditText)).perform(typeText(demoText), closeSoftKeyboard())
        onView(withId(R.id.myAddButton)).perform(click())

        onView(withId(R.id.myTaskText)).check(matches(withText(demoText)))
    }
}