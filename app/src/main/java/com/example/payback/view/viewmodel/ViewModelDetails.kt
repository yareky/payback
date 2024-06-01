package com.example.payback.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Hit
import com.example.domain.model.Status
import com.example.domain.model.asStatus
import com.example.domain.usecase.GetImagesUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class ViewModelDetails(private val getImagesUseCase: GetImagesUseCase) :
    ViewModel() {
    private val _hit: MutableSharedFlow<Status<Hit>> =
        MutableSharedFlow()
    val hit = _hit.asSharedFlow()


    fun getImage(imageId: Long) {
        viewModelScope.launch {
            _hit.emit(Status.LOADING)
            val result = getImagesUseCase.invoke(imageId)
            _hit.emit(result.asStatus())
        }
    }
}