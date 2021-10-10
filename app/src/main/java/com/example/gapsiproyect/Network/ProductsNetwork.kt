package com.example.gapsiproyect.Network

import com.example.gapsiproyect.daos.ProductsDao
import com.example.gapsiproyect.daos.ResponseMovies
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.ArrayList

interface ProductsNetwork {


    /**
     *  @GET("search/movie")
     *  "api_key" =  Api Key Generado por TMDB para el consumo de sus servicios,
     *  "language" = Lenguague del tipo de consulta de información
     *  "query" = Texto a Buscar para el Api.
     *  "page" = Pagina consultada
     *  "include_adult" = Si Incluye o no Peliculas para Adultos.
     */
    @GET("search/movie")
    suspend fun getProductos(
            @Query("api_key") api_key : String = API_KEY,
            @Query("language")language : String = "en-US", //Default
            @Query("query")query: String,
            @Query("page") page: Int,
            @Query("include_adult") include_adult : String = "false" //Default
    ): ResponseMovies


    companion object {

        //URL Root del Servicio de TMDB. (Abrir en Web Documentacion -> https://developers.themoviedb.org/3/getting-started/introduction)
        private val URL_Serivce = "https://api.themoviedb.org/3/"

        private val API_KEY = "5a255ef8233f1de0699ca16198d9535b"

        /**
         * Creates the Retrofit Client Prepare the TransactionService to Use.
         * Crea el cliente de Retrofit que deja preparado el objeto para la operacion web.
         */
         fun create(): ProductsNetwork {

            // Creamos un interceptor y le indicamos el log level a usar
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            // Asociamos el interceptor a las peticiones
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(logging)

            //Creamos el cliente Retrofit Final.
            return  Retrofit.Builder()
                .client(httpClient.build())
                .baseUrl(URL_Serivce)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ProductsNetwork::class.java)
        }



    }


}