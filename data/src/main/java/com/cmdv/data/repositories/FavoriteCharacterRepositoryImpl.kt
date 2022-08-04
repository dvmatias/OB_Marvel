package com.cmdv.data.repositories

import com.cmdv.data.entities.FavoriteCharacterRoomEntity
import com.cmdv.data.mappers.CharacterRoomMapper
import com.cmdv.data.sources.dbdaos.CharacterDao
import com.cmdv.data.sources.dbdaos.FavoriteCharacterDao
import com.cmdv.domain.models.CharacterModel
import com.cmdv.domain.repositories.FavoriteCharacterRepository
import com.cmdv.domain.utils.Event
import com.cmdv.domain.utils.FailureType
import com.cmdv.domain.utils.ResponseWrapper

/**
 * Repository class implementation. This class implements functions to handle Favorite characters requests.
 */
class FavoriteCharacterRepositoryImpl(
    private val characterDao: CharacterDao,
    private val favoriteCharacterDao: FavoriteCharacterDao
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
        favoriteCharacterDao.insert(roomEntity)
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
        favoriteCharacterDao.delete(characterId)
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
        val favoriteCharacterIds = favoriteCharacterDao.getAll().map {
            it.characterId
        }
        val favoriteCharacters = characterDao.getById(favoriteCharacterIds).distinctBy {
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
        return ResponseWrapper.success(favoriteCharacterDao.exists(characterId))
    }

    /**
     * Remove all favorite characters in the DB
     */
    override fun removeAll(): ResponseWrapper<Event<Int>> {
        // Delete all characters from favorites DB
        favoriteCharacterDao.deleteAll()
        // Update character status in characters DB
        updateModel()
        return if (favoriteCharacterDao.getAll().isEmpty()) {
            ResponseWrapper.success(Event(1))
        } else {
            ResponseWrapper.error(null, FailureType.LocalError(""))
        }
    }

    /**
     * Updates the favorite status for all the stored characters in DB.
     */
    private fun updateModel() = kotlin.run {
        characterDao.getAll().forEach { characterRoom ->
            // If the stored character is in the favorite DB, then set it as favorite
            with(characterRoom.characterId) {
                characterDao.update(isFavorite = favoriteCharacterDao.getById(this) != null, this)
            }
        }
    }
}