package com.onerous.kotlin.seewhat.detailActivity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.onerous.kotlin.seewhat.R
import com.onerous.kotlin.seewhat.api.ApiService
import com.onerous.kotlin.seewhat.data.MovieDetailBean
import com.onerous.kotlin.seewhat.data.PersonBean
import com.onerous.kotlin.seewhat.util.formatCastsToString
import com.onerous.kotlin.seewhat.util.formatListToString
import com.orhanobut.logger.Logger
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.content_movie_detail.*
import kotlinx.android.synthetic.main.content_movie_detail_header.*

class MovieDetailActivity : AppCompatActivity() {
    private var share: String? = null
    private var title: String? = null
    private var imgUrl: String? = null
    private lateinit var id: String
    private var persons = ArrayList<PersonBean>()
    lateinit var adapter: MultiItemTypeAdapter<PersonBean>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val intent = intent

        if (intent != null) {
            title = intent.getStringExtra("MovieTitle")
            id = intent.getStringExtra("MovieId")
            imgUrl = intent.getStringExtra("MovieImg")
            Logger.v("Moviedetail " + "id:$id-title:$title")
            tv_header_title.text = title
            //            getSupportActionBar().setTitle(title);
        }

        fab_scrolling.setOnClickListener {
            if (share == null)
                Toast.makeText(baseContext, "未加载完毕", Toast.LENGTH_SHORT).show()
            else {
                val intent = Intent()
                intent.action = Intent.ACTION_SEND
                intent.putExtra(Intent.EXTRA_TEXT, share)
                intent.type = "text/plain"
                startActivity(intent)
            }
        }
        setAdapter()
        getMovieDetail()
        setHeader()
    }

    private fun setHeader() {
        // 高斯模糊背景 原来 参数：12,5  23,4
        Glide.with(this).load(imgUrl)
                .bitmapTransform(BlurTransformation(this, 23, 4))
                .into(image_background_scrolling)
        toolbar.alpha = 1.0f
    }

    private fun getMovieDetail() {
        if (id != null) {
            showProgressDialog()
            val dis=ApiService.douBanService
                    .getMovieDetail(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { it ->
                                hideProgressDialog()
                                showMovieDetail(it)
                            }
                            , { e -> showError(e.toString()) }
                            , {
                        hideProgressDialog()
                        collapsing_layout.setTitle(title)
                        //设置当toolbar扩展时为透明，即不可见，只有收缩时可见
                        collapsing_layout.setExpandedTitleColor(resources.getColor(R.color.transparent_white))
                        collapsing_layout.setCollapsedTitleTextColor(resources.getColor(R.color.white))
                    })
            CompositeDisposable().add(dis)
        }
    }

    private fun showMovieDetail(movieDetailBean: MovieDetailBean) {
        //header
        Glide.with(applicationContext).load(movieDetailBean.images.large)
                .into(iv_header_photo)

        tv_header_rating_rate.setText(movieDetailBean.rating.average.toString())
        tv_header_rating_number.setText(movieDetailBean.ratings_count.toString() + "人评分")

        tv_header_directors.setText(formatCastsToString(movieDetailBean.directors))

        //casts
        tv_header_casts.setText(formatCastsToString(movieDetailBean.casts))
        //geners
        tv_header_genres.setText(formatListToString(movieDetailBean.genres))
        //year
        tv_header_years.setText(movieDetailBean.year)
        //countries
        tv_header_countries.setText(formatListToString(movieDetailBean.countries))

        //content
        ratingbar.setRating(movieDetailBean.rating.average.toFloat()/2)
        //aka
        tv_title_aka.setText(formatListToString(movieDetailBean.aka))
        tv_summary.setText(movieDetailBean.summary)
        MovieUrl = movieDetailBean.mobile_url
        share = movieDetailBean.original_title + '\n' + movieDetailBean.mobile_url
        //cast &director
        persons.addAll(movieDetailBean.directors)
        persons.addAll(movieDetailBean.casts)

        adapter.notifyDataSetChanged()
    }

    private fun setAdapter() {
        adapter = MultiItemTypeAdapter<PersonBean>(this, persons)
        adapter.addItemViewDelegate(PersonItemDelegate())
        rv_casts.setLayoutManager(LinearLayoutManager(this))
        rv_casts.setAdapter(adapter)
    }

    fun showProgressDialog() {
        progressBar.visibility = View.VISIBLE
        layout_content.visibility = View.INVISIBLE
        layout_header_content.visibility = View.INVISIBLE
    }

    fun hideProgressDialog() {
        progressBar.visibility = View.INVISIBLE
        layout_content.visibility = View.VISIBLE
        layout_header_content.visibility = View.VISIBLE
    }

    fun showError(error: String) {
        Logger.e(error)
        if (framelayout != null) {
            Snackbar.make(framelayout, getString(R.string.please_check_your_network), Snackbar.LENGTH_INDEFINITE).setAction("重试") { getMovieDetail() }.show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_moviedetail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_moreInfo) {
            showCustomTabs()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private lateinit var MovieUrl: String
    private fun showCustomTabs() {
        if (MovieUrl == null) return
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(MovieUrl))
    }
}
