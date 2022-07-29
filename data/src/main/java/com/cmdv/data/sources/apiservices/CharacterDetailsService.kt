package com.cmdv.data.sources.apiservices

import com.cmdv.data.entities.GetCharactersResponseEntity
import com.cmdv.data.entities.GetComicsResponseEntity
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

    /**
     * API call. Makes a service call to get the response with list of character's comics.
     *
     * @param characterId The character's unique identifier.
     * @param offset Offset applied to the service query call.
     *
     * @return The list of comics for a particular character.
     */
    override fun getComicsByCharacterId(characterId: Int, offset: Int): Call<GetComicsResponseEntity> =
        charactersService.getComicsByCharacterId(characterId, offset)
}