package ge.nrogava.alarmapp.main

import android.os.AsyncTask
import ge.nrogava.alarmapp.data.AlarmDatabase
import ge.nrogava.alarmapp.data.entity.Alarm
import ge.nrogava.alarmapp.data.entity.dao.AlarmDao
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*

class MainInteractor(val presenter: IMainPresenter) {
    interface AlarmListListener {
        fun onAlarmClicked(alarm: Alarm) : Boolean
        abstract fun onAlarmSwitchClicked(alarm: Alarm)
    }
    fun getAlarmsList() {
        GetAlarms(presenter).execute()
    }

    fun addAlarm(alarm: Alarm) {
        addAlarm(presenter, alarm).execute()
    }
    fun addAlarmForSnooze(id: Int) {
        addAlarmForSnooze(presenter, id).execute()
    }

    fun deleteAlarm(id: Int) {
        deleteAlarm(presenter, id).execute()
    }

    fun updateAlarm(id: Int, time: String, switch: Boolean) {
        updateAlarm(presenter, id, time, switch).execute()
    }
    class addAlarmForSnooze(val presenter: IMainPresenter, val id: Int) :
        AsyncTask<Void, Void, List<Alarm>>() {
        override fun doInBackground(vararg params: Void?): List<Alarm> {

            var alarmDao = AlarmDatabase.getInstance().alarmDao()
            var alarm = alarmDao.getAlarmDetails(id)
            val t = SimpleDateFormat("HH:mm").parse(alarm.Time)
            val cal = Calendar.getInstance()
            cal.time = t
            cal.add(Calendar.MINUTE, 1)
            var newAlarm = Alarm(SimpleDateFormat("HH:mm").format(cal.time), true)
            alarmDao.addAlarm(newAlarm)

            var alarmList = alarmDao.getAlarmList()
            return alarmList
        }

        override fun onPostExecute(result: List<Alarm>) {
            super.onPostExecute(result)
            if (result != null) {
                presenter.newAlarmAddedForSnooze(result)
            }
        }
    }
    class updateAlarm(
        val presenter: IMainPresenter,
        val id: Int,
        val time: String,
        val switch: Boolean
    ) :
        AsyncTask<Void, Void, List<Alarm>>() {
        override fun doInBackground(vararg params: Void?): List<Alarm> {

            var alarmDao = AlarmDatabase.getInstance().alarmDao()
            var alarmList1 = alarmDao.getAlarmList()
            alarmDao.updateAlarm(id, time, switch)
            var alarmList = alarmDao.getAlarmList()
            return alarmList
        }

        override fun onPostExecute(result: List<Alarm>) {
            super.onPostExecute(result)
            if (result != null) {
                presenter.alarmUpdated(result)
            }
        }
    }

    class deleteAlarm(val presenter: IMainPresenter, val id: Int) :
        AsyncTask<Void, Void, List<Alarm>>() {
        override fun doInBackground(vararg params: Void?): List<Alarm> {

            var alarmDao = AlarmDatabase.getInstance().alarmDao()
            alarmDao.deleteAlarm(id)
            var alarmList = alarmDao.getAlarmList()
            return alarmList
        }

        override fun onPostExecute(result: List<Alarm>) {
            super.onPostExecute(result)
            if (result != null) {
                presenter.alarmDeleted(result)
            }
        }
    }


    class addAlarm(val presenter: IMainPresenter, val alarm: Alarm) :
        AsyncTask<Void, Void, List<Alarm>>() {
        override fun doInBackground(vararg params: Void?): List<Alarm> {

            var alarmDao = AlarmDatabase.getInstance().alarmDao()
            alarmDao.addAlarm(alarm)
            var alarmList = alarmDao.getAlarmList()
            return alarmList
        }

        override fun onPostExecute(result: List<Alarm>) {
            super.onPostExecute(result)
            if (result != null) {
                presenter.newAlarmAdded(result)
            }
        }
    }

    class GetAlarms(val presenter: IMainPresenter) : AsyncTask<Void, Void, List<Alarm>>() {
        override fun doInBackground(vararg params: Void?): List<Alarm> {
            var alarmDao = AlarmDatabase.getInstance().alarmDao()
           // initializeSomeData(alarmDao)
            var alarmList = alarmDao.getAlarmList()
            return alarmList
        }

        override fun onPostExecute(result: List<Alarm>?) {
            super.onPostExecute(result)
            if (result != null) {
                presenter.onAlarmListFetched(result)
            }
        }

        fun initializeSomeData(alarmDao: AlarmDao) {
            alarmDao.deleteAll()
            alarmDao.addAlarm(Alarm("123", false))
            alarmDao.addAlarm(Alarm("456", true))
            alarmDao.addAlarm(Alarm("789", false))
        }
    }

}