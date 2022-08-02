package com.cmdv.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cmdv.data.entities.FavoriteCharacterRoomEntity
import com.cmdv.data.sources.dbdaos.FavoriteCharacterDao

/**
 * Room DB. Instance of Room Data Base for favorite characters.
 */
@Database(entities = [FavoriteCharacterRoomEntity::class], version = 2)
abstract class FavoriteCharacterRoomDataBase : RoomDatabase() {
    abstract val favoriteCharacterDao: FavoriteCharacterDao

    companion object {
        @Volatile
        private var INSTANCE: FavoriteCharacterRoomDataBase? = null

        /**
         * Get an instance using singleton pattern.
         */
        fun getInstance(context: Context): FavoriteCharacterRoomDataBase {
            synchronized(this) {
                var instance: FavoriteCharacterRoomDataBase? = INSTANCE
                if (instance == null) {
                    instance =
                        Room.databaseBuilder(
                            context.applicationContext,
                            FavoriteCharacterRoomDataBase::class.java,
                            "favorite-character-room-database"
                        )
                            .fallbackToDestructiveMigration()
                            .build()
                }
                return instance
            }
        }
    }
}