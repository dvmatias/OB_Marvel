package com.cmdv.domain.repositories

import com.cmdv.domain.models.CharacterModel
import com.cmdv.domain.utils.Event
import com.cmdv.domain.utils.ResponseWrapper

/**
 * Repository class interface. This class declares functions to handle Favorite Characters requests.
 */
interface FavoriteCharacterRepository {
    /**
     * Add a favorite character to the DB.
     *
     * @param characterId Character's unique identifier.
     *
     * @return An event with the updated character's adapter position.
     */
    fun add(characterId: Int, position: Int): ResponseWrapper<Event<Int>>

    /**
     * Remove a favorite character from the DB.
     *
     * @param characterId Character's unique identifier.
     * @param position Character's adapter position.
     *
     * @return An event with the updated character's adapter position.
     */
    fun remove(characterId: Int, position: Int): ResponseWrapper<Event<Int>>

    /**
     * Get all favorite characters in the DB
     *
     * @return A list with all the favorite characters.
     */
    fun getAll(): ResponseWrapper<List<CharacterModel>>

    /**
     * Check if a specific character is favorite
     *
     * @param characterId Character's unique identifier
     *
     * @return true if the character is favorite, false otherwise
     */
    fun isFavorite(characterId: Int): ResponseWrapper<Boolean>

    /**
     * Remove all favorite characters in the DB
     */
    fun removeAll(): ResponseWrapper<Event<Int>>
}