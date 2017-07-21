package com.onerous.kotlin.seewhat.inTheaters


import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.onerous.kotlin.seewhat.R
import com.onerous.kotlin.seewhat.data.MoviesBean
import com.orhanobut.logger.Logger
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper
import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper
import kotlinx.android.synthetic.main.fragment_top250.*

/**
 * Created by rrr on 2017/7/15.
 */
class Top250Fragment : Fragment(), Top250Contract.View {

    private object SingletonHolder {
        val Instance = Top250Fragment()
    }

    companion object {
        fun NewInstance(): Top250Fragment = SingletonHolder.Instance
    }

    private var isFirstLoad = true
    private var isLoading = false
    private var start = 0
    private var count = 15
    private var total = 0
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private lateinit var mLoadMoreWrapper: LoadMoreWrapper<MoviesBean.Subjects>
    private var mDatas: ArrayList<MoviesBean.Subjects> = ArrayList()
    private lateinit var mAdapter: Top250Adapter
    private lateinit var mPresenter: Top250Presenter

    fun init() {
        //init adapter and presenter
        mAdapter = Top250Adapter(context, mDatas)
        mPresenter = Top250Presenter(this)
        recyclerView.setLayoutManager(LinearLayoutManager(context))

        val mHeaderAndFooterWrapper=HeaderAndFooterWrapper<MoviesBean.Subjects>(mAdapter)
//        val headtv=TextView(context)
//        headtv.text="header"
//        val foottv=TextView(context)
//        foottv.text="foot"
//        mHeaderAndFooterWrapper.addHeaderView(headtv)
//        mHeaderAndFooterWrapper.addFootView(foottv)
        mLoadMoreWrapper = LoadMoreWrapper<MoviesBean.Subjects>(mHeaderAndFooterWrapper)
        mLoadMoreWrapper.setLoadMoreView(R.layout.item_loading_more)
        recyclerView.adapter = mLoadMoreWrapper
        //listener
        mLoadMoreWrapper.setOnLoadMoreListener({
            if (isFirstLoad) {
                Logger.d("top250:firstloading" )
            } else if (!isLoading&&start < total) {
                start += count
                isLoading=true
                Logger.d("top250:" + start + "-" + count)
                mPresenter.getMoreTop250Movies(start, count)
            } else if (!isFirstLoad&&!isLoading&&start>=total){
                Logger.d("no more movies")
            }
        })

        swipeRefreshLayout.setColorSchemeResources(R.color.blue_primary_dark, R.color.blue_primary_light, R.color.yellow_primary_dark)

        swipeRefreshLayout.setOnRefreshListener({
            initData()
        })
        //loadingdata
        initData()
    }

    fun initData() {
        isFirstLoad = false
        isLoading=true
        start = 0
        count = 15
        total = 0
        mPresenter.getTop250Movies(start, count)
    }

    override fun showError(error: String?) {
        Logger.e(error)
        if (recyclerView != null) {
            Snackbar.make(recyclerView,
                    getString(R.string.please_check_your_network),
                    Snackbar.LENGTH_INDEFINITE).setAction("重试", {
                initData()
            }).show()
        }
    }

    override fun showMovies(moviesBean: MoviesBean) {
        mDatas.clear()
        total = moviesBean.total
        mDatas.addAll(moviesBean.subjects)
        mLoadMoreWrapper.notifyDataSetChanged()

        recyclerView.scrollToPosition(0)
        swipeRefreshLayout.isRefreshing = false
        isLoading=false
    }

    override fun showMoreMovies(moviesBean: MoviesBean) {
        total = moviesBean.total
        mDatas.addAll(moviesBean.subjects)
        mLoadMoreWrapper.notifyDataSetChanged()
        isLoading=false
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

