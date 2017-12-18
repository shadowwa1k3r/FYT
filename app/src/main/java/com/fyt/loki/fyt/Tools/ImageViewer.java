package com.fyt.loki.fyt.Tools;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fyt.loki.fyt.R;


public class ImageViewer extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";


    // TODO: Rename and change types of parameters
    private String mParam1;




    public ImageViewer() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ImageViewer newInstance(String param1) {
        ImageViewer fragment = new ImageViewer();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View IV=inflater.inflate(R.layout.fragment_image_viewer, container, false);

        ImageView imageView = (ImageView)IV.findViewById(R.id.image_viewer);
        Glide.with(getContext()).load(mParam1).into(imageView);

        // Inflate the layout for this fragment
        return IV;
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        FrameLayout fl=(FrameLayout)getActivity().findViewById(R.id.mainFrame);
        fl.setVisibility(View.VISIBLE);
    }

}
