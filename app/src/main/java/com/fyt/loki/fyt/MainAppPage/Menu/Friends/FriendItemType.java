package com.fyt.loki.fyt.MainAppPage.Menu.Friends;

/**
 * Created by ergas on 11/15/2017.
 */

public class FriendItemType {

    private String img;
    private String name;
    private Boolean status;
    private int id;

    public FriendItemType(String img,String name,Boolean status,int id){
        this.img=img;
        this.id=id;
        this.name=name;
        this.status=status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
