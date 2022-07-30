package com.cmdv.common.extensions

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.core.app.ActivityCompat

/**
 * Extension function. Activity.
 *
 * This function allows activities to navigate to a different activity. This function should never be called outside
 * [NavigatorImpl] implemented functions.
 *
 * @param data Bundle to pass from calling Activity to launched Activity.
 * @param finish If true calling Activity finishes.
 * @param A Calling Activity. Marked as reified since the app needs to access the type of the calling Activity class.
 */
@Suppress("KDocUnresolvedReference")
inline fun <reified A : Activity> Activity.navigateTo(data: Bundle?, finishPrevious: Boolean) {
    Intent(this, A::class.java).also { i ->
        data?.let { b -> i.putExtras(b) }
        overridePendingTransition(0, 0)
        ActivityCompat.startActivity(this, i, null)
        if (finishPrevious) this.finish()
    }
}