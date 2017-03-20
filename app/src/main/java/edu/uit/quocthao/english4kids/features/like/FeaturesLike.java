package edu.uit.quocthao.english4kids.features.like;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import edu.uit.quocthao.english4kids.R;
import edu.uit.quocthao.english4kids.object.ObjTopic;

@Fullscreen
@EActivity(R.layout.activity_features_like)
public class FeaturesLike extends AppCompatActivity {

    @ViewById(R.id.activity_features_like_rv_view)
    RecyclerView recyclerView;

    @InstanceState
    ArrayList<ObjTopic> listLikes = new ArrayList<>();

    @InstanceState
    int lengthLikes;

    @InstanceState
    String[] arrTopics;

    private DatabaseReference drEnglish;

    private ObjTopic objLike;

    private LikeAdapter likeAdapter;

    private LinearLayoutManager linearManager;

    @AfterViews
    public void initContent() {
        drEnglish = FirebaseDatabase.getInstance().getReference();
        arrTopics = getResources().getStringArray(R.array.topics);

        //Tạo recycleView
        recyclerView.setHasFixedSize(true);
        linearManager = new LinearLayoutManager(this);
        linearManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearManager);

        loadData();
    }

    private void loadData() {
        //Lấy mảng animal cho vào listGame

        drEnglish.child("study").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listLikes.clear();
                //Lấy giá trị trong animals, sports, jobs
                for (int i = 0; i < arrTopics.length; i++) {
                    lengthLikes = Integer.parseInt(dataSnapshot
                            .child(arrTopics[i] + "s").child("length").getValue().toString());

                    for (int j = 0; j < lengthLikes; j++) {
                        String checkLike = dataSnapshot.child(arrTopics[i] + "s")
                                .child(arrTopics[i] + j).child("isLike").getValue().toString();

                         if ( checkLike.equals("true")) {
                            objLike = new ObjTopic();
                            objLike.setUrlAudio(dataSnapshot.child(arrTopics[i] + "s")
                                    .child(arrTopics[i] + j).child("audio").getValue().toString());
                            objLike.setUrlPicture(dataSnapshot.child(arrTopics[i] + "s")
                                    .child(arrTopics[i] + j).child("picture").getValue().toString());
                            objLike.setEnWord(dataSnapshot.child(arrTopics[i] + "s")
                                    .child(arrTopics[i] + j).child("word").getValue().toString());

                            listLikes.add(objLike);
                        }
                    }
                }

                likeAdapter = new LikeAdapter(FeaturesLike.this, listLikes);
                recyclerView.setAdapter(likeAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}