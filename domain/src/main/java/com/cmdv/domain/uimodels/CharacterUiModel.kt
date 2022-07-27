package com.cmdv.domain.uimodels

import java.io.Serializable

data class CharacterUiModel(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: String,
    var isFavourite: Boolean
) : Serializable