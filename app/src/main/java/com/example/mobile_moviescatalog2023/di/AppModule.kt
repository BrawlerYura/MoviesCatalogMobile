package com.example.mobile_moviescatalog2023.di

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobile_moviescatalog2023.View.AuthScreens.LoginScreen.LoginViewModel
import okhttp3.OkHttpClient
import org.koin.dsl.module

val appModule = module {

}

val appModules = listOf (
    ViewModelModule,
    RepositoryModule
)