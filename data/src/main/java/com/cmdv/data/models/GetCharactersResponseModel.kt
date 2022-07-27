package com.cmdv.data.models

import com.google.gson.annotations.SerializedName

/**
 *  Be model response class. This class represents the response of a particular API service response.
 */
data class GetCharactersResponseModel(
    @SerializedName("code") val code: Int?,
    @SerializedName("status") val status: String?,
    @SerializedName("etag") val etag: String?,
    @SerializedName("data") val data: DataModel?
) {
    data class DataModel(
        @SerializedName("offset") val offset: Int?,
        @SerializedName("limit") val limit: Int?,
        @SerializedName("total") val total: Int?,
        @SerializedName("count") val count: Int?,
        @SerializedName("results") val results: ArrayList<CharacterModel>?
    )
}