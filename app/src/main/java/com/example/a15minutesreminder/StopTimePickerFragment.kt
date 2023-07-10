package com.example.a15minutesreminder

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import java.util.Calendar

class StopTimePickerFragment: DialogFragment(), TimePickerDialog.OnTimeSetListener {


    private lateinit var mViewModel: AlarmSettingsViewModel
    private lateinit var timeChosen: Calendar


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        mViewModel = ViewModelProvider(requireActivity())[AlarmSettingsViewModel::class.java]

        var hour = 0
        var minute = 0

        mViewModel.getAlarmSettingsLiveData().observe(this) {
            hour = it.stopTimeAtUi.substring(0,1).toInt()
            minute = it.stopTimeAtUi.substring(3,4).toInt()

        }


        return TimePickerDialog(activity, this, hour, minute, false)
    }

    override fun onTimeSet(p0: TimePicker?, hour: Int, minute: Int) {

        timeChosen = Calendar.getInstance().apply {
            set(Calendar.HOUR, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }


        mViewModel.updateAlarmSettings(AlarmSettings.STOP_TIME, timeChosen.timeInMillis + 43200000) // the number needed because somehow AM-PM is rverse
        mViewModel.updateTimeAtUi(AlarmSettings.STOP_TIME, hour, minute)

    }


}