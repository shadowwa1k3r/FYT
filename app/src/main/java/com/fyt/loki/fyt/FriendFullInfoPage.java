package com.fyt.loki.fyt;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FriendFullInfoPage extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private ProfileInterface profileInterface;
    private String mToken;
    private String mUserName;
    private String BASE_URL="http://192.168.1.106:8000";
    String BASE_URL_API =BASE_URL+"/api/";

    private TextView tFull_name,tBirth_date,tGender,tCountry,tCity,tEmail,tPhone;
    private CircleImageView iAvatar;
    private myCustomPane panel;



    public FriendFullInfoPage() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FriendFullInfoPage newInstance(String token, String userName) {
        FriendFullInfoPage fragment = new FriendFullInfoPage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, token);
        args.putString(ARG_PARAM2, userName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mToken = getArguments().getString(ARG_PARAM1);
            mUserName = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View Fpage = inflater.inflate(R.layout.fragment_friend_full_info_page, container, false);



        tFull_name = (TextView)Fpage.findViewById(R.id.friend_profile_name);
        tBirth_date = (TextView)Fpage.findViewById(R.id.birth_datef);
        tGender = (TextView)Fpage.findViewById(R.id.genderf);
        tCountry = (TextView)Fpage.findViewById(R.id.countryf);
        tCity = (TextView)Fpage.findViewById(R.id.cityf);
        tEmail = (TextView)Fpage.findViewById(R.id.emailf);
        tPhone = (TextView)Fpage.findViewById(R.id.phonef);
        iAvatar =(CircleImageView) Fpage.findViewById(R.id.friend_profile_photo);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        profileInterface = retrofit.create(ProfileInterface.class);

        final AlertDialog dialog = new SpotsDialog(getContext());
        dialog.show();

        Call<ProfileModel> fprofileInfo = profileInterface.profileInfo(" Token "+mToken,mUserName);
        fprofileInfo.enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {

                if (response.isSuccessful())
                {
                    Glide.with(getActivity()).load(BASE_URL+response.body().getAvatar()).asBitmap().into(iAvatar);

                    tFull_name.setText(response.body().getFull_name());
                    tBirth_date.setText(response.body().getBith_date());
                    tGender.setText(response.body().getGender());
                    tCountry.setText(response.body().getCountry());
                    tCity.setText(response.body().getCity());
                    tEmail.setText(response.body().getEmail());
                    tPhone.setText(response.body().getPhone());
                    dialog.dismiss();
                }
                else
                    dialog.dismiss();


            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {
                dialog.dismiss();

            }
        });



        // Inflate the layout for this fragment
        return Fpage;
    }

}
