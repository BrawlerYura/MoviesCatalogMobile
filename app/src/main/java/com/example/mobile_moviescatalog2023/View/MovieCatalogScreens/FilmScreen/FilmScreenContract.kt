package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen

import com.example.mobile_moviescatalog2023.View.Base.ViewEvent
import com.example.mobile_moviescatalog2023.View.Base.ViewSideEffect
import com.example.mobile_moviescatalog2023.View.Base.ViewState
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.Composables.FilmRating
import com.example.mobile_moviescatalog2023.domain.Entities.Models.MovieDetailsModel
import com.example.mobile_moviescatalog2023.domain.Entities.Models.ReviewModifyModel

class FilmScreenContract {

    sealed class Event : ViewEvent {
        class RefreshScreen(val id: String) : Event()
        class LoadFilmDetails(val id: String) : Event()
        class AddToFavorite(val id: String) : Event()
        class DeleteFavorite(val id: String) : Event()
        class SaveReviewText(val text: String) : Event()
        class SaveReviewRating(val rating: Int) : Event()
        class DeleteMyReview(val filmId: String, val reviewId: String) : Event()
        class AddMyReview(val reviewModifyModel: ReviewModifyModel, val filmId: String) : Event()
        class EditMyReview(
            val reviewModifyModel: ReviewModifyModel,
            val filmId: String,
            val reviewId: String
        ) : Event()

        class SaveIsAnonymous(val isAnonymous: Boolean) : Event()
        object NavigationBack : Event()
    }

    data class State(
        val isLoaded: Boolean,
        val isError: Boolean,
        val isRefreshing: Boolean,
        val movieDetails: MovieDetailsModel,
        val isAddingSuccess: Boolean?,
        val isDeletingSuccess: Boolean?,
        val isMyFavorite: Boolean,
        val isWithMyReview: Boolean,
        val myReviewTextField: String,
        val myRating: Int,
        val isAnonymous: Boolean,
        val currentFilmRating: FilmRating?
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object Back : Navigation()
            object ToIntroducing : Navigation()
        }
    }
}