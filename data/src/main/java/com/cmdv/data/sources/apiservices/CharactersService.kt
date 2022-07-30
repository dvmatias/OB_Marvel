package com.cmdv.data.sources.apiservices

import com.cmdv.data.entities.GetCharactersResponseEntity
import retrofit2.Call
import retrofit2.Retrofit

/**
 *  API implementation - Characters. This class is an implementation for [CharactersApi].
 */
class CharactersService(private val retrofit: Retrofit) : CharactersApi {
    /**
     * Lazily creates a [Retrofit] instance from [CharactersApi] class.
     */
    private val charactersService by lazy { retrofit.create(CharactersApi::class.java) }

    /**
     * API call. Makes a service call to get the response with a list of characters.
     *
     * @param limit Limit for the list of characters.
     * @param offset Offset applied to the service query call.
     *
     * @return The list of characters requested by limit and offset.
     */
    override fun getCharacters(limit: Int, offset: Int): Call<GetCharactersResponseEntity> =
        charactersService.getCharacters(limit, offset)

    override fun getCharacterById(characterId: Int): Call<GetCharactersResponseEntity> =
        charactersService.getCharacterById(characterId)
}