package com.cmdv.data.mappers

import com.cmdv.data.entities.CharacterEntity
import com.cmdv.domain.base.BaseMapper
import com.cmdv.domain.models.CharacterModel

private const val DEFAULT_INT = 1
private const val DEFAULT_STRING = ""

/**
 * Data mapper class. Utility class to map data between [CharacterEntity] and [CharacterModel] class types.
 */
object CharacterMapper : BaseMapper<CharacterEntity, CharacterModel>() {

    override fun transformEntityToModel(e: CharacterEntity): CharacterModel =
        CharacterModel(
            id = e.id ?: DEFAULT_INT,
            name = e.name ?: DEFAULT_STRING,
            description = e.description ?: DEFAULT_STRING,
            transformThumbnail(e.thumbnail),
            isFavourite = false,
            comicsCount = e.comics?.available ?: 0,
            seriesCount = e.series?.available ?: 0,
            storiesCount = e.stories?.available ?: 0,
        )

    private fun transformThumbnail(thumbnail: CharacterEntity.ThumbnailEntity?): String {
        val path = thumbnail?.path ?: DEFAULT_STRING
        val extension = thumbnail?.extension ?: DEFAULT_STRING
        return if (path.isNotEmpty() && extension.isNotEmpty()) {
            "$path.$extension"
        } else {
            DEFAULT_STRING
        }
    }
}