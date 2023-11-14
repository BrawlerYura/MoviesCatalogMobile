package com.example.mobile_moviescatalog2023.domain.UseCases

import androidx.compose.ui.graphics.Color
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.Composables.FilmRating
import com.example.mobile_moviescatalog2023.domain.Entities.Models.ReviewModel
import com.example.mobile_moviescatalog2023.domain.Entities.Models.ReviewShortModel

class CalculateRatingUseCase {

    fun calculateFilmsRating(reviews: List<ReviewShortModel>?): FilmRating? {
        if (reviews == null) {
            return null
        } else {

            var sumScore: Int = 0
            reviews.forEach {
                sumScore += it.rating
            }

            val rating = (sumScore.toDouble() / reviews.count())



            return FilmRating(
                rating = if (rating != 10.0) {
                    rating.toString().substring(startIndex = 0, endIndex = 3)
                } else {
                    rating.toString().substring(startIndex = 0, endIndex = 4)
                },
                color = calculateFilmRatingColor(rating)
            )
        }
    }

    fun calculateFilmRatingColor(rating: Double): Color {
        return  when {
            rating >= 0.0 && rating < 3.0 -> {
                Color(0xFFE64646)
            }

            rating >= 3.0 && rating < 4.0 -> {
                Color(0xFFF05C44)
            }

            rating >= 4.0 && rating < 5.0 -> {
                Color(0xFFFFA000)
            }

            rating >= 5.0 && rating < 7.0 -> {
                Color(0xFFFFD54F)
            }

            rating >= 7.0 && rating < 9.0 -> {
                Color(0xFFA3CD4A)
            }

            else -> {
                Color(0xFF4BB34B)
            }
        }
    }

    fun calculateFilmRating(reviews: List<ReviewModel>?): FilmRating? {
        if (reviews == null) {
            return null
        } else {

            var sumScore: Int = 0
            reviews.forEach {
                sumScore += it.rating
            }

            val rating = (sumScore.toDouble() / reviews.count())

            return FilmRating(
                rating = if (rating != 10.0) {
                    rating.toString().substring(startIndex = 0, endIndex = 3)
                } else {
                    rating.toString().substring(startIndex = 0, endIndex = 4)
                },
                color = calculateFilmRatingColor(rating)
            )
        }
    }
}