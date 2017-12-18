package com.fyt.loki.fyt.Auth;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fyt.loki.fyt.MainAppPage.MainPage;
import com.fyt.loki.fyt.R;
import com.fyt.loki.fyt.Tools.AnimListener;
import com.fyt.loki.fyt.Tools.LoginProcessDialog;
import com.fyt.loki.fyt.Tools.LoiginInterface;
import com.fyt.loki.fyt.Tools.SharedPreference;
import com.kofigyan.stateprogressbar.StateProgressBar;

import dmax.dialog.SpotsDialog;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginPage2 extends AnimListener {

    private ImageView lp2backimg,mainbg;
    Toolbar tb;
    private EditText loginT,passwordT;
    private TextView tbtext;
    private Button fb,vk,tw,gp,loginb,signup;
    private String login,password,TokenAuth,username;

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
    private Realm mRealm;
    private SharedPreference mSharedPreference;


    public LoginPage2() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static LoginPage2 newInstance() {
        LoginPage2 fragment = new LoginPage2();

        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StateProgressBar stateProgressBar = (StateProgressBar) getActivity().findViewById(R.id.stateProgressBar);
        stateProgressBar.setVisibility(View.GONE);
       // tbtext.setText("Login");



    }


    @Override
    public void onAnimationStarted()
    {
        lp2backimg.setImageDrawable(null);
    }

    @Override
    public void onAnimationEnded()
    {





    }

    public void showDialog(View v){
        LoginProcessDialog loginProcessDialog = new LoginProcessDialog();

        loginProcessDialog.show(getActivity().getSupportFragmentManager(),"custom");

    }
    @Override
    public void onResume(){
        super.onResume();
        StateProgressBar stateProgressBar = (StateProgressBar) getActivity().findViewById(R.id.stateProgressBar);
        stateProgressBar.setVisibility(View.GONE);
        tbtext.setText("Login");
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View loginPage2 = inflater.inflate(R.layout.fragment_login_page2, container, false);

        tbtext = (TextView)getActivity().findViewById(R.id.tbtxt);
        tbtext.setText("Login");

        mSharedPreference=new SharedPreference();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.BASE_URL)+"/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Realm.init(this.getContext());
        mRealm = Realm.getDefaultInstance();

        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        mainbg = (ImageView)getActivity().findViewById(R.id.loginBack);
        //Glide.with(getContext()).load(R.drawable.loginpage2).asBitmap().centerCrop().into(mainbg);
        mainbg.setImageResource(R.drawable.loginpage2);
        mainbg.setScaleType(ImageView.ScaleType.CENTER_CROP);





        final LoiginInterface loiginInterface = retrofit.create(LoiginInterface.class);

        loginT = (EditText)loginPage2.findViewById(R.id.logintext);
        passwordT = (EditText)loginPage2.findViewById(R.id.passwordtext);

        lp2backimg = (ImageView)loginPage2.findViewById(R.id.lp2backimg);

        signup = (Button)loginPage2.findViewById(R.id.signup);
        loginb = (Button)loginPage2.findViewById(R.id.logInto);
        final StateProgressBar stateProgressBar = (StateProgressBar)getActivity().findViewById(R.id.stateProgressBar);
        stateProgressBar.setVisibility(View.GONE);

        Glide.with(getContext()).load(R.drawable.loginpage2).asBitmap().centerCrop().into(lp2backimg);

        tb =(Toolbar)getActivity().findViewById(R.id.toolbar);
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });


        loginb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login = loginT.getText().toString();
                password = passwordT.getText().toString();



                final RegistrationBody body = new RegistrationBody();
                body.username = login;
                body.password = password;
                final AlertDialog dialog = new SpotsDialog(getContext());
                dialog.show();
                Call<RegistrationResponse> call = loiginInterface.registerUser(body);
                call.enqueue(new Callback<RegistrationResponse>() {
                    @Override
                    public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                        if(response.isSuccessful()){
                            /*mRealm.beginTransaction();
                            TokenStore tokenStore = mRealm.createObject(TokenStore.class);
                            tokenStore.setToken(response.body().token);
                            mRealm.commitTransaction();*/

                            //Toast.makeText(getContext(), "success",Toast.LENGTH_SHORT).show();
                            //Toast.makeText(getContext(), response.body().token,Toast.LENGTH_SHORT).show();

                            mSharedPreference.save(getContext(),body.username,body.password,response.body().token);


                            TokenAuth = response.body().token;
                            username = response.body().username;

                            //getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enterfromright,R.anim.exittoleft,R.anim.enterfromleft,R.anim.exittoright).replace(R.id.loginPageContainer,new ProfilePage().newInstance(TokenAuth,username)).addToBackStack(null).commit();
                            Intent intent = new Intent();
                            intent.setClass(getContext(), MainPage.class);

                            Bundle bundle = new Bundle();
                            bundle.putString("Token",TokenAuth);
                            bundle.putString("UName",username);
                            intent.putExtras(bundle);
                            dialog.dismiss();

                            try {
                                startActivity(intent);
                                getActivity().finish();
                            }
                            catch (Exception e){
                                Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
                            }


                        }
                        else {
                          //  Toast.makeText(getContext(),"fail",Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("Error")
                                    .setMessage("Wrong login or password, please check your data!")
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

                    @Override
                    public void onFailure(Call<RegistrationResponse> call, Throwable t) {

                        //Toast.makeText(getContext(),t.toString(),Toast.LENGTH_SHORT).show();
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
        });




        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               lp2backimg.setImageDrawable(null);
                signup.startAnimation(buttonClick);

                tbtext.setText("SignUp");
                getActivity().getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).replace(R.id.loginPageContainer,new LoginPage3()).addToBackStack(null).commit();

            }
        });




        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(null);




        return loginPage2;
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
