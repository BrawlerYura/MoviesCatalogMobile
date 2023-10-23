package com.example.mobile_moviescatalog2023.View.LoginScreens.RegistrationScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegistrationViewModel: ViewModel() {

    private val stateLiveMutable = MutableLiveData<RegistrationState>()
    val resultLive: LiveData<RegistrationState> = stateLiveMutable

    init {
        stateLiveMutable.value = RegistrationState("", "male", "", "", "")
    }

    fun send(event: RegistrationEvent) {
        when(event) {
            is RegistrationEvent.SaveNameEvent -> {
                saveName(event.name)
            }
            is RegistrationEvent.SaveGenderEvent -> {
                saveGender(event.gender)
            }
            is RegistrationEvent.SaveEmailEvent -> {
                saveEmail(event.email)
            }
            is RegistrationEvent.SaveBirthDateWithFormatEvent -> {
                saveBirthDateWithFormat(event.birthDate)
            }
            is RegistrationEvent.SaveBirthDateEvent -> {
                saveBirthDate(event.birthDate)
            }
            is RegistrationEvent.LoadNameEvent -> {
                loadName()
            }
        }
    }

    private fun saveName(name: String) {
        stateLiveMutable.value = stateLiveMutable.value?.copy(name = name)
        Log.e("bbb", stateLiveMutable.value?.name ?: "")
    }

    private fun saveGender(gender: String) {
        stateLiveMutable.value = stateLiveMutable.value?.copy(gender = gender)
        Log.e("aaa", resultLive.value?.gender ?: "")
    }

    private fun saveEmail(email: String) {
        stateLiveMutable.value = stateLiveMutable.value?.copy(email = email)
    }

    private fun saveBirthDateWithFormat(birthDate: String) {
        stateLiveMutable.value = stateLiveMutable.value?.copy(birthDate = formatDate(birthDate))
    }

    private fun saveBirthDate(birthDate: String) {
        stateLiveMutable.value = stateLiveMutable.value?.copy(birthDate = birthDate)
    }

    private fun loadName() {

    }

    private fun formatDate(date: String): String {
        val parts = date.split("-")
        val year = parts[0]
        val month = parts[1]
        val day = parts[2]
        return "$day.$month.$year"
    }
}