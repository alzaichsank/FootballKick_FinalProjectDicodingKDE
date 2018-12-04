package alzaichsank.com.aplikasifootbalmatchschedule.view.team.fragment

import alzaichsank.com.aplikasifootbalmatchschedule.R
import alzaichsank.com.aplikasifootbalmatchschedule.model.LeaguesItem
import alzaichsank.com.aplikasifootbalmatchschedule.model.TeamsListItem
import alzaichsank.com.aplikasifootbalmatchschedule.utils.ServerCallback
import alzaichsank.com.aplikasifootbalmatchschedule.utils.gone
import alzaichsank.com.aplikasifootbalmatchschedule.utils.invisible
import alzaichsank.com.aplikasifootbalmatchschedule.utils.visible
import alzaichsank.com.aplikasifootbalmatchschedule.view.detailteam.activity.DetailTeamsActivity
import alzaichsank.com.aplikasifootbalmatchschedule.view.match.`interface`.MatchView
import alzaichsank.com.aplikasifootbalmatchschedule.view.search.team.activity.SerchTeamActivity
import alzaichsank.com.aplikasifootbalmatchschedule.view.team.adapter.TeamAdapter
import alzaichsank.com.aplikasifootbalmatchschedule.view.team.presenter.TeamPresenter
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.fragment_list.*
import org.jetbrains.anko.startActivity
import org.json.JSONObject

class FragmentTeam : Fragment(), MatchView {
    private var listLeageu = ArrayList<LeaguesItem>()
    private var idSpinner: String = ""


    companion object {
        const val TAG = "FragmenTeam"
    }

    private val presenter = TeamPresenter(this)
    override fun showLoading() {
        progressbar.visible()
        recyclerview.invisible()
        emptyDataView.invisible()
        noconectionView.invisible()
    }

    override fun hideLoading() {
        progressbar.gone()
        recyclerview.visible()
        emptyDataView.invisible()
        noconectionView.invisible()
    }

    override fun showEmptyData() {
        progressbar.gone()
        recyclerview.invisible()
        if (presenter.isNetworkAvailable(context as Activity)) {
            emptyDataView.visible()
        } else {
            noconectionView.visible()

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_teams_list, container, false)

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getlistLeageu()
        if (isVisible) {
            setSpinner()
        }
        initContainer()
        refreshButton.setOnClickListener {
            if (presenter.isNetworkAvailable(context as Activity)) {
                activity?.finish()
                activity?.startActivity(activity?.intent)
            } else {
                Snackbar.make(fragment_match_id, getString(R.string.turnOn_internet)
                        , Snackbar.LENGTH_LONG).show()
            }
        }

        swipeRefresh.setOnRefreshListener {
            if (swipeRefresh.isRefreshing) {
                swipeRefresh.isRefreshing = false
                setSpinner()
                setDataToContainer(idSpinner)
            }

        }

    }

    private fun getlistLeageu() {
        if (isVisible && presenter.isNetworkAvailable(context as Activity)) {
            presenter.getSpinnerData(object : ServerCallback {
                override fun onSuccess(response: String) {
                    if (isVisible && presenter.isSuccess(response)) {
                        try {
                            val jsonObject = JSONObject(response)
                            var numData = 0
                            val message = jsonObject.getJSONArray("countrys")
                            val idLeague: ArrayList<String> = ArrayList()
                            val leagueName: ArrayList<String> = ArrayList()
                            Log.d("TAG MESSAGE", message.length().toString())
                            if (isVisible && message.length() > 0) {
                                for (i in 0 until message.length()) {
                                    val dataAll = message.getJSONObject(i)
                                    idLeague.add(dataAll.getString("idLeague"))
                                    leagueName.add(dataAll.getString("strLeague"))
                                    listLeageu.add(LeaguesItem(idLeague[i], leagueName[i]))
                                    numData += 1
                                }
                                Log.d("TAG NUMDATA", numData.toString())
                                spinner.adapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, leagueName)
                            } else {
                            }
                        } catch (e: NullPointerException) {
                            showEmptyData()
                        }
                    }
                }

                override fun onFailed(response: String) {
                }

                override fun onFailure(throwable: Throwable) {
                }
            })
        } else {
            showEmptyData()
            Snackbar.make(fragment_match_id, getString(R.string.turnOn_internet)
                    , Snackbar.LENGTH_LONG).show()
        }

    }

    private fun setSpinner() {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                idSpinner = listLeageu[position].idLeague
                setDataToContainer(idSpinner)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }

    private fun initContainer() {
        recyclerview.layoutManager = LinearLayoutManager(context)
        recyclerview.adapter = TeamAdapter { posistionData ->
            val dataIntent = getListAdapter()?.getDataAt(posistionData)
            val intent = Intent(context, DetailTeamsActivity::class.java)
            intent.putExtra(DetailTeamsActivity.INTENT_DETAIL_TEAMS, dataIntent)
            startActivity(intent)
        }
    }

    private fun setDataToContainer(id: String) {
        initContainer()
        var data: MutableList<TeamsListItem>
        if (isVisible && presenter.isNetworkAvailable(context as Activity)) {
            showLoading()
            presenter.getTeamData(id, object : ServerCallback {
                override fun onSuccess(response: String) {
                    if (presenter.isSuccess(response)) {
                        try {
                            if (presenter.isSuccess(response)) {
                                data = presenter.parsingData(context as Activity, response)
                                if (data.size < 1) {
                                    showEmptyData()
                                } else {
                                    getListAdapter()?.setData(data.toMutableList())
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
            Snackbar.make(fragment_match_id, getString(R.string.turnOn_internet)
                    , Snackbar.LENGTH_LONG).show()
        }
    }

    private fun getListAdapter(): TeamAdapter? = (recyclerview?.adapter as? TeamAdapter)

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater?) {

        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_search, menu)

        val searchView = menu?.findItem(R.id.search_menu)?.actionView as SearchView?

        searchView?.queryHint = "Search Team"

        searchView?.setOnQueryTextListener(object : android.support.v7.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                context?.startActivity<SerchTeamActivity>("query" to query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

}