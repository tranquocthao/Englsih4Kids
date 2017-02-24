package edu.uit.quocthao.english4kids.features.study;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import edu.uit.quocthao.english4kids.FeaturesAdapter;
import edu.uit.quocthao.english4kids.MainActivity;
import edu.uit.quocthao.english4kids.R;
import edu.uit.quocthao.english4kids.RecyclerItemClickListener;
import edu.uit.quocthao.english4kids.object.ObjTopic;

public class ContentStudy extends AppCompatActivity {

    private TextView tvWord;

    private ViewPager vpPicture;

    private RecyclerView recyclerView;

    private ListPictureAdapter listPictureAdapter;

    private ArrayList<String> listPicture = new ArrayList<>();

    private int topicStudy;

    private ContentAdapter adapterContent;

    private FirebaseDatabase fbEnglish = FirebaseDatabase.getInstance();

    private DatabaseReference drEnglish;

    private ObjTopic myTopic;

    private ArrayList<ObjTopic> myTopics = new ArrayList<>();

    private int lengthTopics;

    private String nameTopic;

    private ImageView ivLike;

    private ImageView ivHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_features_study_content);

        vpPicture = (ViewPager) findViewById(R.id.activity_features_study_content_vp_picture);
        tvWord = (TextView) findViewById(R.id.activity_features_study_content_tv_word);
        ivLike = (ImageView) findViewById(R.id.activity_features_study_content_iv_like);
        ivHome = (ImageView) findViewById(R.id.activity_features_study_content_iv_home);
        selectTopic();
        listPictures();
        loadData();

        listenViewPager();

    }

    private void listenViewPager() {
        vpPicture.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(final int position,
                                       float positionOffset,
                                       int positionOffsetPixels) {

                tvWord.setText(myTopics.get(position).getEnWord());
                Animation animation = AnimationUtils.loadAnimation(ContentStudy.this, R.anim.anim_combine);
                tvWord.startAnimation(animation);

                if (myTopics.get(position).getIsLike().equals("false")) {
                    ivLike.setImageResource(R.drawable.ic_like);
                } else {
                    ivLike.setImageResource(R.drawable.ic_dislike);
                }

                ivLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (myTopics.get(position).getIsLike().equals("false")) {
                            ivLike.setImageResource(R.drawable.ic_dislike);
                            myTopics.get(position).setIsLike("true");
                            drEnglish.child(nameTopic + position).child("isLike").setValue("true");
                        } else {
                            ivLike.setImageResource(R.drawable.ic_like);
                            myTopics.get(position).setIsLike("false");
                            drEnglish.child(nameTopic + position).child("isLike").setValue("false");
                        }
                    }
                });

                ivHome.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ContentStudy.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
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
                drEnglish = fbEnglish.getReference().child("study").child("animals");
                nameTopic = "animal";
                break;
            case 1:
                drEnglish = fbEnglish.getReference().child("study").child("sports");
                nameTopic = "sport";
                break;
            case 2:
                drEnglish = fbEnglish.getReference().child("study").child("jobs");
                nameTopic = "job";
                break;
        }
    }

    public void loadData() {

        drEnglish.addListenerForSingleValueEvent(new ValueEventListener() {
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
                        myTopic.setIsLike(dataSnapshot
                                .child(nameTopic + i).child("isLike").getValue().toString());

                        listPicture.add(myTopic.getUrlPicture());
                        myTopics.add(myTopic);

                    }

                    adapterContent = new ContentAdapter(ContentStudy.this, myTopics);
                    vpPicture.setAdapter(adapterContent);

                    listPictureAdapter = new ListPictureAdapter(ContentStudy.this, listPicture);
                    recyclerView.setAdapter(listPictureAdapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void listPictures() {
        //Tạo recycleView
        recyclerView = (RecyclerView) findViewById(R.id.activity_features_study_content_rv_picture);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onLongItemClick(View view, int position) {
            }
        }));
    }

}