package com.cmdv.obmarvel.navigator

import android.app.Activity
import android.os.Bundle
import com.cmdv.common.extensions.navigateTo
import com.cmdv.core.navigator.Navigator
import com.cmdv.ph_character_details.CharacterDetailsActivity
import com.cmdv.ph_search_character.SearchCharacterActivity

/**
 * Implementation class for [Navigator] contract class.
 *
 * This class holds all the implementations for the navigation functions declared in super class. Each Activity that
 * needs to launch another Activity must use a function declared implemented in this class.
 */
class NavigatorImpl : Navigator {
    override fun toCharacterDetails(origin: Activity, bundle: Bundle, finishPrevious: Boolean) {
        origin.navigateTo<CharacterDetailsActivity>(bundle, finishPrevious)
    }

    override fun toSearchCharacter(origin: Activity, bundle: Bundle?, finishPrevious: Boolean) {
        origin.navigateTo<SearchCharacterActivity>(bundle, finishPrevious)
    }
}