package com.cmdv.data.repositories

import com.cmdv.data.mappers.CharacterMapper
import com.cmdv.data.networking.ApiHandler
import com.cmdv.data.sources.apiservices.CharacterDetailsApi
import com.cmdv.domain.models.CharacterModel
import com.cmdv.domain.repositories.CharacterDetailsRepository
import com.cmdv.domain.utils.ResponseWrapper

class CharacterDetailsRepositoryImpl(
    private val characterDetailsApi: CharacterDetailsApi,
    private val apiHandler: ApiHandler
) : CharacterDetailsRepository {
    /**
     * API call to get a specific character by its ID.
     */
    override fun getCharacterById(characterId: Int): ResponseWrapper<CharacterModel> =
        apiHandler.doNetworkRequest(characterDetailsApi.getCharacterById(characterId)) { response ->
            response.data?.results?.get(0)!!.let {
                CharacterMapper.transformEntityToModel(it)
            }
        }

    /**
     * API call to get the list of comics for a specific character
     */
    override fun getComics() {
        TODO("Not yet implemented")
    }

    /**
     * API call to get the list of series for a specific character
     */
    override fun getSeries() {
        TODO("Not yet implemented")
    }

    /**
     * API call to get the list of stories for a specific character
     */
    override fun getStories() {
        TODO("Not yet implemented")
    }

    /**
     * API call to get the list of stories for a specific character
     */
    override fun getEvents() {
        TODO("Not yet implemented")
    }
}