package com.cmdv.ph_character_details.ui.listeners

/**
 * Interface. Declares a set of actions over the character details.
 *
 * @author matias.delv.dom@gmail.com
 */
interface CharacterDetailsListener {
    /**
     * Executed when a character's favorite icon is clicked.
     *
     * @param id Character's unique identifier.
     * @param isFavorite Character's fav current status.
     */
    fun onFavoriteClick(id: Int, isFavorite: Boolean)
}