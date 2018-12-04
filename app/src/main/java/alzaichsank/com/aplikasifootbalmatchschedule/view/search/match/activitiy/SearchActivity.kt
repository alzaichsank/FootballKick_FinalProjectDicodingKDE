package alzaichsank.com.aplikasifootbalmatchschedule.view.search.match.activitiy

import alzaichsank.com.aplikasifootbalmatchschedule.R
import alzaichsank.com.aplikasifootbalmatchschedule.model.EventsItem
import alzaichsank.com.aplikasifootbalmatchschedule.utils.ServerCallback
import alzaichsank.com.aplikasifootbalmatchschedule.utils.gone
import alzaichsank.com.aplikasifootbalmatchschedule.utils.invisible
import alzaichsank.com.aplikasifootbalmatchschedule.utils.visible
import alzaichsank.com.aplikasifootbalmatchschedule.view.detailmatch.activitiy.DetailMatchActivity
import alzaichsank.com.aplikasifootbalmatchschedule.view.match.`interface`.MatchView
import alzaichsank.com.aplikasifootbalmatchschedule.view.match.presenter.MatchPresenter
import alzaichsank.com.aplikasifootbalmatchschedule.view.search.match.adapter.SearchAdapter
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.appbar_main.*

class SearchActivity : AppCompatActivity(), MatchView {
    private var type = 4
    private var keywordData = ""
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
        if (presenter.isNetworkAvailable(this)) {
            emptyDataView.visible()
        } else {
            noconectionView.visible()

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        keywordData = intent.getStringExtra("query")
        initLayout()
        initContainer()
        setDataToContainer(keywordData)
        refreshButton.setOnClickListener {
            if (presenter.isNetworkAvailable(this)) {
                this.finish()
                this.startActivity(this.intent)
            } else {
                Snackbar.make(search_activity, getString(R.string.turnOn_internet)
                        , Snackbar.LENGTH_LONG).show()
            }
        }

        swipeRefresh.setOnRefreshListener {
            if (swipeRefresh.isRefreshing) {
                swipeRefresh.isRefreshing = false
            }
            setDataToContainer(keywordData)
        }
    }

    private fun initLayout() {
        setContentView(R.layout.activity_search)
        setSupportActionBar(toolbar_main)
    }

    private fun initContainer() {
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = SearchAdapter { posistionData ->
            val dataIntent = getListAdapter()?.getDataAt(posistionData)
            val intent = Intent(this@SearchActivity, DetailMatchActivity::class.java)
            intent.putExtra(DetailMatchActivity.INTENT_DETAIL, dataIntent)
            intent.putExtra(getString(R.string.menuItem), type.toString())
            startActivity(intent)
        }
    }

    private fun setDataToContainer(keyword: String) {
        initContainer()
        var data: MutableList<EventsItem>
        if (presenter.isNetworkAvailable(this)) {
            showLoading()
            presenter.getSerching(keyword, object : ServerCallback {
                override fun onSuccess(response: String) {
                    if (presenter.isSuccess(response)) {
                        try {
                            if (presenter.isSuccess(response)) {
                                data = presenter.parsingDataSeraching(this@SearchActivity, response)
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
            Snackbar.make(search_activity, getString(R.string.turnOn_internet)
                    , Snackbar.LENGTH_LONG).show()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchView = menu?.findItem(R.id.search_menu)?.actionView as SearchView?
        searchView?.queryHint = "Search Matches"

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(keyword_new: String): Boolean {
                setDataToContainer(keyword_new)
                return false
            }

            override fun onQueryTextChange(keyword_new: String): Boolean {
                setDataToContainer(keyword_new)
                return false
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return false
    }
    override fun onBackPressed() {
        finish()
    }


    private fun getListAdapter(): SearchAdapter? = (recyclerview?.adapter as? SearchAdapter)

}
