package com.example.data.di

import com.example.data.repositoryImpl.RepoMemoryCache
import com.example.data.repositoryImpl.RepoSearch
import org.koin.dsl.module

val moduleServices = module {
    single<com.example.domain.repository.IRepoSearch> { RepoSearch(get()) }
    single<com.example.domain.repository.IRepoCache> { RepoMemoryCache() }
}