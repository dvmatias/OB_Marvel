package com.cmdv.ph_home.ui.layoutmanagers

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import com.cmdv.ph_home.ui.adapters.CharacterAdapter

private const val SPAN_COUNT_CHARACTER = 4
private const val SPAN_COUNT_HEADER = 1
private const val POSITION_HEADER = 0

/**
 * Layout manager for the recycler view that displays the list of characters. This layout manager is a subclass of
 * [GridLayoutManager]. It shows characters in a grid with 4 rows.
 *
 * @see SPAN_COUNT_CHARACTER
 */
class CharacterLayoutManager(context: Context, adapter: CharacterAdapter) :
    GridLayoutManager(context, SPAN_COUNT_CHARACTER) {

    init {
        this.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (position == (adapter.itemCount - 1) || position == POSITION_HEADER) {
                    true -> SPAN_COUNT_CHARACTER
                    false -> SPAN_COUNT_HEADER
                }
            }
        }
    }

}