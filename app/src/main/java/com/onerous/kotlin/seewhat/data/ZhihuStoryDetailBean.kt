package com.onerous.kotlin.seewhat.data

/**
 * Created by rrr on 2017/7/19.
 */
data class ZhihuStoryDetailBean(var body: String,
                                var image_source: String,
                                var title: String,
                                var image: String,
                                var share_url: String,
                                var ga_prefix: String,
                                var type: Int,
                                var id: Int,
                                var section: SectionBean,
                                var js: List<String>,
                                var images: List<String>,
                                var css: List<String>){
    data class SectionBean(var thumbnail:String,
                           var id :Int,
                           var name: String)
}