package com.example.domain.di

import com.example.domain.usecase.GetImagesUseCase
import com.example.domain.usecase.SearchImagesUseCase
import org.koin.dsl.module

val moduleUseCases = module {
    single { SearchImagesUseCase(get(), get()) }
    single { GetImagesUseCase(get(), get()) }
}