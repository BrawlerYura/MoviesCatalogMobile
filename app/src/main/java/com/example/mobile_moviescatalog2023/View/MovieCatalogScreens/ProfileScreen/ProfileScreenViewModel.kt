package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.ProfileScreen

import android.util.Log
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.lifecycle.viewModelScope
import com.example.mobile_moviescatalog2023.domain.Entities.Models.ProfileModel
import com.example.mobile_moviescatalog2023.View.Base.BaseViewModel
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.MainScreenContract
import com.example.mobile_moviescatalog2023.domain.UseCases.FormatDateUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.UserUseCases.GetProfileUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.AuthUseCases.LogoutUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.HandleErrorUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.UserUseCases.PutProfileUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.ValidationUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProfileScreenViewModel(
    private val getProfileUseCase: GetProfileUseCase,
    private val putProfileUseCase: PutProfileUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val formatDateUseCase: FormatDateUseCase,
    private val validationUseCase: ValidationUseCase,
    private val handleErrorUseCase: HandleErrorUseCase,
) : BaseViewModel<ProfileScreenContract.Event, ProfileScreenContract.State, ProfileScreenContract.Effect>() {

    init {
        loadUserDetails()
    }

    override fun setInitialState() = ProfileScreenContract.State(
        id = "",
        nickName = null,
        email = "",
        userIconUrl = null,
        name = "",
        gender = 0,
        birthDate = "",
        isSuccess = true,
        isLoaded = false,
        isError = false,
        errorMessage = null,
        profileModel = null,
        isEnable = false,
        isCancelEnable = false,
        isNameValid = true,
        isEmailValid = true,
        isBirthDateValid = true,
        isRefreshing = true
    )

    override fun handleEvents(event: ProfileScreenContract.Event) {
        when (event) {
            is ProfileScreenContract.Event.RefreshScreen -> refreshScreen()
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
            is ProfileScreenContract.Event.NavigationToMain -> setEffect { ProfileScreenContract.Effect.Navigation.ToMain }
            is ProfileScreenContract.Event.NavigationToFavorite -> setEffect { ProfileScreenContract.Effect.Navigation.ToFavorite }
            is ProfileScreenContract.Event.NavigationToIntroducing -> setEffect { ProfileScreenContract.Effect.Navigation.ToIntroducing }
        }
    }

    private fun refreshScreen() {
        setState {
            copy(
                id = "",
                nickName = null,
                email = "",
                userIconUrl = null,
                name = "",
                gender = 0,
                birthDate = "",
                isLoaded = false,
                isError = false,
                errorMessage = null,
                profileModel = null,
                isEnable = false,
                isSuccess = true,
                isCancelEnable = false,
                isNameValid = true,
                isEmailValid = true,
                isBirthDateValid = true,
                isRefreshing = true
            )
        }
        loadUserDetails()
    }

    private fun saveName(name: String) {
        setState { copy(name = name) }
        checkIfButtonsEnable()
        if (validationUseCase.checkIfNameValid(state.value.name)) {
            setState { copy(isNameValid = true) }
        } else {
            setState { copy(isNameValid = false, isEnable = false) }
        }
    }

    private fun saveGender(gender: Int) {
        setState { copy(gender = gender) }
        checkIfButtonsEnable()
    }

    private fun saveEmail(email: String) {
        setState { copy(email = email) }
        checkIfButtonsEnable()
        if (validationUseCase.checkIfEmailValid(state.value.email)) {
            setState { copy(isEmailValid = true) }
        } else {
            setState { copy(isEmailValid = false, isEnable = false) }
        }
    }

    private fun saveUserIconUrl(userIconUrl: String) {
        setState { copy(userIconUrl = userIconUrl) }
        checkIfButtonsEnable()
    }

    private fun saveBirthDate(birthDate: String) {
        setState { copy(birthDate = birthDate) }
        checkIfButtonsEnable()
        if (validationUseCase.checkIfBirthDateValid(state.value.birthDate)) {
            setState { copy(isBirthDateValid = true) }
        } else {
            setState { copy(isBirthDateValid = false, isEnable = false) }
        }
    }

    private fun checkIfButtonsEnable() {
        checkIfCancelButtonEnable()
        checkIfPutButtonEnable()
    }

    private fun checkIfCancelButtonEnable() {
        if (state.value.profileModel != null) {
            if (checkIfDetailsMatches()) {
                setState { copy(isCancelEnable = true) }
            } else {
                setState { copy(isCancelEnable = false) }
            }
        }
    }

    private fun checkIfPutButtonEnable() {
        if (
            state.value.isBirthDateValid &&
            state.value.isEmailValid &&
            state.value.isNameValid
        ) {
            if (state.value.profileModel != null) {
                if (checkIfDetailsMatches()) {
                    setState { copy(isEnable = true) }
                } else {
                    setState { copy(isEnable = false) }
                }
            }
        } else {
            setState { copy(isEnable = false) }
        }
    }

    private fun checkIfDetailsMatches(): Boolean {
        return !validationUseCase.checkIfProfileModelMatches(
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
    }

    private fun loadUserDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            getProfileUseCase.invoke().collect { result ->
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
                        isEnable = false,
                        isCancelEnable = false,
                        isLoaded = true,
                        isError = false,
                        isSuccess = true,
                        isNameValid = true,
                        isEmailValid = true,
                        isBirthDateValid = true,
                        isRefreshing = false
                    )
                }
            }.onFailure {
                    handleErrorUseCase.handleError(
                        error = it.message,
                        onInputError = { },
                        onTokenError = {
                            setEffect { ProfileScreenContract.Effect.Navigation.ToIntroducing }
                        },
                        onOtherError = {
                            setState { copy(
                                isLoaded = false,
                                isError = true,
                            ) }
                        }
                    )
            }
                setState { copy(isRefreshing = false) }
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
        viewModelScope.launch(Dispatchers.Main) {
            putProfileUseCase.invoke(profileModel).collect { result ->
                result.onSuccess {
                setState {
                    copy(
                        isEnable = false,
                        isCancelEnable = false,
                        isSuccess = true,
                        profileModel = ProfileModel(
                            id = state.value.id,
                            nickName = state.value.nickName,
                            email = state.value.email,
                            avatarLink = state.value.userIconUrl,
                            name = state.value.name,
                            gender = state.value.gender,
                            birthDate = state.value.birthDate
                        )
                    )
                }
            }.onFailure {
                    handleErrorUseCase.handleError(
                        error = it.message,
                        onInputError = {
                            setState {
                                copy(
                                    isSuccess = false,
                                    errorMessage = "Указаны некорректные данные"
                                )
                            }
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        },
                        onTokenError = {
                            setEffect { ProfileScreenContract.Effect.Navigation.ToIntroducing }
                        },
                        onOtherError = {
                            setState { copy(
                                isLoaded = false,
                                isError = true,
                            ) }
                        }
                    )
            }
            }
        }
    }

    private fun logout() {
        viewModelScope.launch(Dispatchers.Main) {
            logoutUseCase.invoke()
            MainScope().launch {
                setEffect { ProfileScreenContract.Effect.Navigation.ToIntroducing }
            }
        }
    }
}