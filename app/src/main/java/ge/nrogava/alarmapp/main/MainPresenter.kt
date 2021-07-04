package ge.nrogava.alarmapp.main

import ge.nrogava.alarmapp.data.entity.Alarm

class MainPresenter(val view: IMainView) : IMainPresenter {
    private val interactor = MainInteractor(this)
    fun getAlarmList() {
        interactor.getAlarmsList()
    }

    fun addNewAlarm(alarm: Alarm) {
        interactor.addAlarm(alarm)

    }

    fun deleteAlarm(id: Int) {
        interactor.deleteAlarm(id)
    }

    fun updateAlarm(id: Int, time: String, switch: Boolean) {
        interactor.updateAlarm(id, time, switch)
    }
    override fun setAlarm(alarmId: Int?) {
        if (alarmId != null) {
            interactor.addAlarmForSnooze(alarmId)
        }
    }

    override fun newAlarmAddedForSnooze(result: List<Alarm>) {
        view.setList(result)
    }

    override fun onAlarmListFetched(result: List<Alarm>) {
        view.setList(result)
    }

    override fun newAlarmAdded(result: List<Alarm>) {
        view.setList(result)
    }

    override fun alarmDeleted(result: List<Alarm>) {
        view.setList(result)
    }

    override fun alarmUpdated(result: List<Alarm>) {
        view.setList(result)
    }



}