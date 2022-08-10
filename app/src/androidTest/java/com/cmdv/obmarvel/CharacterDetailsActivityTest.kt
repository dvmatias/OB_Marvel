package com.cmdv.obmarvel

import WaitUntilVisibleAction
import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.cmdv.common.KEY_CHARACTER_ID_ARG
import com.cmdv.common.KEY_CHARACTER_NAME_ARG
import com.cmdv.ph_character_details.CharacterDetailsActivity
import com.cmdv.ph_character_details.R
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


private const val CHARACTER_ID = 1017100
private const val CHARACTER_NAME = "A-Bomb (HAS)"

class CharacterDetailsActivityTest {
    private lateinit var intent: Intent

    @get:Rule
    var activityRule: ActivityTestRule<CharacterDetailsActivity> =
        ActivityTestRule(CharacterDetailsActivity::class.java, false, false)

    @Before
    fun setupTest() {
        intent = Intent()
        intent.putExtra(KEY_CHARACTER_ID_ARG, CHARACTER_ID)
        intent.putExtra(KEY_CHARACTER_NAME_ARG, CHARACTER_NAME)
    }

    @After
    fun breakDown() {
    }

    @Test
    fun test_loading_state() {
        activityRule.launchActivity(intent)
        // Check toolbar views
        onView(withId(R.id.appbar)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewCharacterName)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewCharacterName)).check(matches(withText(CHARACTER_NAME)))
        onView(withId(R.id.imageViewIsFavorite)).check(matches(not(isDisplayed())))
        // Check content view
        onView(withId(R.id.content)).check(matches(not(isDisplayed())))
        // Check loading view
        onView(withId(R.id.layoutLoading)).check(matches(isDisplayed()))
        // Check error view
        onView(withId(R.id.layoutError)).check(matches(not(isDisplayed())))
    }

    @Test
    fun test_success_state() {
        activityRule.launchActivity(intent)
        onView(withId(R.id.content)).perform(waitUntilVisible(15000L))

        // Check loading view
        onView(withId(R.id.layoutLoading)).check(matches(not(isDisplayed())))
        // Check error view
        onView(withId(R.id.layoutError)).check(matches(not(isDisplayed())))
        // Check content view
        onView(withId(R.id.content)).check(matches(isDisplayed()))
        onView(withId(R.id.imageViewThumbnail)).check(matches(isDisplayed()))
        onView(withId(R.id.imageViewThumbnail)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewDescriptionTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewDescriptionData)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewDescriptionData)).check(matches(withText("Rick Jones has been Hulk's best bud since day one, but now he's more than a friend...he's a teammate! Transformed by a Gamma energy explosion, A-Bomb's thick, armored skin is just as strong and powerful as it is blue. And when he curls into action, he uses it like a giant bowling ball of destruction! ")))
    }

    @Test
    fun test_error_state() {
        // TODO
    }

    @Test
    fun test_error_toolbar_fav_functionality() {
        // TODO
    }

    @Test
    fun test_error_toolbar_on_back_functionality() {
        // TODO
    }

    /**
     * @return a [WaitUntilVisibleAction] instance created with the given [timeout] parameter.
     */
    private fun waitUntilVisible(timeout: Long): ViewAction {
        return WaitUntilVisibleAction(timeout)
    }
}

