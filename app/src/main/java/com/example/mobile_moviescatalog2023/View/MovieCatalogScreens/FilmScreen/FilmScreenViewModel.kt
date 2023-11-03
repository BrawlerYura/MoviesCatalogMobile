package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen

import androidx.lifecycle.viewModelScope
import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.MovieDetailsModel
import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.ReviewModifyModel
import com.example.mobile_moviescatalog2023.Network.FavoriteMovies.FavoriteMoviesRepository
import com.example.mobile_moviescatalog2023.Network.Movie.MovieRepository
import com.example.mobile_moviescatalog2023.Network.Network
import com.example.mobile_moviescatalog2023.Network.Review.ReviewRepository
import com.example.mobile_moviescatalog2023.View.Base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FilmScreenViewModel(
    private val movieRepository: MovieRepository,
    private val favoriteMoviesRepository: FavoriteMoviesRepository,
    private val reviewRepository: ReviewRepository
): BaseViewModel<FilmScreenContract.Event, FilmScreenContract.State, FilmScreenContract.Effect>() {

    override fun setInitialState() = FilmScreenContract.State(
        isSuccess = null,
        movieDetails = null,
        isAddingSuccess = null,
        isDeletingSuccess = null,
        isMyFavorite = false,
        isWithMyReview = false,
        myReviewTextField = "",
        myRating = 1,
        isAnonymous = false
    )

    override fun handleEvents(event: FilmScreenContract.Event) {
        when(event) {
            is FilmScreenContract.Event.LoadFilmDetails -> loadFilmDetails(id = event.id)
            is FilmScreenContract.Event.AddToFavorite -> addToFavorite(id = event.id)
            is FilmScreenContract.Event.DeleteFavorite -> deleteFavorite(id = event.id)
            is FilmScreenContract.Event.SaveReviewText -> saveReviewText(text = event.text)
            is FilmScreenContract.Event.SaveReviewRating -> saveReviewRating(rating = event.rating)
            is FilmScreenContract.Event.DeleteMyReview -> deleteMyReview(filmId = event.filmId, reviewId = event.reviewId)
            is FilmScreenContract.Event.AddMyReview -> addMyReview(reviewModifyModel = event.reviewModifyModel, filmId = event.filmId)
            is FilmScreenContract.Event.EditMyReview -> editMyReview(reviewModifyModel = event.reviewModifyModel, filmId = event.filmId, reviewId = event.reviewId)
            is FilmScreenContract.Event.SaveIsAnonymous -> saveIsAnonymous(isAnonymous = event.isAnonymous)
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
                        checkIfWithMyReview(it)
                        checkIfFavorite(id = it.id)
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

    private fun checkIfWithMyReview(it: MovieDetailsModel) {
        it.reviews?.forEach {
            if(
                try { it.author.userId == Network.userId }
                catch (e: Exception) { false }
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
            favoriteMoviesRepository.addFavoriteMovie(id)
                .collect { result ->
                    result.onSuccess {
                        setState {
                            copy(
                                isAddingSuccess = true,
                                isMyFavorite = true
                            )
                        }
                    }.onFailure {
                        setState {
                            copy(
                                isAddingSuccess = false
                            )
                        }
                    }
                }
        }
    }

    private fun deleteFavorite(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteMoviesRepository.deleteFavoriteMovie(id)
                .collect { result ->
                    result.onSuccess {
                        setState {
                            copy(
                                isDeletingSuccess = true,
                                isMyFavorite = false
                            )
                        }
                    }.onFailure {
                        setState {
                            copy(
                                isDeletingSuccess = false
                            )
                        }
                    }
                }
        }
    }

    private fun checkIfFavorite(id: String){
        viewModelScope.launch(Dispatchers.IO) {
            favoriteMoviesRepository.getFavoriteMovies()
                .collect { result ->
                    result.onSuccess {
                        if(it.movies != null) {
                            it.movies.forEach {
                                if(id == it.id) {
                                    setState {
                                        copy(
                                            isMyFavorite = true
                                        )
                                    }
                                }
                            }
                        } else {
                            setState {
                                copy(
                                    isMyFavorite = false
                                )
                            }
                        }
                    }.onFailure {

                    }
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

    private fun deleteMyReview(filmId: String, reviewId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            reviewRepository.deleteReview(filmId, reviewId)

            loadFilmDetails(filmId)
        }
    }

    private fun addMyReview(reviewModifyModel: ReviewModifyModel, filmId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            reviewRepository.addReview(reviewModifyModel, filmId)
                .collect { result ->
                    result.onSuccess {
                        loadFilmDetails(filmId)
                    }.onFailure {

                    }
                }
        }
    }
    private fun editMyReview(reviewModifyModel: ReviewModifyModel, filmId: String, reviewId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            reviewRepository.editReview(reviewModifyModel, filmId, reviewId)
                .collect { result ->
                    result.onSuccess {
                        loadFilmDetails(filmId)
                    }.onFailure {

                    }
                }
        }
    }

    private fun saveIsAnonymous(isAnonymous: Boolean) {
        setState {
            copy(
                isAnonymous = isAnonymous
            )
        }
    }
}