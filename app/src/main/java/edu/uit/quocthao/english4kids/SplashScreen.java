package edu.uit.quocthao.english4kids;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

import bolts.Continuation;
import bolts.Task;
import bolts.TaskCompletionSource;
import edu.uit.quocthao.english4kids.object.ObjStory;
import edu.uit.quocthao.english4kids.object.ObjTopic;
import edu.uit.quocthao.english4kids.services.LoadDataService;
import edu.uit.quocthao.english4kids.services.NetworkService;
import edu.uit.quocthao.english4kids.services.SQLiteEnglish;
import edu.uit.quocthao.english4kids.services.SQLiteStory;

import static android.widget.Toast.makeText;


@Fullscreen
@EActivity(R.layout.activity_splash_screen)
public class SplashScreen extends AppCompatActivity {

    private LoadDataService loadDataService;

    private SQLiteEnglish sqLiteEnglish;
    private SQLiteStory sqLiteStory;

    private ArrayList<Task<Void>> arrTask = new ArrayList<>();

    Task<ArrayList<ObjTopic>> task1;
    Task<ArrayList<ObjStory>> task2;


    @AfterViews
    protected void init() {

        Hawk.init(this).build();

        if (!Hawk.contains("numQuestion"))
            Hawk.put("numQuestion", 10);

        if (NetworkService.isNetworkAvailable(this)) {
            makeText(this, "Network is available to load data!", Toast.LENGTH_LONG).show();

            loadDataService = new LoadDataService();

            sqLiteEnglish = new SQLiteEnglish(this);
            sqLiteStory = new SQLiteStory(this);

            Task<Void> loadDataVoid = loadDataService.loadData().continueWith(new Continuation<ArrayList<ObjTopic>, Void>() {
                @Override
                public Void then(Task<ArrayList<ObjTopic>> task) throws Exception {
                    task1 = task;
                    return null;
                }
            });

            Task<Void> loadStoryVoid = loadDataService.loadStories().continueWith(new Continuation<ArrayList<ObjStory>, Void>() {
                @Override
                public Void then(Task<ArrayList<ObjStory>> task) throws Exception {
                    task2 = task;
                    return null;
                }
            });

            arrTask.add(loadDataVoid);
            arrTask.add(loadStoryVoid);

            sqLiteEnglish.deleteAllEnglish();
            sqLiteStory.deleteAllEnglish();

            Task.whenAll(arrTask).continueWith(new Continuation<Void, Void>() {
                @Override
                public Void then(Task<Void> task) throws Exception {
                    sqLiteEnglish.insertEnglish(task1.getResult());
                    sqLiteStory.insertStory(task2.getResult());

                    Intent intent = new Intent(SplashScreen.this, MainActivity_.class);
                    startActivity(intent);
                    return null;
                }
            }, Task.UI_THREAD_EXECUTOR);

        } else {
            Intent intent = new Intent(SplashScreen.this, MainActivity_.class);
            startActivity(intent);
        }
    }
}
