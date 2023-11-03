package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen

import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.MovieDetailsModel
import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.ReviewModifyModel
import com.example.mobile_moviescatalog2023.View.Base.ViewEvent
import com.example.mobile_moviescatalog2023.View.Base.ViewSideEffect
import com.example.mobile_moviescatalog2023.View.Base.ViewState

class FilmScreenContract {

    sealed class Event : ViewEvent {
        class LoadFilmDetails(val id: String) : Event()
        class AddToFavorite(val id: String) : Event()
        class DeleteFavorite(val id: String) : Event()
        class SaveReviewText(val text: String) : Event()
        class SaveReviewRating(val rating: Int) : Event()
        class DeleteMyReview(val filmId: String, val reviewId: String) : Event()
        class AddMyReview(val reviewModifyModel: ReviewModifyModel, val filmId: String) : Event()
        class EditMyReview(val reviewModifyModel: ReviewModifyModel, val filmId: String, val reviewId: String) : Event()
        class SaveIsAnonymous(val isAnonymous: Boolean): Event()
    }

    data class State(
        val isSuccess: Boolean?,
        val movieDetails: MovieDetailsModel,
        val isAddingSuccess: Boolean?,
        val isDeletingSuccess: Boolean?,
        val isMyFavorite: Boolean,
        val isWithMyReview: Boolean,
        val myReviewTextField: String,
        val myRating: Int,
        val isAnonymous: Boolean
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object Back : Navigation()
        }
    }
}