package edu.uit.quocthao.english4kids.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import edu.uit.quocthao.english4kids.object.ObjStory;
import edu.uit.quocthao.english4kids.object.ObjTopic;

/**
 * Created by Quoc Thao on 3/29/2017.
 */

public class SQLiteStory extends SQLiteOpenHelper {
    static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "dbStory";
    static final String TABLE_STORY = "tbStory";

    static final String KEY_ID = "id";
    static final String KEY_TITLE_EN = "title_en";
    static final String KEY_TITLE_VI = "title_vi";
    static final String KEY_BODY_EN = "body_en";
    static final String KEY_BODY_VI = "body_vi";
    static final String KEY_PICTURE = "picture";

    public SQLiteStory(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_STORY + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_TITLE_EN + " TEXT, "
                + KEY_TITLE_VI + " TEXT, "
                + KEY_BODY_EN + " TEXT, "
                + KEY_BODY_VI + " TEXT, "
                + KEY_PICTURE + " TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS  " + TABLE_STORY);
        onCreate(db);
    }

    public void insertStory(ArrayList<ObjStory> listStories) {
        SQLiteDatabase dbWrite = getWritableDatabase();
        ContentValues values;

        for (ObjStory objStory : listStories) {
            values = new ContentValues();

            //values.put(KEY_ID, contactInfo.getId());
            values.put(KEY_TITLE_EN, objStory.getTitleEn());
            values.put(KEY_TITLE_VI, objStory.getTitleVi());
            values.put(KEY_BODY_EN, objStory.getBodyEn());
            values.put(KEY_BODY_VI, objStory.getBodyVi());
            values.put(KEY_PICTURE, objStory.getUrlPicture());

            dbWrite.insert(TABLE_STORY, null, values);
        }

    }

    public ArrayList<ObjStory> getListStories() {
        ArrayList<ObjStory> listStories = new ArrayList<>();
        SQLiteDatabase dbRead = getReadableDatabase();
        Cursor cursor = dbRead.rawQuery("select * from " + TABLE_STORY, null);

        if (cursor.moveToFirst()) {
            do {
                ObjStory objStory = new ObjStory();

                objStory.setTitleEn(cursor.getString(1));
                objStory.setTitleVi(cursor.getString(2));
                objStory.setBodyEn(cursor.getString(3));
                objStory.setBodyVi(cursor.getString(4));
                objStory.setUrlPicture(cursor.getString(5));

                listStories.add(objStory);
            } while (cursor.moveToNext());
        }

        return listStories;
    }

    public void deleteAllEnglish() {
        SQLiteDatabase dbDelete = this.getWritableDatabase();
        dbDelete.delete(TABLE_STORY, null, null);
    }
}
