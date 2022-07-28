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
interface CharactersDao {
    @Query(value = "SELECT * FROM `characters-room-database`")
    fun getAll(): List<CharacterRoomEntity>

    @Query(value = "DELETE FROM `characters-room-database`")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(characters: List<CharacterRoomEntity>)

    @Query(value = "SELECT * FROM `characters-room-database`  WHERE characterId IN (:ids)")
    fun getById(ids: List<Int>): List<CharacterRoomEntity>
}