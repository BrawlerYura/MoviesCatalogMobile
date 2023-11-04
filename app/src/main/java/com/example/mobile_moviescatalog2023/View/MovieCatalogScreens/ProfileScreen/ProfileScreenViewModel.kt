package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.ProfileScreen

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
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
    private val formatDateUseCase: FormatDateUseCase
): BaseViewModel<ProfileScreenContract.Event, ProfileScreenContract.State, ProfileScreenContract.Effect>() {
    override fun setInitialState() = ProfileScreenContract.State(
        id = "",
        nickName = null,
        email = "",
        userIconUrl = null,
        name = "",
        gender = 0,
        birthDate = "",
        isSuccess = null
    )

    override fun handleEvents(event: ProfileScreenContract.Event) {
        when (event) {
            is ProfileScreenContract.Event.SaveEmailEvent -> saveEmail(email = event.email)
            is ProfileScreenContract.Event.SaveUserIconUrl -> saveUserIconUrl(userIconUrl = event.userIconUrl)
            is ProfileScreenContract.Event.SaveNameEvent -> saveName(name = event.name)
            is ProfileScreenContract.Event.SaveGenderEvent -> saveGender(gender = event.gender)
            is ProfileScreenContract.Event.SaveBirthDateEvent -> saveBirthDate(birthDate = event.birthDate)
            is ProfileScreenContract.Event.SaveBirthDateWithFormatEvent -> saveBirthDate(formatDateUseCase.formatDateToTextField(event.birthDate))
            is ProfileScreenContract.Event.PutNewUserDetails -> putUserDetails()
            is ProfileScreenContract.Event.LoadUserDetails -> loadUserDetails()
            is ProfileScreenContract.Event.Logout -> logout()
        }
    }

    private fun saveName(name: String) {
        setState { copy(name = name) }
    }

    private fun saveGender(gender: Int) {
        setState { copy(gender = gender) }
    }

    private fun saveEmail(email: String) {
        setState { copy(email = email) }
    }

    private fun saveUserIconUrl(userIconUrl: String) {
        setState { copy(userIconUrl = userIconUrl) }
    }

    private fun saveBirthDate(birthDate: String) {
        setState { copy(birthDate = birthDate) }
    }




    private fun loadUserDetails()
    {
        viewModelScope.launch(Dispatchers.IO) {
            getProfileUseCase.invoke()
                .collect{ result ->
                result.onSuccess {
                    setState {
                        copy(
                            isSuccess = true,
                            id = it.id,
                            nickName = it.nickName,
                            email = it.email,
                            userIconUrl = it.avatarLink,
                            name = it.name,
                            gender = it.gender,
                            birthDate = formatDateUseCase.formatDateFromApi(it.birthDate),
                        )
                    }
                } .onFailure {
                    setState {
                        copy(isSuccess = false)
                    }
                }
            }
        }
    }

    private fun putUserDetails() {
        val profileModel = ProfileModel (
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
                            copy(isSuccess = true)
                        }
                    } .onFailure {
                        setState {
                            copy(isSuccess = false)
                        }
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