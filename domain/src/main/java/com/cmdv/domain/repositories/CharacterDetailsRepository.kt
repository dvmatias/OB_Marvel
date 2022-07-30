package com.cmdv.domain.repositories

import com.cmdv.domain.models.CharacterModel
import com.cmdv.domain.models.ComicModel
import com.cmdv.domain.models.SerieModel
import com.cmdv.domain.utils.ResponseWrapper

interface CharacterDetailsRepository {
    /**
     * API call to get a specific character by its ID.
     *
     * @param characterId The character's unique identifier.
     */
    fun getCharacterById(characterId: Int): ResponseWrapper<CharacterModel>

    /**
     * API call to get the list of comics for a specific character
     *
     * @param characterId The character's unique identifier.
     * @param offset Offset applied to the service query call.
     */
    fun getComics(characterId: Int, offset: Int): ResponseWrapper<List<ComicModel>>

    /**
     * API call to get the list of series for a specific character
     *
     * @param characterId The character's unique identifier.
     * @param offset Offset applied to the service query call.
     */
    fun getSeries(characterId: Int, offset: Int): ResponseWrapper<List<SerieModel>>

    /**
     * API call to get the list of stories for a specific character
     *
     * @param characterId The character's unique identifier.
     * @param offset Offset applied to the service query call.
     */
    fun getStories(characterId: Int, offset: Int): Unit

    /**
     * API call to get the list of stories for a specific character
     *
     * @param characterId The character's unique identifier.
     * @param offset Offset applied to the service query call.
     */
    fun getEvents(characterId: Int, offset: Int): Unit
}