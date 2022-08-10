package com.cmdv.data.mappers

import com.cmdv.data.entities.CharacterRoomEntity
import com.cmdv.domain.base.BaseMapper
import com.cmdv.domain.models.CharacterModel

/**
 * Data mapper class. Utility class to map data between [CharacterRoomEntity] and [CharacterModel] class types.
 */
object CharacterRoomMapper : BaseMapper<CharacterRoomEntity, CharacterModel>() {

    override fun transformEntityToModel(e: CharacterRoomEntity): CharacterModel =
        CharacterModel(
            e.characterId,
            e.name,
            e.description,
            e.thumbnail,
            e.isFavorite,
            e.comicsCount,
            e.seriesCount,
            e.storiesCount
        )

    override fun transformModelToEntity(m: CharacterModel): CharacterRoomEntity =
        CharacterRoomEntity(
            null,
            m.id,
            m.name,
            m.description,
            m.thumbnail,
            m.isFavorite,
            m.comicsCount,
            m.seriesCount,
            m.storiesCount
        )
}