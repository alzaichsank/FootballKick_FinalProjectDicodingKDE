package alzaichsank.com.aplikasifootbalmatchschedule.view.match.fragmnet

import alzaichsank.com.aplikasifootbalmatchschedule.R
import alzaichsank.com.aplikasifootbalmatchschedule.adapter.ViewPagerAdapter
import alzaichsank.com.aplikasifootbalmatchschedule.view.search.match.activitiy.SearchActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.SearchView
import android.view.*
import kotlinx.android.synthetic.main.fragment_view_pager.*
import org.jetbrains.anko.startActivity


class FragmentMatch : Fragment() {
    private var leagueId = "4335"
    companion object {
        const val TAG = "FragmentMatch"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_view_pager, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViewPager(matches_viewpager)
        matches_tabs.setupWithViewPager(matches_viewpager)
    }


    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFrag(FragmentList().newFragment(1,leagueId), "NEXT")
        adapter.addFrag(FragmentList().newFragment(2,leagueId), "LAST")
        viewPager.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater?) {

        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_search, menu)

        val searchView = menu?.findItem(R.id.search_menu)?.actionView as SearchView?

        searchView?.queryHint = "Search Match"

        searchView?.setOnQueryTextListener(object : android.support.v7.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                context?.startActivity<SearchActivity>("query" to query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }


}