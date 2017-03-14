package com.example.gamenet.loaddata.object;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by GameNet on 3/13/2017.
 */

public class ObjAccount implements Serializable {

    @SerializedName("access_token")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
