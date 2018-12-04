package alzaichsank.com.aplikasifootbalmatchschedule.system.retrofit

import alzaichsank.com.aplikasifootbalmatchschedule.model.TeamsItem
import alzaichsank.com.aplikasifootbalmatchschedule.system.config.ApiConfig
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by alzaichsank on 1/22/18.
 */
interface BaseApiServices {

    @GET(ApiConfig.allLeagues)
    fun getallLeagues(): Call<ResponseBody>

    @GET(ApiConfig.eventsPastleague)
    fun geteventsPasteague(@Query("id") id : String): Call<ResponseBody>

    @GET(ApiConfig.eventsNextleague)
    fun geteeventsNextleague(@Query("id") id : String): Call<ResponseBody>

    @GET(ApiConfig.lookupTeam)
    fun getLookupteam(@Query("id") id : String): Call<ResponseBody>

    @GET(ApiConfig.searching)
    fun getSerchingData(@Query("e") e : String): Call<ResponseBody>

    // teams

    @GET(ApiConfig.lookup_all_teams)
    fun getAllTeamsList(@Query("id") id : String): Call<ResponseBody>

    @GET(ApiConfig.searchteams)
    fun getSerchingTeams(@Query("t") t : String): Call<ResponseBody>

    //player

    @GET(ApiConfig.get_all_player)
    fun getAllPlayerList(@Query("id") id : String): Call<ResponseBody>

    @GET(ApiConfig.get_player)
    fun getPlayerData(@Query("id") id : String): Call<ResponseBody>
}

