package com.onerous.kotlin.seewhat.meizi


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.onerous.kotlin.seewhat.R
import com.onerous.kotlin.seewhat.data.MeiziBean
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_meizi.*

/**
 * Created by oner0128 on 2017/8/12.
 */
class MeiziFragment : Fragment(), MeiziContract.View {
    override fun showMeizi(meizi: List<MeiziBean.Results>) {
        mDatas.addAll(meizi)
        swipeRefreshLayout_meizi.isRefreshing = false
        mAdapter.loadMoreComplete()
        page++
    }

    override fun scrollToTop() {
        recyclerView_meizi.smoothScrollToPosition(0)
    }
    

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private var page = -1
    private var mDatas: ArrayList<MeiziBean.Results> = ArrayList()
    private lateinit var mAdapter: MeiziAdapter
    private lateinit var mPresenter: MeiziContract.Presenter


    fun init() {
        mAdapter = MeiziAdapter(R.layout.item_recyclerview_meizi, mDatas)
        mPresenter = MeiziPresenter(this)
        recyclerView_meizi.setLayoutManager(GridLayoutManager(activity, 2))
        recyclerView_meizi.adapter = mAdapter

        swipeRefreshLayout_meizi.setColorSchemeResources(R.color.blue_primary_dark, R.color.blue_primary_light, R.color.yellow_primary_dark)
        //listener
        swipeRefreshLayout_meizi.setOnRefreshListener {
            mDatas.clear()
            page = 0
            mPresenter.loadMeizis(page)
        }
        //listener
        mAdapter.setOnLoadMoreListener({
            mPresenter.loadMeizis(++page)
        }, recyclerView_meizi)
        
        mAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adaptet, view, position ->
            showMeiziDetail(mDatas.get(position))
        }
        mPresenter.loadMeizis(++page)
    }

    fun showMeiziDetail(meizi: MeiziBean.Results) {
        val intent=Intent(context,MeiziPictureActivity::class.java)
        intent.putExtra("meiziUrl",meizi.url)
        startActivity(intent)
    }

    override fun showError(error: String?) {
        Logger.e(error)
        showProgressDialog()
    }


    override fun showProgressDialog() {
        swipeRefreshLayout_meizi.isRefreshing = true
    }

    override fun hideProgressDialog() {
        swipeRefreshLayout_meizi.isRefreshing = false
    }

    override fun onResume() {
        super.onResume()
        mPresenter.subscribe()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_meizi, container, false);
    }
}