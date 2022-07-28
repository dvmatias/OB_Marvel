package com.cmdv.domain.repositories

import com.cmdv.domain.models.CharacterModel
import com.cmdv.domain.utils.ResponseWrapper

interface CharacterDetailsRepository {
    /**
     * API call to get a specific character by its ID.
     */
    fun getCharacterById(characterId: Int): ResponseWrapper<CharacterModel>

    /**
     * API call to get the list of comics for a specific character
     */
    fun getComics(): Unit

    /**
     * API call to get the list of series for a specific character
     */
    fun getSeries(): Unit

    /**
     * API call to get the list of stories for a specific character
     */
    fun getStories(): Unit

    /**
     * API call to get the list of stories for a specific character
     */
    fun getEvents(): Unit
}