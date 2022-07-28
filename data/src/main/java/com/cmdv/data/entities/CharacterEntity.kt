package com.cmdv.data.entities

import com.google.gson.annotations.SerializedName

/**
 * Business Entity class. This class represents a single Character backend Entity.
 */
data class CharacterEntity(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("modified") val modified: String?,
    @SerializedName("resourceURI") val resourceURI: String?,
    @SerializedName("urls") val urls: List<UrlEntity>?,
    @SerializedName("thumbnail") val thumbnail: ThumbnailEntity?,
    @SerializedName("comics") val comics: ComicsEntity?,
    @SerializedName("stories") val stories: StoriesEntity?,
    @SerializedName("events") val events: EventsEntity?,
    @SerializedName("series") val series: SeriesEntity?,
) {
    data class UrlEntity(
        @SerializedName("type") val type: String?,
        @SerializedName("url") val url: String?
    )

    data class ThumbnailEntity(
        @SerializedName("path") val path: String?,
        @SerializedName("extension") val extension: String?
    )

    data class ComicsEntity(
        @SerializedName("available") val available: Int?,
        @SerializedName("returned") val returned: Int?,
        @SerializedName("collectionURI") val collectionURI: String?,
        @SerializedName("items") val items: List<ItemEntity>?
    ) {
        data class ItemEntity(
            @SerializedName("resourceURI") val resourceURI: String?,
            @SerializedName("name") val name: String?
        )
    }

    data class StoriesEntity(
        @SerializedName("available") val available: Int?,
        @SerializedName("returned") val returned: Int?,
        @SerializedName("collectionURI") val collectionURI: String?,
        @SerializedName("items") val items: List<ItemEntity>?
    ) {
        data class ItemEntity(
            @SerializedName("resourceURI") val resourceURI: String?,
            @SerializedName("name") val name: String?,
            @SerializedName("type") val type: String?
        )
    }

    data class EventsEntity(
        @SerializedName("available") val available: Int?,
        @SerializedName("returned") val returned: Int?,
        @SerializedName("collectionURI") val collectionURI: String?,
        @SerializedName("items") val items: List<ItemEntity>?
    ) {
        data class ItemEntity(
            @SerializedName("resourceURI") val resourceURI: String?,
            @SerializedName("name") val name: String?
        )
    }

    data class SeriesEntity(
        @SerializedName("available") val available: Int?,
        @SerializedName("returned") val returned: Int?,
        @SerializedName("collectionURI") val collectionURI: String?,
        @SerializedName("items") val items: List<ItemEntity>?
    ) {
        data class ItemEntity(
            @SerializedName("resourceURI") val resourceURI: String?,
            @SerializedName("name") val name: String?
        )
    }
}