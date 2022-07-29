package com.cmdv.core.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.cmdv.common.R
import com.cmdv.common.extensions.secureUrl

@BindingAdapter("loadCharacterImage")
fun loadCharacterImage(imageView: ImageView, imageUrl: String?) {
    imageUrl?.let {
        Glide.with(imageView.context)
            .load(it.secureUrl())
            .placeholder(R.drawable.img_mock_character)
            .dontAnimate()
            .centerCrop()
            .into(imageView)
    }
}