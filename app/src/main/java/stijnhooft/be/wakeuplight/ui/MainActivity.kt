package stijnhooft.be.wakeuplight.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import stijnhooft.be.wakeuplight.R
import stijnhooft.be.wakeuplight.ui.alarm.AlarmViewAdapter
import stijnhooft.be.wakeuplight.ui.alarmbottomsheet.CreateAlarmBottomSheet
import stijnhooft.be.wakeuplight.ui.viewmodel.AlarmViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var alarmRecyclerView: RecyclerView
    private lateinit var alarmsLayoutManager: RecyclerView.LayoutManager
    private lateinit var alarmViewModel: AlarmViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        initCreateAlarm()
        initAlarmView()
    }

    private fun initCreateAlarm() {
        val createAlarmBottomSheet =
            CreateAlarmBottomSheet()

        add.setOnClickListener { showCreateAlarmBottomSheet(createAlarmBottomSheet) }
        createAlarmBottomSheet.setSaveListener { alarm -> alarmViewModel.create(alarm) }
    }

    private fun showCreateAlarmBottomSheet(modalBottomSheet: CreateAlarmBottomSheet) {
        val bundle = Bundle()
        val myMessage = "Stack Overflow is cool!"
        bundle.putString("message", myMessage)
        modalBottomSheet.arguments = bundle
        modalBottomSheet.show(supportFragmentManager, CreateAlarmBottomSheet.TAG)
    }

    private fun initAlarmView() {
        alarmViewModel = ViewModelProvider(this).get(AlarmViewModel::class.java)

        alarmsLayoutManager = LinearLayoutManager(this)
        val alarmViewAdapter = AlarmViewAdapter(this, alarmViewModel)
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

}
