package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import com.example.mobile_moviescatalog2023.domain.Entities.Models.MovieDetailsModel
import com.example.mobile_moviescatalog2023.domain.Entities.Models.ReviewModifyModel
import com.example.mobile_moviescatalog2023.Network.Network
import com.example.mobile_moviescatalog2023.View.Base.BaseViewModel
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.Composables.FilmRating
import com.example.mobile_moviescatalog2023.domain.Entities.Models.ReviewModel
import com.example.mobile_moviescatalog2023.domain.UseCases.FavoriteMoviesUseCases.AddToFavoriteUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.FavoriteMoviesUseCases.DeleteFavoriteMovieUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.FavoriteMoviesUseCases.GetFavoriteMoviesUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.FormatDateUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.MoviesUseCases.GetFilmDetailsUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.ReviewUseCases.AddReviewUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.ReviewUseCases.DeleteReviewUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.ReviewUseCases.PutReviewUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FilmScreenViewModel(
    private val getFilmDetailsUseCase: GetFilmDetailsUseCase,
    private val addToFavoriteUseCase: AddToFavoriteUseCase,
    private val deleteFavoriteMovieUseCase: DeleteFavoriteMovieUseCase,
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val deleteReviewUseCase: DeleteReviewUseCase,
    private val addReviewUseCase: AddReviewUseCase,
    private val putReviewUseCase: PutReviewUseCase,
    private val formatDateUseCase: FormatDateUseCase
) : BaseViewModel<FilmScreenContract.Event, FilmScreenContract.State, FilmScreenContract.Effect>() {

    override fun setInitialState() = FilmScreenContract.State(
        isLoaded = false,
        isLoading = false,
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

    private fun loadFilmDetails(id: String) {
        setState { copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            getFilmDetailsUseCase.invoke(id)
                .onSuccess {
                    setState {
                        copy(
                            isLoaded = true,
                            movieDetails = formatDateUseCase.formatCreateDateTime(it),
                            currentFilmRating = calculateFilmRating(it.reviews)
                        )
                    }
                    checkIfWithMyReview(it)
                    checkIfFavorite(id = it.id)
                }.onFailure {
                    setState {
                        copy(
                            isLoaded = false
                        )
                    }
                }
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
            addToFavoriteUseCase.invoke(id)
            setState {
                copy(
                    isMyFavorite = true
                )
            }
        }
    }

    private fun deleteFavorite(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteFavoriteMovieUseCase.invoke(id)
            setState {
                copy(
                    isMyFavorite = false
                )
            }
        }
    }

    private fun checkIfFavorite(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getFavoriteMoviesUseCase.invoke()
                .onSuccess {
                        it.movies?.forEach {
                            if (id == it.id) {
                                setState {
                                    copy(
                                        isMyFavorite = true
                                    )
                                }
                            }
                    }
                }.onFailure {

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
            deleteReviewUseCase.invoke(filmId, reviewId)
            loadFilmDetails(filmId)
        }
    }

    private fun addMyReview(reviewModifyModel: ReviewModifyModel, filmId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            addReviewUseCase.invoke(reviewModifyModel, filmId)
            loadFilmDetails(filmId)
        }
    }

    private fun editMyReview(
        reviewModifyModel: ReviewModifyModel,
        filmId: String,
        reviewId: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            putReviewUseCase.invoke(reviewModifyModel, filmId, reviewId)
            loadFilmDetails(filmId)
        }
    }

    private fun calculateFilmRating(reviews: List<ReviewModel>?): FilmRating? {
        if (reviews == null) {
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
                rating = if (rating != 10.0) {
                    rating.toString().substring(startIndex = 0, endIndex = 3)
                } else {
                    rating.toString().substring(startIndex = 0, endIndex = 4)
                },
                color = color
            )
        }
    }
}