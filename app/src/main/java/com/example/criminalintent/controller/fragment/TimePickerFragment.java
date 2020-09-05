package com.example.criminalintent.controller.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.criminalintent.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimePickerFragment extends DialogFragment {

    private Date mCurrentDate;
    private TimePicker mTimePicker;
    public static final String ARG_DATE = "argDate";
    public static final String EXTRA_USER_SELECTED_TIME =
            "com.example.criminalintent.controller.fragment.userSelectedDate";

    private int hour;
    private  int minute;
    private  int second;
    public TimePickerFragment() {
        // Required empty public constructor
    }


    public static TimePickerFragment newInstance(Date currentDate) {
        TimePickerFragment fragment = new TimePickerFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, currentDate);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCurrentDate = (Date) getArguments().getSerializable(ARG_DATE);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_time_picker, null);

        findViews(view);
        initTimePicker();
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.date_picker_title)
                .setIcon(R.mipmap.ic_launcher)
                .setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Date datePicked = getSelectedDateFromDatePicker();
                        setResult(datePicked);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create();
    }

    private void findViews(View view) {
        mTimePicker = view.findViewById(R.id.time_picker_crime);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initTimePicker() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mCurrentDate);
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        mTimePicker.setHour(hour);
        mTimePicker.setMinute(minute);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private Date getSelectedDateFromDatePicker() {
        hour = mTimePicker.getHour();
        minute = mTimePicker.getMinute();
        // TimePiker have not second???
//        second=mTimePicker.getSecond();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mCurrentDate);
        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        GregorianCalendar gregorianCalendar = new GregorianCalendar(year, monthOfYear, dayOfMonth,hour,minute);
        return gregorianCalendar.getTime();
    }
    private void setResult(Date userSelectedDate) {

        Fragment fragment = getTargetFragment();
        Intent intent = new Intent();
        intent.putExtra(EXTRA_USER_SELECTED_TIME, userSelectedDate);
        fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
    }
}