package ge.nrogava.alarmapp.main

import ge.nrogava.alarmapp.data.entity.Alarm

interface IMainPresenter {
    abstract fun onAlarmListFetched(result: List<Alarm>)
    abstract fun newAlarmAdded(result: List<Alarm>)
    abstract fun alarmDeleted(result: List<Alarm>)
    abstract fun alarmUpdated(result: List<Alarm>)
    abstract fun setAlarm(alarmId: Int?)
    abstract fun newAlarmAddedForSnooze(result: List<Alarm>)
}