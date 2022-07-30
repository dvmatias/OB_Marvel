package com.cmdv.domain.models

data class GetSeriesResponseModel(
    val total: Int,
    val series: ArrayList<SerieModel>
)
