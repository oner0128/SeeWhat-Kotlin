package com.onerous.kotlin.seewhat.zhihu

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.onerous.kotlin.seewhat.R
import com.onerous.kotlin.seewhat.data.ZhihuBeforeNewsBean
import com.onerous.kotlin.seewhat.data.ZhihuLatestNewsBean
import com.onerous.kotlin.seewhat.util.DateUtil
import com.onerous.kotlin.seewhat.zhihu.item.ZhihuHeaderTitleItem
import com.onerous.kotlin.seewhat.zhihu.item.ZhihuItem
import com.orhanobut.logger.Logger
import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper
import kotlinx.android.synthetic.main.fragment_zhihu.*
import java.util.*

/**
 * Created by rrr on 2017/7/17.
 */
class ZhihuFragment : Fragment(), ZhihuContract.View {


    private object SingletonHolder {
        val Instance = ZhihuFragment()
    }

    companion object {
        fun NewInstance(): ZhihuFragment = SingletonHolder.Instance
    }

    private var mPresenter: ZhihuPresenter = ZhihuPresenter(this)
    private var mDatas = ArrayList<ZhihuItem>()
    private var mAdapter: ZhihuAdapter? = null
    private var mdate: String?=null
    lateinit private var mLoadMoreWrapper: LoadMoreWrapper<ZhihuItem>
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        recyclerView.setLayoutManager(LinearLayoutManager(context))
        mAdapter = ZhihuAdapter(context, mDatas)
        mLoadMoreWrapper = LoadMoreWrapper(mAdapter)
        mLoadMoreWrapper.setLoadMoreView(R.layout.item_loading_more)
        mLoadMoreWrapper.setOnLoadMoreListener({
                if (mdate != null) {
                    Logger.d(DateUtil.getYesterday(mdate!!))
                    mPresenter.getZhihuBeforeNews(mdate!!)
                }
            }
        )

        recyclerView.adapter = mLoadMoreWrapper

        swipeRefreshLayout.setColorSchemeResources(R.color.blue_primary_dark, R.color.blue_primary_light, R.color.yellow_primary_dark)
        //swipe refresh
        swipeRefreshLayout.setOnRefreshListener(this::getLatestNews)
        getLatestNews()
    }

    fun getLatestNews() {
        mPresenter.getZhihuLastestNews()
    }

    override fun showProgressDialog() {
        swipeRefreshLayout.isRefreshing = true
    }

    override fun hideProgressDialog() {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun showError(error: String?) {
        Logger.e(error)
    }

    override fun showLastestNews(zhihuLatestNewsBean: ZhihuLatestNewsBean) {
        mDatas.clear()
        mdate = zhihuLatestNewsBean.date
        //banner top stories
//        Logger.d(mdate)
        mDatas.add(zhihuLatestNewsBean)
        //date title
        mDatas.add(ZhihuHeaderTitleItem(mdate!!))
        //stories
        mDatas.addAll(zhihuLatestNewsBean.stories)
        mAdapter?.notifyDataSetChanged()
        recyclerView.scrollToPosition(0)
        swipeRefreshLayout.isRefreshing = false
    }

    override fun showBeforeNews(zhihuBeforeNewsBean: ZhihuBeforeNewsBean) {
        mdate = zhihuBeforeNewsBean.date
        //banner top stories
        Logger.d(mdate)
        //date title
        mDatas.add(ZhihuHeaderTitleItem(mdate!!))
        //stories
        mDatas.addAll(zhihuBeforeNewsBean.stories)
        mLoadMoreWrapper.notifyDataSetChanged()
        swipeRefreshLayout.isRefreshing = false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_zhihu, container, false)
    }
}

