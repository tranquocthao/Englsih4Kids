package edu.uit.quocthao.english4kids.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import edu.uit.quocthao.english4kids.object.ObjTopic;

/**
 * Created by Quoc Thao on 3/27/2017.
 */

public class SQLiteEnglish extends SQLiteOpenHelper {

    static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "dbEnglish";
    static final String TABLE_ENGLSIH = "tbEnglish";

    static final String KEY_ID = "id";
    static final String KEY_EN_WORD = "en_word";
    static final String KEY_VI_WORD = "vi_word";
    static final String KEY_AUDIO = "audio";
    static final String KEY_PICTURE = "picture";

    String topic;

    public SQLiteEnglish(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_ENGLSIH + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_EN_WORD + " TEXT, "
                + KEY_VI_WORD + " TEXT, "
                + KEY_AUDIO + " TEXT, "
                + KEY_PICTURE + " TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS  " + TABLE_ENGLSIH);
        //onCreate(sqLiteDatabase);
    }

    public void insertEnglish(ArrayList<ObjTopic> listObjTopics) {
        SQLiteDatabase dbWrite = getWritableDatabase();
        ContentValues values;

        for (ObjTopic objTopic : listObjTopics) {
            values = new ContentValues();

            //values.put(KEY_ID, contactInfo.getId());
            values.put(KEY_EN_WORD, objTopic.getEnWord());
            values.put(KEY_VI_WORD, objTopic.getViWord());
            values.put(KEY_AUDIO, objTopic.getUrlAudio());
            values.put(KEY_PICTURE, objTopic.getUrlPicture());

            dbWrite.insert(TABLE_ENGLSIH, null, values);
        }

    }

    public ArrayList<ObjTopic> getListEnglish() {
        ArrayList<ObjTopic> listEnglish = new ArrayList<>();
        SQLiteDatabase dbRead = getReadableDatabase();
        Cursor cursor = dbRead.rawQuery("select * from " + TABLE_ENGLSIH, null);

        if (cursor.moveToFirst()) {
            do {
                ObjTopic objTopic = new ObjTopic();

                objTopic.setEnWord(cursor.getString(1));
                objTopic.setViWord(cursor.getString(2));
                objTopic.setUrlAudio(cursor.getString(3));
                objTopic.setUrlPicture(cursor.getString(4));

                listEnglish.add(objTopic);
            } while (cursor.moveToNext());
        }

        return listEnglish;
    }

    public void deleteAllEnglish() {
        SQLiteDatabase dbDelete = this.getWritableDatabase();
        dbDelete.delete(TABLE_ENGLSIH, null, null);
    }
}
