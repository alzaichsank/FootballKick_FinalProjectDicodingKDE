package alzaichsank.com.aplikasifootbalmatchschedule.view.detailteam.activity

import alzaichsank.com.aplikasifootbalmatchschedule.R
import alzaichsank.com.aplikasifootbalmatchschedule.adapter.ViewPagerAdapter
import alzaichsank.com.aplikasifootbalmatchschedule.model.TeamsListItem
import alzaichsank.com.aplikasifootbalmatchschedule.view.detailteam.fragment.FragmentOverview
import alzaichsank.com.aplikasifootbalmatchschedule.view.detailteam.player.fragment.FragmentPlayer
import alzaichsank.com.aplikasifootbalmatchschedule.view.detailteam.presenter.DetailTeamPresenter
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_teams.*

class DetailTeamsActivity : AppCompatActivity() {

    private var menuFavorites: Menu? = null
    private var isFavorite: Boolean = false
    private val presenter = DetailTeamPresenter()
    private var idTeam = ""
    private lateinit var data: TeamsListItem

    companion object {
        const val INTENT_DETAIL_TEAMS = "INTENT_DETAIL_TEAMS"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        data = intent.getParcelableExtra(INTENT_DETAIL_TEAMS)
        initLayout()
        implementPutExtra()

    }

    private fun initLayout() {
        setContentView(R.layout.activity_detail_teams)
        setSupportActionBar(toolbar)
        supportActionBar?.title = data.strTeam
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setupViewPager(viewpagerTeam)
        teamstabs.setupWithViewPager(viewpagerTeam)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_favorites_only, menu)
        menuFavorites = menu
        setFavorite()
        return super.onCreateOptionsMenu(menu)
    }

    private fun setFavorite() {
        if (isFavorite) {
            menuFavorites?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorites_yes)
        } else {
            menuFavorites?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorites_no)
        }
    }

    private fun implementPutExtra() {
        if (data.strTeamFanart1.toString().toLowerCase().contains("null")) {
            Picasso.with(applicationContext)
                    .load(data.strTeamBadge)
                    .placeholder(R.drawable.ic_no_data)
                    .error(R.drawable.ic_no_data)
                    .resize(200,200)
                    .centerInside()
                    .into(imageTeam)
        } else {
            Picasso.with(applicationContext)
                    .load(data.strTeamFanart1)
                    .placeholder(R.drawable.ic_no_data)
                    .error(R.drawable.ic_no_data)
                    .centerCrop()
                    .fit()
                    .into(imageTeam)
        }
        isFavorite = presenter.isFavorite(this, data)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        } else if (item.itemId == R.id.mn_favorites) {
            if (isFavorite) {
                presenter.removeFavorites(this, data)
                Log.d("TAG REMOVE", "Remove fav")
            } else {
                presenter.addFavorites(this, data)
                Log.d("TAG ADD", "Add fav")
            }
            isFavorite = !isFavorite
            setFavorite()
            return true
        }
        return false
    }

    override fun onBackPressed() {
        finish()
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFrag(FragmentOverview(),"OVERVIEW")
        adapter.addFrag(FragmentPlayer(), "PLAYER")
        viewPager.adapter = adapter
    }


}
