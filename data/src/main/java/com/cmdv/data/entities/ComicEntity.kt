package com.cmdv.data.entities

import com.google.gson.annotations.SerializedName

/**
 *
 */
data class ComicEntity(
    @SerializedName("id") val id: Int?,
    @SerializedName("description") val description: String?,
    @SerializedName("thumbnail") val thumbnail: ThumbnailEntity?,
    @SerializedName("title") val title: String?
) {
    data class ThumbnailEntity(
        @SerializedName("path") val path: String?,
        @SerializedName("extension") val extension: String?
    )
}