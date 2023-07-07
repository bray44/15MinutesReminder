package com.example.a15minutesreminder

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import java.util.Calendar

class StopTimePickerFragment: DialogFragment(), TimePickerDialog.OnTimeSetListener {


    private lateinit var mViewModel: AlarmSettingsViewModel
    private lateinit var timeChosen: Calendar


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        mViewModel = ViewModelProvider(requireActivity())[AlarmSettingsViewModel::class.java]

        val hour = mViewModel.getStartTimeAtUi().value!!.stopTimeAtUi[0]
        val minute = mViewModel.getStartTimeAtUi().value!!.stopTimeAtUi[1]

        return TimePickerDialog(activity, this, hour, minute, false)
    }

    override fun onTimeSet(p0: TimePicker?, hour: Int, minute: Int) {

        timeChosen = Calendar.getInstance().apply {
            set(Calendar.HOUR, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }


        mViewModel.updateAlarmSettings(AlarmSettings.START_TIME, timeChosen.timeInMillis)
        mViewModel.updateStopTimeAtUi(hour, minute)
        val time = "$hour:$minute"
        Log.d("ALARM", "Time selected for stop: $time")
        Log.d("ALARM", "The hour is ${mViewModel.getStartTime()} & the minutes is ${mViewModel.getStartTime()}")
    }


}