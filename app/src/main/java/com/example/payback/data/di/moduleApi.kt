package com.example.payback.data.di

import com.example.payback.data.api.ApiPixBay
import com.example.payback.data.api.interceptor.ApiKeyInterceptor
import com.example.payback.view.di.Configuration
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val moduleApi = module {
    single { Configuration.DEFAULT }
    single { ApiKeyInterceptor(get<Configuration>().apiKey) }
    single { GsonBuilder().create() }
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        OkHttpClient
            .Builder()
            .addInterceptor(get<ApiKeyInterceptor>())
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(get<Configuration>().apiUrl)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }

    single<ApiPixBay> {
        get<Retrofit>().create(ApiPixBay::class.java)
    }
}