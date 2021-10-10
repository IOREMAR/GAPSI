package com.example.gapsiproyect.viewmodels

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.gapsiproyect.Loaders.LoadingState
import com.example.gapsiproyect.Repositories.ProductRepository
import com.example.gapsiproyect.daos.Results
import kotlinx.coroutines.flow.Flow

class MoviesViewModel (private val repository :  ProductRepository,
                       private val savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val _loading = MutableLiveData<LoadingState>()

    val loading: LiveData<LoadingState> get() = _loading


    private val _txtMensaje = MutableLiveData<String>()

    val txtMensaje: LiveData<String> get() = _txtMensaje


    private var currentQueryValue: String? = null

    private var currentSearchResult: Flow<PagingData<Results>>? = null


    fun listProductsCriteria(queryString : String): Flow<PagingData<Results>> {

        currentQueryValue = queryString
        val newResult: Flow<PagingData<Results>> = repository.getListProducts(queryString)
            .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }



}