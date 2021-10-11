package com.example.gapsiproyect.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MovieResults::class], version = 1, exportSchema = false)
abstract class MoviesDatabase : RoomDatabase() {

        abstract fun moviesDao(): MovieResultsDao

    companion object {

        @Volatile
        private var INSTANCE: MoviesDatabase? = null

        fun getDatabase(context: Context): MoviesDatabase? {
            if (INSTANCE == null) {
                synchronized(MoviesDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            MoviesDatabase::class.java, "Movies_Store"
                        )
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }

}