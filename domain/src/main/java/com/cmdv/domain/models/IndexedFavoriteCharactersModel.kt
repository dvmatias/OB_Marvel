package com.cmdv.domain.models

data class IndexedFavoriteCharactersModel(
    val index: String,
    val favoriteCharacters: List<CharacterModel>
)
