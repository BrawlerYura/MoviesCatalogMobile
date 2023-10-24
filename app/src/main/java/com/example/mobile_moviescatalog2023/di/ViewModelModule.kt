package com.example.mobile_moviescatalog2023.di

import com.example.mobile_moviescatalog2023.View.LoginScreens.LoginScreen.LoginViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

val ViewModelModule = module {
    viewModel { LoginViewModel() }
}