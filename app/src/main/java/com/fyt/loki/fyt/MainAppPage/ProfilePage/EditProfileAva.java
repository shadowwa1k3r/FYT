package com.fyt.loki.fyt.MainAppPage.ProfilePage;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fyt.loki.fyt.R;
import com.fyt.loki.fyt.Tools.ProfileInterface;
import com.fyt.loki.fyt.Tools.ProgressRequestBody;
import com.fyt.loki.fyt.Tools.SharedPreference;
import com.kbeanie.multipicker.api.FilePicker;
import com.kbeanie.multipicker.api.Picker;
import com.kbeanie.multipicker.api.callbacks.FilePickerCallback;
import com.kbeanie.multipicker.api.entity.ChosenFile;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.FileInputStream;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditProfileAva extends Fragment {

    private FilePicker mFilePicker;
    private FilePickerCallback mFilePickerCallback;
    private String filepath;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ProgressRequestBody body;
    private MultipartBody.Part part;

    private ProgressBar upload;
    private Button done, pick;
    private ImageButton back;
    private CropImageView cropview;
    private CircleImageView avaimg,searchava;
    private  String BASE_URL ;
    private  String BASE_URL_API ;
    private Retrofit mRetrofit;
    private ProfileInterface mProfileInterface;

    private SharedPreference mSharedPreference;

    private String mParam1;
    private String mParam2;


    public EditProfileAva() {
        // Required empty public constructor
    }


    public static EditProfileAva newInstance(String param1, String param2) {
        EditProfileAva fragment = new EditProfileAva();
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
        View EPA=inflater.inflate(R.layout.fragment_edit_profile_ava, container, false);

        BASE_URL = getContext().getString(R.string.BASE_URL);
        BASE_URL_API =BASE_URL+"/api/";

        done=(Button)EPA.findViewById(R.id.done);
        pick=(Button)EPA.findViewById(R.id.pick_image);
        back=(ImageButton)EPA.findViewById(R.id.back);
        avaimg=(CircleImageView)EPA.findViewById(R.id.setimage);
        searchava=(CircleImageView)EPA.findViewById(R.id.searchava);
        upload=(ProgressBar)EPA.findViewById(R.id.uploadprogress);
        upload.setVisibility(View.GONE);


        mSharedPreference=new SharedPreference();


        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mProfileInterface = mRetrofit.create(ProfileInterface.class);
        /*       crop
        cropview=(CropImageView)EPA.findViewById(R.id.cropImageView);
        cropview.setAspectRatio(5, 10);
        cropview.setFixedAspectRatio(true);
        cropview.setGuidelines(CropImageView.Guidelines.ON);
        cropview.setCropShape(CropImageView.CropShape.OVAL);
        cropview.setScaleType(CropImageView.ScaleType.FIT_CENTER);
        cropview.setAutoZoomEnabled(true);
        cropview.setShowProgressBar(true);
        cropview.setCropRect(new Rect(0, 0, 800, 500));




        cropview.setOnSetImageUriCompleteListener(new CropImageView.OnSetImageUriCompleteListener() {
            @Override
            public void onSetImageUriComplete(CropImageView view, Uri uri, Exception error) {
                if(error==null)Toast.makeText(getContext(),uri.toString(),Toast.LENGTH_LONG).show();
                else{
                    Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    Log.e("crop", "onSetImageUriComplete: " + error.getMessage() );
                }

            }
        });*/


        Call<ProfileModel> profinfo = mProfileInterface.profileInfo(" Token "+mSharedPreference.getToken(getContext()),mSharedPreference.getUserName(getContext()));
        profinfo.enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getContext(),mSharedPreference.getToken(getContext()),Toast.LENGTH_LONG).show();
                    Glide.with(getActivity()).load( response.body().getProfile().getAvatar()).asBitmap().animate(R.anim.zoom_in).into(avaimg);
                    Glide.with(getActivity()).load( response.body().getProfile().getAvatar()).asBitmap().animate(R.anim.zoom_in).into(searchava);
                }
            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {

            }
        });
        pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload.setVisibility(View.GONE);
                mFilePicker=new FilePicker(EditProfileAva.this);
                mFilePickerCallback= new FilePickerCallback() {
                    @Override
                    public void onFilesChosen(List<ChosenFile> list) {
                        filepath=list.get(0).getOriginalPath();
                        Glide.with(getActivity()).load(filepath).asBitmap().animate(R.anim.zoom_in).into(avaimg);
                      /*  Bitmap bitmap=null;
                        try {
                             bitmap= MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),Uri.parse(filepath));
                        }
                        catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                        cropview.setImageUriAsync(Uri.parse(filepath));
                        //cropview.setImageBitmap(bitmap);

*/


                      try {
                          body=new ProgressRequestBody(new FileInputStream(filepath), new ProgressRequestBody.UploadCallbacks() {
                              @Override
                              public void onProgressUpdate(int percentage) {
                                  upload.setProgress(percentage);

                              }
                          }, MediaType.parse("file/*"));
                      }
                      catch (Exception e){
                          e.printStackTrace();
                      }
                      part=MultipartBody.Part.createFormData("avatar",list.get(0).getDisplayName(),body);

                    }

                    @Override
                    public void onError(String s) {

                    }
                };
                mFilePicker.setFilePickerCallback(mFilePickerCallback);
                mFilePicker.pickFile();
            }
        });


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                upload.setVisibility(View.VISIBLE);
                Call<ProfilePhotoChangeResponse> photochange=mProfileInterface.setava(" Token "+mSharedPreference.getToken(getContext()),part,mSharedPreference.getUserName(getContext()));
                photochange.enqueue(new Callback<ProfilePhotoChangeResponse>() {
                    @Override
                    public void onResponse(Call<ProfilePhotoChangeResponse> call, Response<ProfilePhotoChangeResponse> response) {
                        if (response.isSuccessful()) {
                            Glide.with(getActivity()).load(BASE_URL+response.body().avatar).asBitmap().animate(R.anim.zoom_in).into(searchava);
                        }
                    }

                    @Override
                    public void onFailure(Call<ProfilePhotoChangeResponse> call, Throwable t) {

                    }
                });
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });


        return EPA;
    }


    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        if(requestCode== Picker.PICK_FILE){
            if (resultCode== Activity.RESULT_OK){
                if(mFilePicker==null){
                    mFilePicker= new FilePicker(EditProfileAva.this);
                    mFilePicker.setFilePickerCallback(mFilePickerCallback);
                }
                mFilePicker.submit(data);
            }
        }
    }

}
