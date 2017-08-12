package com.onerous.kotlin.seewhat.zhihu

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.onerous.kotlin.seewhat.R
import com.onerous.kotlin.seewhat.data.ZhihuBeforeNewsBean
import com.onerous.kotlin.seewhat.data.ZhihuLatestNewsBean
import com.onerous.kotlin.seewhat.detailActivity.ZhihuStoryDetailActivity
import com.onerous.kotlin.seewhat.util.DateUtil
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_zhihu.*
import java.util.*

/**
 * Created by rrr on 2017/7/17.
 */
class ZhihuFragment : Fragment(), ZhihuContract.View {
    override fun scrollToTop() {
        recyclerView_zhihu.smoothScrollToPosition(0)
    }

    private object SingletonHolder {
        var Instance : ZhihuFragment?= ZhihuFragment()
    }

    companion object {
        fun NewInstance(): ZhihuFragment? = SingletonHolder.Instance
    }

    override fun onDestroy() {
        super.onDestroy()
        SingletonHolder.Instance=null
    }
    private var mPresenter: ZhihuContract.Presenter = ZhihuPresenter(this)
    private var mDatas = ArrayList<ZhihuMultiItem>()
    private var mAdapter: ZhihuAdapter? = null
    private var mdate: String? = null

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        recyclerView_zhihu.setLayoutManager(LinearLayoutManager(context))
        mAdapter = ZhihuAdapter(mDatas)
        swipeRefreshLayout_zhihu.setColorSchemeResources(R.color.blue_primary_dark, R.color.blue_primary_light, R.color.yellow_primary_dark)

        recyclerView_zhihu.adapter = mAdapter
        //listener
        mAdapter?.setOnLoadMoreListener({
            if (mdate != null) {
                Logger.d(DateUtil.getYesterday(mdate!!))
                mPresenter.getZhihuBeforeNews(mdate!!)
            }
        }, recyclerView_zhihu)
        mAdapter?.setOnItemClickListener { adapter, view, position ->
            val item = adapter.getItem(position) as ZhihuMultiItem
            val storiesEntity = mDatas.get(position)
            when (item.Itemtype) {
                ZhihuMultiItem.NEWS -> {
                    val intent = Intent(context, ZhihuStoryDetailActivity::class.java)
                    val bundle = Bundle()
                    bundle.putInt("storyId", storiesEntity.mLatestNews!!.id)
                    bundle.putString("storyTitle", storiesEntity.mLatestNews!!.title)
                    intent.putExtra("story", bundle)
                    startActivity(intent)
                }
                 ZhihuMultiItem.BEFORENEWS->{
                     val intent = Intent(context, ZhihuStoryDetailActivity::class.java)
                     val bundle = Bundle()
                     bundle.putInt("storyId", storiesEntity.mBeforeNews!!.id)
                     bundle.putString("storyTitle", storiesEntity.mBeforeNews!!.title)
                     intent.putExtra("story", bundle)
                     startActivity(intent)
                 }
            }

        }

        //swipe refresh
        swipeRefreshLayout_zhihu.setOnRefreshListener(this::getLatestNews)

        //init
        getLatestNews()
    }

    fun getLatestNews() {
        mPresenter.getZhihuLastestNews()
    }

    override fun showProgressDialog() {
        swipeRefreshLayout_zhihu.isRefreshing = true
    }

    override fun hideProgressDialog() {
        swipeRefreshLayout_zhihu.isRefreshing = false
    }

    override fun showError(error: String?) {
        Logger.e(error)
        mAdapter?.loadMoreFail()
    }

    override fun showLastestNews(zhihuLatestNewsBean: ZhihuLatestNewsBean) {
        mDatas.clear()
        mdate = zhihuLatestNewsBean.date
        //banner top stories
        mDatas.add(ZhihuMultiItem(ZhihuMultiItem.HEADER,zhihuLatestNewsBean))
        //date title
//        Logger.d(mdate)
        mDatas.add(ZhihuMultiItem(ZhihuMultiItem.DATE,mdate))
        //stories
        zhihuLatestNewsBean.stories.forEach {it->mDatas.add(ZhihuMultiItem(ZhihuMultiItem.NEWS,it)) }

        mAdapter?.notifyDataSetChanged()
        recyclerView_zhihu.scrollToPosition(0)
        swipeRefreshLayout_zhihu.isRefreshing = false
    }

    override fun showBeforeNews(zhihuBeforeNewsBean: ZhihuBeforeNewsBean) {
        mdate = zhihuBeforeNewsBean.date
        //date title
        Logger.d(mdate)
        mDatas.add(ZhihuMultiItem(ZhihuMultiItem.DATE, mdate!!))
        //stories
        zhihuBeforeNewsBean.stories.forEach {it->mDatas.add(ZhihuMultiItem(ZhihuMultiItem.BEFORENEWS,it)) }
        mAdapter?.loadMoreComplete()
        swipeRefreshLayout_zhihu.isRefreshing = false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_zhihu, container, false)
    }
}

