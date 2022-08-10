package com.cmdv.domain.models

import java.io.Serializable

/**
 * Model class. This class is the UI model to represent a Marvel's character in the screen.
 */
data class CharacterModel(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: String,
    var isFavorite: Boolean,
    val comicsCount: Int,
    val seriesCount: Int,
    val storiesCount: Int
) : Serializable