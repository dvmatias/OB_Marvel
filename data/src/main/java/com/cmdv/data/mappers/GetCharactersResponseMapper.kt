package com.cmdv.data.mappers

import com.cmdv.data.entities.CharacterEntity
import com.cmdv.data.entities.GetCharactersResponseEntity
import com.cmdv.domain.base.BaseMapper
import com.cmdv.domain.models.CharacterModel
import com.cmdv.domain.models.GetCharactersResponseModel

private const val DEFAULT_INT = 1

/**
 * Mapper class for [GetCharactersResponseEntity] and [GetCharactersResponseModel]. Provides transformation functions.
 */
object GetCharactersResponseMapper : BaseMapper<GetCharactersResponseEntity, GetCharactersResponseModel>() {

    override fun transformEntityToModel(e: GetCharactersResponseEntity): GetCharactersResponseModel {
        return GetCharactersResponseModel(
            total = e.data?.total ?: DEFAULT_INT,
            characters = transformCharacters(e.data?.results)
        )
    }

    private fun transformCharacters(results: ArrayList<CharacterEntity>?): ArrayList<CharacterModel> {
        val characters = arrayListOf<CharacterModel>()
        results?.map { characters.add(CharacterMapper.transformEntityToModel(it)) }
        return characters
    }
}