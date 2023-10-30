package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.ProfileScreen

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.mobile_moviescatalog2023.Network.Auth.AuthRepository
import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.ProfileModel
import com.example.mobile_moviescatalog2023.Network.Network
import com.example.mobile_moviescatalog2023.Network.User.UserRepository
import com.example.mobile_moviescatalog2023.TokenManager.TokenManager
import com.example.mobile_moviescatalog2023.View.AuthScreens.LoginScreen.LoginContract
import com.example.mobile_moviescatalog2023.View.Base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

class ProfileScreenViewModel(
    private val context: Context
): BaseViewModel<ProfileScreenContract.Event, ProfileScreenContract.State, ProfileScreenContract.Effect>() {

    private val userRepository = UserRepository()
    private val authRepository = AuthRepository()
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
            is ProfileScreenContract.Event.SaveBirthDateWithFormatEvent -> saveBirthDate(formatDateToTextField(event.birthDate))
            is ProfileScreenContract.Event.LoadUserDetails -> loadUserDetails()
            is ProfileScreenContract.Event.Logout -> logout()
        }
    }

    private fun formatDateToApi(date: String): String {
        val parts = date.split(".")
        val year = parts[0]
        val month = parts[1]
        val day = parts[2]
        return "$year-$month-${day}T08:12:28.534Z"
    }

    private fun formatDateFromApi(date: String): String {
        val parts = date.split("-")
        val year = parts[0]
        val month = parts[1]
        val day = parts[2]
        return "${day.substring(startIndex = 0, endIndex = 2)}.$month.$year"
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

    @SuppressLint("SimpleDateFormat")
    private fun formatDateToTextField(selectedDateMillis: Long?): String {
        if (selectedDateMillis == null) {
            return ""
        }

        val dateFormat = SimpleDateFormat("dd.MM.yyyy")
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")

        val date = Date(selectedDateMillis)
        return dateFormat.format(date)
    }

    private fun loadUserDetails()
    {
        val dataStore = TokenManager(context)

        viewModelScope.launch(Dispatchers.IO) {
            val tokenValue = dataStore.getToken.first()

            Network.token = tokenValue.toString()

            userRepository.getProfile()
                .collect { result ->
                    result.onSuccess {
                        setState {copy(
                                id = it.id,
                                nickName = it.nickName,
                                email = it.email,
                                userIconUrl = it.avatarLink,
                                name = it.name,
                                gender = it.gender,
                                birthDate = formatDateFromApi(it.birthDate),
                                isSuccess = true
                            )
                        }
                    }.onFailure {
                        setState { copy(isSuccess = false) }
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
            birthDate = state.value.birthDate
        )
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.putProfile(profileModel)
                .collect { result ->
                    result.onSuccess {
                        setState {copy(
                            isSuccess = true
                            )
                        }
                    }.onFailure {
                        setState { copy(isSuccess = false) }
                    }
                }
        }
    }

    private fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.logout()
            TokenManager(context).deleteToken()
        }
    }
}