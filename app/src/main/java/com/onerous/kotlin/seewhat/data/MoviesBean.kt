package com.onerous.kotlin.seewhat.data

/**
 * Created by rrr on 2017/7/17.
 */
const val nullString=""
data class MoviesBean(var count: Int,
                      var start: Int,
                      var total: Int,
                      var title: String,
                      var subjects: List<Subjects>) {
    data class Subjects(var rating: Rating,
                        var title: String=nullString,
                        var collect_count: Int=0,
                        var original_title: String=nullString,
                        var subtype: String=nullString,
                        var year: String=nullString,
                        var images: Images,
                        var alt: String=nullString,
                        var id: String=nullString,
                        var genres: List<String>,
                        var casts: List<Casts>,
                        var directors: List<Directors>) {
        data class Rating(var max: Int=0,
                          var average: Double=0.0,
                          var stars: String=nullString,
                          var min: Int=0)

        data class Images(var small: String=nullString,
                          var large: String=nullString,
                          var medium: String=nullString)

        data class Casts(var alt: String=nullString,
                         var avatars: Avatars=Avatars(),
                         var name: String,
                         var id: String=nullString) : PersonBean {
            override fun getPersonId(): String =id

            override fun getPersonName(): String = name

            override fun getJob(): String = "演员"

            override fun getImgUrl(): String = avatars.medium

            data class Avatars(var small: String=nullString,
                               var large: String=nullString,
                               var medium: String=nullString)
        }

        data class Directors(var alt: String=nullString,
                             var avatars: Avatars=Avatars(),
                             var name: String,
                             var id: String=nullString) : PersonBean {
            override fun getPersonId(): String =id

            override fun getPersonName(): String = name

            override fun getJob(): String = "导演"

            override fun getImgUrl(): String = avatars.medium

            data class Avatars(var small: String=nullString,
                               var large: String=nullString,
                               var medium: String=nullString)
        }
    }
}