package ge.nrogava.alarmapp.main

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import ge.nrogava.alarmapp.R
import ge.nrogava.alarmapp.data.entity.Alarm
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.*
lateinit var TIMET:Date

class MainActivity : AppCompatActivity(), MainInteractor.AlarmListListener, IMainView
   {
    lateinit var btn_add: Button
    lateinit var txt_light: TextView
    lateinit var alarmList: RecyclerView
    private lateinit var presenter: MainPresenter
    private var adapter = AlarmAdapter(this)


    lateinit var notificationManager: NotificationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()

        presenter = MainPresenter(this)
        presenter.getAlarmList()

        setTheme()


    }

    private fun setTheme() {
        when (this?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {
                txt_light.text = LIGHT
                btn_add.setBackgroundResource(R.drawable.white_add)
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                txt_light.text = DARK
                btn_add.setBackgroundResource(R.drawable.add)
            }
            Configuration.UI_MODE_NIGHT_UNDEFINED -> {
                txt_light.text = DARK
                btn_add.setBackgroundResource(R.drawable.add)
            }
        }
    }


    fun initView() {
        btn_add = findViewById(R.id.btn_add)
        txt_light = findViewById(R.id.txt_light)
        alarmList = findViewById(R.id.alarm_list)
        alarmList.adapter = adapter

        btn_add.setOnClickListener {
            TimePickerFragment(presenter, this).show(supportFragmentManager, "timePicker")
        }
        txt_light.setOnClickListener {
            changeTheme()
        }

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    }


    private fun changeTheme() {
        val txt = txt_light.text

        if (txt == DARK) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else if (txt == LIGHT) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    override fun onAlarmClicked(alarm: Alarm): Boolean {
        DialogDemoFragment(presenter, alarm.id).show(
            supportFragmentManager,
            DialogDemoFragment.FRAGMENT_TAG
        )
        return true
    }

    override fun onAlarmSwitchClicked(alarm: Alarm) {
        presenter.updateAlarm(alarm.id, alarm.Time, !alarm.Active)
    }

    override fun setList(result: List<Alarm>) {
        adapter.list = result
        adapter.notifyDataSetChanged()
    }

    private fun setAlarm(time: Date) {

        val cal = getInstance()
        cal.time = time

        val intent1 = Intent(applicationContext, MyBroadcastReceiver::class.java)
        TIMET = time
        intent1.putExtra("time", SimpleDateFormat("HH:mm").format(time))
        val pendIntent = PendingIntent.getBroadcast(applicationContext, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT)
        val manager = getSystemService(ALARM_SERVICE) as AlarmManager
        manager.set(AlarmManager.RTC_WAKEUP, cal.timeInMillis, pendIntent)


    }

    class TimePickerFragment(val presenter: MainPresenter, val mainActivity: MainActivity) :
        DialogFragment(), TimePickerDialog.OnTimeSetListener {

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val c = getInstance()
            val hour = c.get(HOUR_OF_DAY)
            val minute = c.get(MINUTE)

            return TimePickerDialog(
                activity,
                this,
                hour,
                minute,
                DateFormat.is24HourFormat(activity)
            )
        }

        override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
            val cal = getInstance()
            val c = getInstance()
            cal.set(HOUR_OF_DAY, hourOfDay)
            cal.set(MINUTE, minute)
            if (cal.time >= c.time) {
                presenter.addNewAlarm(Alarm(SimpleDateFormat("HH:mm").format(cal.time), true))
                mainActivity.setAlarm(cal.time)
            } else {
                Toast.makeText(mainActivity, "Invalid Time", Toast.LENGTH_LONG).show();
            }
        }
    }

    class DialogDemoFragment(val presenter: MainPresenter, val alarmid: Int) : DialogFragment() {
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {


            var dialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
                .setMessage(ALERT_MESSAGE)
                .setCancelable(false)
                .setPositiveButton(YES_BTN) { dialog, which ->
                    presenter.deleteAlarm(alarmid)
                }
                .setNegativeButton(NO_BTN) { dialog, which ->

                }
                .create()
            return dialog
        }

        companion object {
            const val FRAGMENT_TAG = "dialogTag"
            val ALERT_MESSAGE = "Are you sure you want to delete this item"
        }
    }


    companion object {

        val YES_BTN = "YES"
        val NO_BTN = "NO"
        val DARK = "Switch to dark"
        val LIGHT = "Switch to light"
        const val ALARM_Message = "Alarm message!"
        const val ALARM_SET_ON = "Alarm set on "
        const val ALARM_REQUEST_CODE = 200


    }


}