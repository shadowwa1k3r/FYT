package com.fyt.loki.fyt;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.kofigyan.stateprogressbar.StateProgressBar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CreateAccountPage4 extends Fragment {

    Calendar mCalendar = Calendar.getInstance();

    EditText mEditText ;
    private Button finish;
    private String email,username,last_name,first_name,birth;
    private static String ekey="email",ukey="user",lkey="last",fkey="first";
    private int gender;

    public static CreateAccountPage4 newInstance(String em,String un,String ln,String fn){
        CreateAccountPage4 fragment = new CreateAccountPage4();
        Bundle args = new Bundle();
        args.putString(ekey,em);
        args.putString(ukey,un);
        args.putString(lkey,ln);
        args.putString(fkey,fn);
        fragment.setArguments(args);
        return fragment;
    }

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

        SimpleDateFormat toserv = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
        mEditText.setText(sdf.format(mCalendar.getTime()));
        birth=toserv.format(mCalendar.getTime());

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
        if (getArguments()!=null){
            email=getArguments().getString(ekey);
            username=getArguments().getString(ukey);
            last_name=getArguments().getString(lkey);
            first_name=getArguments().getString(fkey);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View cap4 = inflater.inflate(R.layout.fragment_create_account_page4, container, false);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.BASE_URL)+"/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final LoiginInterface loginterface = retrofit.create(LoiginInterface.class);
        final StateProgressBar stateProgressBar = (StateProgressBar)getActivity().findViewById(R.id.stateProgressBar);
        stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);
        RadioGroup rg = (RadioGroup)cap4.findViewById(R.id.gr);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId==R.id.m){
                    Toast.makeText(getContext(),"male",Toast.LENGTH_LONG).show();
                    gender=0;
                }
                else if(checkedId==R.id.f) {
                    Toast.makeText(getContext(),"female",Toast.LENGTH_LONG).show();
                    gender=1;
                }
            }
        });

        mEditText = (EditText)cap4.findViewById(R.id.birthday);
        mEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(),date,mCalendar.get(Calendar.YEAR),mCalendar.get(Calendar.MONTH),mCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        finish = (Button)cap4.findViewById(R.id.doneButton);

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)/*.setCustomAnimations(R.anim.enterfromright,R.anim.exittoleft,R.anim.enterfromleft,R.anim.exittoright)*/.replace(R.id.loginPageContainer,CreateAccountPage3.newInstance(email,username,first_name,last_name,birth,gender)).addToBackStack(null).commit();

            }
        });





        return cap4;
    }

}
