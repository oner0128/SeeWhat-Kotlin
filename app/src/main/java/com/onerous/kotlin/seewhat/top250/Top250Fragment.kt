package com.onerous.kotlin.seewhat.inTheaters


import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.onerous.kotlin.seewhat.R
import com.onerous.kotlin.seewhat.data.MoviesBean
import com.orhanobut.logger.Logger
import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper
import kotlinx.android.synthetic.main.fragment_in_theaters.*

/**
 * Created by rrr on 2017/7/15.
 */
class Top250Fragment : Fragment(), Top250Contract.View {
    override fun showMoreMovies(moviesBean: MoviesBean) {
        total=moviesBean.total
        mDatas.addAll(moviesBean.subjects)
        mAdapter.notifyDataSetChanged()
    }

    var start=0
    var count=20
    var total=0
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private var mDatas: ArrayList<MoviesBean.Subjects> = ArrayList()
    private lateinit var mAdapter: Top250Adapter
    private lateinit var mPresenter: Top250Presenter

    fun init() {
        mAdapter = Top250Adapter(context, mDatas)
        mPresenter = Top250Presenter(this)
        recyclerView.setLayoutManager(LinearLayoutManager(context))
        val mLoadMoreWrapper = LoadMoreWrapper<MoviesBean.Subjects>(mAdapter)
        mLoadMoreWrapper.setLoadMoreView(R.layout.item_loading_more)
        mLoadMoreWrapper.setOnLoadMoreListener(LoadMoreWrapper.OnLoadMoreListener {
            val handler = Handler()
            handler.postDelayed({
                if (start+count < total) {
                    start+=count;
                    mPresenter.getTop250Movies(start,count)
                }else Logger.d("no more movies")
            }, 500)
        })
        recyclerView.setAdapter(mLoadMoreWrapper)

        recyclerView.adapter = mAdapter
        mPresenter.getTop250Movies(start,count)
//        swipeRefreshLayout.setOnRefreshListener(mPresenter.getTop250Movies(start,count))
    }

    override fun showError(error: String?) {
        Logger.e(error)
    }

    override fun showMovies(moviesBean: MoviesBean) {
        mDatas.clear()
        total=moviesBean.total
        mDatas.addAll(moviesBean.subjects)
        mAdapter.notifyDataSetChanged()
        recyclerView.scrollToPosition(0)
        swipeRefreshLayout.isRefreshing=false
    }

//    override fun setPresenter(presenter: Top250Contract.Presenter) {
////        mPresenter=presenter
//    }

    override fun showProgressDialog() {
        swipeRefreshLayout.isRefreshing = true
    }

    override fun hideProgressDialog() {
        swipeRefreshLayout.isRefreshing = false
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_top250, container, false);
    }
}