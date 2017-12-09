package com.fyt.loki.fyt;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kofigyan.stateprogressbar.StateProgressBar;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CreateAccountPage2 extends Fragment {

    public CreateAccountPage2() {
        // Required empty public constructor
    }

    public static CreateAccountPage2 newInstance() {
        CreateAccountPage2 fragment = new CreateAccountPage2();

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
        final View cap2=inflater.inflate(R.layout.fragment_create_account_page2, container, false);
       //getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN|WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.BASE_URL)+"/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final LoiginInterface loginterface = retrofit.create(LoiginInterface.class);

        final StateProgressBar stateProgressBar = (StateProgressBar)getActivity().findViewById(R.id.stateProgressBar);
        stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
        Button cap3b=(Button)cap2.findViewById(R.id.topage3);
        final TextInputLayout tle = (TextInputLayout) cap2.findViewById(R.id.elay);
        final TextInputLayout tlu = (TextInputLayout) cap2.findViewById(R.id.ulay);
        final TextInputLayout tlf = (TextInputLayout) cap2.findViewById(R.id.flay);
        final TextInputLayout tll = (TextInputLayout) cap2.findViewById(R.id.llay);

        cap3b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText emailedit = (EditText)cap2.findViewById(R.id.emailedit);
                emailedit.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        tle.setError(null);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                final EditText usernameedit = (EditText)cap2.findViewById(R.id.usernameedit);
                usernameedit.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        tlu.setError(null);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                final EditText fnameedit = (EditText)cap2.findViewById(R.id.fnameedit);
                fnameedit.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        tlf.setError(null);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });


                final EditText lnameedit = (EditText)cap2.findViewById(R.id.lnameedit);
                lnameedit.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        tll.setError(null);

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });




                if(emailedit.getText().length()!=0&&usernameedit.getText().length()!=0&&fnameedit.getText().length()!=0&&lnameedit.getText().length()!=0) {
                    CheckEmailBody checkEmailBody = new CheckEmailBody();
                    checkEmailBody.email=emailedit.getText().toString();
                    final AlertDialog dialog = new SpotsDialog(getContext());
                    dialog.show();
                    Call<CheckEmailResponse> call = loginterface.checkEmail(checkEmailBody);
                    call.enqueue(new Callback<CheckEmailResponse>() {
                        @Override
                        public void onResponse(Call<CheckEmailResponse> call, Response<CheckEmailResponse> response) {
                            if(response.isSuccessful())
                            {
                                if(response.body().status==0){
                                    stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                                    CheckUserNameBody checkUserNameBody = new CheckUserNameBody();
                                    checkUserNameBody.username=usernameedit.getText().toString();

                                    Call<CheckUserNameResponse> callu = loginterface.checkUser(checkUserNameBody);
                                    callu.enqueue(new Callback<CheckUserNameResponse>() {
                                        @Override
                                        public void onResponse(Call<CheckUserNameResponse> call, Response<CheckUserNameResponse> response) {
                                            if(response.isSuccessful())
                                            {
                                                if (response.body().status==0){
                                                    stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                                                    getActivity().getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)/*.setCustomAnimations(R.anim.enterfromright,R.anim.exittoleft,R.anim.enterfromleft,R.anim.exittoright)*/.replace(R.id.loginPageContainer,CreateAccountPage4.newInstance(emailedit.getText().toString(),usernameedit.getText().toString(),lnameedit.getText().toString(),fnameedit.getText().toString())).addToBackStack(null).commit();

                                                    //Toast.makeText(getContext(),"Done",Toast.LENGTH_LONG).show();
                                                    dialog.dismiss();}

                                                else
                                                {
                                                   // Toast.makeText(getContext(), "This username already registered", Toast.LENGTH_LONG).show();
                                                    tlu.setError("This username already registered");
                                                    dialog.dismiss();
                                                }

                                            }
                                            else {
                                                Toast.makeText(getContext(),"Incorrect username",Toast.LENGTH_LONG).show();
                                                tlu.setError("Incorrect username");
                                                dialog.dismiss();
                                            }


                                        }

                                        @Override
                                        public void onFailure(Call<CheckUserNameResponse> call, Throwable t) {
                                            Toast.makeText(getContext(),"unexpected error",Toast.LENGTH_LONG).show();
                                            dialog.dismiss();

                                        }
                                    });




                                }
                                else {
                                   // Toast.makeText(getContext(), "This email already registered", Toast.LENGTH_LONG).show();
                                    tle.setError("This email already registered");
                                    dialog.dismiss();

                                }
                            }
                            else{
                               // Toast.makeText(getContext(),"Incorrect email",Toast.LENGTH_LONG).show();
                                tle.setError("Invalid email");
                                dialog.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<CheckEmailResponse> call, Throwable t) {
                            Toast.makeText(getContext(),"unexpected error",Toast.LENGTH_LONG).show();
                            dialog.dismiss();

                        }
                    });





                }

                else {
                 //   Toast.makeText(getContext(), "Enter email", Toast.LENGTH_LONG).show();

                    if (emailedit.getText().length()==0) tle.setError("Email is required");
                    if (usernameedit.getText().length()==0) tlu.setError("Username is required");
                    if (fnameedit.getText().length()==0) tlf.setError("First name is required");
                    if (lnameedit.getText().length()==0) tll.setError("Last name is required");

                }
            }
        });

        return cap2;
    }

    /*public String GetCountryZipCode(){
        String CountryID="";
        String CountryZipCode="";

        TelephonyManager manager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        //getNetworkCountryIso
        CountryID= manager.getSimCountryIso().toUpperCase();
        String[] rl=this.getResources().getStringArray(R.array.CountryCodes);
        for(int i=0;i<rl.length;i++){
            String[] g=rl[i].split(",");
            if(g[1].trim().equals(CountryID.trim())){
                CountryZipCode=g[0];
                break;
            }
        }
        return CountryZipCode;
    }*/

}
