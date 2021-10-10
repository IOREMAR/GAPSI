package com.example.gapsiproyect.Repositories

import androidx.core.view.NestedScrollingParent
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.gapsiproyect.Network.ProductsNetwork
import com.example.gapsiproyect.daos.ProductsDao
import com.example.gapsiproyect.daos.Results
import retrofit2.HttpException
import java.io.IOException
import java.util.ArrayList


private const val PUNK_STARTING_PAGE_INDEX = 1
private const val NETWORK_PAGE_SIZE = 1

class ProductsPagingSource
    (
    private val service:ProductsNetwork,
    private val serch: String
            ) : PagingSource<Int, Results>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Results> {
        val position = params.key ?: PUNK_STARTING_PAGE_INDEX

        return try {
            val response = service.getProductos(query = serch ,page = position )
            val repos = response
            val nextKey = if (repos.results.isEmpty()) {
                null
            } else {
                position + NETWORK_PAGE_SIZE
            }
            LoadResult.Page(
                data = repos.results,
                prevKey = if (position == PUNK_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Results>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}