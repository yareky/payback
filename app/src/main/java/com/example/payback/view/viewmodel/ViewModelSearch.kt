package com.example.payback.view.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Hit
import com.example.domain.model.Status
import com.example.domain.usecase.SearchImagesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class ViewModelSearch(private val searchImagesUseCase: SearchImagesUseCase) :
    ViewModel() {
    var query by mutableStateOf(DEFAULT_SEARCH_QUERY)
        private set

    private val _hits: MutableStateFlow<List<Hit>> =
        MutableStateFlow(emptyList())
    val hits = _hits.asStateFlow()

    fun updateQueryAndRefresh(text: String) {
        query = text
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            when (val result = searchImagesUseCase.invoke(query.trim())) {
                is Status.ERROR -> {
                    Timber.e(result.throwable)
                    _hits.emit(emptyList())
                }

                is Status.SUCCESS -> _hits.emit(result.value.hits)
            }
        }
    }

    private companion object {
        const val DEFAULT_SEARCH_QUERY = "fruits"
    }
}