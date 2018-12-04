package alzaichsank.com.aplikasifootbalmatchschedule.model
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TeamsListItem(
        val id: Long?,
        val idTeam : String?,
        val strTeamBadge : String?,
        val strTeam : String?,
        val strManager : String?,
        val strStadium : String?,
        val strTeamFanart1 : String?,
        val intFormedYear : String?,
        val strDescriptionEN : String?): Parcelable {
    companion object {
        const val TABLE_FAVORITES_TEAM = "TABLE_FAVORITES_TEAM"
        const val ID = "ID"
        const val ID_TEAM = "ID_TEAM"
        const val STR_TEAMBADGE = "STR_TEAMBADGE"
        const val STR_TEAM = "STR_TEAM"
        const val STR_MANAGER = "STR_MANAGER"
        const val STR_STADIUM = "STR_STADIUM"
        const val STR_TEAMFANART1 = "STR_TEAMFANART1"
        const val STR_FORMED_YEAR = "STR_FORMED_YEAR"
        const val STR_DESCRIPTIONEN = "STR_DESCRIPTIONEN"
    }
}
