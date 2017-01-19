package net.dynu.w3rkaut.presentation.ui.fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

import timber.log.Timber;

public class TimerPickerFragment extends DialogFragment implements
        TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Set 30 minutes ahead
        minute += 30;
        if(minute > 59) {
            hour++;
            minute = minute%60;
        }


        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), TimePickerDialog
                .THEME_HOLO_LIGHT, this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Timber.e(String.valueOf(hourOfDay + minute));
    }
}
