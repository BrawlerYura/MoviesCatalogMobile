package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen

import androidx.lifecycle.viewModelScope
import com.example.mobile_moviescatalog2023.domain.Entities.Models.MovieDetailsModel
import com.example.mobile_moviescatalog2023.domain.Entities.Models.ReviewModifyModel
import com.example.mobile_moviescatalog2023.Network.Network
import com.example.mobile_moviescatalog2023.View.Base.BaseViewModel
import com.example.mobile_moviescatalog2023.domain.UseCases.CalculateRatingUseCase
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
    private val formatDateUseCase: FormatDateUseCase,
    private val calculateRatingUseCase: CalculateRatingUseCase
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
            getFilmDetailsUseCase.invoke(id).collect { result ->
                result.onSuccess {
                setState {
                    copy(
                        isLoaded = true,
                        movieDetails = formatDateUseCase.formatCreateDateTime(it),
                        currentFilmRating = calculateRatingUseCase.calculateFilmRating(it.reviews)
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
                .onSuccess {
                    setState {
                        copy(
                            isMyFavorite = true
                        )
                    }
                } .onFailure {

                }
        }
    }

    private fun deleteFavorite(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteFavoriteMovieUseCase.invoke(id)
                .onSuccess {
                    setState {
                        copy(
                            isMyFavorite = false
                        )
                    }
                }.onFailure {

                }
        }
    }

    private fun checkIfFavorite(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getFavoriteMoviesUseCase.invoke()
                .onSuccess {
                        it.movies?.forEach {
                                setState {
                                    copy(
                                        isMyFavorite = true
                                    )
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
                .onSuccess {
                    loadFilmDetails(filmId)
                    setState { copy(isWithMyReview = false) }
                } .onFailure {

                }
        }
    }

    private fun addMyReview(reviewModifyModel: ReviewModifyModel, filmId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            addReviewUseCase.invoke(reviewModifyModel, filmId)
                .onSuccess {
                    loadFilmDetails(filmId)
                }
                .onFailure {

                }
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
}