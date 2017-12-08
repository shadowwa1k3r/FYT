package com.fyt.loki.fyt;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kbeanie.multipicker.api.FilePicker;
import com.kbeanie.multipicker.api.Picker;
import com.kbeanie.multipicker.api.callbacks.FilePickerCallback;
import com.kbeanie.multipicker.api.entity.ChosenFile;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class NewsPage extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    List<MultipartBody.Part> parts=new ArrayList<MultipartBody.Part>();
    File file;
    RequestBody reqFile;
    RequestBody name,context;




    private FilePicker mFilePicker;
    private FilePickerCallback mFilePickerCallback;

    private ProfileInterface profileInterface;
    private String mToken;
    private String mUserName;
    private String BASE_URL;
    private String BASE_URL_API;
    private ImageButton sendpost;
    private ImageView add;
    private EditText posttext;
    private Button include;
    private ExpandableLayout media,newstop;

    private RecyclerView mRecyclerView,mediaRV;
    private RecyclerView.LayoutManager mLayoutManager,mediaLM;
     NewsPostsAdapter mNewsPostsAdapter;
    private MediaAdapter mMediaAdapter;
    private String[] extension={"jpg","jpeg","png","gif","mp4","avi","3gp","mkv"};
    private ArrayList<NewsFeedItemType> mDataset;
    private ArrayList<String> mediaset;

    private TextView searchtext;
    SearchView searchview;
    private CircleImageView searchava;

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
   // DialogProperties properties;
    //FilePickerDialog dialog;
    static int y;





    public static NewsPage newInstance(String param1, String param2) {
        NewsPage fragment = new NewsPage();
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
            mToken = getArguments().getString(ARG_PARAM1);
            mUserName = getArguments().getString(ARG_PARAM2);
        }
    }
  /*  @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case FilePickerDialog.EXTERNAL_READ_PERMISSION_GRANT: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(dialog!=null)
                    {   //Show dialog if the read permission has been granted.
                        dialog.show();
                    }
                }
                else {
                    //Permission has not been granted. Notify the user.
                    Toast.makeText(getContext(),"Permission is Required for getting list of files",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       final View NewsPage=inflater.inflate(R.layout.fragment_news_page, container, false);
        BASE_URL= getContext().getString(R.string.BASE_URL);
        BASE_URL_API =BASE_URL+"/api/";
        FrameLayout fl=(FrameLayout)getActivity().findViewById(R.id.mainFrame);
        fl.setVisibility(View.VISIBLE);
        searchview=(SearchView)NewsPage.findViewById(R.id.search);

        searchtext=(TextView)searchview.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchtext.setTextColor(Color.BLACK);
        searchtext.setHintTextColor(Color.GRAY);
        searchtext.setTextSize(14);
        searchava=(CircleImageView)NewsPage.findViewById(R.id.searchava);




        sendpost=(ImageButton)NewsPage.findViewById(R.id.sendpost);
        posttext=(EditText)NewsPage.findViewById(R.id.posttxt);
        include=(Button)NewsPage.findViewById(R.id.photo_video);
        media=(ExpandableLayout)NewsPage.findViewById(R.id.mediaexpand);
        newstop=(ExpandableLayout)NewsPage.findViewById(R.id.newstop);
        add=(ImageView)NewsPage.findViewById(R.id.media_add);
/*
        properties = new DialogProperties();

        properties.selection_mode= DialogConfigs.MULTI_MODE;
        properties.selection_type=DialogConfigs.FILE_SELECT;
        properties.root=new File(DialogConfigs.DEFAULT_DIR);
        properties.error_dir = new File(DialogConfigs.DEFAULT_DIR);
        properties.offset = new File(DialogConfigs.DEFAULT_DIR);
        properties.extensions=extension;
        dialog = new FilePickerDialog(getContext(),properties);
        dialog.setTitle("Select Photo");
*/

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        profileInterface = retrofit.create(ProfileInterface.class);

        mRecyclerView = (RecyclerView)NewsPage.findViewById(R.id.newsRV);
        mediaRV = (RecyclerView)NewsPage.findViewById(R.id.mediacontainer);
        mediaRV.setHasFixedSize(true);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mediaLM = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,true);
        mediaRV.setLayoutManager(mediaLM);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mDataset = new ArrayList<NewsFeedItemType>();
        mediaset = new ArrayList<String>();
        mMediaAdapter = new MediaAdapter(getActivity(),mediaset);

        Call<ProfileModel> mprofileInfo = profileInterface.profileInfo(" Token "+mToken,mUserName);
        mprofileInfo.enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                final String ava = BASE_URL + response.body().getAvatar();
                Glide.with(getContext()).load(ava).asBitmap().into(searchava);
                final String usnm = response.body().getUsername();
                final Call<List<NewsFeedModel>> getnews = profileInterface.getNews(" Token "+mToken);
                getnews.enqueue(new Callback<List<NewsFeedModel>>() {
                    @Override
                    public void onResponse(Call<List<NewsFeedModel>> call, Response<List<NewsFeedModel>> response) {
                        if (response.isSuccessful()){
                            mDataset.clear();


                            //Button button = (Button)NewsPage.findViewById(R.id.button17);

                            for (int i = 0; i <response.body().size(); i++) {

                                mDataset.add(new NewsFeedItemType(response.body().get(i).target.avatar,response.body().get(i).target.owner.username,response.body().get(i).target.created,response.body().get(i).target.context,
                                        response.body().get(i).target.likes_count,response.body().get(i).target.comments,response.body().get(i).target.images,response.body().get(i).target.videos,response.body().get(i).target_id,response.body().get(i).target.likes));

                            }

                            mNewsPostsAdapter = new NewsPostsAdapter(getActivity(),mDataset,mToken,mUserName);

                            mNewsPostsAdapter.notifyDataSetChanged();

                            mRecyclerView.setAdapter(mNewsPostsAdapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<NewsFeedModel>> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {

            }
        });
        searchava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).replace(R.id.contentContainer,ProfilePage.newInstance(mToken,mUserName)).addToBackStack(null).commit();
            }
        });

        sendpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog dial=new SpotsDialog(getContext());
                dial.setTitle("Uploading Media");
                dial.show();
                context = RequestBody.create(MediaType.parse("text/plain"),posttext.getText().toString());
                final retrofit2.Call<createPostResponse> req = profileInterface.postimage(" Token "+mToken,parts,name,context);
                req.enqueue(new Callback<createPostResponse>() {
                    @Override
                    public void onResponse(Call<createPostResponse> call, Response<createPostResponse> response) {

                        if (response.isSuccessful()){

                            mDataset.add(0,new NewsFeedItemType(response.body().owner.avatar,response.body().owner.username,response.body().created,response.body().context,response.body().likes_count,response.body().comments,
                                    response.body().images,response.body().videos,response.body().id,response.body().likes));
                            mNewsPostsAdapter.notifyItemInserted(0);
                            mRecyclerView.smoothScrollToPosition(0);

                            mediaset.clear();

                            mMediaAdapter.notifyDataSetChanged();
                            media.collapse();
                            posttext.setText("");
                            dial.dismiss();
                        }
                        else{ dial.dismiss();
                       /* include.setText("resperror");*/}
                    }


                    @Override
                    public void onFailure(Call<createPostResponse> call, Throwable t) {
                        t.printStackTrace();
                        //include.setText(t.toString());
                        dial.dismiss();

                    }
                });
            }
        });
        include.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (media.isExpanded()){
                    media.collapse();
                }
                else {
                    media.expand();
                }
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
               // super.onScrolled(recyclerView, dx, dy);
              /*  if(dy >0){
                    newstop.collapse();
                }
                else {
                    newstop.expand();

                }*/
              y=dy;
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,int newState){
                super.onScrollStateChanged(recyclerView,newState);
                if(mRecyclerView.SCROLL_STATE_DRAGGING==newState)
                {

                }
                if(mRecyclerView.SCROLL_STATE_IDLE==newState){

                    if (y<=0)newstop.expand();
                    else {
                        y=0;
                        newstop.collapse();
                    }
                }

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mFilePicker = new FilePicker(com.fyt.loki.fyt.NewsPage.this);
                Log.e("FilePick", "onClick: "+mFilePicker.toString() );
                mFilePickerCallback = new FilePickerCallback() {
                    @Override
                    public void onFilesChosen(List<ChosenFile> list) {
                        for (int i = 0; i <list.size() ; i++) {

                            file = new File(list.get(i).getOriginalPath());

                                Log.e("FilePick", "onImagesChosen: " + list.get(i).getOriginalPath());
                                Log.e("FilePick", "onImagesChosen: " + list.get(i).getDisplayName());
                                Log.e("FilePick", "onImagesChosenext: " + list.get(i).getOriginalPath().substring(list.get(i).getOriginalPath().length() - (list.get(i).getOriginalPath().length() - list.get(i).getOriginalPath().lastIndexOf(".") - 1)));
                                Log.e("FilePick", "onImagesChosen: " + file.getName());

                            String wholeID = DocumentsContract.getDocumentId(Uri.parse(list.get(i).getOriginalPath()));

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
                            cursor.close();
                            Log.e("FilePick", "onImagesChosen: "+filepathh );
                            file=new File(filepathh);

                            if (SET_IMAGE_EXTENSIONS.contains((filepathh.substring(filepathh.length() - (filepathh.length() - filepathh.lastIndexOf(".") - 1))).toLowerCase()) || SET_VIDEO_EXTENSIONS.contains((filepathh.substring(filepathh.length() - (filepathh.length() - filepathh.lastIndexOf(".") - 1))).toLowerCase())) {


                                reqFile = RequestBody.create(MediaType.parse("file/*"), file);
                                parts.add(MultipartBody.Part.createFormData("file", file.getName(), reqFile));
                                mediaset.add(list.get(i).getOriginalPath());
                                //include.setText(list.get(i).getOriginalPath());


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
                        Log.e("FilePick", "onError: "+s );

                    }
                };
                mFilePicker.setFilePickerCallback(mFilePickerCallback);
                mFilePicker.allowMultiple();


                mFilePicker.pickFile();


            }
        });

        requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},10);






        return NewsPage;
    }


    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){

        if(requestCode == Picker.PICK_FILE){
            if(resultCode==Activity.RESULT_OK){
                if(mFilePicker==null){
                    mFilePicker=new FilePicker(NewsPage.this);
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
