package edu.uit.quocthao.english4kids.features.study;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.formats.NativeAd;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import edu.uit.quocthao.english4kids.R;
import edu.uit.quocthao.english4kids.object.ObjTopic;

public class ContentStudy extends AppCompatActivity {

    private TextView tvWord;

    private ViewPager vpPicture;

    private int topicStudy;

    private ContentAdapter adapterContent;

    private FirebaseDatabase fbEnglish = FirebaseDatabase.getInstance();

    private DatabaseReference drEnglish;

    private ObjTopic myTopic;

    private ArrayList<ObjTopic> myTopics = new ArrayList<>();

    private int lengthTopics;

    private String nameTopic;

    private ImageView ivLike;

    private int numLike = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_features_study_content);

        vpPicture = (ViewPager) findViewById(R.id.activity_features_study_content_vp_picture);
        tvWord = (TextView) findViewById(R.id.activity_features_study_content_tv_word);
        ivLike = (ImageView) findViewById(R.id.activity_features_study_content_iv_like);
        selectTopic();
        loadData();
        vpPicture.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position,
                                       float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                tvWord.setText(adapterContent.getPageTitle(position) + "");
                ivLike.setImageResource(R.drawable.ic_like);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        ivLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numLike == 0) {
                    ivLike.setImageResource(R.drawable.ic_dislike);
                    numLike = 1;
                } else {
                    ivLike.setImageResource(R.drawable.ic_like);
                    numLike = 0;
                }
            }
        });
    }

    public void selectTopic() {
        Intent intentCaller = getIntent();
        //có intent rồi thì lấy topics trong Bundle.
        Bundle topics = intentCaller.getBundleExtra("topics");
        //lấy giá trị topic trong topics.
        topicStudy = topics.getInt("topic");

        switch (topicStudy) {
            case 0:
                tvWord.setText("dog");
                drEnglish = fbEnglish.getReference().child("study").child("animals");
                nameTopic = "animal";

                break;
            case 1:
                tvWord.setText("basketball");
                drEnglish = fbEnglish.getReference().child("study").child("sports");
                nameTopic = "sport";

                break;
            case 2:
                tvWord.setText("doctor");
                drEnglish = fbEnglish.getReference().child("study").child("jobs");
                nameTopic = "job";

                break;
        }
    }

    public void loadData() {
        drEnglish.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lengthTopics = Integer.parseInt(dataSnapshot.child("length").getValue().toString());

                //Animal animal = dataSnapshot.getValue(Animal.class);
                for (int i = 0; i < lengthTopics; i++) {

                    myTopic = new ObjTopic();
                    myTopic.setUrlPicture(dataSnapshot
                            .child(nameTopic + i).child("picture").getValue().toString());
                    myTopic.setUrlAudio(dataSnapshot
                            .child(nameTopic + i).child("audio").getValue().toString());
                    myTopic.setEnWord(dataSnapshot
                            .child(nameTopic + i).child("word").getValue().toString());

                    myTopics.add(myTopic);

                }

                adapterContent = new ContentAdapter(ContentStudy.this, myTopics);
                vpPicture.setAdapter(adapterContent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}