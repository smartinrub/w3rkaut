package net.dynu.w3rkaut.presentation.ui.fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;

public class ButtonAddLocationFragment extends DialogFragment {

    private Context context;
    private TimePickerDialog.OnTimeSetListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

        try {
            listener = (TimePickerDialog.OnTimeSetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement" +
                    " OnTimeSetListener");
        }

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(context, TimePickerDialog
                .THEME_HOLO_LIGHT, listener, 0, 30,
                DateFormat.is24HourFormat(getActivity()));
    }

}
