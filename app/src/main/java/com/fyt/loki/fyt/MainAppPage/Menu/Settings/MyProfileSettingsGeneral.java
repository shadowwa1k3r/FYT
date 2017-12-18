package com.fyt.loki.fyt.MainAppPage.Menu.Settings;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.fyt.loki.fyt.MainAppPage.ProfilePage.ProfileModel;
import com.fyt.loki.fyt.R;
import com.fyt.loki.fyt.Tools.ProfileInterface;
import com.fyt.loki.fyt.Tools.SharedPreference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyProfileSettingsGeneral extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText curr_pass,new_pass,new_repass,fname,lname,bday,email,mphone;
    private Spinner country,city;
    private Button change_pass,commit_info;
    private SharedPreference mSharedPreference;
    private General_pass_body mBody;
    private String BASE_URL,BASE_URL_API;
    private ProfileInterface profileInterface;
    private General_info_body mInfoBody;



    public MyProfileSettingsGeneral() {
        // Required empty public constructor
    }

    public static MyProfileSettingsGeneral newInstance(String param1, String param2) {
        MyProfileSettingsGeneral fragment = new MyProfileSettingsGeneral();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View General=inflater.inflate(R.layout.fragment_my_profile_settings_general, container, false);

        BASE_URL = getContext().getString(R.string.BASE_URL);
        BASE_URL_API =BASE_URL+"/api/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        profileInterface = retrofit.create(ProfileInterface.class);

        mSharedPreference = new SharedPreference();
        curr_pass=(EditText)General.findViewById(R.id.curr_pass);
        new_pass=(EditText)General.findViewById(R.id.new_pass);
        new_repass=(EditText)General.findViewById(R.id.new_repass);
        change_pass = (Button)General.findViewById(R.id.change_pass);

        fname=(EditText)General.findViewById(R.id.first_name);
        lname=(EditText)General.findViewById(R.id.last_name);
        bday=(EditText)General.findViewById(R.id.bday);
        country=(Spinner)General.findViewById(R.id.country);
        city=(Spinner)General.findViewById(R.id.city);
        email=(EditText)General.findViewById(R.id.email);
        mphone=(EditText)General.findViewById(R.id.mobile_phone);
        commit_info=(Button)General.findViewById(R.id.commitinfo);

        final String[] countries=getResources().getStringArray(R.array.CountryList);
        List<String> bufcountriesArray= Arrays.asList(countries);
        ArrayList<String> countriesArray=new ArrayList<>(bufcountriesArray);

        final String[] cities=getResources().getStringArray(R.array.CityList);
        List<String> bufcitiesArray=Arrays.asList(cities);
        ArrayList<String> citiesArray=new ArrayList<>(bufcitiesArray);

        ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,countriesArray);
        country.setAdapter(countryAdapter);

        ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,citiesArray);
        city.setAdapter(cityAdapter);


        Call<ProfileModel> mprofileInfo = profileInterface.profileInfo(" Token "+mSharedPreference.getToken(getContext()),mSharedPreference.getUserName(getContext()));
        mprofileInfo.enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                if(response.isSuccessful()){
                    fname.setText(response.body().getFirstName());
                    lname.setText(response.body().getLastName());
                    bday.setText(response.body().getProfile().getBirthDate());
                    for (int i = 0; i <country.getCount() ; i++) {
                        if (country.getItemAtPosition(i).equals(response.body().getProfile().getCountry())){
                            country.setSelection(i);
                            break;
                        }
                        else country.setSelection(0);

                    }
                    for (int i = 0; i <city.getCount() ; i++) {
                        if (city.getItemAtPosition(i).equals(response.body().getProfile().getCity())){
                            city.setSelection(i);
                            break;
                        }
                        else city.setSelection(0);

                    }
                    email.setText(response.body().getEmail());
                    mphone.setText(response.body().getProfile().getPhone());
                }
            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {

            }
        });




        commit_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInfoBody=new General_info_body();
                mInfoBody.first_name=fname.getText().toString();
                mInfoBody.last_name=lname.getText().toString();
                mInfoBody.profile.birthday=bday.getText().toString();
                mInfoBody.profile.country=country.getSelectedItem().toString();
                mInfoBody.profile.city=city.getSelectedItem().toString();
                mInfoBody.email=email.getText().toString();
                mInfoBody.profile.phone=mphone.getText().toString();

            }
        });

        change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!curr_pass.getText().toString().equals(mSharedPreference.getPassword(getContext()))){

                    curr_pass.setError("Wrong current password");

                }
                else if(new_pass.getText().toString().equals("")){
                    new_pass.setError("Enter new password");
                }
                else if(!new_repass.getText().toString().equals(new_pass.getText().toString())){
                    new_repass.setError("Confirm your new password");
                }
                else {
                    mBody= new General_pass_body();
                    mBody.current_password=curr_pass.getText().toString();
                    mBody.new_password=new_pass.getText().toString();

                    Call<General_pass_response> changepassword = profileInterface.change_password(" Token "+mSharedPreference.getToken(getContext()),mBody);
                    changepassword.enqueue(new Callback<General_pass_response>() {
                        @Override
                        public void onResponse(Call<General_pass_response> call, Response<General_pass_response> response) {
                            if (response.isSuccessful()) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setTitle("Successful")
                                        .setMessage("Done.")
                                        .setIcon(R.drawable.succes)
                                        .setCancelable(false)
                                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();
                                mSharedPreference.save(getContext(),mSharedPreference.getUserName(getContext()),new_pass.getText().toString(),mSharedPreference.getToken(getContext()));
                                curr_pass.setText("");
                                new_pass.setText("");
                                new_repass.setText("");

                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setTitle("Error")
                                        .setMessage("Check your entries!")
                                        .setIcon(R.drawable.fail)
                                        .setCancelable(false)
                                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();
                            }

                        }

                        @Override
                        public void onFailure(Call<General_pass_response> call, Throwable t) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("Error")
                                    .setMessage("Server error, please try again later.")
                                    .setIcon(R.drawable.fail)
                                    .setCancelable(false)
                                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();
                        }
                    });


                }
            }
        });

        return General;
    }
}
