package com.cmdv.domain.models

import java.io.Serializable

/**
 * Model class. This class is the UI model to represent a Marvel character's comic in the screen.
 */
data class ComicModel(
    val id: Int,
    val description: String,
    val thumbnail: String,
) : Serializable