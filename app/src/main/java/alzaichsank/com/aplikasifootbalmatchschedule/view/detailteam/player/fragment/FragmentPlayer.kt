package alzaichsank.com.aplikasifootbalmatchschedule.view.detailteam.player.fragment

import alzaichsank.com.aplikasifootbalmatchschedule.R
import alzaichsank.com.aplikasifootbalmatchschedule.model.PlayerItem
import alzaichsank.com.aplikasifootbalmatchschedule.model.TeamsListItem
import alzaichsank.com.aplikasifootbalmatchschedule.utils.ServerCallback
import alzaichsank.com.aplikasifootbalmatchschedule.utils.gone
import alzaichsank.com.aplikasifootbalmatchschedule.utils.invisible
import alzaichsank.com.aplikasifootbalmatchschedule.utils.visible
import alzaichsank.com.aplikasifootbalmatchschedule.view.detailteam.activity.DetailTeamsActivity
import alzaichsank.com.aplikasifootbalmatchschedule.view.detailteam.player.adapter.PlayerAdapter
import alzaichsank.com.aplikasifootbalmatchschedule.view.detailteam.player.detailplayer.DetailPlayerActivity
import alzaichsank.com.aplikasifootbalmatchschedule.view.detailteam.presenter.DetailTeamPresenter
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_player.*

class FragmentPlayer : Fragment() {

    private lateinit var data: TeamsListItem
    companion object {
        const val TAG = "FragmenPlayer"
    }

    private val presenter = DetailTeamPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_player, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        data = activity?.intent!!.getParcelableExtra(DetailTeamsActivity.INTENT_DETAIL_TEAMS)
        initContainer()
        setDataToContainer(data.idTeam.toString())
        refreshButton.setOnClickListener {
            if (presenter.isNetworkAvailable(context as Activity)) {
                activity?.finish()
                activity?.startActivity(activity?.intent)
            } else {
                Snackbar.make(fragment_player_id, getString(R.string.turnOn_internet)
                        , Snackbar.LENGTH_LONG).show()
            }
        }

        swipeRefresh.setOnRefreshListener {
            if (swipeRefresh.isRefreshing) {
                swipeRefresh.isRefreshing = false
                setDataToContainer(data.idTeam.toString())
            }

        }
        super.onActivityCreated(savedInstanceState)

    }

    private fun initContainer() {
        recyclerview.layoutManager = LinearLayoutManager(context)
        recyclerview.adapter = PlayerAdapter { posistionData ->
            val dataIntent = getListAdapter()?.getDataAt(posistionData)
            val intent = Intent(context, DetailPlayerActivity::class.java)
            intent.putExtra(DetailPlayerActivity.INTENT_DETAIL_PLAYER, dataIntent)
            startActivity(intent)
        }
    }

    private fun setDataToContainer(id: String) {
        initContainer()
        var data_player: MutableList<PlayerItem>
        if (isVisible && presenter.isNetworkAvailable(context as Activity)) {
            showLoading()
            presenter.getAllPlayer(id, object : ServerCallback {
                override fun onSuccess(response: String) {
                    if (presenter.isSuccess(response)) {
                        try {
                            if (presenter.isSuccess(response)) {
                                data_player = presenter.parsingData(response)
                                if (data_player.size < 1) {
                                    showEmptyData()
                                } else {
                                    getListAdapter()?.setData(data_player.toMutableList())
                                    hideLoading()
                                }
                            }
                        } catch (e: NullPointerException) {
                            showEmptyData()
                        }
                    }
                }

                override fun onFailed(response: String) {
                    showEmptyData()
                }

                override fun onFailure(throwable: Throwable) {
                    showEmptyData()
                }
            })

        } else {
            showEmptyData()
            Snackbar.make(fragment_player_id, getString(R.string.turnOn_internet)
                    , Snackbar.LENGTH_LONG).show()
        }
    }

    private fun getListAdapter(): PlayerAdapter? = (recyclerview?.adapter as? PlayerAdapter)

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