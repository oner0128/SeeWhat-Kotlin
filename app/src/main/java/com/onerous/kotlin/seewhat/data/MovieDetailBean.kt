package com.onerous.kotlin.seewhat.data

/**
 * Created by rrr on 2017/7/19.
 */
data class MovieDetailBean(var rating: Rating,
                           var reviews_count: Int,
                           var wish_count: Int,
                           var douban_site: String,
                           var year: String,
                           var images: Images,
                           var alt: String,
                           var id: String,
                           var mobile_url: String,
                           var title: String,
                           var do_count: Any,
                           var share_url: String,
                           var seasons_count: Any,
                           var schedule_url: String,
                           var episodes_count: Any,
                           var collect_count: Int,
                           var current_season: Any,
                           var original_title: String,
                           var summary: String,
                           var subtype: String,
                           var comments_count: Int,
                           var ratings_count: Int,
                           var countries: List<String>,
                           var genres: List<String>,
                           var casts: List<Casts>,
                           var directors: List<Directors>,
                           var aka: List<String>) {
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
                     var id: String) : PersonBean {
        override fun getPersonId(): String = id

        override fun getPersonName(): String = name

        override fun getJob(): String = javaClass.simpleName

        override fun getImgUrl(): String = avatars.medium

        data class Avatars(var small: String,
                           var large: String,
                           var medium: String)
    }

    data class Directors(var alt: String,
                         var avatars: Avatars,
                         var name: String,
                         var id: String) : PersonBean {
        override fun getPersonId(): String = id

        override fun getPersonName(): String = name

        override fun getJob(): String = javaClass.simpleName

        override fun getImgUrl(): String = avatars.medium

        data class Avatars(var small: String,
                           var large: String,
                           var medium: String)
    }
}