package com.fyt.loki.fyt.MainAppPage.Map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fyt.loki.fyt.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class MapPage extends Fragment  implements LocationListener,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener{

    private GoogleMap mMap;
    private FrameLayout markerInfo;
    private ArrayList<MarkerInfoType> markerList;

    private Marker mCurrLocationMarker;

    private GoogleApiClient mGoogleApiClient;
    private GeoDataClient mGeoDataClient;
    private PlaceDetectionClient mPlaceDetectionClient;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private LocationRequest mLocationRequest;

    private Location mLastLocation;

    private TextView LocationTitle,LocationName,likes,comments;

    public MapPage() {
        // Required empty public constructor
    }

    public static MapPage newInstance() {
        MapPage fragment = new MapPage();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View Location=inflater.inflate(R.layout.fragment_map_page, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);

        mGeoDataClient = Places.getGeoDataClient(getActivity(),null);
        mPlaceDetectionClient = Places.getPlaceDetectionClient(getActivity(),null);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        markerInfo = (FrameLayout)Location.findViewById(R.id.marker_info_panel);
        LocationName=(TextView)Location.findViewById(R.id.location_name);
        LocationTitle=(TextView)Location.findViewById(R.id.training_count);
        likes = (TextView)Location.findViewById(R.id.likef);
        comments=(TextView)Location.findViewById(R.id.commentf);

        markerList=new ArrayList<>();
        markerList.add(new MarkerInfoType("Some Fitnes Center",5,10,2,new LatLng(41.2914,69.227)));
        markerList.add(new MarkerInfoType("Another Fitnes Center", 2 , 3, 1,new LatLng(41.2950,69.210)));

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                mMap=googleMap;
                mMap.setPadding(0,100,0,0);
                Toast.makeText(getContext(),"ready",Toast.LENGTH_LONG).show();
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        buildGoogleApiClient();
                        mMap.setMyLocationEnabled(true);
                    }
                }
                else {
                    buildGoogleApiClient();
                    mMap.setMyLocationEnabled(true);
                }
                updateLocationUI();
            }
        });


        return Location;
    }


    public void updateLocationUI(){


        //mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.cake)).anchor(0.0f,1.0f).position(new LatLng(41.889,-87.622)));
        for (int i = 0; i <markerList.size() ; i++) {

            mMap.addMarker(new MarkerOptions().position(markerList.get(i).getLocation()).title(markerList.get(i).getMarkerLocationName()));

        }
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(41.889,-87.622),16));
        MapStyleOptions mapStyle = MapStyleOptions.loadRawResourceStyle(getActivity(),R.raw.mapstyle_grayscale);
        mMap.setMapStyle(mapStyle);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                if(markerInfo.getVisibility()==View.VISIBLE){
                    markerInfo.setVisibility(View.INVISIBLE);
                }
            }
        });


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                if(markerInfo.getVisibility()==View.VISIBLE){
                    markerInfo.setVisibility(View.INVISIBLE);
                }
                for (int i = 0; i <markerList.size() ; i++) {
                    if(marker.getTitle().equals(markerList.get(i).getMarkerLocationName())){
                        LocationName.setText(markerList.get(i).getMarkerLocationName());
                        LocationTitle.setText(markerList.get(i).getLocationTitle());
                        comments.setText(String.valueOf(markerList.get(i).getComments()));
                        likes.setText(String.valueOf(markerList.get(i).getLikes()));
                        markerInfo.setVisibility(View.VISIBLE);


                    }

                }

                return true;
            }
        });

    }


    protected synchronized void buildGoogleApiClient(){
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle){
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

    }
    @Override
    public void onLocationChanged(Location location)
    {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
       /* MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mMap.addMarker(markerOptions);*/

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }


    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // Permission was granted.
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(getContext(), "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            //You can add here other case statements according to your requirement.
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }





}
