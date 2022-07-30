package com.cmdv.domain.repositories

import com.cmdv.domain.models.CharacterModel
import com.cmdv.domain.utils.Event
import com.cmdv.domain.utils.ResponseWrapper

/**
 * Repository class interface. This class declares functions to handle Favorite Characters requests.
 */
interface FavoriteCharacterRepository {
    /**
     * Add a favorite character to the DB
     */
    fun add(characterId: Int, position: Int): ResponseWrapper<Event<Int>>

    /**
     * Remove a favorite character from the DB
     */
    fun remove(characterId: Int, position: Int): ResponseWrapper<Event<Int>>

    /**
     * Get all favorite characters in the DB
     */
    fun getAll(): ResponseWrapper<List<CharacterModel>>

    /**
     * Get all favorite characters in the DB
     */
    fun isFavorite(characterId: Int): ResponseWrapper<Boolean>

    /**
     * Remove all favorite characters in the DB
     */
    fun removeAll(): ResponseWrapper<Event<Int>>
}