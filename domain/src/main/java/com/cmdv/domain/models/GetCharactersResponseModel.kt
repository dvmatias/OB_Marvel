package com.cmdv.domain.models

data class GetCharactersResponseModel(
    val total: Int,
    val characters: ArrayList<CharacterModel>
)
