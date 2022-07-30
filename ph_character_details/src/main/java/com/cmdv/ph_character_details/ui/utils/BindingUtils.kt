package com.cmdv.ph_character_details.ui.utils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cmdv.domain.models.ComicModel
import com.cmdv.domain.models.SerieModel
import com.cmdv.ph_character_details.ui.adapters.ComicAdapter
import com.cmdv.ph_character_details.ui.adapters.SerieAdapter

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

@BindingAdapter("loadSeries")
fun RecyclerView.loadSeries(
    data: MutableList<SerieModel>?
) {
    adapter.run {
        if (this is SerieAdapter && data != null) {
            addItems(data)
            visibility = android.view.View.VISIBLE
        }
    }
}
