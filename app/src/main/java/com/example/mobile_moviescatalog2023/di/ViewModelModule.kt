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
    viewModel { SplashScreenViewModel(checkTokenUseCase = get(), handleErrorUseCase = get()) }
    viewModel {
        LoginViewModel(
            androidContext(),
            loginUseCase = get(),
            validationUseCase = get(),
            handleErrorUseCase = get()
        )
    }
    viewModel { RegistrationViewModel(formatDateUseCase = get(), validationUseCase = get()) }
    viewModel {
        RegistrationPasswordViewModel(
            androidContext(),
            registerUseCase = get(),
            validationUseCase = get(),
            handleErrorUseCase = get()
        )
    }
    viewModel {
        MainScreenViewModel(
            getMoviesUseCase = get(),
            getMyIdUseCase = get(),
            getFilmDetailsUseCase = get(),
            calculateRatingUseCase = get(),
            handleErrorUseCase = get()
        )
    }
    viewModel {
        FavoriteScreenViewModel(
            getFavoriteMoviesUseCase = get(),
            fromListToPartsMovieUseCase = get(),
            calculateRatingUseCase = get(),
            getFilmDetailsUseCase = get(),
            handleErrorUseCase = get()
        )
    }

    viewModel {
        ProfileScreenViewModel(
            getProfileUseCase = get(),
            putProfileUseCase = get(),
            logoutUseCase = get(),
            formatDateUseCase = get(),
            validationUseCase = get(),
            handleErrorUseCase = get()
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
            formatDateUseCase = get(),
            calculateRatingUseCase = get(),
            handleErrorUseCase = get()
        )
    }
}