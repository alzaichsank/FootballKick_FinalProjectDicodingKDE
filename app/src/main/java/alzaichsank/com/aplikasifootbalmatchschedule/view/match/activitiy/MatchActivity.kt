package alzaichsank.com.aplikasifootbalmatchschedule.view.match.activitiy

import alzaichsank.com.aplikasifootbalmatchschedule.R
import alzaichsank.com.aplikasifootbalmatchschedule.view.favorites.corefragment.FragmentFavorites
import alzaichsank.com.aplikasifootbalmatchschedule.view.match.fragmnet.FragmentMatch
import alzaichsank.com.aplikasifootbalmatchschedule.view.team.fragment.FragmentTeam
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_match.*

class MatchActivity : AppCompatActivity() {

    private var menuItem: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLayout()
        initBottomNavigationContainer()
    }

    private fun initLayout() {
        setContentView(R.layout.activity_match)
        setSupportActionBar(toolbar_main)
    }

    private fun initBottomNavigationContainer() {
        navigation.setOnNavigationItemSelectedListener(bottomNavigationListener)
        setMatch()

    }

    private val bottomNavigationListener by lazy {
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.main_menu_match -> {
                    menuItem = 1
                    setMatch()
                    changeToolbarTitle(getString(R.string.match))
                    Log.d("TAG", "ini match")
                    true
                }
                R.id.main_menu_team -> {
                    menuItem = 2
                    setTeam()
                    changeToolbarTitle(getString(R.string.team))
                    Log.d("TAG", "ini team")
                    true
                }
                R.id.main_menu_favorites -> {
                    menuItem = 3
                    setFavorites()
                    changeToolbarTitle(getString(R.string.fav))
                    Log.d("TAG", "ini fav")
                    true
                }
                else -> {
                    true
                }
            }
        }
    }

    private fun setMatch() {
        loadFragment(FragmentMatch(), FragmentMatch.TAG)
    }

    private fun setTeam() {
        loadFragment(FragmentTeam(), FragmentTeam.TAG)
    }

    private fun setFavorites() {
        loadFragment(FragmentFavorites(), FragmentFavorites.TAG)
    }

    private fun loadFragment(fragment: Fragment, tag: String?) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_fragment, fragment, tag)
        transaction.commit()
    }


    private fun changeToolbarTitle(title: String) {
        setTitle(title)
    }


}
