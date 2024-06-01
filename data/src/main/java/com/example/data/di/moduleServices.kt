package com.example.data.di

import com.example.data.repositoryImpl.RepoMemoryCache
import com.example.data.repositoryImpl.RepoSearch
import com.example.domain.repository.IRepoCache
import com.example.domain.repository.IRepoSearch
import org.koin.dsl.module

val moduleServices = module {
    single<IRepoSearch> { RepoSearch(get()) }
    single<IRepoCache> { RepoMemoryCache() }
}