package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.ProfileScreen

import androidx.compose.ui.hapticfeedback.HapticFeedback
import com.example.mobile_moviescatalog2023.View.Base.ViewEvent
import com.example.mobile_moviescatalog2023.View.Base.ViewSideEffect
import com.example.mobile_moviescatalog2023.View.Base.ViewState
import com.example.mobile_moviescatalog2023.domain.Entities.Models.ProfileModel

class ProfileScreenContract {

    sealed class Event : ViewEvent {
        class SaveNameEvent(val name: String) : Event()
        class SaveGenderEvent(val gender: Int) : Event()
        class SaveEmailEvent(val email: String) : Event()
        class SaveBirthDateEvent(val birthDate: String) : Event()
        class SaveBirthDateWithFormatEvent(val birthDate: Long?) : Event()
        class SaveUserIconUrl(val userIconUrl: String) : Event()
        class PutNewUserDetails(val haptic: HapticFeedback) : Event()
        object RefreshScreen : Event()
        object LoadUserDetails : Event()
        object Logout : Event()
        object NavigationToMain : Event()
        object NavigationToFavorite : Event()
        object NavigationToIntroducing : Event()
    }

    data class State(
        val id: String,
        val nickName: String?,
        val email: String,
        val userIconUrl: String?,
        val name: String,
        val gender: Int,
        val birthDate: String,
        val isLoaded: Boolean,
        val isError: Boolean,
        val isSuccess: Boolean,
        val errorMessage: String?,
        val profileModel: ProfileModel?,
        val isEnable: Boolean,
        val isCancelEnable: Boolean,
        val isNameValid: Boolean,
        val isEmailValid: Boolean,
        val isBirthDateValid: Boolean,
        val isRefreshing: Boolean
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object ToIntroducing : Navigation()
            object ToFavorite : Navigation()
            object ToMain : Navigation()
        }
    }
}
