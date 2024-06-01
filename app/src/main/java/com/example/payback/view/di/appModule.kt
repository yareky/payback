package com.example.payback.view.di

import com.example.payback.view.viewmodel.ViewModelDetails
import com.example.payback.view.viewmodel.ViewModelSearch
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val moduleApp = module {
    viewModelOf(::ViewModelSearch)
    viewModelOf(::ViewModelDetails)
}