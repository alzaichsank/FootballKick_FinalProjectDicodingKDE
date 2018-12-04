package alzaichsank.com.aplikasifootbalmatchschedule.view.favorites.presenter

import alzaichsank.com.aplikasifootbalmatchschedule.helper.database
import alzaichsank.com.aplikasifootbalmatchschedule.model.EventsItem
import alzaichsank.com.aplikasifootbalmatchschedule.model.TeamsListItem
import alzaichsank.com.aplikasifootbalmatchschedule.view.favorites.`interface`.FavoritesInterface
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class FavoritesPresenter : FavoritesInterface {
    override fun isNetworkAvailable(context: Activity): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    override fun getFavoritesMatchesAll(context: Context): ArrayList<EventsItem> {
        val data: ArrayList<EventsItem> = ArrayList()
        context.database.use {
            val favoritesMatches = select(EventsItem.TABLE_FAVORITES)
                    .parseList(classParser<EventsItem>())
            data.addAll(favoritesMatches)
        }
        return data
    }

    override fun getFavoritesTeamAll(context: Context): ArrayList<TeamsListItem> {
        val data: ArrayList<TeamsListItem> = ArrayList()
        context.database.use {
            val favoritesTeam = select(TeamsListItem.TABLE_FAVORITES_TEAM)
                    .parseList(classParser<TeamsListItem>())
            data.addAll(favoritesTeam)
        }
        return data
    }
}