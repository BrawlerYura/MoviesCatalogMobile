package com.example.mobile_moviescatalog2023.domain.UseCases

class ValidationUseCase {

    fun checkIfLoginValid(text: String): Boolean {
        return text.length > 3
    }

    fun checkIfNameValid(text: String): Boolean {
        return text.length > 3
    }

    fun checkIfLoginPasswordValid(text: String): Boolean {
        return text.length > 3
    }

    fun checkIfBirthDateValid(text: String): Boolean {
        return Regex("""((([1-2][0-9]|3[0-1]|0[1-9])\.(0[13-9]|1[0-2])|(0[13-9]|1[0-2])\.([1-2][0-9]|3[0-1]|0[1-9]))\.\d{4})""").matches(text)
    }

    fun checkIfEmailValid(text: String): Boolean {
        return Regex("""^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+${'$'}""").matches(text)
    }

    fun checkIfPasswordEqualsRepeatedPassword(password: String, repeatedPassword: String): Boolean {
        return password == repeatedPassword
    }
}