package com.cmdv.data.sources.dbdaos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cmdv.data.entities.CharacterRoomEntity

/**
 * Data Access Object - Room DB. Characters DAO to perform DB operations on Marvel's characters.
 */
@Dao
interface CharacterDao {
    @Query(value = "SELECT * FROM `character-room-database`")
    fun getAll(): List<CharacterRoomEntity>

    @Query(value = "DELETE FROM `character-room-database`")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(characters: List<CharacterRoomEntity>)

    @Query(value = "SELECT * FROM `character-room-database` WHERE characterId IN (:ids)")
    fun getById(ids: List<Int>): List<CharacterRoomEntity>

    @Query("UPDATE `character-room-database` SET is_favorite=:isFavorite WHERE characterId = :characterId")
    fun update(isFavorite: Boolean?, characterId: Int)
}