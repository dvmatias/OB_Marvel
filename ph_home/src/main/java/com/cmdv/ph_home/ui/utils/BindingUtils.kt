package com.cmdv.ph_home.ui.utils

import android.graphics.Typeface
import android.graphics.drawable.AnimatedVectorDrawable
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cmdv.common.R
import com.cmdv.common.extensions.secureUrl
import com.cmdv.domain.models.CharacterModel
import com.cmdv.domain.utils.ResponseWrapper.Status
import com.cmdv.domain.utils.ResponseWrapper.Status.*
import com.cmdv.ph_home.ui.adapters.CharacterAdapter
import com.cmdv.ph_home.ui.adapters.IndexFavoriteCharacterAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
    viewModelStatus: Status,
    adapter: CharacterAdapter
) {
    if (viewModelStatus == LOADING) {
        view?.visibility = if (adapter.isEmpty()) View.VISIBLE else View.GONE
        adapter.isLoading = !adapter.isEmpty()
    } else {
        view?.visibility = View.GONE
        adapter.isLoading = false
    }
}

@BindingAdapter("loadFavoriteCharacters")
fun RecyclerView.loadFavoriteCharacters(
    data: MutableList<CharacterModel>?
) {
    adapter.run {
        if (this is IndexFavoriteCharacterAdapter && data != null) {
            if (data.isEmpty()) clear() else setCharacters(data)
        }
    }
}

@BindingAdapter("characterItemDescription")
fun bindCharacterItemImage(textView: TextView, description: String) {
    if (description.isBlank()) {
        textView.apply {
            text = textView.context.getString(R.string.text_item_character_no_description)
            alpha = 0.7F
            setTypeface(textView.typeface, Typeface.ITALIC)
        }
    } else {
        textView.text = description
    }
}

@BindingAdapter(value = ["viewModelStatus", "favoriteCharacters"], requireAll = true)
fun favoritesEmptyStateVisibility(
    view: ImageView?,
    viewModelStatus: Status,
    favoriteCharacters: List<CharacterModel>?
) {
    when (viewModelStatus) {
        LOADING,
        ERROR -> view?.visibility = View.GONE
        READY -> {
            if (favoriteCharacters?.isEmpty() == true) {
                view?.run {
                    CoroutineScope(Dispatchers.Main.immediate).launch {
                        setImageDrawable(null)
                        visibility = View.VISIBLE
                        delay(150)
                        setImageDrawable(
                            androidx.core.content.ContextCompat.getDrawable(
                                view.context,
                                R.drawable.animated_empty_state_spider_man
                            )
                        )
                        (drawable as AnimatedVectorDrawable).start()
                    }
                }
            } else {
                view?.visibility = View.GONE
            }
        }
    }
}