package com.example.mobile_moviescatalog2023.di

import com.example.mobile_moviescatalog2023.View.AuthScreens.LoginScreen.LoginViewModel
import com.example.mobile_moviescatalog2023.View.AuthScreens.RegistrationPasswordScreen.RegistrationPasswordViewModel
import com.example.mobile_moviescatalog2023.View.AuthScreens.RegistrationScreen.RegistrationViewModel
import com.example.mobile_moviescatalog2023.View.AuthScreens.SplashScreen.SplashScreenViewModel
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FavoriteScreen.FavoriteScreenViewModel
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen.FilmScreenViewModel
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.MainScreenViewModel
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.ProfileScreen.ProfileScreenViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

val ViewModelModule = module {
    viewModel { SplashScreenViewModel(androidContext(), userRepository = get()) }
    viewModel { LoginViewModel(androidContext(), authRepository = get()) }
    viewModel { RegistrationViewModel() }
    viewModel { RegistrationPasswordViewModel(androidContext(), authRepository = get()) }
    viewModel { MainScreenViewModel(movieRepository = get(), userRepository = get()) }
    viewModel { FavoriteScreenViewModel(favoriteMoviesRepository = get()) }
    viewModel { ProfileScreenViewModel(androidContext(), userRepository = get(), authRepository = get()) }
    viewModel { FilmScreenViewModel(movieRepository = get(), favoriteMoviesRepository = get(), reviewRepository = get()) }
}