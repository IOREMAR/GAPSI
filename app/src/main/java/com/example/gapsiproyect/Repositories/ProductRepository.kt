package com.example.gapsiproyect.Repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.gapsiproyect.Network.ProductsNetwork
import com.example.gapsiproyect.daos.ProductsDao
import com.example.gapsiproyect.daos.Results
import kotlinx.coroutines.flow.Flow

class ProductRepository (private val service: ProductsNetwork){

    /**
     * @getlistProducts made the networkTransactions and handle the errors for RestApi.
     */


    fun getListProducts(query : String) : Flow<PagingData<Results>> {

        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ProductsPagingSource(service,query) }
        ).flow
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }


    /*
     *//** "Migrated -- Deprecated
     * @getlistTransactions made the networkTransactions and handle the errors for RestApi.
     * @Criteria : is the Criteria Query
     *//*
      fun getListProducts( Criteria:String) : Flow< PagingData<ProductsDao>> {


        try {
            val NetworkListBank = Network.getProductsService(Criteria)
            if(NetworkListBank.items.isNotEmpty()) {
                listTransactions.complete(NetworkListBank.items)
            }else{
                throw RuntimeException("No existen Datos")
            }
        }catch (exe : MalformedJsonException){
            Log.e("Error",exe.message!!)
            throw Exception("Favor de Intentarlo Mas Tarde")
        }
        catch (exe: RuntimeException){
            Log.e("Error",exe.message!!)
            throw exe
        }
        return listTransactions.await()
    }

*/







}