package com.cmdv.data.sources.dbdaos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cmdv.data.entities.FavoriteCharacterRoomEntity

/**
 * Data Access Object - Room DB. Favorite characters DAO to perform DB operations on User's favorite characters.
 */
@Dao
interface FavoriteCharacterDao {
    @Query("SELECT * FROM `favorite-character-room-database`")
    fun getAll(): List<FavoriteCharacterRoomEntity>

    @Query("SELECT * FROM `favorite-character-room-database` WHERE characterId IN (:id)")
    fun getById(id: Int): FavoriteCharacterRoomEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(character: FavoriteCharacterRoomEntity): Long

    @Query("DELETE FROM `favorite-character-room-database` WHERE characterId IN (:id)")
    fun delete(id: Int): Int

    @Query("DELETE FROM `favorite-character-room-database`")
    fun deleteAll()

    @Query("SELECT EXISTS (SELECT * FROM `favorite-character-room-database` WHERE characterId = :id)")
    fun exists(id: Int): Boolean
}