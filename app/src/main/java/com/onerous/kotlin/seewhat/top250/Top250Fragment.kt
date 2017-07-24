package com.onerous.kotlin.seewhat.inTheaters


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.chad.library.adapter.base.BaseQuickAdapter
import com.onerous.kotlin.seewhat.R
import com.onerous.kotlin.seewhat.data.MoviesBean
import com.onerous.kotlin.seewhat.detailActivity.MovieDetailActivity
import com.onerous.kotlin.seewhat.top250.Top250Adapter
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_top250.*

/**
 * Created by rrr on 2017/7/15.
 */
class Top250Fragment : Fragment(), Top250Contract.View {
    private var mFirstLoad = true
    var start = 0
    var count = 10
    var total = 250
    private var mDatas: ArrayList<MoviesBean.Subjects> = ArrayList()
    private lateinit var mAdapter: Top250Adapter
    private lateinit var mPresenter: Top250Contract.Presenter

    private object SingletonHolder {
        val Instance = Top250Fragment()
    }

    companion object {
        fun NewInstance(): Top250Fragment = SingletonHolder.Instance
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_top250, container, false);
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        if (savedInstanceState != null) mFirstLoad = savedInstanceState.getBoolean("firstLoad")
        if (mFirstLoad) mPresenter.subscribe()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putBoolean("firstLoad", mFirstLoad)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.unsubscribe()
    }

    override fun showMovies(movies: List<MoviesBean.Subjects>) {
        mDatas.clear()
        mDatas.addAll(movies)
        start = mDatas.size
        recyclerView_top250.scrollToPosition(0)
        mAdapter.notifyDataSetChanged()
        mAdapter.setEnableLoadMore(true);
    }

    override fun showMoreMovies(movies: List<MoviesBean.Subjects>) {
        mDatas.addAll(movies)
        start = mDatas.size
        mAdapter.loadMoreComplete()
    }

    fun init() {
        //init adapter and presenter
        mAdapter = Top250Adapter(R.layout.item_recyclerview_top250, mDatas)
        recyclerView_top250.layoutManager = LinearLayoutManager(context)
        recyclerView_top250.adapter = mAdapter

        mPresenter = Top250Presenter(this)

        swipeRefreshLayout_top250.setColorSchemeResources(R.color.blue_primary_dark, R.color.blue_primary_light, R.color.yellow_primary_dark)

        //listener
        swipeRefreshLayout_top250.setOnRefreshListener {
            start = 0
            mPresenter.loadTop250Movies(false, start, count)
        }

        mAdapter.setOnLoadMoreListener(
                {
                    Handler().postDelayed({
                        Logger.v("start load more$start")
                        if (start < total) {
                            mPresenter.loadTop250Movies(false, start, count)
                        } else showNoMovies()
                    }, 200)
                }
                , recyclerView_top250)

        mAdapter.disableLoadMoreIfNotFullPage()//首次加载不调用加载下拉更多

        mAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener {
            adapter, view, position ->
            Logger.v("onItemClickListener:$position")
            showMovieDetail(mDatas.get(position))
        }
    }

    override fun showError(error: String?) {
        Logger.e(error)
        if (start > 0) mAdapter.loadMoreFail()
        else {
            Snackbar.make(recyclerView_top250,
                    getString(R.string.please_check_your_network),
                    Snackbar.LENGTH_INDEFINITE).setAction("重试", {
                start = 0
                mPresenter.loadTop250Movies(true, start, count)
            }).show()
        }
    }

    override fun showProgressDialog() {
        swipeRefreshLayout_top250.isRefreshing = true
    }

    override fun hideProgressDialog() {
        swipeRefreshLayout_top250.isRefreshing = false
    }

    override fun showNoMovies() {
        Toast.makeText(context, R.string.no_more_movie, Toast.LENGTH_LONG).show();
        mAdapter.setEnableLoadMore(false);
    }

    override fun showMovieDetail(movie: MoviesBean.Subjects?) {
        if (movie == null) return
        val intent = Intent(context, MovieDetailActivity::class.java)
        Logger.v("movieTitle:${movie.images.large}")
        intent.putExtra("MovieId", movie.id)
        intent.putExtra("MovieTitle", movie.title)
        intent.putExtra("MovieImg", movie.images.large)
        startActivity(intent)
    }

}

