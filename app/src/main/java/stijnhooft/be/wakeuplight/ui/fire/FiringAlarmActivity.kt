package stijnhooft.be.wakeuplight.ui.fire

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import stijnhooft.be.wakeuplight.R
import stijnhooft.be.wakeuplight.backend.LightManager

class FiringAlarmActivity : AppCompatActivity() {

    private lateinit var spotifyPlayer: SpotifyPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        // set up class
        super.onCreate(savedInstanceState)
        spotifyPlayer = SpotifyPlayer(this)

        // set up ui
        setContentView(R.layout.activity_firing_alarm)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        hideUI()
        allowTheActivityToBeVisibleOnTheLockScreen()
        initTurnOffAlarmButton()
        initToggleLightButton()

        // ring the alarm
        spotifyPlayer.play()
    }

    override fun onDestroy() {
        super.onDestroy()
        spotifyPlayer.stop()
    }

    private fun initTurnOffAlarmButton() {
        findViewById<FloatingActionButton>(R.id.turn_off_alarm).setOnClickListener{
            finish()
        }
    }

    private fun hideUI() {
        supportActionBar?.hide()

        val fullScreenContentText = findViewById<TextView>(R.id.clock)
        fullScreenContentText.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LOW_PROFILE or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }

    private fun allowTheActivityToBeVisibleOnTheLockScreen() {
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)
    }

    private fun initToggleLightButton() {
        toggleLight.setOnClickListener {
            try {
                LightManager.INSTANCE.toggle()
            } catch (e: Exception) {
                Log.e("FiringAlarmActivity","Could not toggle light.", e)
            }
        }
    }
}
