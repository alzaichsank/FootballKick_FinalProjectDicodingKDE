package alzaichsank.com.aplikasifootbalmatchschedule.view.detailteam.player.detailplayer

import alzaichsank.com.aplikasifootbalmatchschedule.R
import alzaichsank.com.aplikasifootbalmatchschedule.model.PlayerItem
import alzaichsank.com.aplikasifootbalmatchschedule.view.detailteam.presenter.DetailTeamPresenter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_player.*

class DetailPlayerActivity : AppCompatActivity() {
    private val presenter = DetailTeamPresenter()
    private var idTeam = ""
    private lateinit var data: PlayerItem
    companion object {
        const val INTENT_DETAIL_PLAYER = "INTENT_DETAIL_PLAYER"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        data = intent.getParcelableExtra(INTENT_DETAIL_PLAYER)
        initLayout()
        implementPutExtra()

    }
    private fun initLayout() {
        setContentView(R.layout.activity_detail_player)
        setSupportActionBar(toolbar)
        supportActionBar?.title = data.strPlayer

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    private fun implementPutExtra(){
        if (data.strFanart1.toString().toLowerCase().contains("null")) {
            Picasso.with(applicationContext)
                    .load(data.strCutout)
                    .placeholder(R.drawable.ic_no_data)
                    .error(R.drawable.ic_no_data)
                    .resize(200,200)
                    .centerInside()
                    .into(imageBannerPlayer)
        } else {
            Picasso.with(applicationContext)
                    .load(data.strFanart1)
                    .placeholder(R.drawable.ic_no_data)
                    .error(R.drawable.ic_no_data)
                    .centerCrop()
                    .fit()
                    .into(imageBannerPlayer)
        }
        name_player.text = data.strTeam
        team_player.text = data.strNationality
        national_player.text = data.dateBorn
        height_player.text = data.strHeight
        weight_player.text = data.strWeight
        position_Player.text = data.strPosition
        description_player.text = data.strDescriptionEN

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return false
    }

    override fun onBackPressed() {
        finish()
    }
}
