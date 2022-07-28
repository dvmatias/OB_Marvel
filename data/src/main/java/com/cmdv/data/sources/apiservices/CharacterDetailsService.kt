package com.cmdv.data.sources.apiservices

import com.cmdv.data.entities.GetCharactersResponseEntity
import retrofit2.Call
import retrofit2.Retrofit

/**
 *  API implementation - Character Details. This class is an implementation for [CharacterDetailsApi].
 */
class CharacterDetailsService(private val retrofit: Retrofit) : CharacterDetailsApi {
    /**
     * Lazily creates a [Retrofit] instance from [CharacterDetailsApi] class.
     */
    private val charactersService by lazy { retrofit.create(CharacterDetailsApi::class.java) }

    /**
     * API call. Makes a service call to get the response with a character details.
     *
     * @param characterId The character's unique identifier.
     *
     * @return The character details.
     */
    override fun getCharacterById(characterId: Int): Call<GetCharactersResponseEntity> =
        charactersService.getCharacterById(characterId)
}