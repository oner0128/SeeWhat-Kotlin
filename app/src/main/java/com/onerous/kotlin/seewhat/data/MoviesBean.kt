package com.onerous.kotlin.seewhat.data

/**
 * Created by rrr on 2017/7/15.
 */

data class MoviesBean(val count: Int,
                      val start: Int,
                      val total: Int,
                      val title: String,
                      val subjects: List<Subjects>)

data class Subjects(
        val rating: Rating,
        val title: String,
        val collect_count: Int,
        val original_title: String,
        val subtype: String,
        val year: String,
        val images: Images,
        val alt: String,
        val id: String,
        val genres: List<String>,
        val casts: List<Casts>,
        val directors: List<Directors>
)

data class Rating(var max: Int ,
                  var average: Double ,
                  var stars: String,
                  var min: Int) {
    /**
     * max : 10
     * average : 9.6
     * stars : 50
     * min : 0
     */


}

data class Images(var small: String,
                  var large: String,
                  var medium: String) {
    /**
     * small : https://img3.doubanio.com/view/movie_poster_cover/ipst/public/p480747492.webp
     * large : https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p480747492.webp
     * medium : https://img3.doubanio.com/view/movie_poster_cover/spst/public/p480747492.webp
     */


}

data class Casts(val alt: String,
                 val avatars: Avatars,
                 private val name: String,
                 var id: String) : PersonBean {
    /**
     * alt : https://movie.douban.com/celebrity/1054521/
     * avatars : {"small":"https://img3.doubanio.com/img/celebrity/small/17525.jpg","large":"https://img3.doubanio.com/img/celebrity/large/17525.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/17525.jpg"}
     * name : 蒂姆·罗宾斯
     * id : 1054521
     */

    override fun getName(): String {
        return name!!
    }

    override fun getJob(): String {
        return "Casts"
    }

    override fun getImgUrl(): String {
        return avatars.medium!!
    }

    class Avatars {
        /**
         * small : https://img3.doubanio.com/img/celebrity/small/17525.jpg
         * large : https://img3.doubanio.com/img/celebrity/large/17525.jpg
         * medium : https://img3.doubanio.com/img/celebrity/medium/17525.jpg
         */

        var small: String? = null
        var large: String? = null
        var medium: String? = null
    }
}

data class Directors (
         var alt: String,
        var avatars: AvatarsX,
         private var name: String,
        var id: String
): PersonBean {
    /**
     * alt : https://movie.douban.com/celebrity/1047973/
     * avatars : {"small":"https://img3.doubanio.com/img/celebrity/small/230.jpg","large":"https://img3.doubanio.com/img/celebrity/large/230.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/230.jpg"}
     * name : 弗兰克·德拉邦特
     * id : 1047973
     */

    override fun getName(): String {
        return name
    }

    override fun getJob(): String {
        return "Directors"
    }

    override fun getImgUrl(): String {
        return avatars.medium!!
    }

    class AvatarsX {
        /**
         * small : https://img3.doubanio.com/img/celebrity/small/230.jpg
         * large : https://img3.doubanio.com/img/celebrity/large/230.jpg
         * medium : https://img3.doubanio.com/img/celebrity/medium/230.jpg
         */

        var small: String? = null
        var large: String? = null
        var medium: String? = null
    }
}
