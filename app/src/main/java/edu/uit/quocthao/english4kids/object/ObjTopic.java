package edu.uit.quocthao.english4kids.object;

/**
 * Created by Quoc Thao on 2/16/2017.
 */

public class ObjTopic {
    private String urlPicture;

    private String urlAudio;

    private String enWord;

    private String isLike;

    public ObjTopic() {
        urlPicture = "";
        urlAudio = "";
        enWord = "";
        isLike = "";
    }

    public String getUrlPicture() {
        return urlPicture;
    }

    public void setUrlPicture(String urlPicture) {
        this.urlPicture = urlPicture;
    }

    public String getUrlAudio() {
        return urlAudio;
    }

    public void setUrlAudio(String urlAudio) {
        this.urlAudio = urlAudio;
    }

    public String getEnWord() {
        return enWord;
    }

    public void setEnWord(String enWord) {
        this.enWord = enWord;
    }

    public String getIsLike() {
        return isLike;
    }

    public void setIsLike(String isLike) {
        this.isLike = isLike;
    }
}
