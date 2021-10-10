package com.example.gapsiproyect.UIFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gapsiproyect.Adapters.ProductsAdapter
import com.example.gapsiproyect.Injection
import com.example.gapsiproyect.daos.SuggestionItem
import com.example.gapsiproyect.databinding.MoviesFragmentBinding
import com.example.gapsiproyect.viewmodels.MoviesViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class MoviesFragment : Fragment() {

    companion object {
        fun newInstance() = MoviesFragment()
        private const val LAST_SEARCH_QUERY: String = "last_search_query"
        private const val DEFAULT_QUERY = "Hello"
    }


    private lateinit var mainViewModel: MoviesViewModel

    private val PREFERENCE_KEY ="KEY"

    private  lateinit var binding : MoviesFragmentBinding



    private var searchJob: Job? = null

    var adapter = ProductsAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MoviesFragmentBinding.inflate(getLayoutInflater())
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel =  ViewModelProvider(this, Injection.provideViewModelFactory(owner = this))
            .get(MoviesViewModel::class.java)

        binding.lifecycleOwner  = this

        binding.viewModel = mainViewModel

        val layoutt_Manager = LinearLayoutManager(activity)
        layoutt_Manager.orientation = LinearLayoutManager.VERTICAL
        layoutt_Manager.isAutoMeasureEnabled  = true
        binding.TransactionItems.isNestedScrollingEnabled = false
        binding.TransactionItems.setHasFixedSize(false)
        binding.TransactionItems.layoutManager = layoutt_Manager
        binding.TransactionItems.adapter =adapter


        val query = savedInstanceState?.getString(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY

        search(query)
        initSearch()


        binding.floatingSearchView.setOnQueryChangeListener { oldQuery, newQuery ->
            search(newQuery)
            initSearch()



            binding.floatingSearchView.setOnBindSuggestionCallback { s, l, textView, item, itemPosition ->
                var suggestionItem  = item as SuggestionItem
                textView.text = suggestionItem.item
            }

        }

    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(LAST_SEARCH_QUERY, binding.floatingSearchView.query.trim())
    }

    private fun search(query: String) {
        // Nos asegutamos de detener el hilo de busqueda antes de inicar un nuevo.
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            mainViewModel.listProductsCriteria(query).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun initSearch() {

        // Scroll to top when the list is refreshed from network.
        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow
                // Only emit when REFRESH LoadState changes.
                .distinctUntilChangedBy { it.refresh }
                // Only react to cases where REFRESH completes i.e., NotLoading.
                .filter { it.refresh is LoadState.NotLoading }
                .collectLatest { binding.TransactionItems.scrollToPosition(0 )}
        }
    }

}