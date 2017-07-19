package com.onerous.kotlin.seewhat.detailActivity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.webkit.WebSettings
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.onerous.kotlin.seewhat.R
import com.onerous.kotlin.seewhat.api.ApiService
import com.onerous.kotlin.seewhat.data.ZhihuStoryDetailBean
import com.onerous.kotlin.seewhat.util.HtmlUtil
import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_zhihu_story_detail.*
import kotlinx.android.synthetic.main.content_zhihu_story_detail.*

class ZhihuStoryDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zhihu_story_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);//设置toolbar返回键

        val bundle: Bundle = intent.getBundleExtra("story")
        supportActionBar?.title=bundle.getString("storyTitle")

        val settings = wv_content.getSettings()
        settings.setJavaScriptEnabled(true)
        settings.setLoadWithOverviewMode(true)
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN)
        settings.setSupportZoom(true)
        getStoryContent(bundle.getInt("storyId"))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() == android.R.id.home) {//响应toolbar返回键
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getStoryContent(storyId: Int) {
        ApiService.zhiHuService
                .getStoryContent(storyId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it -> showContent(it) },
                        { error -> Logger.e(error.toString()) })
    }

    fun showContent(storyContentBean: ZhihuStoryDetailBean) {
        val imgUrl = storyContentBean.image
        Glide.with(this)
                .load(imgUrl)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .placeholder(R.mipmap.ic_launcher)
                .into(iV_zhihustory_header_image)
        tv_zhihustory_header_title.setText(storyContentBean.title)
        tv_zhihustory_header_copyright.setText(storyContentBean.image_source)
        val htmlData = HtmlUtil.createHtmlData(storyContentBean.body, storyContentBean.css, storyContentBean.js)
        wv_content.loadData(htmlData, HtmlUtil.MIME_TYPE, HtmlUtil.ENCODING)
    }
}
