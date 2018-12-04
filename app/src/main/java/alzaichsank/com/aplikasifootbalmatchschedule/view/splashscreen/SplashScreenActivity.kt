package alzaichsank.com.aplikasifootbalmatchschedule.view.splashscreen

import alzaichsank.com.aplikasifootbalmatchschedule.R
import alzaichsank.com.aplikasifootbalmatchschedule.view.match.activitiy.MatchActivity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import org.jetbrains.anko.startActivity

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLayout()
        goToMain()
    }

    private fun initLayout(){
        setContentView(R.layout.activity_splash_screen)
    }

    private fun goToMain() {
        Handler().postDelayed({
            startActivity<MatchActivity>()
            finish()
        }, 2500)

    }

}
