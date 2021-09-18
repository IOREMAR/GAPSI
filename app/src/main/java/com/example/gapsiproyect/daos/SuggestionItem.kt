package com.example.gapsiproyect.daos

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion
import kotlinx.parcelize.Parcelize

@Parcelize
class SuggestionItem (val item :String) : SearchSuggestion  {

    override fun getBody(): String {
       return item
    }
}