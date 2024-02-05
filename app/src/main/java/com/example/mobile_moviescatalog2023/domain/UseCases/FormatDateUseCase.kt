package com.example.mobile_moviescatalog2023.domain.UseCases

import android.annotation.SuppressLint
import android.util.Log
import com.example.mobile_moviescatalog2023.domain.Entities.Models.MovieDetailsModel
import com.example.mobile_moviescatalog2023.domain.Entities.Models.ReviewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

class FormatDateUseCase {

    fun formatCreateDateTime(movieDetails: MovieDetailsModel): MovieDetailsModel {
        return MovieDetailsModel(
            id = movieDetails.id,
            name = movieDetails.name,
            poster = movieDetails.poster,
            year = movieDetails.year,
            country = movieDetails.country,
            genres = movieDetails.genres,
            reviews = formatDateInReviews(movieDetails.reviews),
            time = movieDetails.time,
            tagline = movieDetails.tagline,
            description = movieDetails.description,
            director = movieDetails.director,
            budget = movieDetails.budget,
            fees = movieDetails.fees,
            ageLimit = movieDetails.ageLimit
        )
    }

    private fun formatDateInReviews(reviews: List<ReviewModel>?): List<ReviewModel>? {
        if (reviews == null) {
            return null
        }
        var newReviews: List<ReviewModel> = listOf()
        reviews.forEach {
            newReviews += listOf(
                ReviewModel(
                    id = it.id,
                    rating = it.rating,
                    reviewText = it.reviewText,
                    isAnonymous = it.isAnonymous,
                    author = it.author,
                    createDateTime = formatDateFromApi(it.createDateTime)
                )
            )
        }
        return newReviews
    }

    fun formatDateFromApi(date: String): String {
        val parts = date.split("-")
        val year = parts[0]
        val month = parts[1]
        val day = parts[2]
        return "${day.substring(startIndex = 0, endIndex = 2)}.$month.$year"
    }

    @SuppressLint("SimpleDateFormat")
    fun formatDateToTextField(selectedDateMillis: Long?): String {
        if (selectedDateMillis == null) {
            return ""
        }

        val dateFormat = SimpleDateFormat("dd.MM.yyyy")
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")

        val date = Date(selectedDateMillis)
        return dateFormat.format(date)
    }

    fun formatDateToApi(date: String): String {
        val parts = date.split(".")
        val day = parts[0]
        val month = parts[1]
        val year = parts[2]
        Log.e("a", "$year-$month-${day}T08:12:28.534Z")
        return "$year-$month-${day}T08:12:28.534Z"
    }
}