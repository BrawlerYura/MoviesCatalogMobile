package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.ProfileScreen

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.lifecycle.viewModelScope
import com.example.mobile_moviescatalog2023.Network.Auth.AuthRepository
import com.example.mobile_moviescatalog2023.domain.Entities.Models.ProfileModel
import com.example.mobile_moviescatalog2023.Network.Network
import com.example.mobile_moviescatalog2023.Network.User.UserRepository
import com.example.mobile_moviescatalog2023.TokenManager.TokenManager
import com.example.mobile_moviescatalog2023.View.AuthScreens.LoginScreen.LoginContract
import com.example.mobile_moviescatalog2023.View.Base.BaseViewModel
import com.example.mobile_moviescatalog2023.domain.UseCases.FormatDateUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.UserUseCases.GetProfileUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.UserUseCases.LogoutUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.UserUseCases.PutProfileUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.ValidationUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

class ProfileScreenViewModel(
    private val getProfileUseCase: GetProfileUseCase,
    private val putProfileUseCase: PutProfileUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val formatDateUseCase: FormatDateUseCase,
    private val validationUseCase: ValidationUseCase
) : BaseViewModel<ProfileScreenContract.Event, ProfileScreenContract.State, ProfileScreenContract.Effect>() {
    override fun setInitialState() = ProfileScreenContract.State(
        id = "",
        nickName = null,
        email = "",
        userIconUrl = null,
        name = "",
        gender = 0,
        birthDate = "",
        isSuccess = null,
        errorMessage = null,
        profileModel = null,
        isEnable = false
    )

    override fun handleEvents(event: ProfileScreenContract.Event) {
        when (event) {
            is ProfileScreenContract.Event.SaveEmailEvent -> saveEmail(email = event.email)
            is ProfileScreenContract.Event.SaveUserIconUrl -> saveUserIconUrl(userIconUrl = event.userIconUrl)
            is ProfileScreenContract.Event.SaveNameEvent -> saveName(name = event.name)
            is ProfileScreenContract.Event.SaveGenderEvent -> saveGender(gender = event.gender)
            is ProfileScreenContract.Event.SaveBirthDateEvent -> saveBirthDate(birthDate = event.birthDate)
            is ProfileScreenContract.Event.SaveBirthDateWithFormatEvent -> saveBirthDate(
                formatDateUseCase.formatDateToTextField(event.birthDate)
            )

            is ProfileScreenContract.Event.PutNewUserDetails -> putUserDetails(haptic = event.haptic)
            is ProfileScreenContract.Event.LoadUserDetails -> loadUserDetails()
            is ProfileScreenContract.Event.Logout -> logout()
        }
    }

    private fun saveName(name: String) {
        setState { copy(name = name) }
        if (validationUseCase.checkIfNameValid(state.value.name)) {
            checkIfProfileModelMatches()
        } else {
            setState { copy(isEnable = false) }
        }
    }

    private fun saveGender(gender: Int) {
        setState { copy(gender = gender) }
    }

    private fun saveEmail(email: String) {
        setState { copy(email = email) }
        if (validationUseCase.checkIfEmailValid(state.value.email)) {
            checkIfProfileModelMatches()
        } else {
            setState { copy(isEnable = false) }
        }
    }

    private fun saveUserIconUrl(userIconUrl: String) {
        setState { copy(userIconUrl = userIconUrl) }
        checkIfProfileModelMatches()
    }

    private fun saveBirthDate(birthDate: String) {
        setState { copy(birthDate = birthDate) }
        if (validationUseCase.checkIfBirthDateValid(state.value.birthDate)) {
            checkIfProfileModelMatches()
        } else {
            setState { copy(isEnable = false) }
        }
    }

    private fun checkIfProfileModelMatches() {
        if (state.value.profileModel != null) {
            if (!validationUseCase.checkIfProfileModelMatches(
                    newProfileModel = ProfileModel(
                        id = state.value.id,
                        nickName = state.value.nickName,
                        email = state.value.email,
                        avatarLink = state.value.userIconUrl,
                        name = state.value.name,
                        gender = state.value.gender,
                        birthDate = state.value.birthDate
                    ),
                    state.value.profileModel!!
                )
            ) {
                setState { copy(isEnable = true) }
            } else {
                setState { copy(isEnable = false) }
            }
        }
    }

    private fun loadUserDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            getProfileUseCase.invoke()
                .collect { result ->
                    result.onSuccess {
                        setState {
                            copy(
                                id = it.id,
                                nickName = it.nickName,
                                email = it.email,
                                userIconUrl = it.avatarLink,
                                name = it.name,
                                gender = it.gender,
                                birthDate = formatDateUseCase.formatDateFromApi(it.birthDate),
                                profileModel = ProfileModel(
                                    id = it.id,
                                    nickName = it.nickName,
                                    email = it.email,
                                    avatarLink = it.avatarLink,
                                    name = it.name,
                                    gender = it.gender,
                                    birthDate = formatDateUseCase.formatDateFromApi(it.birthDate)
                                ),
                                isEnable = false
                            )
                        }
                    }.onFailure {
                        setState {
                            copy()
                        }
                    }
                }
        }
    }

    private fun putUserDetails(haptic: HapticFeedback) {
        val profileModel = ProfileModel(
            id = state.value.id,
            nickName = state.value.nickName,
            email = state.value.email,
            avatarLink = state.value.userIconUrl,
            name = state.value.name,
            gender = state.value.gender,
            birthDate = formatDateUseCase.formatDateToApi(state.value.birthDate)
        )
        viewModelScope.launch(Dispatchers.IO) {
            putProfileUseCase.invoke(profileModel)
                .collect { result ->
                    result.onSuccess {
                        setState {
                            copy(isSuccess = true, profileModel = profileModel)
                        }
                    }.onFailure {
                        setState {
                            copy(isSuccess = false, errorMessage = "Указаны некорректные данные")
                        }
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    }
                }
        }
    }

    private fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            logoutUseCase.invoke()
        }
    }
}