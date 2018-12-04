package alzaichsank.com.aplikasifootbalmatchschedule.view.detailteam.`interface`

import alzaichsank.com.aplikasifootbalmatchschedule.model.PlayerItem
import alzaichsank.com.aplikasifootbalmatchschedule.model.TeamsListItem
import alzaichsank.com.aplikasifootbalmatchschedule.utils.ServerCallback
import android.app.Activity
import android.content.Context

interface DetailTeamsInterface {
    fun isSuccess(response: String): Boolean
    fun isNetworkAvailable(context: Activity): Boolean
    fun getAllPlayer(id : String,  callback: ServerCallback)
    fun getDetailTeam(id : String,  callback: ServerCallback)
    fun getDetailPlayer(id : String,  callback: ServerCallback)
    fun parsingData(response: String): ArrayList<PlayerItem>
    fun parsingDetailPlayer(response: String): ArrayList<PlayerItem>
    fun parsingDataTeam(response: String): ArrayList<TeamsListItem>
    fun addFavorites(context: Context, data: TeamsListItem)
    fun removeFavorites(context: Context, data: TeamsListItem)
    fun isFavorite(context: Context, data: TeamsListItem): Boolean
}