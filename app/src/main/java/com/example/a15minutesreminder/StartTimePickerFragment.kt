package com.example.a15minutesreminder

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import java.util.Calendar
import kotlin.math.min

class StartTimePickerFragment: DialogFragment(), TimePickerDialog.OnTimeSetListener {


    private lateinit var mViewModel: AlarmSettingsViewModel
    private lateinit var calendar: Calendar


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        mViewModel = ViewModelProvider(requireActivity())[AlarmSettingsViewModel::class.java]

        val hour = mViewModel.getStartTime().get(Calendar.HOUR)
        val minute = mViewModel.getStartTime().get(Calendar.MINUTE)

        return TimePickerDialog(activity, this, hour, minute, false)
    }

    override fun onTimeSet(p0: TimePicker?, hour: Int, minute: Int) {

        calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        mViewModel.setTime(AlarmSettings.START_TIME, calendar)
        mViewModel.setStartTimeAtUi(hour, minute)
        val time = "$hour:$minute"
        Log.d("ALARM", "Time selected for start: $time")
        Log.d("ALARM", "The hour is ${mViewModel.getStartTime().get(Calendar.HOUR)} & the minutes is ${mViewModel.getStartTime().get(Calendar.MINUTE)}")
    }


}