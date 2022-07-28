package com.cmdv.ph_home.ui.listeners

/**
 * Interface. This interface declares a set of actions over the list of characters.
 */
interface CharacterAdapterListener {
    /**
     * Executed when the list of characters displayed is not empty and needs to load more items.
     */
    fun onLoadMoreCharacters(offset: Int)

    /**
     * Executed when a character view item is clicked.
     */
    fun onCharacterClick(characterId: Int)

    /**
     * Executed when a character's favorite icon is clicked.
     */
    fun onFavoriteClick(characterId: Int, characterIndex: Int, isFavourite: Boolean)
}