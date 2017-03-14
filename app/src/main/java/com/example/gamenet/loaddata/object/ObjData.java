package com.example.gamenet.loaddata.object;

import com.google.gson.annotations.SerializedName;

/**
 * Created by GameNet on 3/13/2017.
 */

public class ObjData {

    @SerializedName("id")
    private String id;

    @SerializedName("startedDate")
    private String startedDate;

    @SerializedName("endDate")
    private String endDate;

    @SerializedName("content")
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartedDate() {
        return startedDate;
    }

    public void setStartedDate(String startedDate) {
        this.startedDate = startedDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
