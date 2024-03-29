package stijnhooft.be.wakeuplight.ui.configure

import android.Manifest.permission.BLUETOOTH_CONNECT
import android.Manifest.permission.BLUETOOTH_SCAN
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import stijnhooft.be.wakeuplight.R
import stijnhooft.be.wakeuplight.backend.LightManager
import stijnhooft.be.wakeuplight.ui.configure.alarm.AlarmViewAdapter


class MainActivity : AppCompatActivity() {

    private lateinit var alarmRecyclerView: RecyclerView
    private lateinit var alarmsLayoutManager: RecyclerView.LayoutManager
    private lateinit var alarmViewModel: AlarmViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        initCreateAlarmButton()
        initToggleLightButton()
        initAlarmView()

        askForBluetoothPermissions()
    }

    private fun askForBluetoothPermissions() {
        val permission1 =
            ActivityCompat.checkSelfPermission(this, BLUETOOTH_CONNECT)
        val permission2 =
            ActivityCompat.checkSelfPermission(this, BLUETOOTH_SCAN)
        if (permission1 != PackageManager.PERMISSION_GRANTED || permission2 != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(BLUETOOTH_CONNECT, BLUETOOTH_SCAN),
                1
            )
        }
    }

    private fun initCreateAlarmButton() {
        val createAlarmBottomSheet = CreateAlarmBottomSheet()
        add.setOnClickListener {
            createAlarmBottomSheet.show(
                supportFragmentManager,
                CreateAlarmBottomSheet.TAG
            )
        }
        createAlarmBottomSheet.setSaveListener { alarm -> alarmViewModel.create(alarm) }
    }

    private fun initAlarmView() {
        alarmViewModel = ViewModelProvider(this).get(AlarmViewModel::class.java)

        alarmsLayoutManager = LinearLayoutManager(this)
        val alarmViewAdapter =
            AlarmViewAdapter(this, alarmViewModel)
        alarmRecyclerView = alarms.apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            layoutManager = alarmsLayoutManager
            adapter = alarmViewAdapter
        }

        val itemTouchHelperCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    alarmViewAdapter.remove(viewHolder.adapterPosition)
                }
            }
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(alarmRecyclerView)
    }

    private fun initToggleLightButton() {
        toggleLight.setOnClickListener {
            try {
                LightManager.INSTANCE.toggle()
            } catch (e: Exception) {
                Log.e("MainActivity", "Could not toggle light.", e)
            }
        }
    }

}
