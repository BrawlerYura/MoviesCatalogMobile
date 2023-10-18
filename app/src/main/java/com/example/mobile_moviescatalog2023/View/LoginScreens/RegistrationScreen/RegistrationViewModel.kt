package com.example.mobile_moviescatalog2023.View.LoginScreens.RegistrationScreen

import androidx.lifecycle.ViewModel

class RegistrationViewModel: ViewModel() {
    fun formatDate(date: String): String {
        val parts = date.split("-")
        val year = parts[0]
        val month = parts[1]
        val day = parts[2]
        return "$day.$month.$year"
    }
}