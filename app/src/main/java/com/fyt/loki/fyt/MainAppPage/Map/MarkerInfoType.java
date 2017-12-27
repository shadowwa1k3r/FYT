package com.fyt.loki.fyt.MainAppPage.Map;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by ergas on 12/27/2017.
 */

public class MarkerInfoType {

    private String MarkerLocationName;
    private int comments,likes,trainingCount;
    private LatLng location;

    public MarkerInfoType(String MarkerLocationName, int comments, int likes, int trainingCount, LatLng location){
        this.MarkerLocationName=MarkerLocationName;
        this.comments=comments;
        this.location=location;
        this.likes=likes;
        this.trainingCount=trainingCount;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public String getLocationTitle(){
        return "Have a "+trainingCount+" training";
    }
    public void setTrainingCount(int trainingCount) {
        this.trainingCount = trainingCount;
    }

    public String getMarkerLocationName() {
        return MarkerLocationName;
    }

    public void setMarkerLocationName(String markerLocationName) {
        MarkerLocationName = markerLocationName;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
