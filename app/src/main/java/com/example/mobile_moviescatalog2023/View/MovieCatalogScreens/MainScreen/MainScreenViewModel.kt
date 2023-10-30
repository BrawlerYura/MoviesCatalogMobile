package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.MovieElementModel
import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.MoviesModel
import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.ReviewModel
import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.ReviewShortModel
import com.example.mobile_moviescatalog2023.Network.Movie.MovieRepository
import com.example.mobile_moviescatalog2023.View.Base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainScreenViewModel(
): BaseViewModel<MainScreenContract.Event, MainScreenContract.State, MainScreenContract.Effect>() {

    private val movieRepository = MovieRepository()
    override fun setInitialState() = MainScreenContract.State(
        currentMoviePage = 1,
        isRequestingMoviePage = true,
        movieList = listOf(),
        movieCarouselList = listOf(),
        isSuccess = false,
        pageCount = 1,
        isUpdatingList = false
    )

    override fun handleEvents(event: MainScreenContract.Event) {
        when (event) {
            is MainScreenContract.Event.UpdateMoviesList -> updateMoviesList()
            is MainScreenContract.Event.GetMovies -> getMovies()
            is MainScreenContract.Event.CalculateFilmScore -> calculateFilmScore(reviews = event.reviews)
        }
    }

    private fun updateMoviesList() {
        setState { copy(isUpdatingList = true) }
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.getMovies(state.value.currentMoviePage)
                .collect { result ->
                    result.onSuccess {
                        setState {
                            copy(
                                movieList = state.value.movieList + it.movies,
                                currentMoviePage = state.value.currentMoviePage + 1,
                                isUpdatingList = false
                            )
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
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.getMovies(1)
                .collect { result ->
                    result.onSuccess {
                        setState { copy(
                            isRequestingMoviePage = false,
                            isSuccess = true,
                            movieCarouselList = state.value.movieCarouselList + it.movies.take(4),
                            movieList = state.value.movieList + it.movies.drop(4),
                            currentMoviePage = state.value.currentMoviePage + 1,
                            pageCount = it.pageInfo.pageCount
                        ) }
                    }.onFailure {
                        setState { copy(isRequestingMoviePage = true, isSuccess = false) }
                    }
                }
        }
    }

    private fun calculateFilmScore(reviews: List<ReviewShortModel>?): String {
        val _reviews: List<ReviewShortModel>
        if(reviews == null) {
            return ""
        } else {
            _reviews = reviews
        }
        var sumScore: Int = 0
        _reviews.forEach {
            sumScore += it.rating
        }
        return (sumScore / _reviews.count()).toString()
    }
}