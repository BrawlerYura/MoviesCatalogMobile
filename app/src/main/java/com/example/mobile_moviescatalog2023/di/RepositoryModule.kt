package com.example.mobile_moviescatalog2023.di

import com.example.mobile_moviescatalog2023.Network.Auth.AuthRepository
import com.example.mobile_moviescatalog2023.Network.FavoriteMovies.FavoriteMoviesRepository
import com.example.mobile_moviescatalog2023.Network.Movie.MovieRepository
import com.example.mobile_moviescatalog2023.Network.Network
import com.example.mobile_moviescatalog2023.Network.User.UserRepository
import org.koin.dsl.module

val RepositoryModule = module {
    single { Network.getUserApi() }
    single { UserRepository(get()) }

    single { Network.getAuthApi() }
    single { AuthRepository(get()) }

    single { Network.getFavoriteMoviesApi() }
    single { FavoriteMoviesRepository(get()) }

    single { Network.getMovieApi() }
    single { MovieRepository(get()) }
}