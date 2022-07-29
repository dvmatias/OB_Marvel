package com.cmdv.data.sources.apiservices

import com.cmdv.data.entities.GetCharactersResponseEntity
import com.cmdv.data.entities.GetComicsResponseEntity
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
        private const val QUERY_OFFSET = "offset"

        private const val EP_CHARACTER = ROOT_PATH.plus("characters/{$PATH_CHARACTER_ID}")
        private const val EP_CHARACTER_COMICS = ROOT_PATH.plus("characters/{$PATH_CHARACTER_ID}/comics")
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

    /**
     * API call. Makes a service call to get the response with list of character's comics.
     *
     * @param characterId The character's unique identifier.
     * @param offset Offset applied to the service query call.
     *
     * @return The list of comics for a particular character.
     */
    @GET(EP_CHARACTER_COMICS)
    fun getComicsByCharacterId(
        @Path(PATH_CHARACTER_ID) characterId: Int,
        @Query(QUERY_OFFSET) offset: Int
    ): Call<GetComicsResponseEntity>
}