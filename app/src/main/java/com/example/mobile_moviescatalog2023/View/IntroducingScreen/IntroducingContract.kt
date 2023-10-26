package com.example.mobile_moviescatalog2023.View.IntroducingScreen

import com.example.mobile_moviescatalog2023.View.Base.ViewSideEffect

class IntroducingContract {
    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object ToLogin : Navigation()
            object ToRegistration : Navigation()
        }
    }
}