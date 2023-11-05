package com.example.mobile_moviescatalog2023.di

import com.example.mobile_moviescatalog2023.View.AuthScreens.LoginScreen.LoginViewModel
import com.example.mobile_moviescatalog2023.View.AuthScreens.RegistrationPasswordScreen.RegistrationPasswordViewModel
import com.example.mobile_moviescatalog2023.View.AuthScreens.RegistrationScreen.RegistrationViewModel
import com.example.mobile_moviescatalog2023.View.AuthScreens.SplashScreen.SplashScreenViewModel
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FavoriteScreen.FavoriteScreenViewModel
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen.FilmScreenViewModel
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.MainScreenViewModel
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.ProfileScreen.ProfileScreenViewModel
import com.example.mobile_moviescatalog2023.domain.UseCases.FormatDateUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.UserUseCases.LogoutUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

val ViewModelModule = module {
    viewModel { SplashScreenViewModel( getProfileUseCase = get()) }
    viewModel { LoginViewModel(androidContext(), loginUseCase = get(), validationUseCase = get()) }
    viewModel { RegistrationViewModel(formatDateUseCase = get(), validationUseCase = get()) }
    viewModel { RegistrationPasswordViewModel(androidContext(), registerUseCase = get(), validationUseCase = get()) }
    viewModel { MainScreenViewModel(getMoviesUseCase = get(), getMyIdUseCase = get()) }
    viewModel { FavoriteScreenViewModel(getFavoriteMoviesUseCase = get()) }

    viewModel {
        ProfileScreenViewModel(
            getProfileUseCase = get(),
            putProfileUseCase = get(),
            logoutUseCase = get(),
            formatDateUseCase = get(),
            validationUseCase = get()
        )
    }

    viewModel {
        FilmScreenViewModel(
            getFilmDetailsUseCase = get(),
            addToFavoriteUseCase = get(),
            deleteFavoriteMovieUseCase = get(),
            getFavoriteMoviesUseCase = get(),
            deleteReviewUseCase = get(),
            addReviewUseCase = get(),
            putReviewUseCase = get(),
            formatDateUseCase = get()
        )
    }
}