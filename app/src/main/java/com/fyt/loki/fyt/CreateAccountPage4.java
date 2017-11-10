package com.fyt.loki.fyt;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class CreateAccountPage4 extends Fragment {

    Calendar mCalendar = Calendar.getInstance();

    EditText mEditText ;

    DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH,month);
            mCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            updateLabel();
        }
    };

    private void updateLabel(){
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mEditText.setText(sdf.format(mCalendar.getTime()));
    }

    public CreateAccountPage4() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CreateAccountPage4 newInstance() {
        CreateAccountPage4 fragment = new CreateAccountPage4();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View cap4 = inflater.inflate(R.layout.fragment_create_account_page4, container, false);

        mEditText = (EditText)cap4.findViewById(R.id.birthday);
        mEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(),date,mCalendar.get(Calendar.YEAR),mCalendar.get(Calendar.MONTH),mCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        return cap4;
    }

}
