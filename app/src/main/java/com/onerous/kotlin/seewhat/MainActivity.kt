package com.onerous.kotlin.seewhat

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.onerous.kotlin.seewhat.inTheaters.InTheatersFragment
import com.onerous.kotlin.seewhat.util.addFragmentToActivity
import com.onerous.kotlin.seewhat.zhihu.ZhihuFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val mInTheaterFragment: InTheatersFragment by lazy {InTheatersFragment()}
    private val mZhihuFragment: ZhihuFragment by lazy {ZhihuFragment()}
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_intheater -> {
                supportActionBar?.setTitle(R.string.toolbar_title_intheater)
                addFragmentToActivity(supportFragmentManager, mInTheaterFragment, R.id.container)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_top250 -> {
                supportActionBar?.setTitle(R.string.toolbar_title_top250)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_zhihu -> {
                supportActionBar?.setTitle(R.string.toolbar_title_zhihu)
                addFragmentToActivity(supportFragmentManager, mZhihuFragment, R.id.container)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        supportActionBar?.setTitle(R.string.toolbar_title_intheater)
        addFragmentToActivity(supportFragmentManager, mInTheaterFragment, R.id.container)
    }
}
