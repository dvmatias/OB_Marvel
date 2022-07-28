package com.cmdv.data.mappers

import com.cmdv.data.entities.ComicEntity
import com.cmdv.data.entities.GetComicsResponseEntity
import com.cmdv.domain.base.BaseMapper
import com.cmdv.domain.models.ComicModel
import com.cmdv.domain.models.GetComicsResponseModel

private const val DEFAULT_INT = 1

/**
 * Mapper class for [GetComicsResponseEntity] and [GetComicsResponseModel]. Provides transformation functions.
 */
object GetComicsResponseMapper : BaseMapper<GetComicsResponseEntity, GetComicsResponseModel>() {

    override fun transformEntityToModel(e: GetComicsResponseEntity): GetComicsResponseModel {
        return GetComicsResponseModel(
            total = e.data?.total ?: DEFAULT_INT,
            comics = transformCharacters(e.data?.results)
        )
    }

    private fun transformCharacters(results: ArrayList<ComicEntity>?): ArrayList<ComicModel> {
        val comics = arrayListOf<ComicModel>()
        results?.map { comics.add(ComicMapper.transformEntityToModel(it)) }
        return comics
    }
}