package alzaichsank.com.aplikasifootbalmatchschedule.view.detailmatch.presenter

import alzaichsank.com.aplikasifootbalmatchschedule.helper.database
import alzaichsank.com.aplikasifootbalmatchschedule.model.EventsItem
import alzaichsank.com.aplikasifootbalmatchschedule.system.retrofit.BaseApiServices
import alzaichsank.com.aplikasifootbalmatchschedule.system.retrofit.UtilsApi
import alzaichsank.com.aplikasifootbalmatchschedule.utils.CoroutineContextProvider
import alzaichsank.com.aplikasifootbalmatchschedule.utils.ServerCallback
import alzaichsank.com.aplikasifootbalmatchschedule.view.detailmatch.`interface`.DetailInterface
import android.app.Activity
import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.net.ConnectivityManager
import android.util.Log
import okhttp3.ResponseBody
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
class DetailPresenter : DetailInterface {
    override fun addFavorites(context: Context, data: EventsItem) {
        try {
            context.database.use {
                insert(EventsItem.TABLE_FAVORITES,
                        EventsItem.ID_EVENT to data.idEvent,
                        EventsItem.DATE to data.dateEvent,
                        EventsItem.STR_SPORT to data.strSport,
                        EventsItem.STR_TIME to data.strTime,

                        // home team
                        EventsItem.HOME_ID to data.idHomeTeam,
                        EventsItem.HOME_TEAM to data.strHomeTeam,
                        EventsItem.HOME_SCORE to data.intHomeScore,
                        EventsItem.HOME_FORMATION to data.strHomeFormation,
                        EventsItem.HOME_GOAL_DETAILS to data.strHomeGoalDetails,
                        EventsItem.HOME_SHOTS to data.intHomeShots,
                        EventsItem.HOME_LINEUP_GOALKEEPER to data.strHomeLineupGoalkeeper,
                        EventsItem.HOME_LINEUP_DEFENSE to data.strHomeLineupDefense,
                        EventsItem.HOME_LINEUP_MIDFIELD to data.strHomeLineupMidfield,
                        EventsItem.HOME_LINEUP_FORWARD to data.strHomeLineupForward,
                        EventsItem.HOME_LINEUP_SUBSTITUTES to data.strHomeLineupSubstitutes,

                        // away team
                        EventsItem.AWAY_ID to data.idAwayTeam,
                        EventsItem.AWAY_TEAM to data.strAwayTeam,
                        EventsItem.AWAY_SCORE to data.intAwayScore,
                        EventsItem.AWAY_FORMATION to data.strAwayFormation,
                        EventsItem.AWAY_GOAL_DETAILS to data.strAwayGoalDetails,
                        EventsItem.AWAY_SHOTS to data.intAwayShots,
                        EventsItem.AWAY_LINEUP_GOALKEEPER to data.strAwayLineupGoalkeeper,
                        EventsItem.AWAY_LINEUP_DEFENSE to data.strAwayLineupDefense,
                        EventsItem.AWAY_LINEUP_MIDFIELD to data.strAwayLineupMidfield,
                        EventsItem.AWAY_LINEUP_FORWARD to data.strAwayLineupForward,
                        EventsItem.AWAY_LINEUP_SUBSTITUTES to data.strAwayLineupSubstitutes)
            }
        } catch (e: SQLiteConstraintException) {
            context.toast("Error: ${e.message}")
        }
    }

    override fun removeFavorites(context: Context, data: EventsItem) {
        try {
            context.database.use {
                delete(EventsItem.TABLE_FAVORITES,
                        EventsItem.ID_EVENT + " = {id}",
                        "id" to data.idEvent.toString())
            }
        } catch (e: SQLiteConstraintException) {
            context.toast("Error: ${e.message}")
        }
    }

