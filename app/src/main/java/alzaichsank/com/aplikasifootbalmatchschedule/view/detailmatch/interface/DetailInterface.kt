package alzaichsank.com.aplikasifootbalmatchschedule.view.detailmatch.`interface`

import alzaichsank.com.aplikasifootbalmatchschedule.model.EventsItem
import alzaichsank.com.aplikasifootbalmatchschedule.utils.ServerCallback
import android.app.Activity
import android.content.Context

interface DetailInterface {
    fun isNetworkAvailable(context: Activity): Boolean
    fun geDetailTeam(id : String,  callback: ServerCallback)
    fun isSuccess(response: String): Boolean
    fun parsingData(response: String): ArrayList<EventsItem>
    fun addFavorites(context: Context, data: EventsItem)
    fun removeFavorites(context: Context, data: EventsItem)
    fun isFavorite(context: Context, data: EventsItem): Boolean
}