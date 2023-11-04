package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import com.example.mobile_moviescatalog2023.domain.Entities.Models.MovieElementModel
import com.example.mobile_moviescatalog2023.domain.Entities.Models.MoviesModel
import com.example.mobile_moviescatalog2023.domain.Entities.Models.ReviewModel
import com.example.mobile_moviescatalog2023.domain.Entities.Models.ReviewShortModel
import com.example.mobile_moviescatalog2023.Network.Movie.MovieRepository
import com.example.mobile_moviescatalog2023.Network.Network
import com.example.mobile_moviescatalog2023.Network.User.UserRepository
import com.example.mobile_moviescatalog2023.View.Base.BaseViewModel
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.Composables.FilmRating
import com.example.mobile_moviescatalog2023.domain.UseCases.MoviesUseCases.GetMoviesUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.UserUseCases.GetMyIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainScreenViewModel(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val getMyIdUseCase: GetMyIdUseCase,
): BaseViewModel<MainScreenContract.Event, MainScreenContract.State, MainScreenContract.Effect>() {

    override fun setInitialState() = MainScreenContract.State(
        currentMoviePage = 1,
        isRequestingMoviePage = true,
        movieList = listOf(),
        movieCarouselList = listOf(),
        isSuccess = false,
        pageCount = 1,
        isUpdatingList = false,
        filmRatingsList = listOf()
    )

    override fun handleEvents(event: MainScreenContract.Event) {
        when (event) {
            is MainScreenContract.Event.UpdateMoviesList -> updateMoviesList()
            is MainScreenContract.Event.GetMovies -> getMovies()
        }
    }

    private fun updateMoviesList() {
        setState { copy(isUpdatingList = true) }
        viewModelScope.launch(Dispatchers.IO) {
            getMoviesUseCase.invoke(state.value.currentMoviePage)
                .collect { result ->
                    result.onSuccess {
                        setState {
                            copy(
                                movieList = state.value.movieList + it.movies,
                                currentMoviePage = state.value.currentMoviePage + 1,
                                isUpdatingList = false
                            )
                        }
                        it.movies.forEach {
                            setState {
                                copy(
                                    filmRatingsList = filmRatingsList + calculateFilmRating(it.reviews)
                                )
                            }
                        }
                    }.onFailure {
                        setState {
                            copy(
                                isUpdatingList = false
                            )
                        }
                    }
                }
        }
    }
    private fun getMovies()
    {
        setState {
            copy(
                isUpdatingList = true
            )
        }
        viewModelScope.launch(Dispatchers.IO) {
            getMoviesUseCase.invoke(1)
                .collect { result ->
                    result.onSuccess {
                        setState { copy(
                            isRequestingMoviePage = false,
                            isSuccess = true,
                            movieCarouselList = it.movies.take(4),
                            movieList = it.movies.drop(4),
                            currentMoviePage = state.value.currentMoviePage + 1,
                            pageCount = it.pageInfo.pageCount,
                            isUpdatingList = false
                        ) }
                        getMyId()
                        state.value.movieList.forEach {
                            setState {
                                copy(
                                    filmRatingsList = filmRatingsList + calculateFilmRating(it.reviews)
                                )
                            }
                        }
                    }.onFailure {
                        setState { copy(isRequestingMoviePage = true, isSuccess = false, isUpdatingList = false) }
                    }
                }
        }
    }

    private suspend fun getMyId()
    {
        getMyIdUseCase.invoke()
            .collect { result ->
                result.onSuccess {
                    Network.userId = it.id
                }.onFailure {

                }
            }
    }

    private fun calculateFilmRating(reviews: List<ReviewShortModel>?): FilmRating? {
        if(reviews == null) {
            return null
        } else {

            var sumScore: Int = 0
            reviews.forEach {
                sumScore += it.rating
            }

            val rating = (sumScore.toDouble() / reviews.count())

            val color = when {
                rating >= 0.0 && rating < 3.0 -> {
                    Color(0xFFE64646)
                }
                rating >= 3.0 && rating < 4.0 -> {
                    Color(0xFFF05C44)
                }
                rating >= 4.0 && rating < 5.0 -> {
                    Color(0xFFFFA000)
                }
                rating >= 5.0 && rating < 7.0 -> {
                    Color(0xFFFFD54F)
                }
                rating >= 7.0 && rating < 9.0 -> {
                    Color(0xFFA3CD4A)
                }
                else -> {
                    Color(0xFF4BB34B)
                }
            }

            return FilmRating(
                rating = if (rating != 10.0)
                { rating.toString().substring(startIndex = 0, endIndex = 3) }
                else { rating.toString().substring(startIndex = 0, endIndex = 4) },
                color = color
            )
        }
    }
}