    override fun isFavorite(context: Context, data: EventsItem): Boolean {
        var bFavorite = false

        context.database.use {
            val favorites = select(EventsItem.TABLE_FAVORITES)
                    .whereArgs(EventsItem.ID_EVENT + " = {id}",
                            "id" to data.idEvent.toString())
                    .parseList(classParser<EventsItem>())

            bFavorite = !favorites.isEmpty()
        }

        return bFavorite
    }

    override fun isNetworkAvailable(context: Activity): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    private val contextProvider: CoroutineContextProvider = CoroutineContextProvider()
    override fun geDetailTeam( id: String, callback: ServerCallback) {
        val mApiService: BaseApiServices = UtilsApi.apiService
//        GlobalScope.async(contextProvider.main) {
//            withContext(Dispatchers.Default){
                mApiService.getLookupteam(id)
                        .enqueue(object : Callback<ResponseBody> {
                            override fun onResponse(call: Call<ResponseBody>, response: retrofit2.Response<ResponseBody>) {
                                if (response.isSuccessful) {
                                    callback.onSuccess(response.body()!!.string())
                                } else {
                                    callback.onFailed(response.errorBody()!!.toString())
                                }
                            }

                            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                                callback.onFailure(t)
                            }
                        })
//            }
//        }
    }

    override fun parsingData(response: String): ArrayList<EventsItem> {
        val listData: ArrayList<EventsItem> = ArrayList()
        try {
            val jsonObject = JSONObject(response)
            Log.d("TAG", "Response error exception $jsonObject")
            val message = jsonObject.getJSONArray("events")
            for (i in 0 until message.length()) {
                val data = message.getJSONObject(i)
                val idEvent = data.getString("idEvent")
                val dateEvent = data.getString("dateEvent")
                val strSport = data.getString("strSport")
                val strTime = data.getString("strTime")
                //home
                val idHome = data.getString("idHomeTeam")
                val nameHome = data.getString("strHomeTeam")
                val scoreHome = data.getString("intHomeScore")
                val formationHome = data.getString("strHomeFormation")
                val golDetailHome = data.getString("intHomeScore")
                val intShotHome = data.getString("intHomeShots")
                val lineupgkHome = data.getString("strHomeLineupGoalkeeper")
                val lineupdefHome = data.getString("strHomeLineupDefense")
                val lineupmidhome = data.getString("strHomeLineupMidfield")
                val lineupforwadHome = data.getString("strHomeLineupForward")
                val lineupsubHome = data.getString("strHomeLineupSubstitutes")
                //away
                val idAway = data.getString("idAwayTeam")
                val nameAway = data.getString("strAwayTeam")
                val scoreAway = data.getString("intAwayScore")
                val formationAway = data.getString("strAwayFormation")
                val golDetailAway = data.getString("intAwayScore")
                val intShotAway = data.getString("intAwayShots")
                val lineupgkAway = data.getString("strAwayLineupGoalkeeper")
                val lineupdefAway = data.getString("strAwayLineupDefense")
                val lineupmidAway = data.getString("strAwayLineupMidfield")
                val lineupforwadAway = data.getString("strAwayLineupForward")
                val lineupsubAway = data.getString("strAwayLineupSubstitutes")
                listData.add(EventsItem(i.toLong(), idEvent, dateEvent, strSport,strTime, idHome, nameHome, scoreHome, formationHome, golDetailHome, intShotHome, lineupgkHome, lineupdefHome, lineupmidhome, lineupforwadHome, lineupsubHome,
                        idAway, nameAway, scoreAway, formationAway, golDetailAway, intShotAway, lineupgkAway, lineupdefAway, lineupmidAway, lineupforwadAway, lineupsubAway
                ))
            }
        } catch (e: Exception) {
            Log.d("TAG", "Response error exception $e")
        }
        return listData
    }

    override fun isSuccess(response: String): Boolean {
        var success = true
        try {
            val jObj = JSONObject(response)
            success = jObj.getBoolean("success")
        } catch (e: Exception) {

        }
        return success
    }
}