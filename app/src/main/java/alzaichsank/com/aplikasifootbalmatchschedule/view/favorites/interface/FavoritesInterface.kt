package alzaichsank.com.aplikasifootbalmatchschedule.view.favorites.`interface`

import alzaichsank.com.aplikasifootbalmatchschedule.model.EventsItem
import alzaichsank.com.aplikasifootbalmatchschedule.model.TeamsListItem
import android.app.Activity
import android.content.Context

interface FavoritesInterface {
    fun isNetworkAvailable(context: Activity): Boolean
    fun getFavoritesMatchesAll(context: Context) : ArrayList<EventsItem>
    fun getFavoritesTeamAll(context: Context) : ArrayList<TeamsListItem>
}