package com.example.a15minutesreminder

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import java.util.Calendar

class StartTimePickerFragment: DialogFragment(), TimePickerDialog.OnTimeSetListener {


    private lateinit var mViewModel: AlarmSettingsViewModel
    private lateinit var timeChosen: Calendar



    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        mViewModel = ViewModelProvider(requireActivity())[AlarmSettingsViewModel::class.java]

        var hour = 0
        var minute = 0

        mViewModel.getAlarmSettingsLiveData().observe(this) {
            hour = it.startTimeAtUi.substring(0,1).toInt()
            minute = it.startTimeAtUi.substring(3,4).toInt()

        }


        return TimePickerDialog(activity, this, hour, minute, true)
    }

    override fun onTimeSet(p0: TimePicker?, hour: Int, minute: Int) {

        timeChosen = Calendar.getInstance().apply {
            set(Calendar.HOUR, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        Log.d("ALARM", "The time in on time set is $hour hour & $minute minute")
        Log.d("ALARM", "the time on calendar object is ${timeChosen.get(Calendar.HOUR)} hour & ${timeChosen.get(Calendar.MINUTE)}")


        mViewModel.updateAlarmSettings(AlarmSettings.START_TIME, timeChosen.timeInMillis + 43200000)
        mViewModel.updateTimeAtUi(AlarmSettings.START_TIME, hour, minute)


    }


}