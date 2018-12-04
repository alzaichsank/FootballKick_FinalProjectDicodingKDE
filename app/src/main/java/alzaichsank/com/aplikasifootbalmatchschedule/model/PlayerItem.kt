package alzaichsank.com.aplikasifootbalmatchschedule.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlayerItem(
        val id: Long?,
        val idPlayer : String?,
        val idTeam : String?,
        val strPlayer : String?,
        val strTeam : String?,
        val strNationality : String?,
        val strCutout: String?,
        val strPosition : String?,
        val strThumb : String?,
        val strHeight : String?,
        val strWeight : String?,
        val strFanart1 : String?,
        val dateBorn : String?,
        val strDescriptionEN : String?): Parcelable {
    companion object {
        const val TABLE_PLAYER = "TABLE_PLAYER"
        const val ID = "ID"
        const val ID_PLAYER = "ID_PLAYER"
        const val ID_TEAM = "ID_TEAM"
        const val STR_PLAYER  = "STR_PLAYER"
        const val STR_TEAM = "STR_TEAM"
        const val STR_NATIONALITY = "STR_NATIONALITY"
        const val STR_CUTOUT = "STR_CUTOUT"
        const val STR_POSITION = "STR_POSITION"
        const val STR_THUMB= "STR_THUMB"
        const val STR_HEIGHT= "STR_HEIGHT"
        const val STR_WEIGHT = "STR_WEIGHT"
        const val STR_FANART1 = "STR_FANART1"
        const val STR_DATEBORN = "STR_DATEBORN"
        const val STR_DESCRIPTIONEN = "STR_DESCRIPTIONEN"
    }
}
