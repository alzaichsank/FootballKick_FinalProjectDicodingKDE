package alzaichsank.com.aplikasifootbalmatchschedule.view.match.presenter

import alzaichsank.com.aplikasifootbalmatchschedule.helper.database
import alzaichsank.com.aplikasifootbalmatchschedule.model.EventsItem
import alzaichsank.com.aplikasifootbalmatchschedule.system.retrofit.BaseApiServices
import alzaichsank.com.aplikasifootbalmatchschedule.system.retrofit.UtilsApi
import alzaichsank.com.aplikasifootbalmatchschedule.utils.CoroutineContextProvider
import alzaichsank.com.aplikasifootbalmatchschedule.utils.ServerCallback
import alzaichsank.com.aplikasifootbalmatchschedule.view.match.`interface`.MatchInterface
import alzaichsank.com.aplikasifootbalmatchschedule.view.match.`interface`.MatchView
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback

class MatchPresenter(private val view: MatchView) : MatchInterface {
    override fun getSerching(keyword: String, callback: ServerCallback) {
        view.showLoading()
        val mApiService: BaseApiServices = UtilsApi.apiService
        GlobalScope.launch(contextProvider.main) {
            withContext(Default) {
                mApiService.getSerchingData(keyword)
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
            }
        }
        view.hideLoading()
    }

    override fun getFavoritesAll(context: Context): ArrayList<EventsItem> {
        view.showLoading()
        val data: ArrayList<EventsItem> = ArrayList()

        context.database.use {
            val favorites = select(EventsItem.TABLE_FAVORITES)
                    .parseList(classParser<EventsItem>())
            data.addAll(favorites)
        }

        view.hideLoading()
        return data
    }

    override fun isNetworkAvailable(context: Activity): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    private val contextProvider: CoroutineContextProvider = CoroutineContextProvider()
    override fun getPrevMatch(id: String, callback: ServerCallback) {
        view.showLoading()
        val mApiService: BaseApiServices = UtilsApi.apiService
        GlobalScope.launch(contextProvider.main) {
            withContext(Default) {
                mApiService.geteventsPasteague(id)
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
            }
        }
        view.hideLoading()
    }

    override fun parsingData(context: Activity, response: String): ArrayList<EventsItem> {
        val listData: ArrayList<EventsItem> = ArrayList()
        try {
            val jsonObject = JSONObject(response)
            Log.d("TAG hasil", "$jsonObject")
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
                val golDetailHome = data.getString("strHomeGoalDetails")
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
                val golDetailAway = data.getString("strAwayGoalDetails")
                val intShotAway = data.getString("intAwayShots")
                val lineupgkAway = data.getString("strAwayLineupGoalkeeper")
                val lineupdefAway = data.getString("strAwayLineupDefense")
                val lineupmidAway = data.getString("strAwayLineupMidfield")
                val lineupforwadAway = data.getString("strAwayLineupForward")
                val lineupsubAway = data.getString("strAwayLineupSubstitutes")

                listData.add(EventsItem(i.toLong(), idEvent, dateEvent, strSport, strTime, idHome, nameHome, scoreHome, formationHome, golDetailHome, intShotHome, lineupgkHome, lineupdefHome, lineupmidhome, lineupforwadHome, lineupsubHome,
                        idAway, nameAway, scoreAway, formationAway, golDetailAway, intShotAway, lineupgkAway, lineupdefAway, lineupmidAway, lineupforwadAway, lineupsubAway
                ))
            }
        } catch (e: Exception) {
            Log.d("ResponseErrorException", e.toString())
        }
        return listData
    }


    override fun parsingDataSeraching(context: Activity, response: String): ArrayList<EventsItem> {
        val listData: ArrayList<EventsItem> = ArrayList()
        try {
            val jsonObject = JSONObject(response)
            Log.d("TAG hasil", "$jsonObject")
            val message = jsonObject.getJSONArray("event")
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
                val golDetailHome = data.getString("strHomeGoalDetails")
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
                val golDetailAway = data.getString("strAwayGoalDetails")
                val intShotAway = data.getString("intAwayShots")
                val lineupgkAway = data.getString("strAwayLineupGoalkeeper")
                val lineupdefAway = data.getString("strAwayLineupDefense")
                val lineupmidAway = data.getString("strAwayLineupMidfield")
                val lineupforwadAway = data.getString("strAwayLineupForward")
                val lineupsubAway = data.getString("strAwayLineupSubstitutes")

                listData.add(EventsItem(i.toLong(), idEvent, dateEvent, strSport, strTime, idHome, nameHome, scoreHome, formationHome, golDetailHome, intShotHome, lineupgkHome, lineupdefHome, lineupmidhome, lineupforwadHome, lineupsubHome,
                        idAway, nameAway, scoreAway, formationAway, golDetailAway, intShotAway, lineupgkAway, lineupdefAway, lineupmidAway, lineupforwadAway, lineupsubAway
                ))
            }
        } catch (e: Exception) {
            Log.d("ResponseErrorException", e.toString())
        }
        return listData
    }
    override fun getNextMatch(id: String, callback: ServerCallback) {
        view.showLoading()
        val mApiService: BaseApiServices = UtilsApi.apiService
        GlobalScope.launch(contextProvider.main) {
            withContext(Default) {
                mApiService.geteeventsNextleague(id)
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
            }
        }
        view.hideLoading()
    }

    override fun getSpinnerData(callback: ServerCallback) {
        val mApiService: BaseApiServices = UtilsApi.apiService
        GlobalScope.launch(contextProvider.main) {
            withContext(Default) {
                mApiService.getallLeagues()
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
            }
        }
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