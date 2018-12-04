package alzaichsank.com.aplikasifootbalmatchschedule.view.detailteam.fragment

import alzaichsank.com.aplikasifootbalmatchschedule.R
import alzaichsank.com.aplikasifootbalmatchschedule.model.TeamsListItem
import alzaichsank.com.aplikasifootbalmatchschedule.view.detailteam.activity.DetailTeamsActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_overview.*

class FragmentOverview : Fragment() {

    private lateinit var data: TeamsListItem


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_overview, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        data = activity?.intent!!.getParcelableExtra(DetailTeamsActivity.INTENT_DETAIL_TEAMS)
        super.onActivityCreated(savedInstanceState)
        setData(data)
    }

    private fun setData(data: TeamsListItem?) {
        Picasso.with(context)
                .load(data!!.strTeamBadge)
                .placeholder(R.drawable.ic_no_data)
                .error(R.drawable.ic_no_data)
                .into(imgBadge)
        teamName.text = data.strTeam
        tvManager.text = data.strManager
        tvDate.text = data.intFormedYear
        tvStadium.text = data.strStadium
        teamOverview.text = data.strDescriptionEN
    }
}