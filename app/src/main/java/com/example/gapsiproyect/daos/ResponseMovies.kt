package com.example.gapsiproyect.daos

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseMovies (

         @SerializedName("page") var page : Int,
         @SerializedName("results") var results : List<Results>,
         @SerializedName("total_pages") var totalPages : Int,
         @SerializedName("total_results") var totalResults : Int
    )