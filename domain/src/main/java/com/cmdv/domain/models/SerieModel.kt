package com.cmdv.domain.models

import java.io.Serializable

/**
 * Model class. This class is the UI model to represent a Marvel character's serie in the screen.
 */
data class SerieModel(
    val id: Int,
    val description: String,
    val thumbnail: String,
    val title: String
) : Serializable