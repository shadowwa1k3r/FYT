package com.fyt.loki.fyt.MainAppPage.Menu.Friends;

/**
 * Created by ergas on 11/15/2017.
 */

public class FriendItemType {

    private String img;
    private String name;
    private Boolean status;

    public FriendItemType(String img,String name,Boolean status){
        this.img=img;
        this.name=name;
        this.status=status;
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
