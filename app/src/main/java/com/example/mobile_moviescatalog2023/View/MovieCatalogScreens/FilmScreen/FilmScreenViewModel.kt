package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen

import android.content.Context
import android.widget.Toast
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.lifecycle.viewModelScope
import com.example.mobile_moviescatalog2023.domain.Entities.Models.MovieDetailsModel
import com.example.mobile_moviescatalog2023.domain.Entities.Models.ReviewModifyModel
import com.example.mobile_moviescatalog2023.Network.Network
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.View.Base.BaseViewModel
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.ProfileScreen.ProfileScreenContract
import com.example.mobile_moviescatalog2023.domain.UseCases.CalculateRatingUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.FavoriteMoviesUseCases.AddToFavoriteUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.FavoriteMoviesUseCases.DeleteFavoriteMovieUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.FavoriteMoviesUseCases.GetFavoriteMoviesUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.FormatDateUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.HandleErrorUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.MoviesUseCases.GetFilmDetailsUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.ReviewUseCases.AddReviewUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.ReviewUseCases.DeleteReviewUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.ReviewUseCases.PutReviewUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FilmScreenViewModel(
    private val getFilmDetailsUseCase: GetFilmDetailsUseCase,
    private val addToFavoriteUseCase: AddToFavoriteUseCase,
    private val deleteFavoriteMovieUseCase: DeleteFavoriteMovieUseCase,
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val deleteReviewUseCase: DeleteReviewUseCase,
    private val addReviewUseCase: AddReviewUseCase,
    private val putReviewUseCase: PutReviewUseCase,
    private val formatDateUseCase: FormatDateUseCase,
    private val calculateRatingUseCase: CalculateRatingUseCase,
    private val handleErrorUseCase: HandleErrorUseCase,
    private val context: Context
) : BaseViewModel<FilmScreenContract.Event, FilmScreenContract.State, FilmScreenContract.Effect>() {
    override fun setInitialState() = FilmScreenContract.State(
        isLoaded = false,
        isError = false,
        isRefreshing = false,
        movieDetails = MovieDetailsModel(
            id = "",
            name = null,
            poster = null,
            year = 0,
            country = null,
            genres = null,
            reviews = null,
            time = 0,
            tagline = null,
            description = null,
            director = null,
            budget = null,
            fees = null,
            ageLimit = 0
        ),
        isAddingSuccess = null,
        isDeletingSuccess = null,
        isMyFavorite = false,
        isWithMyReview = false,
        myReviewTextField = "",
        myRating = 1,
        isAnonymous = false,
        currentFilmRating = null
    )

    override fun handleEvents(event: FilmScreenContract.Event) {
        when (event) {
            is FilmScreenContract.Event.RefreshScreen -> refreshScreen(id = event.id)
            is FilmScreenContract.Event.LoadFilmDetails -> loadFilmDetails(id = event.id)
            is FilmScreenContract.Event.AddToFavorite -> addToFavorite(id = event.id)
            is FilmScreenContract.Event.DeleteFavorite -> deleteFavorite(id = event.id)
            is FilmScreenContract.Event.SaveReviewText -> saveReviewText(text = event.text)
            is FilmScreenContract.Event.SaveReviewRating -> saveReviewRating(rating = event.rating)
            is FilmScreenContract.Event.DeleteMyReview ->
                deleteMyReview(filmId = event.filmId, reviewId = event.reviewId)

            is FilmScreenContract.Event.AddMyReview ->
                addMyReview(reviewModifyModel = event.reviewModifyModel, filmId = event.filmId)

            is FilmScreenContract.Event.EditMyReview ->
                editMyReview(
                    reviewModifyModel = event.reviewModifyModel,
                    filmId = event.filmId,
                    reviewId = event.reviewId
                )

            is FilmScreenContract.Event.SaveIsAnonymous ->
                saveIsAnonymous(isAnonymous = event.isAnonymous)
            is FilmScreenContract.Event.NavigationBack -> setEffect {
                FilmScreenContract.Effect.Navigation.Back
            }
        }
    }

    private fun refreshScreen(id: String) {
        setState {
            copy(
                isLoaded = false,
                isError = false,
                isRefreshing = true,
                movieDetails = MovieDetailsModel(
                    id = "",
                    name = null,
                    poster = null,
                    year = 0,
                    country = null,
                    genres = null,
                    reviews = null,
                    time = 0,
                    tagline = null,
                    description = null,
                    director = null,
                    budget = null,
                    fees = null,
                    ageLimit = 0
                ),
                isAddingSuccess = null,
                isDeletingSuccess = null,
                isMyFavorite = false,
                isWithMyReview = false,
                myReviewTextField = "",
                myRating = 1,
                isAnonymous = false,
                currentFilmRating = null
            )
        }
        loadFilmDetails(id)
    }

    private fun loadFilmDetails(id: String) {
        viewModelScope.launch(Dispatchers.Main) {
            getFilmDetailsUseCase.invoke(id).collect { result ->
                result.onSuccess {
                checkIfWithMyReview(it)
                checkIfFavorite(id = it.id)
                    setState {
                        copy(
                            isLoaded = true,
                            movieDetails = formatDateUseCase.formatCreateDateTime(it),
                            currentFilmRating = calculateRatingUseCase.calculateFilmRating(it.reviews)
                        )
                    }
            }.onFailure {
                    handleErrorUseCase.handleError(
                        error = it.message,
                        onInputError = {},
                        onTokenError = {
                            setEffect { FilmScreenContract.Effect.Navigation.ToIntroducing }
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
            }
            setState {copy(isRefreshing = false)}
        }
    }

    private fun checkIfWithMyReview(it: MovieDetailsModel) {
        it.reviews?.forEach {
            if (
                if (it.author != null) {
                    try {
                        it.author.userId == Network.userId
                    } catch (e: Exception) {
                        false
                    }
                } else {
                    false
                }
            ) {
                setState {
                    copy(
                        isWithMyReview = true
                    )
                }
            }
        }
    }

    private fun addToFavorite(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            addToFavoriteUseCase.invoke(id).collect { result ->
                result.onSuccess {
                setState {
                    copy(
                        isMyFavorite = true
                    )
                }
            }.onFailure {
                    handleErrorUseCase.handleError(
                        error = it.message,
                        onInputError = {},
                        onTokenError = {
                            setEffect { FilmScreenContract.Effect.Navigation.ToIntroducing }
                            launch(Dispatchers.Main) {
                                MakeToast(text = context.getString(R.string.toast_auth_error))
                            }
                        },
                        onOtherError = {
                            launch(Dispatchers.Main) {
                                MakeToast(text = context.getString(R.string.toast_fav_add_error))
                            }
                        }
                    )
            }
            }
        }
    }

    private fun deleteFavorite(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteFavoriteMovieUseCase.invoke(id).collect { result ->
                result.onSuccess {
                    setState {
                        copy(
                            isMyFavorite = false
                        )
                    }
                }.onFailure {
                    handleErrorUseCase.handleError(
                        error = it.message,
                        onInputError = {},
                        onTokenError = {
                            setEffect { FilmScreenContract.Effect.Navigation.ToIntroducing }
                            launch(Dispatchers.Main) {
                                MakeToast(text = context.getString(R.string.toast_auth_error))
                            }
                        },
                        onOtherError = {
                            launch(Dispatchers.Main) {
                                MakeToast(text = context.getString(R.string.toast_fav_delete_error))
                            }
                        }
                    )
                }
            }
        }
    }

    private suspend fun checkIfFavorite(id: String) {
        getFavoriteMoviesUseCase.invoke().collect { result ->
            result.onSuccess {
                it.movies?.forEach {
                    if (it.id == id) {
                        setState {
                            copy(
                                isMyFavorite = true
                            )
                        }
                    }
                }
            }.onFailure {
                handleErrorUseCase.handleError(
                    error = it.message,
                    onInputError = {},
                    onTokenError = {
                        setEffect { FilmScreenContract.Effect.Navigation.ToIntroducing }
                        CoroutineScope(Dispatchers.Main).launch {
                            MakeToast(text = context.getString(R.string.toast_auth_error))
                        }
                    },
                    onOtherError = {
                        CoroutineScope(Dispatchers.Main).launch {
                            MakeToast(text = context.getString(R.string.toast_error))
                        }
                    }
                )
            }
        }
    }

    private fun saveReviewText(text: String) {
        setState {
            copy(
                myReviewTextField = text
            )
        }
    }

    private fun saveReviewRating(rating: Int) {
        setState {
            copy(
                myRating = rating
            )
        }
    }

    private fun saveIsAnonymous(isAnonymous: Boolean) {
        setState {
            copy(
                isAnonymous = isAnonymous
            )
        }
    }

    private fun deleteMyReview(filmId: String, reviewId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteReviewUseCase.invoke(filmId, reviewId).collect { result ->
                result.onSuccess {
                    loadFilmDetails(filmId)
                    setState { copy(isWithMyReview = false) }
                }.onFailure {
                    handleErrorUseCase.handleError(
                        error = it.message,
                        onInputError = {
                            launch(Dispatchers.Main) {
                                MakeToast(text = context.getString(R.string.toast_review_delete_error))
                            }
                        },
                        onTokenError = {
                            setEffect { FilmScreenContract.Effect.Navigation.ToIntroducing }
                            launch(Dispatchers.Main) {
                                MakeToast(text = context.getString(R.string.toast_auth_error))
                            }
                        },
                        onOtherError = {
                            launch(Dispatchers.Main) {
                                MakeToast(text = context.getString(R.string.toast_review_delete_error))
                            }
                        }
                    )
                }
            }
        }
    }

    private fun addMyReview(reviewModifyModel: ReviewModifyModel, filmId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            addReviewUseCase.invoke(reviewModifyModel, filmId).collect { result ->
                result.onSuccess {
                    loadFilmDetails(filmId)
                    setState { copy(isWithMyReview = true) }
                }
                    .onFailure {
                        handleErrorUseCase.handleError(
                            error = it.message,
                            onInputError = {
                                launch(Dispatchers.Main) {
                                    MakeToast(text = context.getString(R.string.toast_review_add_error))
                                }
                            },
                            onTokenError = {
                                setEffect { FilmScreenContract.Effect.Navigation.ToIntroducing }
                                launch(Dispatchers.Main) {
                                    MakeToast(text = context.getString(R.string.toast_auth_error))
                                }
                            },
                            onOtherError = {
                                launch(Dispatchers.Main) {
                                    MakeToast(text = context.getString(R.string.toast_review_add_error))
                                }
                            }
                        )
                    }
            }
        }
    }

    private fun editMyReview(
        reviewModifyModel: ReviewModifyModel,
        filmId: String,
        reviewId: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            putReviewUseCase.invoke(reviewModifyModel, filmId, reviewId).collect { result ->
                result.onSuccess {
                    loadFilmDetails(filmId)
                }
                    .onFailure {
                        handleErrorUseCase.handleError(
                            error = it.message,
                            onInputError = {
                                launch(Dispatchers.Main) {
                                    MakeToast(text = context.getString(R.string.toast_review_input_error))
                                }
                            },
                            onTokenError = {
                                setEffect { FilmScreenContract.Effect.Navigation.ToIntroducing }
                                launch(Dispatchers.Main) {
                                    MakeToast(text = context.getString(R.string.toast_auth_error))
                                }
                            },
                            onOtherError = {
                                launch(Dispatchers.Main) {
                                    MakeToast(text = context.getString(R.string.toast_review_input_error))
                                }
                            }
                        )
                    }
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