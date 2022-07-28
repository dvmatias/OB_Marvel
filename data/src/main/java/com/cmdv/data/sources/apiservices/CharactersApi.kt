package com.cmdv.data.sources.apiservices

import com.cmdv.data.entities.GetCharactersResponseEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * API interface - Characters. Interface that provides service call declarations to be used in order to make API service calls.
 */
interface CharactersApi {
    companion object {
        private const val ROOT_PATH = "v1/public/"

        private const val QUERY_LIMIT = "limit"
        private const val QUERY_OFFSET = "offset"

        private const val PATH_CHARACTER_ID = "characterId"

        private const val EP_CHARACTERS = ROOT_PATH.plus("characters")
        private const val EP_CHARACTER = ROOT_PATH.plus("characters/{$PATH_CHARACTER_ID}")
    }

    /**
     * API call. Makes a service call to get the response with a list of characters.
     *
     * @param limit Limit for the list of characters.
     * @param offset Offset applied to the service query call.
     *
     * @return The list of characters requested by limit and offset.
     */
    @GET(EP_CHARACTERS)
    fun getCharacters(
        @Query(QUERY_LIMIT) limit: Int,
        @Query(QUERY_OFFSET) offset: Int
    ): Call<GetCharactersResponseEntity>

    /**
     * API call. Makes a service call to get the response with a character details.
     *
     * @param characterId The character's unique identifier.
     *
     * @return The character details.
     */
    @GET(EP_CHARACTER)
    fun getCharacterById(
        @Path(PATH_CHARACTER_ID) characterId: Int
    ): Call<GetCharactersResponseEntity>
}