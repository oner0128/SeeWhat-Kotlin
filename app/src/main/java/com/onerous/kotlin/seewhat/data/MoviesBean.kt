package com.onerous.kotlin.seewhat.data

/**
 * Created by rrr on 2017/7/17.
 */
data class MoviesBean(var count: Int,
                        var start: Int,
                        var total: Int,
                        var title: String,
                        var subjects: List<Subjects>) {
    data class Subjects(var rating: Rating,
                        var title: String,
                        var collect_count: Int,
                        var original_title: String,
                        var subtype: String,
                        var year: String,
                        var images: Images,
                        var alt: String,
                        var id: String,
                        var genres: List<String>,
                        var casts: List<Casts>,
                        var directors: List<Directors>) {
        data class Rating(var max: Int,
                          var average: Double,
                          var stars: String,
                          var min: Int)

        data class Images(var small: String,
                          var large: String,
                          var medium: String)

        data class Casts(var alt: String,
                         var avatars: Avatars,
                         var name: String,
                         var id: String) {
            data class Avatars(var small: String,
                               var large: String,
                               var medium: String)
        }

        data class Directors(var alt: String,
                             var avatars: Avatars,
                             var name: String,
                             var id: String) {
            data class Avatars(var small: String,
                               var large: String,
                               var medium: String)
        }
    }
}