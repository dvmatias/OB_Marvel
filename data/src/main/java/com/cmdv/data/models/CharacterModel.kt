package com.cmdv.data.models

import com.google.gson.annotations.SerializedName

/**
 * BE model class. This class represents a single Character BE model.
 */
data class CharacterModel(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("modified") val modified: String?,
    @SerializedName("resourceURI") val resourceURI: String?,
    @SerializedName("urls") val urls: List<UrlModel>?,
    @SerializedName("thumbnail") val thumbnail: ThumbnailModel?,
    @SerializedName("comics") val comics: ComicsModel?,
    @SerializedName("stories") val stories: StoriesModel?,
    @SerializedName("events") val events: EventsModel?,
    @SerializedName("series") val series: SeriesModel?,
) {
    data class UrlModel(
        @SerializedName("type") val type: String?,
        @SerializedName("url") val url: String?
    )

    data class ThumbnailModel(
        @SerializedName("path") val path: String?,
        @SerializedName("extension") val extension: String?
    )

    data class ComicsModel(
        @SerializedName("available") val available: Int?,
        @SerializedName("returned") val returned: Int?,
        @SerializedName("collectionURI") val collectionURI: String?,
        @SerializedName("items") val items: List<ItemModel>?
    ) {
        data class ItemModel(
            @SerializedName("resourceURI") val resourceURI: String?,
            @SerializedName("name") val name: String?
        )
    }

    data class StoriesModel(
        @SerializedName("available") val available: Int?,
        @SerializedName("returned") val returned: Int?,
        @SerializedName("collectionURI") val collectionURI: String?,
        @SerializedName("items") val items: List<ItemModel>?
    ) {
        data class ItemModel(
            @SerializedName("resourceURI") val resourceURI: String?,
            @SerializedName("name") val name: String?,
            @SerializedName("type") val type: String?
        )
    }

    data class EventsModel(
        @SerializedName("available") val available: Int?,
        @SerializedName("returned") val returned: Int?,
        @SerializedName("collectionURI") val collectionURI: String?,
        @SerializedName("items") val items: List<ItemModel>?
    ) {
        data class ItemModel(
            @SerializedName("resourceURI") val resourceURI: String?,
            @SerializedName("name") val name: String?
        )
    }

    data class SeriesModel(
        @SerializedName("available") val available: Int?,
        @SerializedName("returned") val returned: Int?,
        @SerializedName("collectionURI") val collectionURI: String?,
        @SerializedName("items") val items: List<ItemModel>?
    ) {
        data class ItemModel(
            @SerializedName("resourceURI") val resourceURI: String?,
            @SerializedName("name") val name: String?
        )
    }
}