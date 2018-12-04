package alzaichsank.com.aplikasifootbalmatchschedule.view.team.`interface`

import alzaichsank.com.aplikasifootbalmatchschedule.model.TeamsListItem
import alzaichsank.com.aplikasifootbalmatchschedule.utils.ServerCallback
import android.app.Activity

interface TeamInterface {
    fun isNetworkAvailable(context: Activity): Boolean
    fun getSpinnerData(callback: ServerCallback)
    fun isSuccess(response: String): Boolean
    fun parsingData(context: Activity, response: String): ArrayList<TeamsListItem>
    fun getTeamData(keyword : String,  callback: ServerCallback)
    fun getSerching(keyword : String,  callback: ServerCallback)

}