package com.example.payback.view.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class ViewModelSearch(private val searchImagesUseCase: com.example.domain.usecase.SearchImagesUseCase) :
    ViewModel() {
    var query by mutableStateOf("fruits")
        private set

    private val _hits: MutableStateFlow<List<com.example.domain.model.Hit>> =
        MutableStateFlow(emptyList())
    val hits = _hits.asStateFlow()

    fun updateQueryAndRefresh(text: String) {
        query = text
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            when (val result = searchImagesUseCase.invoke(query.trim())) {
                is com.example.domain.model.Status.ERROR -> Timber.e(result.throwable)
                is com.example.domain.model.Status.SUCCESS -> _hits.emit(result.value.hits)
            }
        }
    }
}