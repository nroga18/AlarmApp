package ge.nrogava.alarmapp

import android.app.Application
import ge.nrogava.alarmapp.data.AlarmDatabase

class MVPAlarmApplication() : Application(){
    override fun onCreate() {
        super.onCreate()
        AlarmDatabase.createDatabase(this)
    }

}