package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FavoriteScreen

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import com.example.mobile_moviescatalog2023.Network.Network
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.View.Base.BaseViewModel
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.Composables.FilmRating
import com.example.mobile_moviescatalog2023.domain.UseCases.CalculateRatingUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.FavoriteMoviesUseCases.GetFavoriteMoviesUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.FromListToPartsMovieUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.HandleErrorUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.MoviesUseCases.GetFilmDetailsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteScreenViewModel(
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val fromListToPartsMovieUseCase: FromListToPartsMovieUseCase,
    private val getFilmDetailsUseCase: GetFilmDetailsUseCase,
    private val calculateRatingUseCase: CalculateRatingUseCase,
    private val handleErrorUseCase: HandleErrorUseCase,
    private val context: Context
) : BaseViewModel<FavoriteScreenContract.Event, FavoriteScreenContract.State, FavoriteScreenContract.Effect>() {

    init {
        getFavoriteMovies()
    }

    override fun setInitialState() = FavoriteScreenContract.State(
        favoriteMovieList = null,
        isLoaded = false,
        isError = false,
        isRefreshing = true,
        myRating = listOf()
    )

    override fun handleEvents(event: FavoriteScreenContract.Event) {
        when (event) {
            is FavoriteScreenContract.Event.RefreshScreen -> refreshScreen()
            is FavoriteScreenContract.Event.GetFavoriteMovies -> getFavoriteMovies()
            is FavoriteScreenContract.Event.NavigationToMain -> setEffect { FavoriteScreenContract.Effect.Navigation.ToMain }
            is FavoriteScreenContract.Event.NavigationToProfile -> setEffect { FavoriteScreenContract.Effect.Navigation.ToProfile }
            is FavoriteScreenContract.Event.NavigationToFilm -> setEffect {
                FavoriteScreenContract.Effect.Navigation.ToFilm(
                    id = event.id
                )
            }
        }
    }

    private fun refreshScreen() {
        setState {
            copy(
                favoriteMovieList = null,
                isLoaded = false,
                isError = false,
                isRefreshing = true
            )
        }
        getFavoriteMovies()
    }

    private fun getFavoriteMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            getFavoriteMoviesUseCase.invoke().collect { result ->
                result.onSuccess {
                    if (it.movies != null) {
                        it.movies.forEach {
                            loadMyFilmReview(it.id)
                        }
                    }
                    setState {
                        copy(
                            favoriteMovieList = fromListToPartsMovieUseCase.fromListToPartsMovies(it.movies),
                            isLoaded = true
                        )
                    }
                }.onFailure {
                    handleErrorUseCase.handleError(
                        error = it.message,
                        onInputError = {},
                        onTokenError = {
                            setEffect { FavoriteScreenContract.Effect.Navigation.ToIntroducing }
                            launch(Dispatchers.Main) {
                                MakeToast(text = context.getString(R.string.toast_auth_error))
                            }
                        },
                        onOtherError = {
                            setState {
                                copy(
                                    isLoaded = false,
                                    isError = true
                                )
                            }
                        }
                    )
                }
                setState { copy(isRefreshing = false) }
            }
        }
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
                        setEffect { FavoriteScreenContract.Effect.Navigation.ToIntroducing }
                        CoroutineScope(Dispatchers.Main).launch {
                            MakeToast(text = context.getString(R.string.toast_auth_error))
                        }
                    },
                    onOtherError = {
                        setState {
                            copy(myRating = myRating + null)
                        }
                        CoroutineScope(Dispatchers.Main).launch {
                            MakeToast(text = context.getString(R.string.toast_error))
                        }
                    }
                )

            }
        }
    }

    private fun MakeToast(
        text: String
    ) {
        Toast.makeText(
            context,
            text,
            Toast.LENGTH_SHORT
        ).show()
    }
}