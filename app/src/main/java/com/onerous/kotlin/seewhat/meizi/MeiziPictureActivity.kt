package com.onerous.kotlin.seewhat.meizi

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.onerous.kotlin.seewhat.R
import kotlinx.android.synthetic.main.activity_meizi_picture.*
import java.lang.Exception

class MeiziPictureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meizi_picture)

        val url = intent.getStringExtra("meiziUrl")
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            val id = intent.getStringExtra("id")
//            photoview_meizi.transitionName = id
//        }
        Glide.with(this)
                .load(url)
                .into(photoview_meizi)
//        supportPostponeEnterTransition();
//
//        Glide.with(this)
//                .load(url)
//                .centerCrop()
//                .dontAnimate()
//                .listener(object : RequestListener<String, GlideDrawable> {
//                    override fun onResourceReady(resource: GlideDrawable?, model: String?, target: Target<GlideDrawable>?, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
//                        supportStartPostponedEnterTransition()
//                        return false
//                    }
//
//                    override fun onException(e: Exception?, model: String?, target: Target<GlideDrawable>?, isFirstResource: Boolean): Boolean {
//                        supportStartPostponedEnterTransition()
//                        return false
//                    }
//                })
//                .into(photoview_meizi)


        //点击一次退出
        photoview_meizi.setOnClickListener({ view ->
            supportFinishAfterTransition()
            finish()
            true
        })
    }
}
