package alzaichsank.com.aplikasifootbalmatchschedule.view.favorites.favoriteteam

import alzaichsank.com.aplikasifootbalmatchschedule.R
import alzaichsank.com.aplikasifootbalmatchschedule.model.TeamsListItem
import alzaichsank.com.aplikasifootbalmatchschedule.utils.gone
import alzaichsank.com.aplikasifootbalmatchschedule.utils.invisible
import alzaichsank.com.aplikasifootbalmatchschedule.utils.visible
import alzaichsank.com.aplikasifootbalmatchschedule.view.detailteam.activity.DetailTeamsActivity
import alzaichsank.com.aplikasifootbalmatchschedule.view.favorites.favoriteteam.adapter.FavoritesTeamAdapter
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

class FragmentFavoritesTeam : Fragment() {
    private val presenter = FavoritesPresenter()

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
        recyclerview.adapter = FavoritesTeamAdapter { posistionData ->
            val dataIntent = getListAdapter()?.getDataAt(posistionData)
            val intent = Intent(context, DetailTeamsActivity::class.java)
            intent.putExtra(DetailTeamsActivity.INTENT_DETAIL_TEAMS, dataIntent)
            startActivity(intent)
        }
    }

    private fun setDataToContainer() {
        initContainer()

        if (isVisible && presenter.isNetworkAvailable(context as Activity)) {
            showLoading()
            val data: MutableList<TeamsListItem>
            data = presenter.getFavoritesTeamAll(context as Activity)
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

    private fun getListAdapter(): FavoritesTeamAdapter? = (recyclerview?.adapter as? FavoritesTeamAdapter)

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