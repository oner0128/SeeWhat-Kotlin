package com.onerous.kotlin.seewhat.inTheaters


import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.onerous.kotlin.seewhat.R
import com.onerous.kotlin.seewhat.data.MoviesBean
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_in_theaters.*
import kotlinx.android.synthetic.main.fragment_in_theaters.view.*

/**
 * Created by rrr on 2017/7/15.
 */
class InTheatersFragment : Fragment(), InTheatersContract.View {
    private object SingletonHolder {
        val Instance = InTheatersFragment()
    }

    companion object {
        fun NewInstance(): InTheatersFragment = SingletonHolder.Instance
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private var mDatas: ArrayList<MoviesBean.Subjects> = ArrayList()
    private lateinit var mAdapter: InTheatersAdapter
    private lateinit var mPresenter: InTheatersPresenter

    fun init() {
        mAdapter = InTheatersAdapter(context, mDatas)
        mPresenter = InTheatersPresenter(this)
        recyclerView.setLayoutManager(GridLayoutManager(context, 2))
        recyclerView.adapter = mAdapter
        mPresenter.getInTheatersMovies()

        swipeRefreshLayout.setColorSchemeResources(R.color.blue_primary_dark, R.color.blue_primary_light, R.color.yellow_primary_dark)

        swipeRefreshLayout.setOnRefreshListener(mPresenter::getInTheatersMovies)
    }

    override fun showError(error: String?) {
        Logger.e(error)
        if (recyclerView != null) {
            Snackbar.make(recyclerView,
                    getString(R.string.please_check_your_network),
                    Snackbar.LENGTH_INDEFINITE).setAction("重试", {
                mPresenter.getInTheatersMovies()
            }).show()
        }
    }

    override fun setLoadingIndicator(active: Boolean) {
        if (view == null) {
            return
        }
        val srl = view!!.swipeRefreshLayout
        // Make sure setRefreshing() is called after the layout is done with everything else.
        srl.post({ srl.setRefreshing(active) })
    }
    override fun showMovies(moviesBean: MoviesBean) {
        mDatas.clear()
        mDatas.addAll(moviesBean.subjects)
        mAdapter.notifyDataSetChanged()
        recyclerView.scrollToPosition(0)
        swipeRefreshLayout.isRefreshing = false
    }

//    override fun setPresenter(presenter: InTheatersContract.Presenter) {
////        mPresenter=presenter
//    }

    override fun showProgressDialog() {
        swipeRefreshLayout.isRefreshing = true
    }

    override fun hideProgressDialog() {
        swipeRefreshLayout.isRefreshing = false
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_in_theaters, container, false);
    }
}