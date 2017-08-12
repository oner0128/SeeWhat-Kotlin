package com.onerous.kotlin.seewhat

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.onerous.kotlin.seewhat.detailActivity.SearchActivity
import com.onerous.kotlin.seewhat.inTheaters.InTheatersFragment
import com.onerous.kotlin.seewhat.inTheaters.Top250Fragment
import com.onerous.kotlin.seewhat.zhihu.ZhihuFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mInTheaterFragment: InTheatersFragment? by lazy { InTheatersFragment.NewInstance() }
    private val mZhihuFragment: ZhihuFragment?  by lazy { ZhihuFragment.NewInstance() }
    private val mTop250Fragment: Top250Fragment?  by lazy { Top250Fragment.NewInstance() }
    private var currentFragment: Fragment? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_intheater -> {
                supportActionBar?.setTitle(R.string.toolbar_title_intheater)
                addorShowFragmentToActivity(supportFragmentManager, R.id.container, mInTheaterFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_top250 -> {
                supportActionBar?.setTitle(R.string.toolbar_title_top250)
                addorShowFragmentToActivity(supportFragmentManager, R.id.container, mTop250Fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_zhihu -> {
                supportActionBar?.setTitle(R.string.toolbar_title_zhihu)
                addorShowFragmentToActivity(supportFragmentManager, R.id.container, mZhihuFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        supportActionBar?.setTitle(R.string.toolbar_title_intheater)


        currentFragment = supportFragmentManager.findFragmentById(R.id.container)
        if (currentFragment == null) addorShowFragmentToActivity(supportFragmentManager, R.id.container, mInTheaterFragment)

        fab_search.setOnClickListener {
            val intent = Intent(applicationContext, SearchActivity::class.java)
            startActivity(intent)
        }
    }


    fun hideFabToTop(view: View) {

        val animator = view.animate()
                .translationY(view.height.toFloat() + view.bottom.toFloat())
                .setDuration(500)

        animator.setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                com.orhanobut.logger.Logger.v("animator end")
                view.visibility = View.INVISIBLE
            }
        })

        animator.start()
    }

    fun addorShowFragmentToActivity(fragmentManager: FragmentManager,
                                    frameId: Int, fragment: Fragment?) {
        if (fragment == null) return
        if (currentFragment == null) {
            addFragmentToActivity(supportFragmentManager, R.id.container, fragment)
            currentFragment = fragment
            return
        } else if (currentFragment == fragment) return
        val transaction = fragmentManager.beginTransaction()
        if (fragment.isAdded) transaction.hide(currentFragment).show(fragment)
        else transaction.hide(currentFragment).add(frameId, fragment)
        transaction.commit()
        currentFragment?.userVisibleHint = false
        currentFragment = fragment
        currentFragment?.userVisibleHint = true
    }

    fun addFragmentToActivity(fragmentManager: FragmentManager,
                              frameId: Int, fragment: Fragment?) {
        if (fragment == null) return
        val transaction = fragmentManager.beginTransaction()
        transaction.add(frameId, fragment)
        transaction.commit()
    }

//    override fun onBackPressed() {
//        val intent = Intent(Intent.ACTION_MAIN)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        intent.addCategory(Intent.CATEGORY_HOME)
//        startActivity(intent)
//    }
}
