package com.cmdv.ph_character_details.ui.utils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cmdv.domain.models.ComicModel
import com.cmdv.ph_character_details.ui.adapters.ComicAdapter

@BindingAdapter("loadComics")
fun RecyclerView.loadComics(
    data: MutableList<ComicModel>?
) {
    adapter.run {
        if (this is ComicAdapter && data != null) {
            addItems(data)
            visibility = android.view.View.VISIBLE
        }
    }
}
