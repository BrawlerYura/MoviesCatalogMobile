package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen

import android.annotation.SuppressLint
import androidx.lifecycle.viewModelScope
import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.MovieElementModel
import com.example.mobile_moviescatalog2023.Network.FavoriteMovies.FavoriteMoviesRepository
import com.example.mobile_moviescatalog2023.Network.Movie.MovieRepository
import com.example.mobile_moviescatalog2023.View.Base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

class FilmScreenViewModel(
    val movieRepository: MovieRepository,
): BaseViewModel<FilmScreenContract.Event, FilmScreenContract.State, FilmScreenContract.Effect>() {

    override fun setInitialState() = FilmScreenContract.State(
        isSuccess = null,
        movieDetails = null
    )

    override fun handleEvents(event: FilmScreenContract.Event) {
        when(event) {
            is FilmScreenContract.Event.LoadFilmDetails -> loadFilmDetails(id = event.id)
        }
    }

    private fun loadFilmDetails(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.getMovieDetails(id)
                .collect { result ->
                    result.onSuccess {
                        setState {
                            copy(
                                isSuccess = true,
                                movieDetails = it
                            )
                        }
                    }.onFailure {
                        setState {
                            copy(
                                isSuccess = false
                            )
                        }
                    }
                }
        }
    }
}