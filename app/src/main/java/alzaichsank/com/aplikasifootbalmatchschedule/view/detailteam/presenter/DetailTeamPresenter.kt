package alzaichsank.com.aplikasifootbalmatchschedule.view.detailteam.presenter

import alzaichsank.com.aplikasifootbalmatchschedule.helper.database
import alzaichsank.com.aplikasifootbalmatchschedule.model.PlayerItem
import alzaichsank.com.aplikasifootbalmatchschedule.model.TeamsListItem
import alzaichsank.com.aplikasifootbalmatchschedule.system.retrofit.BaseApiServices
import alzaichsank.com.aplikasifootbalmatchschedule.system.retrofit.UtilsApi
import alzaichsank.com.aplikasifootbalmatchschedule.utils.ServerCallback
import alzaichsank.com.aplikasifootbalmatchschedule.view.detailteam.`interface`.DetailTeamsInterface
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

class DetailTeamPresenter : DetailTeamsInterface {
    override fun parsingDetailPlayer(response: String): ArrayList<PlayerItem> {
        val listData: ArrayList<PlayerItem> = ArrayList()
        try {
            val jsonObject = JSONObject(response)
            Log.d("TAG", "Result $jsonObject")
            val message = jsonObject.getJSONArray("players")
            for (i in 0 until message.length()) {
                val data = message.getJSONObject(i)
                val idPlayer = data.getString("idPlayer")
                val idTeam = data.getString("idTeam")
                val strPlayer = data.getString("strPlayer")
                val strTeam = data.getString("strTeam")
                val strNationality = data.getString("strNationality")
                val strCutout = data.getString("strCutout")
                val strPosition = data.getString("strPosition")
                val strThumb = data.getString("strThumb")
                val strHeight = data.getString("strHeight")
                val strWeight = data.getString("strWeight")
                val strFanart1 = data.getString("strFanart1")
                val dateBorn = data.getString("dateBorn")
                val strDescriptionEN = data.getString("strDescriptionEN")

                listData.add(PlayerItem(i.toLong(), idPlayer, idTeam, strPlayer, strTeam, strNationality, strCutout, strPosition,
                        strThumb, strHeight, strWeight, strFanart1, dateBorn, strDescriptionEN
                ))
            }
        } catch (e: Exception) {
            Log.d("TAG", "Response error exception $e")
        }
        return listData
    }

    override fun parsingDataTeam(response: String): ArrayList<TeamsListItem> {
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

    override fun getDetailTeam(id: String, callback: ServerCallback) {
        val mApiService: BaseApiServices = UtilsApi.apiService
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
    }

    override fun getDetailPlayer(id: String, callback: ServerCallback) {
        val mApiService: BaseApiServices = UtilsApi.apiService
        mApiService.getPlayerData(id)
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

    override fun isSuccess(response: String): Boolean {
        var success = true
        try {
            val jObj = JSONObject(response)
            success = jObj.getBoolean("success")
        } catch (e: Exception) {

        }
        return success
    }

    override fun isNetworkAvailable(context: Activity): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    override fun getAllPlayer(id: String, callback: ServerCallback) {
        val mApiService: BaseApiServices = UtilsApi.apiService
        mApiService.getAllPlayerList(id)
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

    override fun parsingData(response: String): ArrayList<PlayerItem> {
        val listData: ArrayList<PlayerItem> = ArrayList()
        try {
            val jsonObject = JSONObject(response)
            Log.d("TAG", "Result $jsonObject")
            val message = jsonObject.getJSONArray("player")
            for (i in 0 until message.length()) {
                val data = message.getJSONObject(i)
                val idPlayer = data.getString("idPlayer")
                val idTeam = data.getString("idTeam")
                val strPlayer = data.getString("strPlayer")
                val strTeam = data.getString("strTeam")
                val strNationality = data.getString("strNationality")
                val strCutout = data.getString("strCutout")
                val strPosition = data.getString("strPosition")
                val strThumb = data.getString("strThumb")
                val strHeight = data.getString("strHeight")
                val strWeight = data.getString("strWeight")
                val strFanart1 = data.getString("strFanart1")
                val dateBorn = data.getString("dateBorn")
                val strDescriptionEN = data.getString("strDescriptionEN")

                listData.add(PlayerItem(i.toLong(), idPlayer, idTeam, strPlayer, strTeam, strNationality, strCutout, strPosition,
                        strThumb, strHeight, strWeight, strFanart1,dateBorn, strDescriptionEN
                ))
            }
        } catch (e: Exception) {
            Log.d("TAG", "Response error exception $e")
        }
        return listData
    }

    override fun addFavorites(context: Context, data: TeamsListItem) {
        try {
            context.database.use {
                insert(TeamsListItem.TABLE_FAVORITES_TEAM,
                        TeamsListItem.ID_TEAM to data.idTeam,
                        TeamsListItem.STR_TEAMBADGE to data.strTeamBadge,
                        TeamsListItem.STR_TEAM to data.strTeam,
                        TeamsListItem.STR_MANAGER to data.strManager,
                        TeamsListItem.STR_STADIUM to data.strStadium,
                        TeamsListItem.STR_TEAMFANART1 to data.strTeamFanart1,
                        TeamsListItem.STR_FORMED_YEAR to data.intFormedYear,
                        TeamsListItem.STR_DESCRIPTIONEN to data.strDescriptionEN
                )
            }
        } catch (e: SQLiteConstraintException) {
            context.toast("Error: ${e.message}")
        }
    }

    override fun removeFavorites(context: Context, data: TeamsListItem) {
        try {
            context.database.use {
                delete(TeamsListItem.TABLE_FAVORITES_TEAM,
                        TeamsListItem.ID_TEAM + " = {id}",
                        "id" to data.idTeam.toString())
            }
        } catch (e: SQLiteConstraintException) {
            context.toast("Error: ${e.message}")
        }
    }

    override fun isFavorite(context: Context, data: TeamsListItem): Boolean {
        var bFavorite = false

        context.database.use {
            val favorites = select(TeamsListItem.TABLE_FAVORITES_TEAM)
                    .whereArgs(TeamsListItem.ID_TEAM + " = {id}",
                            "id" to data.idTeam.toString())
                    .parseList(classParser<TeamsListItem>())

            bFavorite = !favorites.isEmpty()
        }

        return bFavorite
    }
}