package com.example.gamenet.loaddata.object;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by GameNet on 3/13/2017.
 */

public class ObjFullData {

    @SerializedName("data")
    private ArrayList<ObjData> data;

    public ArrayList<ObjData> getData() {
        return data;
    }

    public void setData(ArrayList<ObjData> data) {
        this.data = data;
    }
}
