package alzaichsank.com.aplikasifootbalmatchschedule.view.match.`interface`

import alzaichsank.com.aplikasifootbalmatchschedule.model.EventsItem
import alzaichsank.com.aplikasifootbalmatchschedule.utils.ServerCallback
import android.app.Activity
import android.content.Context

interface MatchInterface {
    fun isNetworkAvailable(context: Activity): Boolean
    fun getSpinnerData(callback: ServerCallback)
    fun getPrevMatch( id : String,  callback: ServerCallback)
    fun getNextMatch( id : String,  callback: ServerCallback)
    fun isSuccess(response: String): Boolean
    fun parsingData(context: Activity, response: String): ArrayList<EventsItem>
    fun getFavoritesAll(context: Context) : ArrayList<EventsItem>
    fun getSerching(keyword : String,  callback: ServerCallback)
    fun parsingDataSeraching(context: Activity, response: String): ArrayList<EventsItem>

}