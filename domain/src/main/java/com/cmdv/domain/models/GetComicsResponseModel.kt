package com.cmdv.domain.models

data class GetComicsResponseModel(
    val total: Int,
    val comics: ArrayList<ComicModel>
)
