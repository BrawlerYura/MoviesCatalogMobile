package com.example.mobile_moviescatalog2023.View.AuthScreens.SplashScreen

import android.content.Context
import com.example.mobile_moviescatalog2023.View.Base.ViewEvent
import com.example.mobile_moviescatalog2023.View.Base.ViewSideEffect
import com.example.mobile_moviescatalog2023.View.Base.ViewState

class SplashContract {

    sealed class Event : ViewEvent {
        object GetToken : Event()
        object OnTokenLoadedSuccess : Event()
        object OnTokenLoadedFailed : Event()
    }

    data class State (
        val isError: Boolean,
        val isNetworkError: Boolean
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object ToMain : Navigation()
            object ToIntroducingScreen : Navigation()
        }
    }
}