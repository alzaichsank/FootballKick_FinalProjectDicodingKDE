package alzaichsank.com.aplikasifootbalmatchschedule.view.favorites.corefragment

import alzaichsank.com.aplikasifootbalmatchschedule.R
import alzaichsank.com.aplikasifootbalmatchschedule.adapter.ViewPagerAdapter
import alzaichsank.com.aplikasifootbalmatchschedule.view.favorites.favoritematch.FragmentFavoritesMatch
import alzaichsank.com.aplikasifootbalmatchschedule.view.favorites.favoriteteam.FragmentFavoritesTeam
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_view_pager.*

class FragmentFavorites : Fragment() {
    companion object {
        const val TAG = "FragmenFavorites"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_view_pager, container, false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViewPager(matches_viewpager)
        matches_tabs.setupWithViewPager(matches_viewpager)

    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFrag(FragmentFavoritesMatch(), "Matches")
        adapter.addFrag(FragmentFavoritesTeam(), "Teams")
        viewPager.adapter = adapter
    }

}