package alzaichsank.com.aplikasifootbalmatchschedule.view.match.fragmnet

import alzaichsank.com.aplikasifootbalmatchschedule.R
import alzaichsank.com.aplikasifootbalmatchschedule.model.EventsItem
import alzaichsank.com.aplikasifootbalmatchschedule.model.LeaguesItem
import alzaichsank.com.aplikasifootbalmatchschedule.utils.ServerCallback
import alzaichsank.com.aplikasifootbalmatchschedule.utils.gone
import alzaichsank.com.aplikasifootbalmatchschedule.utils.invisible
import alzaichsank.com.aplikasifootbalmatchschedule.utils.visible
import alzaichsank.com.aplikasifootbalmatchschedule.view.detailmatch.activitiy.DetailMatchActivity
import alzaichsank.com.aplikasifootbalmatchschedule.view.match.`interface`.MatchView
import alzaichsank.com.aplikasifootbalmatchschedule.view.match.adapter.matchAdapter
import alzaichsank.com.aplikasifootbalmatchschedule.view.match.presenter.MatchPresenter
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.fragment_list.*
import org.json.JSONObject

class FragmentList : Fragment(), MatchView {

    private var listLeageu = ArrayList<LeaguesItem>()
    private var idSpinner: String = ""
    private var type = 1
    private var leagueId = "4335"   //EPL

    fun newFragment(type: Int, leagueId: String): FragmentList{
        val fragment = FragmentList()
        fragment.type = type
        fragment.leagueId = leagueId

        return fragment
    }
    private val presenter = MatchPresenter(this)
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
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getlistLeageu()
        if(isVisible ) {
            setSpinner(type)
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
                setSpinner(type)
                setDataToContainer(idSpinner, type)
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

    private fun setSpinner(menu: Int) {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                idSpinner = listLeageu[position].idLeague
                when (menu) {
                    1 -> setDataToContainer(idSpinner, 1)
                    2 -> setDataToContainer(idSpinner, 2)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }
    private fun initContainer() {
        recyclerview.layoutManager = LinearLayoutManager(context)
        recyclerview.adapter = matchAdapter(type) { posistionData ->
            val dataIntent = getListAdapter()?.getDataAt(posistionData)
            val intent = Intent(context, DetailMatchActivity::class.java)
            intent.putExtra(DetailMatchActivity.INTENT_DETAIL, dataIntent)
            intent.putExtra(getString(R.string.menuItem), type.toString())
            startActivity(intent)
        }
    }

    private fun setDataToContainer(id: String, menu: Int) {
        initContainer()
        var data: MutableList<EventsItem>
        if (isVisible && presenter.isNetworkAvailable(context as Activity)) {
            if (menu == 2) {
                showLoading()
                presenter.getPrevMatch(id, object : ServerCallback {
                    override fun onSuccess(response: String) {
                        if (presenter.isSuccess(response)) {
                            try {
                                if (isVisible && presenter.isSuccess(response)) {
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
            } else if (menu == 1) {
                showLoading()
                presenter.getNextMatch(id, object : ServerCallback {
                    override fun onSuccess(response: String) {
                        if (isAdded && presenter.isSuccess(response)) {
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
            } else if (menu == 3) {
                var data: MutableList<EventsItem>
                data = presenter.getFavoritesAll(context as Activity)
                if (data.size < 1) {
                    showEmptyData()
                } else {
                    getListAdapter()?.setData(data.toMutableList())
                }

            } else {
                showEmptyData()
            }
        } else {
            showEmptyData()
            Snackbar.make(fragment_match_id, getString(R.string.turnOn_internet)
                    , Snackbar.LENGTH_LONG).show()
        }
    }

    private fun getListAdapter(): matchAdapter? = (recyclerview?.adapter as? matchAdapter)
}
