package alzaichsank.com.aplikasifootbalmatchschedule.view.favorites.favoritematch

import alzaichsank.com.aplikasifootbalmatchschedule.R
import alzaichsank.com.aplikasifootbalmatchschedule.model.EventsItem
import alzaichsank.com.aplikasifootbalmatchschedule.utils.gone
import alzaichsank.com.aplikasifootbalmatchschedule.utils.invisible
import alzaichsank.com.aplikasifootbalmatchschedule.utils.visible
import alzaichsank.com.aplikasifootbalmatchschedule.view.detailmatch.activitiy.DetailMatchActivity
import alzaichsank.com.aplikasifootbalmatchschedule.view.favorites.favoritematch.adapter.FavoritesMatchesAdapter
import alzaichsank.com.aplikasifootbalmatchschedule.view.favorites.presenter.FavoritesPresenter
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_list_favorites.*

class FragmentFavoritesMatch : Fragment() {

    private val presenter = FavoritesPresenter()
    private var type = 3

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list_favorites, container, false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setDataToContainer()
        refreshButton.setOnClickListener {
            if (presenter.isNetworkAvailable(context as Activity)) {
                activity?.finish()
                activity?.startActivity(activity?.intent)
            } else {
                Snackbar.make(favorites_id, getString(R.string.turnOn_internet)
                        , Snackbar.LENGTH_LONG).show()
            }
        }

        swipeRefresh.setOnRefreshListener {
            if (swipeRefresh.isRefreshing) {
                swipeRefresh.isRefreshing = false
                setDataToContainer()
            }

        }

    }

    private fun initContainer() {
        recyclerview.layoutManager = LinearLayoutManager(context)
        recyclerview.adapter = FavoritesMatchesAdapter(type) { posistionData ->
            val dataIntent = getListAdapter()?.getDataAt(posistionData)
            val intent = Intent(context, DetailMatchActivity::class.java)
            intent.putExtra(DetailMatchActivity.INTENT_DETAIL, dataIntent)
            intent.putExtra(getString(R.string.menuItem), type.toString())
            startActivity(intent)
        }
    }

    private fun setDataToContainer() {
        initContainer()

        if (isVisible && presenter.isNetworkAvailable(context as Activity)) {
            showLoading()
            val data: MutableList<EventsItem>
            data = presenter.getFavoritesMatchesAll(context as Activity)
            if (data.size < 1) {
                hideLoading()
                showEmptyData()
            } else {
                hideLoading()
                getListAdapter()?.setData(data.toMutableList())
            }


        } else {
            hideLoading()
            showEmptyData()
            Snackbar.make(favorites_id, getString(R.string.turnOn_internet)
                    , Snackbar.LENGTH_LONG).show()
        }
    }

    private fun getListAdapter(): FavoritesMatchesAdapter? = (recyclerview?.adapter as? FavoritesMatchesAdapter)

    fun showLoading() {
        progressbar.visible()
        recyclerview.invisible()
        emptyDataView.invisible()
        noconectionView.invisible()
    }

    fun hideLoading() {
        progressbar.gone()
        recyclerview.visible()
        emptyDataView.invisible()
        noconectionView.invisible()
    }

    fun showEmptyData() {
        progressbar.gone()
        recyclerview.invisible()
        if (presenter.isNetworkAvailable(context as Activity)) {
            emptyDataView.visible()
        } else {
            noconectionView.visible()

        }
    }
}