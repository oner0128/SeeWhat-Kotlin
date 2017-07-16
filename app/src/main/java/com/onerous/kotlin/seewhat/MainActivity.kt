package com.onerous.kotlin.seewhat

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.onerous.kotlin.seewhat.InTheaters.InTheatersFragment
import com.onerous.kotlin.seewhat.util.addFragmentToActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var InTheaterFragment: InTheatersFragment? = null
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_intheater -> {
                supportActionBar?.setTitle(R.string.toolbar_title_intheater)
                if (InTheaterFragment == null) InTheaterFragment = InTheatersFragment()
                addFragmentToActivity(supportFragmentManager, InTheaterFragment, R.id.container)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_top250 -> {
                supportActionBar?.setTitle(R.string.toolbar_title_top250)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_zhihu -> {
                supportActionBar?.setTitle(R.string.toolbar_title_zhihu)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        if (InTheaterFragment == null) InTheaterFragment = InTheatersFragment()
        addFragmentToActivity(supportFragmentManager, InTheaterFragment, R.id.container)
    }
}
