package com.cmdv.data.mappers

import com.cmdv.data.entities.ComicEntity
import com.cmdv.domain.base.BaseMapper
import com.cmdv.domain.models.ComicModel

private const val DEFAULT_INT = 1
private const val DEFAULT_STRING = ""

/**
 * Data mapper class. Utility class to map data between [ComicEntity] and [ComicModel] class types.
 */
object ComicMapper : BaseMapper<ComicEntity, ComicModel>() {

    override fun transformEntityToModel(e: ComicEntity): ComicModel =
        ComicModel(
            id = e.id ?: DEFAULT_INT,
            description = e.description ?: DEFAULT_STRING,
            transformThumbnail(e.thumbnail)
        )

    private fun transformThumbnail(thumbnail: ComicEntity.ThumbnailEntity?): String {
        val path = thumbnail?.path ?: DEFAULT_STRING
        val extension = thumbnail?.extension ?: DEFAULT_STRING
        return if (path.isNotEmpty() && extension.isNotEmpty()) {
            "$path.$extension"
        } else {
            DEFAULT_STRING
        }
    }
}