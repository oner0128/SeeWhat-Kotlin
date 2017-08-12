package com.onerous.kotlin.seewhat.inTheaters


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.onerous.kotlin.seewhat.R
import com.onerous.kotlin.seewhat.data.MoviesBean
import com.onerous.kotlin.seewhat.detailActivity.MovieDetailActivity
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_in_theaters.*

/**
 * Created by rrr on 2017/7/15.
 */
class InTheatersFragment : Fragment(), InTheatersContract.View {
    override fun scrollToTop() {
        recyclerView_intheaters.smoothScrollToPosition(0)
    }

    private object SingletonHolder {
        var Instance :InTheatersFragment?= InTheatersFragment()
    }
    
    companion object {
        fun NewInstance(): InTheatersFragment? = SingletonHolder.Instance
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private var mDatas: ArrayList<MoviesBean.Subjects> = ArrayList()
    private lateinit var mAdapter: InTheatersAdapter
    private lateinit var mPresenter: InTheatersContract.Presenter


    fun init() {
        layout_noMovies.visibility = View.INVISIBLE

        mAdapter = InTheatersAdapter(R.layout.item_recyclerview_intheaters, mDatas)
        mPresenter = InTheatersPresenter(this)
        recyclerView_intheaters.setLayoutManager(GridLayoutManager(context, 2))
        recyclerView_intheaters.adapter = mAdapter

        swipeRefreshLayout_intheaters.setColorSchemeResources(R.color.blue_primary_dark, R.color.blue_primary_light, R.color.yellow_primary_dark)
        //listener
        swipeRefreshLayout_intheaters.setOnRefreshListener {
            mPresenter.loadInTheatersMovies(false)
        }
        mAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adaptet, view, position ->
            showMovieDetail(mDatas.get(position))
        }
    }

    override fun showError(error: String?) {
        Logger.e(error)
        layout_noMovies.visibility = View.VISIBLE
        showProgressDialog()
        mPresenter.loadInTheatersMovies(false)
    }

    override fun showMovies(movies: List<MoviesBean.Subjects>) {
        mDatas.clear()
        mDatas.addAll(movies)
        recyclerView_intheaters.scrollToPosition(0)
        swipeRefreshLayout_intheaters.isRefreshing = false
    }


    override fun showProgressDialog() {
        swipeRefreshLayout_intheaters.isRefreshing = true
    }

    override fun hideProgressDialog() {
        layout_noMovies.visibility = View.INVISIBLE
        swipeRefreshLayout_intheaters.isRefreshing = false
        mAdapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        mPresenter.subscribe()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.unsubscribe()
        SingletonHolder.Instance=null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_in_theaters, container, false);
    }

    override fun showMovieDetail(movie: MoviesBean.Subjects?) {
        if (movie == null) return
        val intent = Intent(context, MovieDetailActivity::class.java)
//        Logger.v("movieTitle:${movie.images.large}")
        intent.putExtra("MovieId", movie.id)
        intent.putExtra("MovieTitle", movie.title)
        intent.putExtra("MovieImg", movie.images.large)

        startActivity(intent)
    }
}