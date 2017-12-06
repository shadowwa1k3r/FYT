package com.fyt.loki.fyt;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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


public class CreateAccountPage3 extends Fragment {

    private String email,username,lname,fname,bdate;
    private int gender;
    private static String ekey="email",ukey="user",lkey="last",fkey="first",gkey="gender",bkey="birth";

    public CreateAccountPage3() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CreateAccountPage3 newInstance( String p1,String p2,String p3,String p4,String p5,int p6) {
        CreateAccountPage3 fragment = new CreateAccountPage3();
        Bundle args = new Bundle();
        args.putString(ekey,p1);
        args.putString(ukey,p2);
        args.putString(fkey,p3);
        args.putString(lkey,p4);
        args.putString(bkey,p5);
        args.putInt(gkey,p6);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            email=getArguments().getString(ekey);
            username=getArguments().getString(ukey);
            fname=getArguments().getString(fkey);
            lname=getArguments().getString(lkey);
            gender=getArguments().getInt(gkey);
            bdate=getArguments().getString(bkey);

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View cap3=inflater.inflate(R.layout.fragment_create_account_page3, container, false);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.BASE_URL)+"/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final LoiginInterface loginterface = retrofit.create(LoiginInterface.class);
        final EditText pass = (EditText)cap3.findViewById(R.id.pass_edit);
        final EditText repass = (EditText)cap3.findViewById(R.id.repass);

        Button tcap4 = (Button)cap3.findViewById(R.id.finish);
        final StateProgressBar stateProgressBar = (StateProgressBar)getActivity().findViewById(R.id.stateProgressBar);
        stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FIVE);


        tcap4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(pass.getText().length()!=0&&repass.getText().length()!=0){
                    SignUpBody signUpBody = new SignUpBody();
                    signUpBody.email=email;
                    signUpBody.username=username;
                    signUpBody.first_name=fname;
                    signUpBody.last_name=lname;
                    signUpBody.gender=gender;
                    signUpBody.birth_date=bdate;
                    signUpBody.password=pass.getText().toString();
                    final AlertDialog dialog = new SpotsDialog(getContext());
                    dialog.show();
                    Call<SignUpResponse> call = loginterface.signUpUser(signUpBody);
                    call.enqueue(new Callback<SignUpResponse>() {
                        @Override
                        public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                            if(response.isSuccessful())
                            {
                                if (response.code()==200){
                             //  Toast.makeText(getContext(),"DOne",Toast.LENGTH_LONG).show();

                                    RegistrationBody body = new RegistrationBody();
                                    body.username=username;
                                    body.password=pass.getText().toString();

                                    Call<RegistrationResponse>call2=loginterface.registerUser(body);
                                    call2.enqueue(new Callback<RegistrationResponse>() {
                                        @Override
                                        public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                                            if(response.isSuccessful()){
                                                Intent intent = new Intent();
                                                intent.setClass(getContext(),MainPage.class);

                                                Bundle bundle = new Bundle();
                                                bundle.putString("Token",response.body().token);
                                                bundle.putString("UName",response.body().username);
                                                intent.putExtras(bundle);
                                                dialog.dismiss();
                                                try{
                                                    startActivity(intent);
                                                }
                                                catch (Exception e){
                                                    Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
                                                }

                                            }
                                            else {
                                                Toast.makeText(getContext(),"LoginFail",Toast.LENGTH_LONG).show();
                                                dialog.dismiss();

                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                                           // Toast.makeText(getContext(),"Connection Error",Toast.LENGTH_LONG).show();
                                            dialog.dismiss();
                                            if(isOnline())
                                            {
                                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                                builder.setTitle("Error")
                                                        .setMessage("Server is not reachable at this moment, please try again later.")
                                                        .setIcon(R.drawable.warning)
                                                        .setCancelable(false)
                                                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialogInterface, int i) {

                                                            }
                                                        });
                                                AlertDialog alert = builder.create();
                                                alert.show();
                                            }
                                            else {
                                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                                builder.setTitle("Error")
                                                        .setMessage("Network is not available, check your connection.")
                                                        .setIcon(R.drawable.warning)
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
                                    });



                                }

                                else
                                {
                                   // Toast.makeText(getContext(), "Error registering", Toast.LENGTH_LONG).show();
                                    dialog.dismiss();
                                    if(isOnline())
                                    {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                        builder.setTitle("Error")
                                                .setMessage("Server is not reachable at this moment, please try again later.")
                                                .setIcon(R.drawable.warning)
                                                .setCancelable(false)
                                                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {

                                                    }
                                                });
                                        AlertDialog alert = builder.create();
                                        alert.show();
                                    }
                                    else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                        builder.setTitle("Error")
                                                .setMessage("Network is not available, check your connection.")
                                                .setIcon(R.drawable.warning)
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

                            }
                            else {
                               // Toast.makeText(getContext(),"something wrong",Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                                if(isOnline())
                                {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setTitle("Error")
                                            .setMessage("Server is not reachable at this moment, please try again later.")
                                            .setIcon(R.drawable.warning)
                                            .setCancelable(false)
                                            .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {

                                                }
                                            });
                                    AlertDialog alert = builder.create();
                                    alert.show();
                                }
                                else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setTitle("Error")
                                            .setMessage("Network is not available, check your connection.")
                                            .setIcon(R.drawable.warning)
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


                        }

                        @Override
                        public void onFailure(Call<SignUpResponse> call, Throwable t) {
                           // Toast.makeText(getContext(),"unexpected error",Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                            if(isOnline())
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setTitle("Error")
                                        .setMessage("Server is not reachable at this moment, please try again later.")
                                        .setIcon(R.drawable.warning)
                                        .setCancelable(false)
                                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setTitle("Error")
                                        .setMessage("Network is not available, check your connection.")
                                        .setIcon(R.drawable.warning)
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
                    });
                }


            }
        });

        return cap3;
    }
    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

}
