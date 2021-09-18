package com.example.gapsiproyect

import android.content.Context
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gapsiproyect.Adapters.ProductsAdapter
import com.example.gapsiproyect.daos.SuggestionItem
import com.example.gapsiproyect.databinding.ActivityMainBinding
import com.example.gapsiproyect.viewmodels.MainViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var  mainViewModel : MainViewModel

    private  lateinit var binding : ActivityMainBinding

    private val PREFERENCE_KEY ="KEY"


    private var searchJob: Job? = null

      var adapter = ProductsAdapter()



    private fun search(query: String) {
        // Make sure we cancel the previous job before creating a new one
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            mainViewModel.listProductsCriteria(query).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            binding = ActivityMainBinding.inflate(getLayoutInflater())
        setContentView(binding.root)

        mainViewModel =  ViewModelProvider(this, Injection.provideViewModelFactory(owner = this))
            .get(MainViewModel::class.java)

        val sharedPref = this?.getPreferences(Context.MODE_PRIVATE)

        val getSuggestions = sharedPref.getStringSet(PREFERENCE_KEY, null)

        if (getSuggestions != null) {
            CreateSugestionsList(getSuggestions)
        }

        binding.lifecycleOwner  = this

        binding.viewModel = mainViewModel

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        linearLayoutManager.setAutoMeasureEnabled(true)
        binding.TransactionItems.setNestedScrollingEnabled(false)
        binding.TransactionItems.setHasFixedSize(false)
        binding.TransactionItems.setLayoutManager(linearLayoutManager)
        binding.TransactionItems.setNestedScrollingEnabled(false)
        binding.TransactionItems.adapter =adapter


        val query = savedInstanceState?.getString(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY

        search(query)
        initSearch()


        binding.floatingSearchView.setOnQueryChangeListener { oldQuery, newQuery ->
            search(newQuery)
            initSearch()



        binding.floatingSearchView.setOnBindSuggestionCallback { suggestionView, leftIcon, textView, item, itemPosition ->
            var suggestionItem  = item as SuggestionItem
            textView.text = suggestionItem.item
        }

        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(LAST_SEARCH_QUERY, binding.floatingSearchView.query.trim())
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


    /**
     * Inflate the list of Suggestions
     */
    fun CreateSugestionsList(suggestionsSave : Set<String>){

        val SuggestionsInfo: MutableList<SuggestionItem> = ArrayList()

    suggestionsSave.forEach { SuggestionsInfo.add(SuggestionItem(it)) }

        binding.floatingSearchView.swapSuggestions(SuggestionsInfo)

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    companion object {
        private const val LAST_SEARCH_QUERY: String = "last_search_query"
        private const val DEFAULT_QUERY = "Beer"
    }
}