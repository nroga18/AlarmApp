package ge.nrogava.alarmapp.main

import ge.nrogava.alarmapp.data.entity.Alarm

interface IMainView {
    abstract fun setList(result: List<Alarm>)
}