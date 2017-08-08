package com.onerous.kotlin.seewhat.detailActivity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.onerous.kotlin.seewhat.App
import com.onerous.kotlin.seewhat.R
import com.onerous.kotlin.seewhat.api.ApiService
import com.onerous.kotlin.seewhat.data.MoviesBean
import com.onerous.kotlin.seewhat.util.formatCastsToString
import com.onerous.kotlin.seewhat.util.formatListToString
import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {
    private var start = 0
    private var count = 20
    private var total = 20
    private val mData = ArrayList<MoviesBean.Subjects>()
    private val mAdapter = SearchAdapter(R.layout.item_recyclerview_top250, mData)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val searchString = intent.getStringExtra("SearchString")
        getSearchMovies(searchString, start, count)

        recycler_search.layoutManager=LinearLayoutManager(applicationContext)
        recycler_search.adapter=mAdapter
        mAdapter.setOnLoadMoreListener(
                {
                    //                    Handler().postDelayed({
                    Logger.v("start load more$start")
                    if (start < total) {
                        getSearchMovies(searchString, start, count)
                    } else Toast.makeText(applicationContext, "no more items", Toast.LENGTH_LONG).show()
//                    }, 1000)
                }
                , recycler_search)

        mAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener {
            adapter, view, position ->
            Logger.v("onItemClickListener:$position")
            showMovieDetail(mData.get(position))
        }
    }

    private fun showMovieDetail(moviebean: MoviesBean.Subjects) {
        if (moviebean == null) return
        val intent = Intent(applicationContext, MovieDetailActivity::class.java)
        Logger.v("MovieTitle:${moviebean.images.large}")
        intent.putExtra("MovieId", moviebean.id)
        intent.putExtra("MovieTitle", moviebean.title)
        intent.putExtra("MovieImg", moviebean.images.large)
        startActivity(intent)
    }

    private fun getSearchMovies(searchString: String, start: Int, count: Int) {
        ApiService.douBanService.getSearchMovies(searchString, start, count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                        bean: MoviesBean ->  loadSearchData(bean)
                }, { showProgressDialog() }, { hideProgressDialog() }, { showProgressDialog() })
    }

     fun loadSearchData(moviesBean: MoviesBean){
         total=moviesBean.total
         mData.addAll(moviesBean.subjects)
         start=mData.size
         mAdapter.notifyDataSetChanged()
         mAdapter.loadMoreComplete()
     }

    fun showProgressDialog() {
        if (start==0){progressBar.setVisibility(View.VISIBLE)
        recycler_search.setVisibility(View.INVISIBLE)}
    }

    fun hideProgressDialog() {
        progressBar.setVisibility(View.INVISIBLE)
        recycler_search.setVisibility(View.VISIBLE)
    }

    class SearchAdapter(layoutResId: Int, data: MutableList<MoviesBean.Subjects>?) : BaseQuickAdapter<MoviesBean.Subjects, BaseViewHolder>(layoutResId, data) {
        override fun convert(helper: BaseViewHolder, item: MoviesBean.Subjects) {
            Glide.with(mContext)
                    .load(item.images.medium)
                    .override(App.imageSize[0], App.imageSize[1])
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .centerCrop()
                    .into(helper.getView(R.id.imageView))

            helper.setText(R.id.tv_title, "${(helper.adapterPosition + 1)}.${item.title}")

            helper.setText(R.id.tv_rating, "${item.rating.average}/10.0")

            helper.setText(R.id.tv_casts, "主演:${formatCastsToString(item.casts)}")

            helper.setText(R.id.tv_director, "导演:${formatCastsToString(item.directors)}")

            helper.setText(R.id.tv_years, "年份:${item.year}")

            helper.setText(R.id.tv_genres, "类型:${formatListToString(item.genres)}")
        }
    }
}
