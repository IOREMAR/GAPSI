package com.example.gapsiproyect.Network

import com.example.gapsiproyect.daos.ProductsDao
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.ArrayList

interface ProductsNetwork {



    @GET("beers")
    suspend fun getProductos(
        @Query("beer_nam") beer: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int = 5,
    ): List<ProductsDao>


    @GET("beers")
    suspend fun getProductosbyName(
        @Query("?beer_name") page: String,
    ): List<ProductsDao>


    companion object {

        //URL Root del Servicio de Punk Api. (Abrir en Web Documentacion ->  https://punkapi.com/documentation/v2)
        private val URL_Serivce = "https://api.punkapi.com/v2/"

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