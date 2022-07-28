package com.cmdv.ph_home.ui.itemdecorators

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.cmdv.core.utils.DimensHelper

/**
 * Decorator class to be applied in [com.cmdv.domain.models.IndexedFavoriteCharactersModel] items.
 */
class IndexFavoriteCharacterDecorator : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        // Add a space between character and index view if this is the first favorite character after the index view.
        outRect.top =
            if (parent.getChildAdapterPosition(view) == 0) DimensHelper.dpToPx(view.context, 36F).toInt() else 0
        outRect.set(0, outRect.top, 0, 0)
    }
}