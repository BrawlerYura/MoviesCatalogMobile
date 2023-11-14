package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.mobile_moviescatalog2023.Network.Network
import com.example.mobile_moviescatalog2023.View.Base.BaseViewModel
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.Composables.FilmRating
import com.example.mobile_moviescatalog2023.domain.UseCases.CalculateRatingUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.HandleErrorUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.MoviesUseCases.GetFilmDetailsUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.MoviesUseCases.GetMoviesUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.UserUseCases.GetMyIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainScreenViewModel(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val getMyIdUseCase: GetMyIdUseCase,
    private val getFilmDetailsUseCase: GetFilmDetailsUseCase,
    private val calculateRatingUseCase: CalculateRatingUseCase,
    private val handleErrorUseCase: HandleErrorUseCase
) : BaseViewModel<MainScreenContract.Event, MainScreenContract.State, MainScreenContract.Effect>() {

    init {
        getMovies()
        getMyId()
    }

    override fun setInitialState() = MainScreenContract.State(
        currentMoviePage = 1,
        movieList = listOf(),
        isError = false,
        isLoaded = false,
        pageCount = 1,
        isUpdatingList = false,
        filmRatingsList = listOf(),
        myRating = listOf(),
        isRefreshing = false
    )

    override fun handleEvents(event: MainScreenContract.Event) {
        when (event) {
            is MainScreenContract.Event.UpdateMoviesList -> updateMoviesList()
            is MainScreenContract.Event.GetMovies -> getMovies()
            is MainScreenContract.Event.RefreshMovies -> refreshMovies()
            is MainScreenContract.Event.NavigationToFilm -> setEffect {
                MainScreenContract.Effect.Navigation.ToFilm(id = event.id)
            }

            is MainScreenContract.Event.NavigationToProfile -> setEffect {
                MainScreenContract.Effect.Navigation.ToProfile
            }

            is MainScreenContract.Event.NavigationToFavorite -> setEffect {
                MainScreenContract.Effect.Navigation.ToFavorite
            }
        }
    }

    private fun updateMoviesList() {
        viewModelScope.launch(Dispatchers.IO) {
            if (!state.value.isUpdatingList) {
                setState {
                    copy(isUpdatingList = true)
                }
                getMoviesUseCase.invoke(state.value.currentMoviePage).collect { result ->
                    result.onSuccess {
                        it.movies.forEach {
                            setState {
                                copy(
                                    filmRatingsList = filmRatingsList + calculateRatingUseCase.calculateFilmsRating(
                                        it.reviews
                                    )
                                )
                            }
                            loadMyFilmReview(it.id)
                        }
                        setState {
                            copy(
                                movieList = state.value.movieList + it.movies,
                                currentMoviePage = state.value.currentMoviePage + 1,
                                isUpdatingList = false
                            )
                        }
                    }.onFailure {
                        handleErrorUseCase.handleError(
                            error = it.message,
                            onInputError = { },
                            onTokenError = {
                                MainScreenContract.Effect.Navigation.ToIntroducing
                            },
                            onOtherError = {
                                setState {
                                    copy(
                                        isUpdatingList = false
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    }

    private fun getMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            getMoviesUseCase.invoke(1).collect { result ->
                result.onSuccess {
                    it.movies.forEach {
                        setState {
                            copy(
                                filmRatingsList = filmRatingsList + calculateRatingUseCase.calculateFilmsRating(
                                    it.reviews
                                ),
                            )
                        }
                        loadMyFilmReview(it.id)
                    }
                    setState {
                        copy(
                            movieList = it.movies,
                            currentMoviePage = state.value.currentMoviePage + 1,
                            pageCount = it.pageInfo.pageCount,
                            isError = false,
                            isUpdatingList = false,
                            isLoaded = true
                        )
                    }
                }.onFailure {
                    handleErrorUseCase.handleError(
                        error = it.message,
                        onInputError = { },
                        onTokenError = {
                            MainScreenContract.Effect.Navigation.ToIntroducing
                        },
                        onOtherError = {
                            setState {
                                copy(
                                    isError = true,
                                    isUpdatingList = false,
                                    isLoaded = false
                                )
                            }
                        }
                    )
                }
            }
            setState { copy(isRefreshing = false) }
        }
    }

    private fun refreshMovies() {
        setState {
            copy(
                currentMoviePage = 1,
                movieList = listOf(),
                isLoaded = false,
                isError = false,
                isUpdatingList = false,
                filmRatingsList = listOf(),
                myRating = listOf(),
                isRefreshing = true
            )
        }
        getMovies()
    }

    private suspend fun loadMyFilmReview(id: String) {
        getFilmDetailsUseCase.invoke(id).collect { result ->
            result.onSuccess {
                var flag = false
                it.reviews?.forEach { review ->
                    if (!flag && (try {
                            review.author?.userId == Network.userId
                        } catch (e: Exception) {
                            false
                        })
                    ) {
                        setState {
                            copy(
                                myRating = myRating + FilmRating(
                                    review.rating.toString(),
                                    calculateRatingUseCase.calculateFilmRatingColor(review.rating.toDouble())
                                )
                            )
                        }
                        flag = true
                    }
                }
                if (!flag) {
                    setState { copy(myRating = myRating + null) }
                }
            }.onFailure {
                handleErrorUseCase.handleError(
                    error = it.message,
                    onInputError = { },
                    onTokenError = {
                        MainScreenContract.Effect.Navigation.ToIntroducing
                    },
                    onOtherError = {
                        setState {
                            copy(myRating = myRating + null)
                        }
                    }
                )
            }
        }
    }


    private fun getMyId() {
        viewModelScope.launch(Dispatchers.IO) {
            getMyIdUseCase.invoke().collect { result ->
                result.onSuccess {
                    Network.userId = it.id
                }
            }
        }
    }
}