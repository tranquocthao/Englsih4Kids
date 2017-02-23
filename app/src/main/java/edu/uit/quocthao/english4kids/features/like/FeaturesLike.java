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

import java.util.ArrayList;

import edu.uit.quocthao.english4kids.R;
import edu.uit.quocthao.english4kids.object.ObjTopic;

public class FeaturesLike extends AppCompatActivity {

    private DatabaseReference drEnglish;

    private int lengthLikes;

    private String[] arrTopic = {"animal", "sport", "job"};

    private ObjTopic objLike;

    private ArrayList<ObjTopic> listLikes = new ArrayList<>();

    private RecyclerView recyclerView;

    private LikeAdapter likeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_features_like);

        drEnglish = FirebaseDatabase.getInstance().getReference();

        loadData();

        //Tạo recycleView
        recyclerView = (RecyclerView) findViewById(R.id.activity_features_like_rv_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void loadData() {
        //Lấy mảng animal cho vào listGame

        drEnglish.child("study").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listLikes.clear();
                //Lấy giá trị trong animals, sports, jobs
                for (int i = 0; i < arrTopic.length; i++) {
                    lengthLikes = Integer.parseInt(dataSnapshot
                            .child(arrTopic[i] + "s").child("length").getValue().toString());

                    for (int j = 0; j < lengthLikes; j++) {
                        String checkLike = dataSnapshot.child(arrTopic[i] + "s")
                                .child(arrTopic[i] + j).child("isLike").getValue().toString();

                         if ( checkLike.equals("true")) {
                            objLike = new ObjTopic();
                            objLike.setUrlAudio(dataSnapshot.child(arrTopic[i] + "s")
                                    .child(arrTopic[i] + j).child("audio").getValue().toString());
                            objLike.setUrlPicture(dataSnapshot.child(arrTopic[i] + "s")
                                    .child(arrTopic[i] + j).child("picture").getValue().toString());
                            objLike.setEnWord(dataSnapshot.child(arrTopic[i] + "s")
                                    .child(arrTopic[i] + j).child("word").getValue().toString());

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
