package com.example.mobile_moviescatalog2023.domain.UseCases

import com.example.mobile_moviescatalog2023.domain.Entities.Models.MovieElementModel
import com.example.mobile_moviescatalog2023.domain.Entities.Models.ThreeFavoriteMovies

class FromListToPartsMovieUseCase {
    fun fromListToPartsMovies(movies: List<MovieElementModel>?): List<ThreeFavoriteMovies>? {
        val listFavoriteMovies: MutableList<ThreeFavoriteMovies> = mutableListOf()
        return if(movies != null) {
            var index = 0
            while(index < movies.count()) {
                val threeFavoriteMovies = when(movies.count() - index) {
                    2 -> {
                        ThreeFavoriteMovies(
                            firstMovie = movies[index],
                            secondMovie = movies[index + 1],
                            thirdMovie = null
                        )
                    }
                    1 -> {
                        ThreeFavoriteMovies(
                            firstMovie = null,
                            secondMovie = null,
                            thirdMovie = movies[index]
                        )
                    }
                    else -> {
                        ThreeFavoriteMovies(
                            firstMovie = movies[index],
                            secondMovie = movies[index + 1],
                            thirdMovie = movies[index + 2]
                        )
                    }
                }
                index += 3
                listFavoriteMovies += threeFavoriteMovies
            }
            listFavoriteMovies
        } else {
            null
        }
    }
}