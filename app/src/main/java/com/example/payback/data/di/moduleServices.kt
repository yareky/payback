package com.example.payback.data.di

import com.example.payback.data.repositoryImpl.RepoMemoryCache
import com.example.payback.data.repositoryImpl.RepoSearch
import com.example.payback.domain.repository.IRepoCache
import com.example.payback.domain.repository.IRepoSearch
import org.koin.dsl.module

val moduleServices = module {
    single<IRepoSearch> { RepoSearch(get()) }
    single<IRepoCache> { RepoMemoryCache() }
}