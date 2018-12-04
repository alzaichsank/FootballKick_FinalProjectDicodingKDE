package alzaichsank.com.aplikasifootbalmatchschedule.helper

import alzaichsank.com.aplikasifootbalmatchschedule.model.EventsItem
import alzaichsank.com.aplikasifootbalmatchschedule.model.PlayerItem
import alzaichsank.com.aplikasifootbalmatchschedule.model.TeamsListItem
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class FavoritesDbOpenHelper(context: Context) : ManagedSQLiteOpenHelper(context, "Favorites.db", null, 1) {

    companion object {
        private var instance: FavoritesDbOpenHelper? = null
        @Synchronized
        fun getInstance(context: Context): FavoritesDbOpenHelper {
            if (instance == null) {
                instance = FavoritesDbOpenHelper(context.applicationContext)
            }
            return instance as FavoritesDbOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        //matches
        db.createTable(EventsItem.TABLE_FAVORITES, true,
                EventsItem.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                EventsItem.ID_EVENT to TEXT,
                EventsItem.DATE to TEXT,
                EventsItem.STR_SPORT to TEXT,
                EventsItem.STR_TIME to TEXT,

                // home team
                EventsItem.HOME_ID to TEXT,
                EventsItem.HOME_TEAM to TEXT,
                EventsItem.HOME_SCORE to TEXT,
                EventsItem.HOME_FORMATION to TEXT,
                EventsItem.HOME_GOAL_DETAILS to TEXT,
                EventsItem.HOME_SHOTS to TEXT,
                EventsItem.HOME_LINEUP_GOALKEEPER to TEXT,
                EventsItem.HOME_LINEUP_DEFENSE to TEXT,
                EventsItem.HOME_LINEUP_MIDFIELD to TEXT,
                EventsItem.HOME_LINEUP_FORWARD to TEXT,
                EventsItem.HOME_LINEUP_SUBSTITUTES to TEXT,

                // away team
                EventsItem.AWAY_ID to TEXT,
                EventsItem.AWAY_TEAM to TEXT,
                EventsItem.AWAY_SCORE to TEXT,
                EventsItem.AWAY_FORMATION to TEXT,
                EventsItem.AWAY_GOAL_DETAILS to TEXT,
                EventsItem.AWAY_SHOTS to TEXT,
                EventsItem.AWAY_LINEUP_GOALKEEPER to TEXT,
                EventsItem.AWAY_LINEUP_DEFENSE to TEXT,
                EventsItem.AWAY_LINEUP_MIDFIELD to TEXT,
                EventsItem.AWAY_LINEUP_FORWARD to TEXT,
                EventsItem.AWAY_LINEUP_SUBSTITUTES to TEXT)
        //team
        db.createTable(TeamsListItem.TABLE_FAVORITES_TEAM, true,
                TeamsListItem.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                TeamsListItem.ID_TEAM to TEXT,
                TeamsListItem.STR_TEAMBADGE to TEXT,
                TeamsListItem.STR_TEAM to TEXT,
                TeamsListItem.STR_MANAGER to TEXT,
                TeamsListItem.STR_STADIUM to TEXT,
                TeamsListItem.STR_TEAMFANART1 to TEXT,
                TeamsListItem.STR_FORMED_YEAR to TEXT,
                TeamsListItem.STR_DESCRIPTIONEN to TEXT
                )

        //player
        db.createTable(PlayerItem.TABLE_PLAYER, true,
                PlayerItem.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                PlayerItem.ID_PLAYER to TEXT,
                PlayerItem.ID_TEAM to TEXT,
                PlayerItem.STR_PLAYER to TEXT,
                PlayerItem.STR_TEAM to TEXT,
                PlayerItem.STR_NATIONALITY to TEXT,
                PlayerItem.STR_CUTOUT to TEXT,
                PlayerItem.STR_POSITION to TEXT,
                PlayerItem.STR_THUMB to TEXT,
                PlayerItem.STR_HEIGHT to TEXT,
                PlayerItem.STR_WEIGHT to TEXT,
                PlayerItem.STR_FANART1 to TEXT,
                PlayerItem.STR_DATEBORN to TEXT,
                PlayerItem.STR_DESCRIPTIONEN to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(EventsItem.TABLE_FAVORITES, true)
        db.dropTable(TeamsListItem.TABLE_FAVORITES_TEAM, true)
        db.dropTable(PlayerItem.TABLE_PLAYER, true)
    }
}

val Context.database: FavoritesDbOpenHelper
    get() = FavoritesDbOpenHelper.getInstance(applicationContext)