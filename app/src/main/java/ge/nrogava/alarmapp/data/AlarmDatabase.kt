package ge.nrogava.alarmapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ge.nrogava.alarmapp.data.entity.Alarm
import ge.nrogava.alarmapp.data.entity.dao.AlarmDao

@Database(entities = arrayOf(Alarm::class), version = 1)
abstract class AlarmDatabase() : RoomDatabase() {
    abstract fun alarmDao(): AlarmDao

    companion object {
        private val dbName = "alarm-db"

        private lateinit var INSTANCE: AlarmDatabase
        fun getInstance(): AlarmDatabase{
            return INSTANCE
        }
        fun createDatabase(context: Context) {
            INSTANCE = Room.databaseBuilder(context, AlarmDatabase::class.java, dbName).build()
        }
    }
}