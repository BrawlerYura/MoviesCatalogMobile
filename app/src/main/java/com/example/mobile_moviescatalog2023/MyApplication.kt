package com.example.mobile_moviescatalog2023

import android.app.Application
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobile_moviescatalog2023.View.LoginScreens.LoginScreen.LoginViewModel
import com.example.mobile_moviescatalog2023.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@MyApplication)
            modules(appModules)
        }
    }
}