package com.fyt.loki.fyt.Tools;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by ergas on 11/14/2017.
 */

public class TokenStore extends RealmObject {
    @Required
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
