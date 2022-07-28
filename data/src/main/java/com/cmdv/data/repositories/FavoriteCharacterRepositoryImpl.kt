package com.cmdv.data.repositories

import com.cmdv.data.entities.FavoriteCharacterRoomEntity
import com.cmdv.data.mappers.CharacterRoomMapper
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
    override fun add(
        characterId: Int,
        position: Int
    ): ResponseWrapper<Event<Int>> {
        // Create a character room entity and add it to the favorites DB
        val roomEntity = FavoriteCharacterRoomEntity(null, characterId)
        favoriteCharactersDao.insert(roomEntity)
        // Update character status as favorite in characters DB
        updateModel()
        return ResponseWrapper.success(Event(position))
    }

    /**
     * Remove a favorite character from the DB
     */
    override fun remove(
        characterId: Int,
        position: Int
    ): ResponseWrapper<Event<Int>> {
        // Delete character from favorites DB
        favoriteCharactersDao.delete(characterId)
        // Update character status as not favorite in characters DB
        updateModel()
        return ResponseWrapper.success(Event(position))
    }

    /**
     * Get all favorite characters in the DB
     */
    override fun getAll(): ResponseWrapper<List<CharacterModel>> {
        val favoriteCharacterIds = favoriteCharactersDao.getAll().map {
            it.characterId
        }
        val favoriteCharacters = charactersDao.getById(favoriteCharacterIds).distinctBy {
            it.characterId
        }.map {
            CharacterRoomMapper.transformEntityToModel(it)
        }
        return ResponseWrapper.success(favoriteCharacters)
    }

    /**
     * Remove all favorite characters in the DB
     */
    override fun removeAll(): ResponseWrapper<Event<Int>> {
        // Delete all characters from favorites DB
        favoriteCharactersDao.deleteAll()
        // Update character status in characters DB
        updateModel()
        return ResponseWrapper.success(Event(1))
    }

    /**
     * Updates the favorite status for all the stored characters in DB.
     */
    private fun updateModel() = kotlin.run {
        charactersDao.getAll().forEach {
            // If the stored character is in the favorite DB, then set it as favorite
            it.isFavorite = favoriteCharactersDao.getById(it.characterId) != null
        }
    }
}