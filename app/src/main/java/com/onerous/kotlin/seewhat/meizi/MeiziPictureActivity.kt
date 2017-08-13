package com.onerous.kotlin.seewhat.meizi

import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import com.bumptech.glide.Glide
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.onerous.kotlin.seewhat.R
import kotlinx.android.synthetic.main.activity_meizi_picture.*

class MeiziPictureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meizi_picture)
        
        val url=intent.getStringExtra("meiziUrl")
        Glide.with(this)
                .load(url)
                .into(iv_meizi)
        //点击一次退出
        iv_meizi.setOnClickListener({view->
            finish()
            true
        })
    }
}
