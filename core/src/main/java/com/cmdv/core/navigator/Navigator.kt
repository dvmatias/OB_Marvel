package com.cmdv.core.navigator

import android.app.Activity
import android.os.Bundle

/**
 * Contract. Declares all the functions to navigate between activities from different modules/features.
 */
interface Navigator {
    fun toCharacterDetails(origin: Activity, bundle: Bundle, finishPrevious: Boolean)
    fun toSearchCharacter(origin: Activity, bundle: Bundle?, finishPrevious: Boolean)
}