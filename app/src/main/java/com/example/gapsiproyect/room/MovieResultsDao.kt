package com.example.gapsiproyect.room

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface MovieResultsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setmovies(note: MovieResults?)

    @Delete
    fun Delete(note: MovieResults?)

    @Query("DELETE FROM Movies_Store")
    fun DeleteAllNotes()

    @Query("SELECT * FROM Movies_Store")
    fun getAllNotes(): LiveData<List<MovieResults?>?>? //updates and returns


}