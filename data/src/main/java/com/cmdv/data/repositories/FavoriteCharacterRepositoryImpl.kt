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
     * Add a favorite character to the DB.
     *
     * @param characterId Character's unique identifier.
     *
     * @return An event with the updated character's adapter position.
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
     * Remove a favorite character from the DB.
     *
     * @param characterId Character's unique identifier.
     * @param position Character's adapter position.
     *
     * @return An event with the updated character's adapter position.
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
     *
     * @return A list with all the favorite characters.
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
     * Check if a specific character is favorite
     *
     * @param characterId Character's unique identifier
     *
     * @return true if the character is favorite, false otherwise
     */
    override fun isFavorite(characterId: Int): ResponseWrapper<Boolean> {
        return ResponseWrapper.success(favoriteCharactersDao.exists(characterId))
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
        charactersDao.getAll().forEach { characterRoom ->
            // If the stored character is in the favorite DB, then set it as favorite
            with(characterRoom.characterId) {
                charactersDao.update(isFavorite = favoriteCharactersDao.getById(this) != null, this)
            }
        }
    }
}