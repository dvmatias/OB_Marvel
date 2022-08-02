package com.cmdv.data.repositories

import com.cmdv.data.errorhandling.CharacterDetailsApiErrorHandler
import com.cmdv.data.mappers.CharacterMapper
import com.cmdv.data.mappers.GetComicsResponseMapper
import com.cmdv.data.mappers.GetSeriesResponseMapper
import com.cmdv.data.networking.ApiHandler
import com.cmdv.data.sources.apiservices.CharacterDetailsApi
import com.cmdv.domain.models.CharacterModel
import com.cmdv.domain.models.ComicModel
import com.cmdv.domain.models.SerieModel
import com.cmdv.domain.repositories.CharacterDetailsRepository
import com.cmdv.domain.utils.ResponseWrapper

class CharacterDetailsRepositoryImpl(
    private val characterDetailsApi: CharacterDetailsApi,
    private val apiHandler: ApiHandler,
    private val errorHandler: CharacterDetailsApiErrorHandler
) : CharacterDetailsRepository {
    /**
     * API call to get a specific character by its ID.
     */
    override fun getCharacterById(characterId: Int): ResponseWrapper<CharacterModel> =
        apiHandler.doNetworkRequest(characterDetailsApi.getCharacterById(characterId), errorHandler) { response ->
            response.data?.results?.get(0)!!.let {
                CharacterMapper.transformEntityToModel(it)
            }
        }

    /**
     * API call to get the list of comics for a specific character
     *
     * @param characterId The character's unique identifier.
     * @param offset Offset applied to the service query call.
     */
    override fun getComics(characterId: Int, offset: Int): ResponseWrapper<List<ComicModel>> =
        apiHandler.doNetworkRequest(characterDetailsApi.getComicsByCharacterId(characterId, offset), errorHandler) { response ->
            GetComicsResponseMapper.transformEntityToModel(response).comics
        }

    /**
     * API call to get the list of series for a specific character
     *
     * @param characterId The character's unique identifier.
     * @param offset Offset applied to the service query call.
     */
    override fun getSeries(characterId: Int, offset: Int): ResponseWrapper<List<SerieModel>> =
        apiHandler.doNetworkRequest(characterDetailsApi.getSeriesByCharacterId(characterId, offset), errorHandler) { response ->
            GetSeriesResponseMapper.transformEntityToModel(response).series
        }

    /**
     * API call to get the list of stories for a specific character
     *
     * @param characterId The character's unique identifier.
     * @param offset Offset applied to the service query call.
     */
    override fun getStories(characterId: Int, offset: Int) {
        TODO("Not yet implemented")
    }

    /**
     * API call to get the list of events for a specific character
     *
     * @param characterId The character's unique identifier.
     * @param offset Offset applied to the service query call.
     */
    override fun getEvents(characterId: Int, offset: Int) {
        TODO("Not yet implemented")
    }
}