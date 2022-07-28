package com.cmdv.data.sources.apiservices

import com.cmdv.data.entities.GetCharactersResponseEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * API interface - Character Details. Interface that provides service call declarations to be used in order to make API
 * service calls.
 */
interface CharacterDetailsApi {
    companion object {
        private const val ROOT_PATH = "v1/public/"

        private const val PATH_CHARACTER_ID = "characterId"

        private const val EP_CHARACTER = ROOT_PATH.plus("characters/{$PATH_CHARACTER_ID}")
    }

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