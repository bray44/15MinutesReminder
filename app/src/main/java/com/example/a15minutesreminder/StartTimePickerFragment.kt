package com.example.a15minutesreminder

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider

class StartTimePickerFragment(): DialogFragment(), TimePickerDialog.OnTimeSetListener {


    private lateinit var mViewModel: AlarmSettingsViewModel


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        mViewModel = ViewModelProvider(requireActivity())[AlarmSettingsViewModel::class.java]

        val hour = mViewModel.getStartTime()[0]
        val minute = mViewModel.getStartTime()[1]

        return TimePickerDialog(activity, this, hour, minute, false)
    }

    override fun onTimeSet(p0: TimePicker?, hour: Int, minute: Int) {
        mViewModel.setStartTime(mutableListOf(hour, minute))
        val time = "$hour:$minute"
        Log.d("ALARM", "Time selected: $time")
        Log.d("ALARM", "The hour is ${mViewModel.getStartTime()[0]} & the minutes is ${mViewModel.getStartTime()[1]}")
    }
}