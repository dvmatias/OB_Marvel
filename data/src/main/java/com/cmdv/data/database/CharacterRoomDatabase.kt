package com.cmdv.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cmdv.data.entities.CharacterRoomEntity
import com.cmdv.data.sources.dbdaos.CharacterDao

/**
 * Room DB. Instance of Room Data Base for characters.
 */
@Database(entities = [ CharacterRoomEntity::class], version = 2)
abstract class CharacterRoomDatabase : RoomDatabase() {
    abstract val characterDao: CharacterDao

    companion object {
        @Volatile
        private var INSTANCE: CharacterRoomDatabase? = null

        /**
         * Get an instance using singleton pattern.
         */
        fun getInstance(context: Context): CharacterRoomDatabase {
            synchronized(this) {
                var instance: CharacterRoomDatabase? = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CharacterRoomDatabase::class.java,
                        "character-room-database")
                        .fallbackToDestructiveMigration()
                        .build()
                }
                return instance
            }
        }
    }

}