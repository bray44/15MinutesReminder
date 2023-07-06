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

        val hour = mViewModel.getStopTime().get(Calendar.HOUR)
        val minute = mViewModel.getStopTime().get(Calendar.MINUTE)

        return TimePickerDialog(activity, this, hour, minute, false)
    }

    override fun onTimeSet(p0: TimePicker?, hour: Int, minute: Int) {

        timeChosen = Calendar.getInstance().apply {
            set(Calendar.HOUR, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }


        mViewModel.setTime(AlarmSettings.STOP_TIME, timeChosen)
        mViewModel.setStopTimeAtUi(hour, minute)
        val time = "$hour:$minute"
        Log.d("ALARM", "Time selected for stop: $time")
        Log.d("ALARM", "The hour is ${mViewModel.getStartTime().get(Calendar.HOUR)} & the minutes is ${mViewModel.getStartTime().get(Calendar.MINUTE)}")
    }


}