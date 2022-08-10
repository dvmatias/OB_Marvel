package com.cmdv.ph_home.ui.listeners

/**
 * Interface. Declares a set of actions over the list of characters.
 *
 * @author matias.delv.dom@gmail.com
 */
interface CharacterAdapterListener {
    /**
     * Executed when the list of characters displayed is not empty and needs to load more items.
     *
     * @param offset Skip the specified number of resources in the result set.
     */
    fun onLoadMoreCharacters(offset: Int)

    /**
     * Executed when a character view item is clicked.
     *
     * @param characterId Character's unique identifier.
     * @param characterName Character's name.
     */
    fun onCharacterClick(characterId: Int, characterName: String)

    /**
     * Executed when a character's favorite icon is clicked.
     *
     * @param characterId Character's unique identifier.
     * @param characterIndex Character's item position inside the list.
     * @param isFavourite true if Character is a user's favorite, false otherwise.
     */
    fun onFavoriteClick(characterId: Int, characterIndex: Int, isFavourite: Boolean)
}