package com.example.payback.domain.di

import com.example.payback.domain.usecase.GetImagesUseCase
import com.example.payback.domain.usecase.SearchImagesUseCase
import org.koin.dsl.module

val moduleUseCases = module {
    single { SearchImagesUseCase(get(), get()) }
    single { GetImagesUseCase(get(), get()) }
}