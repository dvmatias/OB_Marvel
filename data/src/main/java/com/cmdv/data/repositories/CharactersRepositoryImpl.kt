package com.cmdv.data.repositories

import com.cmdv.data.errorhandling.CharactersApiErrorHandler
import com.cmdv.data.mappers.CharacterRoomMapper
import com.cmdv.data.mappers.GetCharactersResponseMapper
import com.cmdv.data.networking.ApiHandler
import com.cmdv.data.sources.apiservices.CharactersApi
import com.cmdv.data.sources.dbdaos.CharactersDao
import com.cmdv.data.sources.dbdaos.FavoriteCharactersDao
import com.cmdv.domain.models.CharacterModel
import com.cmdv.domain.repositories.CharactersRepository
import com.cmdv.domain.utils.ResponseWrapper

/**
 * Repository class implementation. This class implements functions to handle Characters requests.
 *
 * @author matias.delv.dom@gmail.com
 */
class CharactersRepositoryImpl(
    private val charactersApi: CharactersApi,
    private val charactersDao: CharactersDao,
    private val favoriteCharactersDao: FavoriteCharactersDao,
    private val apiHandler: ApiHandler,
    private val errorHandler: CharactersApiErrorHandler
) : CharactersRepository {
    /**
     * Get the total amount of characters available in Marvel's API.
     */
    override fun getTotalCharactersCount(): ResponseWrapper<Int> =
        apiHandler.doNetworkRequest(charactersApi.getCharacters(1, 0), errorHandler) {
            it.data?.total ?: 0
        }

    /**
     * Returns a list with Marvel's characters.
     *
     * @param fetch If true, fetch from service call, if not, fetch from DB.
     * @param limit Limit the result set to the specified number of resources (applied only to service call).
     * @param offset Skip the specified number of resources in the result set (applied only to service call).
     */
    override fun getCharacters(fetch: Boolean, limit: Int, offset: Int): ResponseWrapper<List<CharacterModel>> {
        // If fetch true or no stored characters found
        if (fetch || getStoredCharacters().isEmpty()) {
            // Get characters from service call
            val fetchedCharacters = fetchCharacters(limit, offset)
            // Store characters in DB
            fetchedCharacters.data?.let { data -> storeCharacters(data) }
            updateModel()

            return when(fetchedCharacters.status) {
                ResponseWrapper.Status.LOADING -> ResponseWrapper.loading()
                ResponseWrapper.Status.ERROR -> ResponseWrapper.error(getStoredCharacters(), fetchedCharacters.failureType!!)
                ResponseWrapper.Status.READY -> ResponseWrapper.success(getStoredCharacters())
            }
        }
        updateModel()
        return ResponseWrapper.success(getStoredCharacters())
    }

    /**
     * Removed all stored characters in DB.
     */
    override fun removeStoredCharacters() {
        charactersDao.deleteAll()
    }

    /**
     * Fetch Marvel's characters from Marvel's API service call.
     *
     * @param limit Limit the result set to the specified number of resources (applied only to service call).
     * @param offset Skip the specified number of resources in the result set (applied only to service call).
     */
    private fun fetchCharacters(limit: Int, offset: Int): ResponseWrapper<ArrayList<CharacterModel>> =
        apiHandler.doNetworkRequest(charactersApi.getCharacters(limit, offset)) { response ->
            GetCharactersResponseMapper.transformEntityToModel(response).characters
        }

    /**
     * Get characters stored in DB.
     */
    private fun getStoredCharacters(): List<CharacterModel> =
        charactersDao.getAll().map {
            CharacterRoomMapper.transformEntityToModel(it)
        }

    /**
     * Store characters in DB.
     */
    private fun storeCharacters(characters: List<CharacterModel>) {
        characters.map {
            CharacterRoomMapper.transformModelToEntity(it)
        }.also {
            charactersDao.insert(it)
        }
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