package alzaichsank.com.aplikasifootbalmatchschedule.matchactivity

import alzaichsank.com.aplikasifootbalmatchschedule.R
import alzaichsank.com.aplikasifootbalmatchschedule.view.match.activitiy.MatchActivity
import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.action.ViewActions.*
import org.hamcrest.CoreMatchers.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*

@RunWith(AndroidJUnit4::class)
class MatchActivityTest {

    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MatchActivity::class.java)

    @Test
    fun testBottomNavigationView() {
        Thread.sleep(1000)
        onView(withId(R.id.main_menu_match)).perform(click())

        Thread.sleep(1000)
        onView(withId(R.id.main_menu_team)).perform(click())

        Thread.sleep(1000)
        onView(withId(R.id.main_menu_match_favorites)).perform(click())

        Thread.sleep(1000)
    }

    @Test
    fun testPrevMatch() {

        Thread.sleep(1000)
        onView(withId(R.id.main_menu_match)).perform(click())

        onView(withId(R.id.matches_viewpager)).perform(swipeRight())

        Thread.sleep(1000)
        onView(withId(R.id.spinner)).check(matches(isDisplayed()))
        onView(withId(R.id.spinner)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("Spanish La Liga")))
                .perform(click())


        Thread.sleep(1000)
        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(5, click()))
        Thread.sleep(1000)

        onView(withId(R.id.mn_favorites)).check(matches(isDisplayed()))
        onView(withId(R.id.mn_favorites)).perform(click())

        Thread.sleep(1000)
        Espresso.pressBack()
//check favorites
        Thread.sleep(1000)
        onView(withId(R.id.main_menu_match_favorites)).perform(click())
        onView(withId(R.id.matches_viewpager)).perform(swipeLeft())

        Thread.sleep(1000)
        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        Thread.sleep(1000)
        onView(withId(R.id.mn_favorites)).check(matches(isDisplayed()))
        onView(withId(R.id.mn_favorites)).perform(click())

        Thread.sleep(1000)
        Espresso.pressBack()
    }


    @Test
    fun testNextMatch() {

        onView(withId(R.id.main_menu_match)).perform(click())

        Thread.sleep(1000)
        onView(withId(R.id.matches_viewpager)).perform(swipeLeft())

        onView(withId(R.id.spinner)).check(matches(isDisplayed()))
        onView(withId(R.id.spinner)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("Spanish La Liga")))
                .perform(click())

        Thread.sleep(1000)
        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click()))

        Thread.sleep(1000)
        onView(withId(R.id.mn_favorites)).check(matches(isDisplayed()))
        onView(withId(R.id.mn_favorites)).perform(click())

        Thread.sleep(1000)
        Espresso.pressBack()

//check favorites
        Thread.sleep(1000)
        onView(withId(R.id.main_menu_match_favorites)).perform(click())
        onView(withId(R.id.matches_viewpager)).perform(swipeLeft())

        Thread.sleep(1000)
        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        Thread.sleep(1000)
        onView(withId(R.id.mn_favorites)).check(matches(isDisplayed()))
        onView(withId(R.id.mn_favorites)).perform(click())

        Thread.sleep(1000)
        Espresso.pressBack()
    }


}