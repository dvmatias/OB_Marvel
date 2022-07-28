package com.cmdv.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cmdv.data.entities.FavoriteCharacterRoomEntity
import com.cmdv.data.sources.dbdaos.FavoriteCharactersDao

/**
 * Room DB. Instance of Room Data Base for favorite characters.
 */
@Database(entities = [FavoriteCharacterRoomEntity::class], version = 2)
abstract class FavouriteCharactersRoomDataBase : RoomDatabase() {
    abstract val favoriteCharactersDao: FavoriteCharactersDao

    companion object {
        @Volatile
        private var INSTANCE: FavouriteCharactersRoomDataBase? = null

        /**
         * Get an instance using singleton pattern.
         */
        fun getInstance(context: Context): FavouriteCharactersRoomDataBase {
            synchronized(this) {
                var instance: FavouriteCharactersRoomDataBase? = INSTANCE
                if (instance == null) {
                    instance =
                        Room.databaseBuilder(
                            context.applicationContext,
                            FavouriteCharactersRoomDataBase::class.java,
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