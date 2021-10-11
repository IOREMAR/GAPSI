package com.example.gapsiproyect.Repositories

import android.content.Context
import com.example.gapsiproyect.daos.Results
import com.example.gapsiproyect.room.MovieResults
import com.example.gapsiproyect.room.MovieResultsDao
import com.example.gapsiproyect.room.MoviesDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class RoomRepository(application:Context) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main


    private val moviesDao :MovieResultsDao?

    init {
        val db = MoviesDatabase.getDatabase(application)
        moviesDao = db?.moviesDao()
    }

    fun getResults() = moviesDao?.getAllNotes()

    fun setMovie(MovieInfo: Results) {
        launch  { setMovieDB(MovieInfo) }
    }

    private suspend fun setMovieDB(Movie: Results){
        withContext(Dispatchers.IO){
            moviesDao?.setmovies(MovieResults(Movie))
        }
    }


}