package com.example.mobile_moviescatalog2023.domain.UseCases

import android.util.Log

class HandleErrorUseCase {
    fun handleError(
        error: String?,
        onInputError: () -> Unit,
        onTokenError: () -> Unit,
        onOtherError: () -> Unit
    ) {
        Log.e("sasa", error ?: "")
        when(error?.split(" ")?.get(1)) {
            "401" -> {
                onTokenError()
            }
            "400" -> {
                onInputError()
            }
            else -> {
                onOtherError()
            }
        }
    }
}