package com.fyt.loki.fyt.MainAppPage.News;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fyt.loki.fyt.MainAppPage.ProfilePage.createPostResponse;
import com.fyt.loki.fyt.R;
import com.fyt.loki.fyt.Tools.ProfileInterface;
import com.fyt.loki.fyt.Tools.ProgressRequestBody;
import com.fyt.loki.fyt.Tools.SharedPreference;
import com.kbeanie.multipicker.api.FilePicker;
import com.kbeanie.multipicker.api.Picker;
import com.kbeanie.multipicker.api.callbacks.FilePickerCallback;
import com.kbeanie.multipicker.api.entity.ChosenFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewPostPublish extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;



    private List<MultipartBody.Part> parts=new ArrayList<MultipartBody.Part>();
    private File file;
    private RequestBody reqFile;
    private RequestBody name,context;
    private ViewGroup.LayoutParams mLayoutParams;
    private LinearLayout lay;
    private MediaAdapter mMediaAdapter;

    ProgressRequestBody body;


    private Button send,media;
    private ImageButton back,cancel;
    private EditText post_txt;
    private TextView action;
    private ProgressBar progress;


    private String BASE_URL;
    private String BASE_URL_API;


    private FilePicker mFilePicker;
    private FilePickerCallback mFilePickerCallback;

    private RecyclerView mediaRV;
    private ArrayList<String> mediaset;
    private RecyclerView.LayoutManager mediaLM;

    private SharedPreference mSharedPreference;

    private ProfileInterface profileInterface;

    static String[] IMAGE_EXTENSIONS = {
            "jpg",
            "jpeg",
            "bmp",
            "png",
            "gif",
            "tiff",
            "webp",
            "ico"
    };

    static String[] VIDEO_EXTENSIONS = {
            "avi",
            "asf",
            "mov",
            "flv",
            "swf",
            "mpg",
            "mpeg",
            "mp4",
            "wmv",
    };
    private static Set<String> SET_IMAGE_EXTENSIONS = new HashSet<String>(Arrays.asList(IMAGE_EXTENSIONS));
    private static Set<String> SET_VIDEO_EXTENSIONS = new HashSet<String>(Arrays.asList(VIDEO_EXTENSIONS));




    public NewPostPublish() {
        // Required empty public constructor
    }


    public static NewPostPublish newInstance(String param1, String param2) {
        NewPostPublish fragment = new NewPostPublish();
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
        View NPP=inflater.inflate(R.layout.fragment_new_post_publish, container, false);

        BASE_URL= getContext().getString(R.string.BASE_URL);
        BASE_URL_API =BASE_URL+"/api/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        profileInterface = retrofit.create(ProfileInterface.class);

        send=(Button)NPP.findViewById(R.id.publishpost);
        back=(ImageButton)NPP.findViewById(R.id.back);
        cancel=(ImageButton)NPP.findViewById(R.id.cancelpublish);
        media=(Button)NPP.findViewById(R.id.photo_video);
        post_txt=(EditText)NPP.findViewById(R.id.posttext);
        action=(TextView)NPP.findViewById(R.id.Actions);
        mediaRV=(RecyclerView)NPP.findViewById(R.id.media);
        progress=(ProgressBar)NPP.findViewById(R.id.progress);
        mSharedPreference=new SharedPreference();

        mediaRV.setHasFixedSize(true);
        mediaLM=new LinearLayoutManager(getActivity());
        mediaRV.setLayoutManager(mediaLM);

        mediaset= new ArrayList<>();
        mMediaAdapter=new MediaAdapter(getActivity(),mediaset);



        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context= RequestBody.create(MediaType.parse("text/plain"),post_txt.getText().toString());
                final retrofit2.Call<createPostResponse> req=profileInterface.postimage(" Token "+mSharedPreference.getToken(getContext()),parts,name,context);
                req.enqueue(new Callback<createPostResponse>() {
                    @Override
                    public void onResponse(Call<createPostResponse> call, Response<createPostResponse> response) {
                        if(response.isSuccessful()){
                            getActivity().getSupportFragmentManager().popBackStack();

                        }
                    }

                    @Override
                    public void onFailure(Call<createPostResponse> call, Throwable t) {

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

        media.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mFilePicker=new FilePicker(NewPostPublish.this);
                mFilePickerCallback=new FilePickerCallback() {
                    @Override
                    public void onFilesChosen(List<ChosenFile> list) {
                        for (int i = 0; i <list.size() ; i++) {

                            file = new File(list.get(i).getOriginalPath());

                            Log.e("FilePick", "onImagesChosen: " + list.get(i).getOriginalPath());
                            Log.e("FilePick", "onImagesChosen: " + list.get(i).getDisplayName());
                            Log.e("FilePick", "onImagesChosenext: " + list.get(i).getOriginalPath().substring(list.get(i).getOriginalPath().length() - (list.get(i).getOriginalPath().length() - list.get(i).getOriginalPath().lastIndexOf(".") - 1)));
                            Log.e("FilePick", "onImagesChosen: " + file.getName());

                           /* String wholeID = DocumentsContract.getDocumentId(Uri.parse(list.get(i).getOriginalPath()));

                            String id = wholeID.split(":")[1];

                            String[] column = {MediaStore.Images.Media.DATA};

                            String sel=MediaStore.Images.Media._ID + "=?";

                            Cursor cursor = getActivity().getContentResolver()
                                    .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,column,sel,new String[]{id},null);

                            String filepathh="";
                            int columnIndex = cursor.getColumnIndex(column[0]);

                            if(cursor.moveToFirst()){
                                filepathh=cursor.getString(columnIndex);
                            }
                            cursor.close();*/
                            String filepathh=getPath(getContext(),Uri.parse(list.get(i).getOriginalPath()));
                            filepathh=list.get(i).getOriginalPath();
                            Log.e("FilePick", "onImagesChosen: "+filepathh );
                            file=new File(filepathh);

                            if (SET_IMAGE_EXTENSIONS.contains((filepathh.substring(filepathh.length() - (filepathh.length() - filepathh.lastIndexOf(".") - 1))).toLowerCase()) || SET_VIDEO_EXTENSIONS.contains((filepathh.substring(filepathh.length() - (filepathh.length() - filepathh.lastIndexOf(".") - 1))).toLowerCase())) {


                                reqFile = RequestBody.create(MediaType.parse("file/*"), file);


                                try {
                                    body=new ProgressRequestBody(new FileInputStream(list.get(i).getOriginalPath()), new ProgressRequestBody.UploadCallbacks() {
                                        @Override
                                        public void onProgressUpdate(int percentage) {
                                            progress.setProgress(percentage);
                                        }
                                    }, MediaType.parse("file/*"));
                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                }
                                parts.add(MultipartBody.Part.createFormData("file", file.getName(), body));
                                mediaset.add(list.get(i).getOriginalPath());
                                //include.setText(list.get(i).getOriginalPath());
                                Log.e("FilePick", "onFilesChosen: hahaha" );


                                name = RequestBody.create(MediaType.parse("text/plain"), "text");
                                mMediaAdapter = new MediaAdapter(getActivity(), mediaset);
                                mMediaAdapter.notifyDataSetChanged();

                                mediaRV.setAdapter(mMediaAdapter);
                                mediaRV.smoothScrollToPosition(mediaset.size() - 1);
                            }
                        }
                    }

                    @Override
                    public void onError(String s) {

                    }
                };
                mFilePicker.setFilePickerCallback(mFilePickerCallback);
                mFilePicker.allowMultiple();


                mFilePicker.pickFile();


            }
        });








        return NPP;
    }


    public static String getPath(final Context context, final Uri uri) {


        // DocumentProvider
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }




    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){

        if(requestCode == Picker.PICK_FILE){
            if(resultCode== Activity.RESULT_OK){
                if(mFilePicker==null){
                    mFilePicker=new FilePicker(NewPostPublish.this);
                    mFilePicker.setFilePickerCallback(mFilePickerCallback);
                }
                mFilePicker.submit(data);


            }
           /* for (int i = 0; i <medialist.size() ; i++) {

            }
            */



        }
    }

}
