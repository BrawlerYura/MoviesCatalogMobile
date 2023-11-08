package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen

import androidx.lifecycle.viewModelScope
import com.example.mobile_moviescatalog2023.Network.Network
import com.example.mobile_moviescatalog2023.View.Base.BaseViewModel
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.Composables.FilmRating
import com.example.mobile_moviescatalog2023.domain.UseCases.CalculateRatingUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.MoviesUseCases.GetFilmDetailsUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.MoviesUseCases.GetMoviesUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.UserUseCases.GetMyIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainScreenViewModel(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val getMyIdUseCase: GetMyIdUseCase,
    private val getFilmDetailsUseCase: GetFilmDetailsUseCase,
    private val calculateRatingUseCase: CalculateRatingUseCase
) : BaseViewModel<MainScreenContract.Event, MainScreenContract.State, MainScreenContract.Effect>() {

    init {
        getMovies()
        getMyId()
    }

    override fun setInitialState() = MainScreenContract.State(
        currentMoviePage = 1,
        isRequestingMoviePage = true,
        movieList = listOf(),
        movieCarouselList = listOf(),
        isSuccess = false,
        pageCount = 1,
        isUpdatingList = false,
        filmRatingsList = listOf(),
        myRating = listOf()
    )

    override fun handleEvents(event: MainScreenContract.Event) {
        when (event) {
            is MainScreenContract.Event.UpdateMoviesList -> updateMoviesList()
            is MainScreenContract.Event.GetMovies -> getMovies()
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
                getMoviesUseCase.invoke(state.value.currentMoviePage)
                    .onSuccess {
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
                                    filmRatingsList = filmRatingsList + calculateRatingUseCase.calculateFilmsRating(
                                        it.reviews
                                    )
                                )
                            }
                            loadMyFilmReview(it.id)
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

    private fun getMovies() {
        setState { copy(isUpdatingList = true) }
        viewModelScope.launch(Dispatchers.IO) {
            getMoviesUseCase.invoke(1)
                .onSuccess {
                    setState {
                        copy(
                            movieCarouselList = it.movies.take(4),
                            movieList = it.movies.drop(4),
                            currentMoviePage = state.value.currentMoviePage + 1,
                            pageCount = it.pageInfo.pageCount,
                            isRequestingMoviePage = false,
                            isSuccess = true,
                            isUpdatingList = false
                        )
                    }
                    state.value.movieList.forEach {
                        setState {
                            copy(
                                filmRatingsList = filmRatingsList + calculateRatingUseCase.calculateFilmsRating(
                                    it.reviews
                                ),
                            )
                        }
                        loadMyFilmReview(it.id)
                    }
                }.onFailure {
                    setState {
                        copy(
                            isRequestingMoviePage = true,
                            isSuccess = false,
                            isUpdatingList = false
                        )
                    }
                }
        }
    }

    private suspend fun loadMyFilmReview(id: String) {
        getFilmDetailsUseCase.invoke(id)
            .onSuccess {
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
                setState {
                    copy(myRating = myRating + null)
                }
            }
    }


    private fun getMyId() {
        viewModelScope.launch(Dispatchers.IO) {
            getMyIdUseCase.invoke().onSuccess {
                Network.userId = it.id
            }
        }
    }


}