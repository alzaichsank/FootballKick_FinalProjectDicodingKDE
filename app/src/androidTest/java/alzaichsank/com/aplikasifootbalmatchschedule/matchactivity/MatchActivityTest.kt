package alzaichsank.com.aplikasifootbalmatchschedule.matchactivity


import alzaichsank.com.aplikasifootbalmatchschedule.R.id.*
import alzaichsank.com.aplikasifootbalmatchschedule.view.match.activitiy.MatchActivity
import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import org.hamcrest.CoreMatchers.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MatchActivityTest {

    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MatchActivity::class.java)

    @Test
    fun testBottomNavigationView() {
        Thread.sleep(1000)
        onView(withId(main_menu_match)).perform(click())

        Thread.sleep(1000)
        onView(withId(main_menu_team)).perform(click())

        Thread.sleep(1000)
        onView(withId(main_menu_favorites)).perform(click())

        Thread.sleep(1000)
    }

    @Test
    fun testPrevMatch() {

        Thread.sleep(1000)
        onView(withId(main_menu_match)).perform(click())

        onView(withId(matches_viewpager)).perform(swipeLeft())
        Thread.sleep(2000)
        onView(allOf(withId(spinner), isCompletelyDisplayed()))
                .check(matches(withEffectiveVisibility(Visibility.VISIBLE))).perform(click())

        onData(allOf(`is`(instanceOf(String::class.java)), `is`("Spanish La Liga")))
                .perform(click())
        onView(allOf(withId(recyclerviewMain), isCompletelyDisplayed()))
                .check(matches(withEffectiveVisibility(Visibility.VISIBLE))).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click()))

        onView(allOf(withId(mn_favorites), isCompletelyDisplayed()))
                .check(matches(withEffectiveVisibility(Visibility.VISIBLE))).perform(click())

        Thread.sleep(1000)
        Espresso.pressBack()
//check favorites
        Thread.sleep(1000)
        onView(withId(main_menu_favorites)).perform(click())


        onView(allOf(withId(recyclerview), isCompletelyDisplayed()))
                .check(matches(withEffectiveVisibility(Visibility.VISIBLE))).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(allOf(withId(mn_favorites), isCompletelyDisplayed()))
                .check(matches(withEffectiveVisibility(Visibility.VISIBLE))).perform(click())

        Thread.sleep(1000)
        Espresso.pressBack()
    }


    @Test
    fun testNextMatch() {

        Thread.sleep(1000)
        onView(withId(main_menu_match)).perform(click())

        Thread.sleep(2000)
        onView(allOf(withId(spinner), isCompletelyDisplayed()))
                .check(matches(withEffectiveVisibility(Visibility.VISIBLE))).perform(click())

        onData(allOf(`is`(instanceOf(String::class.java)), `is`("Spanish La Liga")))
                .perform(click())
        onView(allOf(withId(recyclerviewMain), isCompletelyDisplayed()))
                .check(matches(withEffectiveVisibility(Visibility.VISIBLE))).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click()))

        onView(allOf(withId(mn_favorites), isCompletelyDisplayed()))
                .check(matches(withEffectiveVisibility(Visibility.VISIBLE))).perform(click())

        Thread.sleep(1000)
        Espresso.pressBack()
//check favorites
        Thread.sleep(1000)
        onView(withId(main_menu_favorites)).perform(click())


        onView(allOf(withId(recyclerview), isCompletelyDisplayed()))
                .check(matches(withEffectiveVisibility(Visibility.VISIBLE))).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(allOf(withId(mn_favorites), isCompletelyDisplayed()))
                .check(matches(withEffectiveVisibility(Visibility.VISIBLE))).perform(click())

        Thread.sleep(1000)
        Espresso.pressBack()
    }


}