package com.example.gapsiproyect.Repositories

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.gapsiproyect.Network.ProductsNetwork
import com.example.gapsiproyect.daos.ProductsDao
import retrofit2.HttpException
import java.io.IOException
import java.util.ArrayList


private const val PUNK_STARTING_PAGE_INDEX = 1
private const val NETWORK_PAGE_SIZE = 5

class ProductsPagingSource
    (
    private val service:ProductsNetwork,
    private val query: String
            ) : PagingSource<Int, ProductsDao>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductsDao> {
        val position = params.key ?: PUNK_STARTING_PAGE_INDEX

        return try {
            val response = service.getProductos( query, position, params.loadSize)
            val repos = response
            val nextKey = if (repos.isEmpty()) {
                null
            } else {
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            LoadResult.Page(
                data = repos,
                prevKey = if (position == PUNK_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ProductsDao>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}