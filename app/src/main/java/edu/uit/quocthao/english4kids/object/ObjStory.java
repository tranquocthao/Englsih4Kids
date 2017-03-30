package edu.uit.quocthao.english4kids.object;

import org.androidannotations.annotations.EBean;

import java.io.Serializable;

/**
 * Created by Quoc Thao on 2/10/2017.
 */

public class ObjStory implements Serializable {

    private String urlPicture;

    private String titleVi;

    private String titleEn;

    private String bodyVi;

    private String bodyEn;

    public ObjStory() {
        this.urlPicture = null;
        this.titleVi = null;
        this.titleEn = null;
        this.bodyVi = null;
        this.bodyEn = null;
    }

    public ObjStory(String urlPicture, String titleVi,
                       String titleEn, String bodyVi, String bodyEn) {
        this.urlPicture = urlPicture;
        this.titleVi = titleVi;
        this.titleEn = titleEn;
        this.bodyVi = bodyVi;
        this.bodyEn = bodyEn;
    }

    public String getUrlPicture() {
        return urlPicture;
    }

    public void setUrlPicture(String urlPicture) {
        this.urlPicture = urlPicture;
    }

    public String getTitleVi() {
        return this.titleVi;
    }

    public void setTitleVi(String titleVi) {
        this.titleVi = titleVi;
    }

    public String getTitleEn() {
        return this.titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public String getBodyVi() {
        return this.bodyVi;
    }

    public void setBodyVi(String bodyVi) {
        this.bodyVi = bodyVi;
    }

    public String getBodyEn() {
        return this.bodyEn;
    }

    public void setBodyEn(String bodyEn) {
        this.bodyEn = bodyEn;
    }
}
