package com.example

import android.app.Application
import com.example.data.di.moduleApi
import com.example.data.di.moduleServices
import com.example.domain.di.moduleUseCases
import com.example.payback.view.di.moduleApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(PrefixTree("yy_"))

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                moduleApi,
                moduleServices, moduleUseCases, moduleApp
            )
        }
    }
}

class PrefixTree(private val prefix: String) : Timber.DebugTree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        super.log(priority, prefix + tag, message, t)
    }
}