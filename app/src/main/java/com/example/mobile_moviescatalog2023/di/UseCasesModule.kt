package com.example.mobile_moviescatalog2023.di

import com.example.mobile_moviescatalog2023.domain.UseCases.AuthUseCases.LoginUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.AuthUseCases.LogoutUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.AuthUseCases.RegisterUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.CalculateRatingUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.FavoriteMoviesUseCases.AddToFavoriteUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.FavoriteMoviesUseCases.DeleteFavoriteMovieUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.FavoriteMoviesUseCases.GetFavoriteMoviesUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.FormatDateUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.FromListToPartsMovieUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.HandleErrorUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.MoviesUseCases.GetFilmDetailsUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.MoviesUseCases.GetMoviesUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.ReviewUseCases.AddReviewUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.ReviewUseCases.DeleteReviewUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.ReviewUseCases.PutReviewUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.UserUseCases.CheckTokenUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.UserUseCases.GetMyIdUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.UserUseCases.GetProfileUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.UserUseCases.PutProfileUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.ValidationUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val UseCasesModule = module {
    single { LoginUseCase(repository = get()) }
    single { RegisterUseCase(repository = get()) }
    single { AddToFavoriteUseCase(repository = get()) }
    single { DeleteFavoriteMovieUseCase(repository = get()) }
    single { GetFavoriteMoviesUseCase(repository = get()) }
    single { GetFilmDetailsUseCase(repository = get()) }
    single { GetMoviesUseCase(repository = get()) }
    single { AddReviewUseCase(repository = get()) }
    single { DeleteReviewUseCase(repository = get()) }
    single { PutReviewUseCase(repository = get()) }
    single { GetMyIdUseCase(repository = get()) }
    single { GetProfileUseCase(repository = get()) }
    single { CheckTokenUseCase(repository = get(), context = androidContext()) }
    single { LogoutUseCase(repository = get(), context = androidContext()) }
    single { PutProfileUseCase(repository = get()) }
    single { FormatDateUseCase() }
    single { ValidationUseCase() }
    single { FromListToPartsMovieUseCase() }
    single { CalculateRatingUseCase() }
    single { HandleErrorUseCase() }
}