package com.fyt.loki.fyt;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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
    private Button fb,vk,tw,gp,loginb,signup;
    private String login,password,TokenAuth,username;

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
    private Realm mRealm;


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



    }


    @Override
    public void onAnimationStarted()
    {
        lp2backimg.setImageDrawable(null);
    }

    @Override
    public void onAnimationEnded()
    {
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        mainbg = (ImageView)getActivity().findViewById(R.id.loginBack);
        //Glide.with(getContext()).load(R.drawable.loginpage2).asBitmap().centerCrop().into(mainbg);
        mainbg.setImageResource(R.drawable.loginpage2);
        mainbg.setScaleType(ImageView.ScaleType.CENTER_CROP);


    }

    public void showDialog(View v){
        LoginProcessDialog loginProcessDialog = new LoginProcessDialog();

        loginProcessDialog.show(getActivity().getSupportFragmentManager(),"custom");

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View loginPage2 = inflater.inflate(R.layout.fragment_login_page2, container, false);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.106:8000"+"/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Realm.init(this.getContext());
        mRealm = Realm.getDefaultInstance();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();



        final LoiginInterface loiginInterface = retrofit.create(LoiginInterface.class);

        loginT = (EditText)loginPage2.findViewById(R.id.logintext);
        passwordT = (EditText)loginPage2.findViewById(R.id.passwordtext);

        lp2backimg = (ImageView)loginPage2.findViewById(R.id.lp2backimg);

        signup = (Button)loginPage2.findViewById(R.id.signup);
        loginb = (Button)loginPage2.findViewById(R.id.logInto);
        final StateProgressBar stateProgressBar = (StateProgressBar)getActivity().findViewById(R.id.stateProgressBar);
        stateProgressBar.setVisibility(View.GONE);

        //Glide.with(getContext()).load(R.drawable.loginpage2).asBitmap().centerCrop().into(lp2backimg);

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



                RegistrationBody body = new RegistrationBody();
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


                            TokenAuth = response.body().token;
                            username = response.body().username;
                            getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enterfromright,R.anim.exittoleft,R.anim.enterfromleft,R.anim.exittoright).replace(R.id.loginPageContainer,new ProfilePage().newInstance(TokenAuth,username)).addToBackStack(null).commit();
                            dialog.dismiss();
                        }
                        else {
                            Toast.makeText(getContext(),"fail",Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<RegistrationResponse> call, Throwable t) {

                        Toast.makeText(getContext(),t.toString(),Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                    }
                });

            }
        });



        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               lp2backimg.setImageDrawable(null);
                signup.startAnimation(buttonClick);


                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enterfromright,R.anim.exittoleft,R.anim.enterfromleft,R.anim.exittoright).replace(R.id.loginPageContainer,new LoginPage3()).addToBackStack(null).commit();
            }
        });




        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(null);




        return loginPage2;
    }

}
