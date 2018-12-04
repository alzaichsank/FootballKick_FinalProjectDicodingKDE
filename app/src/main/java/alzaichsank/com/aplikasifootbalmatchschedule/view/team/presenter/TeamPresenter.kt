package alzaichsank.com.aplikasifootbalmatchschedule.view.team.presenter

import alzaichsank.com.aplikasifootbalmatchschedule.model.TeamsListItem
import alzaichsank.com.aplikasifootbalmatchschedule.system.retrofit.BaseApiServices
import alzaichsank.com.aplikasifootbalmatchschedule.system.retrofit.UtilsApi
import alzaichsank.com.aplikasifootbalmatchschedule.utils.CoroutineContextProvider
import alzaichsank.com.aplikasifootbalmatchschedule.utils.ServerCallback
import alzaichsank.com.aplikasifootbalmatchschedule.view.match.`interface`.MatchView
import alzaichsank.com.aplikasifootbalmatchschedule.view.team.`interface`.TeamInterface
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeamPresenter(private val view: MatchView) : TeamInterface {


    private val contextProvider: CoroutineContextProvider = CoroutineContextProvider()
    override fun isNetworkAvailable(context: Activity): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
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

    override fun getSpinnerData(callback: ServerCallback) {
        val mApiService: BaseApiServices = UtilsApi.apiService
        GlobalScope.launch(contextProvider.main) {
            withContext(Dispatchers.Default) {
                mApiService.getallLeagues()
                        .enqueue(object : Callback<ResponseBody> {
                            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
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

    override fun getTeamData(keyword: String, callback: ServerCallback) {
        val mApiService: BaseApiServices = UtilsApi.apiService
        GlobalScope.launch(contextProvider.main) {
            withContext(Dispatchers.Default) {
                mApiService.getAllTeamsList(keyword)
                        .enqueue(object : Callback<ResponseBody> {
                            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
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

    override fun parsingData(context: Activity, response: String): ArrayList<TeamsListItem> {
        val listData: ArrayList<TeamsListItem> = ArrayList()
        try {
            val jsonObject = JSONObject(response)
            Log.d("TAG hasil", "$jsonObject")
            val message = jsonObject.getJSONArray("teams")
            for (i in 0 until message.length()) {
                val data = message.getJSONObject(i)
                val idTeam = data.getString("idTeam")
                val strTeamBadge = data.getString("strTeamBadge")
                val strTeam = data.getString("strTeam")
                val strManager = data.getString("strManager")
                val strStadium = data.getString("strStadium")
                val strTeamFanart1 = data.getString("strTeamFanart1")
                val intFormedYear = data.getString("intFormedYear")
                val strDescriptionEN = data.getString("strDescriptionEN")
                listData.add(TeamsListItem(i.toLong(), idTeam, strTeamBadge, strTeam, strManager, strStadium,strTeamFanart1,intFormedYear, strDescriptionEN
                ))
            }
        } catch (e: Exception) {
            Log.d("ResponseErrorException", e.toString())
        }
        return listData
    }

    override fun getSerching(keyword: String, callback: ServerCallback) {
        val mApiService: BaseApiServices = UtilsApi.apiService
        GlobalScope.launch(contextProvider.main) {
            withContext(Dispatchers.Default) {
                mApiService.getSerchingTeams(keyword)
                        .enqueue(object : Callback<ResponseBody> {
                            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
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

}