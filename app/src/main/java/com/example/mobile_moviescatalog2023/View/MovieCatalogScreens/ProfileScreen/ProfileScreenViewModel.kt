package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.ProfileScreen

import android.util.Log
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.lifecycle.viewModelScope
import com.example.mobile_moviescatalog2023.domain.Entities.Models.ProfileModel
import com.example.mobile_moviescatalog2023.View.Base.BaseViewModel
import com.example.mobile_moviescatalog2023.domain.UseCases.FormatDateUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.UserUseCases.GetProfileUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.AuthUseCases.LogoutUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.UserUseCases.PutProfileUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.ValidationUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class ProfileScreenViewModel(
    private val getProfileUseCase: GetProfileUseCase,
    private val putProfileUseCase: PutProfileUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val formatDateUseCase: FormatDateUseCase,
    private val validationUseCase: ValidationUseCase
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
        isSuccess = null,
        errorMessage = null,
        profileModel = null,
        isEnable = false,
        isCancelEnable = false,
        isNameValid = true,
        isEmailValid = true,
        isBirthDateValid = true
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
            is ProfileScreenContract.Event.NavigationToMain -> setEffect { ProfileScreenContract.Effect.Navigation.ToMain }
            is ProfileScreenContract.Event.NavigationToFavorite -> setEffect { ProfileScreenContract.Effect.Navigation.ToFavorite }
            is ProfileScreenContract.Event.NavigationToIntroducing -> setEffect { ProfileScreenContract.Effect.Navigation.ToIntroducing }
        }
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
        if(
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
            getProfileUseCase.invoke()
                .onSuccess {
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
                            isSuccess = null,
                            isNameValid = true,
                            isEmailValid = true,
                            isBirthDateValid = true
                        )
                    }
                }.onFailure {

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
                .onSuccess {
                    setState {
                        copy(
                            isEnable = false,
                            isCancelEnable = false,
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
                    setState {
                        copy(
                            isSuccess = false,
                            errorMessage = "Указаны некорректные данные"
                        )
                    }
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    Log.e("a", it.message ?: "")
                }
        }
    }

    private fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            logoutUseCase.invoke()
            MainScope().launch {
                setEffect { ProfileScreenContract.Effect.Navigation.ToIntroducing }
            }
        }
    }
}