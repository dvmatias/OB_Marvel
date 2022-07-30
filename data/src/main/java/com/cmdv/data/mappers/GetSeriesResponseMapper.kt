package com.cmdv.data.mappers

import com.cmdv.data.entities.GetComicsResponseEntity
import com.cmdv.data.entities.GetSeriesResponseEntity
import com.cmdv.data.entities.SerieEntity
import com.cmdv.domain.base.BaseMapper
import com.cmdv.domain.models.GetComicsResponseModel
import com.cmdv.domain.models.GetSeriesResponseModel
import com.cmdv.domain.models.SerieModel

private const val DEFAULT_INT = 1

/**
 * Mapper class for [GetComicsResponseEntity] and [GetComicsResponseModel]. Provides transformation functions.
 */
object GetSeriesResponseMapper : BaseMapper<GetSeriesResponseEntity, GetSeriesResponseModel>() {

    override fun transformEntityToModel(e: GetSeriesResponseEntity): GetSeriesResponseModel {
        return GetSeriesResponseModel(
            total = e.data?.total ?: DEFAULT_INT,
            series = transformCharacters(e.data?.results)
        )
    }

    private fun transformCharacters(results: ArrayList<SerieEntity>?): ArrayList<SerieModel> {
        val series = arrayListOf<SerieModel>()
        results?.map { series.add(SerieMapper.transformEntityToModel(it)) }
        return series
    }
}