package com.cmdv.ph_home.ui.utils

import android.view.View
import android.widget.FrameLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cmdv.domain.models.CharacterModel
import com.cmdv.domain.utils.ResponseWrapper
import com.cmdv.ph_home.ui.adapters.CharacterAdapter

@BindingAdapter("loadCharacters")
fun RecyclerView.loadCharacters(
    data: MutableList<CharacterModel>?
) {
    adapter.run {
        if (this is CharacterAdapter && data != null) {
            addItems(data)
            isLoading = false
            visibility = View.VISIBLE
        }
    }
}

@BindingAdapter(value = ["viewModelStatus", "adapter"], requireAll = false)
fun loadingVisibility(
    view: FrameLayout?,
    viewModelStatus: ResponseWrapper.Status,
    adapter: CharacterAdapter
) {
    if (viewModelStatus == ResponseWrapper.Status.LOADING) {
        view?.visibility = if (adapter.isEmpty()) View.VISIBLE else View.GONE
        adapter.isLoading = !adapter.isEmpty()
    } else {
        view?.visibility = View.GONE
        adapter.isLoading = false
    }
}