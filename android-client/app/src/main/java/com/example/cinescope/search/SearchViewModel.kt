package com.example.cinescope.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinescope.domain.searches.MediaContent
import com.example.cinescope.domain.searches.SearchContent
import com.example.cinescope.services.cinescopeAPI.SearchServices
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchServices: SearchServices
): ViewModel() {

    var loading by mutableStateOf(false)
        private set

    var searchContent by mutableStateOf<List<MediaContent>?>(null)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    fun search(query: String) {
        viewModelScope.launch {
            loading = true

            try {
                searchContent = searchServices.searchByQuery(query)

            } catch (e: Exception) {
                error = e.message
            } finally {
                loading = false
            }
        }
    }

    fun clearError(){
        error = null
    }
}