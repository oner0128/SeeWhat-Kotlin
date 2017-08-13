package com.onerous.kotlin.seewhat.api


import com.onerous.kotlin.seewhat.App
import com.onerous.kotlin.seewhat.util.isInternetAvailable
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by rrr on 2017/7/15.
 */

object ApiService {
    val gankService: GankService by lazy {
        Retrofit.Builder()
                .baseUrl(BASE_GANK_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient).build().create(GankService::class.java)
    }
    
    val douBanService: DouBanService by lazy {
        Retrofit.Builder()
                .baseUrl(BASE_MOVIE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient).build().create(DouBanService::class.java)
    }
    val zhiHuService: ZhihuService by lazy {
        Retrofit.Builder()
                .baseUrl(BASE_ZHIHU_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient).build().create(ZhihuService::class.java)

    }

    val BASE_MOVIE_URL = "https://api.douban.com/v2/movie/"
    val BASE_GANK_URL = "https://gank.io/"
    val BASE_ZHIHU_URL = "https://news-at.zhihu.com/api/4/"
    private val CACHE_CONTROL_INTERCEPTOP = Interceptor { chain ->
        val mResponse = chain.proceed(chain.request())
        if (isInternetAvailable(App.instance)) {
            val maxAge = 10 * 60 // 5分钟在线缓存
            mResponse.newBuilder().removeHeader("Prama")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build()
        } else {
            val maxStale = 60 * 60 * 24 * 28 // 4周离线缓存
            mResponse.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build()
        }
    }

    private val HTTPCACHEDIRECTORY = File(App.instance.cacheDir, "doubanCache")
    private val CACHE_SIZE = (20 * 1024 * 1024).toLong()//20MB
    private val cache = Cache(HTTPCACHEDIRECTORY, CACHE_SIZE)
    private val okHttpClient = OkHttpClient.Builder().cache(cache)
            .addNetworkInterceptor(CACHE_CONTROL_INTERCEPTOP)
            .addInterceptor(CACHE_CONTROL_INTERCEPTOP)
            .writeTimeout(8, TimeUnit.SECONDS)
            .build()
}
