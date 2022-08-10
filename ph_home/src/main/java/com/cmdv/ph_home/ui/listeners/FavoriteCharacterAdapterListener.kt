package com.cmdv.ph_home.ui.listeners

/**
 * Interface. Declares a set of actions over the list of favorite characters.
 *
 * @author matias.delv.dom@gmail.com
 */
interface FavoriteCharacterAdapterListener {
    /**
     * Executed when a character's favorite icon is clicked.
     *
     * @param id Character's unique identifier.
     * @param index Character's item position inside the list.
     */
    fun onFavoriteClick(id: Int, index: Int, name: String)
}