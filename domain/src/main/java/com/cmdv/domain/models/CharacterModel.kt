package com.cmdv.domain.models

import java.io.Serializable

/**
 * TODO
 */
data class CharacterModel(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: String,
    var isFavourite: Boolean
) : Serializable