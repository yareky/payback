package com.example.payback.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.asStatus
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class ViewModelDetails(private val getImagesUseCase: com.example.domain.usecase.GetImagesUseCase) :
    ViewModel() {
    private val _hit: MutableSharedFlow<com.example.domain.model.Status<com.example.domain.model.Hit>> =
        MutableSharedFlow()
    val hit = _hit.asSharedFlow()

    fun getImage(imageId: Long) {
        viewModelScope.launch {
            _hit.emit(com.example.domain.model.Status.LOADING)
            val result = getImagesUseCase.invoke(imageId)
            _hit.emit(result.asStatus())
        }
    }
}