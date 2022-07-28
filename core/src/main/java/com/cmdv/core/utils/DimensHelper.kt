package com.cmdv.core.utils

import android.content.Context
import android.util.TypedValue

/**
 * Helper object to convert dimens.
 */
object DimensHelper {
    /**
     * Convert DP to PX.
     *
     * @param context
     * @param dp DP [Float] value to be converted.
     */
    fun dpToPx(context: Context, dp: Float): Float =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics)

    /**
     * Convert PX to DP.
     *
     * @param context
     * @param px PX [Int] value to be converted.
     */
    fun pxToDp(context: Context, px: Int): Int {
        val r = context.resources
        return Math.round(px / (r.displayMetrics.densityDpi / 160f))
    }

    fun pxToActualDp(context: Context, px: Int): Float {
        val r = context.resources
        return px / (r.displayMetrics.densityDpi / 160f)
    }

    fun spToPx(context: Context, sp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.resources.displayMetrics)
    }

    fun pxToSp(context: Context, px: Float): Float {
        return px / context.resources.displayMetrics.scaledDensity
    }
}