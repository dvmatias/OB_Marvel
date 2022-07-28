package com.cmdv.data.repositories

import com.cmdv.data.sources.dbdaos.CharactersDao
import com.cmdv.data.sources.dbdaos.FavoriteCharactersDao
import com.cmdv.domain.models.CharacterModel
import com.cmdv.domain.repositories.FavoriteCharacterRepository
import com.cmdv.domain.utils.Event
import com.cmdv.domain.utils.ResponseWrapper

/**
 * Repository class implementation. This class implements functions to handle Favorite characters requests.
 */
class FavoriteCharacterRepositoryImpl(
    private val charactersDao: CharactersDao,
    private val favoriteCharactersDao: FavoriteCharactersDao
) : FavoriteCharacterRepository {
    /**
     * Add a favorite character to the DB
     */
    override fun add(characterId: Int, position: Int): ResponseWrapper<Event<Int>> {
        TODO("Not yet implemented")
    }

    /**
     * Remove a favorite character from the DB
     */
    override fun remove(characterId: Int, position: Int): ResponseWrapper<Event<Int>> {
        TODO("Not yet implemented")
    }

    /**
     * Get all favorite characters in the DB
     */
    override fun getAll(): ResponseWrapper<List<CharacterModel>> {
        TODO("Not yet implemented")
    }

    /**
     * Remove all favorite characters in the DB
     */
    override fun removeAll(): ResponseWrapper<Event<Int>> {
        TODO("Not yet implemented")
    }

}