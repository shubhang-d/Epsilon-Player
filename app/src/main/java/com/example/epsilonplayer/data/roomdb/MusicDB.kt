package com.example.epsilonplayer.data.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MusicEntity::class], version = 1)
abstract class MusicDB: RoomDatabase() {
    abstract fun MusicDAO(): MusicDAO

    companion object{
        @Volatile
        private var instance: MusicDB? = null

        fun getInstance(context: Context): MusicDB{
            return instance?: synchronized(this){
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): MusicDB{
            return Room.databaseBuilder(
                context,
                MusicDB::class.java,
                "music_db_1129"
            )
                .allowMainThreadQueries()
                .build()
        }
    }
}