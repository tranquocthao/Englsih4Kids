package edu.uit.quocthao.english4kids.services;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.androidannotations.annotations.EBean;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import bolts.Task;
import bolts.TaskCompletionSource;
import edu.uit.quocthao.english4kids.object.ObjStory;
import edu.uit.quocthao.english4kids.object.ObjTopic;

import static org.androidannotations.annotations.EBean.Scope.Singleton;

/**
 * Created by Quoc Thao on 3/27/2017.
 */

@EBean(scope = Singleton)
public class LoadDataService {

    private String[] topics = {"animal", "sport", "job"};
    private DatabaseReference drEnglish;
    public ObjTopic objTopic;
    public ObjStory objStory;
    public ArrayList<ObjTopic> listPictures;
    public ArrayList<ObjStory> listStories;

    private TaskCompletionSource<ArrayList<ObjTopic>> taskData;
    private TaskCompletionSource<ArrayList<ObjStory>> taskStory;

    public Task<ArrayList<ObjTopic>> loadData() {

        listPictures = new ArrayList<>();
        taskData = new TaskCompletionSource<>();
        drEnglish = FirebaseDatabase.getInstance().getReference().child("study");

        drEnglish.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (int i = 0; i < topics.length; i++) {
                    int lengthEnglish = Integer.parseInt(dataSnapshot.child(topics[i] + "s")
                            .child("length").getValue().toString());

                    for (int j = 0; j < lengthEnglish; j++) {
                        objTopic = new ObjTopic();

                        objTopic.setEnWord(dataSnapshot.child(topics[i] + "s")
                                .child(topics[i] + j).child("word").getValue().toString());
                        objTopic.setViWord(dataSnapshot.child(topics[i] + "s")
                                .child(topics[i] + j).child("mean").getValue().toString());
                        objTopic.setUrlPicture(dataSnapshot.child(topics[i] + "s")
                                .child(topics[i] + j).child("picture").getValue().toString());
                        objTopic.setUrlAudio(dataSnapshot.child(topics[i] + "s")
                                .child(topics[i] + j).child("audio").getValue().toString());

                        listPictures.add(objTopic);
                    }
                }
                taskData.setResult(listPictures);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return taskData.getTask();
    }

    public Task<ArrayList<ObjStory>> loadStories() {

        listStories = new ArrayList<>();
        taskStory = new TaskCompletionSource<>();
        drEnglish = FirebaseDatabase.getInstance().getReference().child("story");

        drEnglish.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int lengthStory = Integer.parseInt(dataSnapshot.child("length").getValue().toString());

                for (int i = 0; i < lengthStory; i++) {
                    objStory = new ObjStory();

                    objStory.setUrlPicture(dataSnapshot.child("story" + i)
                            .child("picture").getValue().toString());
                    objStory.setTitleVi(dataSnapshot.child("story" + i)
                            .child("vietnam").child("title").getValue().toString());
                    objStory.setTitleEn(dataSnapshot.child("story" + i)
                            .child("english").child("title").getValue().toString());
                    objStory.setBodyVi(dataSnapshot.child("story" + i)
                            .child("vietnam").child("body").getValue().toString());
                    objStory.setBodyEn(dataSnapshot.child("story" + i)
                            .child("english").child("body").getValue().toString());

                    listStories.add(objStory);
                }
                taskStory.setResult(listStories);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return taskStory.getTask();
    }

}
