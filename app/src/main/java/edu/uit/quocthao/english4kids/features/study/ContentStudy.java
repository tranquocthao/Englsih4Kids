package edu.uit.quocthao.english4kids.features.study;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import edu.uit.quocthao.english4kids.MainActivity;
import edu.uit.quocthao.english4kids.MainActivity_;
import edu.uit.quocthao.english4kids.R;
import edu.uit.quocthao.english4kids.RecyclerItemClickListener;
import edu.uit.quocthao.english4kids.object.ObjTopic;

@Fullscreen
@EActivity(R.layout.activity_features_study_content)
public class ContentStudy extends AppCompatActivity {

    @ViewById(R.id.viewflipper)
    ViewFlipper viewFlipper;

    @ViewById(R.id.activity_features_study_content_tv_word)
    TextView tvWord;

    @ViewById(R.id.activity_features_study_content_tv_mean)
    TextView tvMean;

    @ViewById(R.id.activity_features_study_content_vp_picture)
    ViewPager vpPicture;

    @ViewById(R.id.activity_features_study_content_iv_like)
    ImageView ivLike;

    @ViewById(R.id.activity_features_study_content_iv_home)
    ImageView ivHome;

    @ViewById(R.id.activity_features_study_content_rv_picture)
    RecyclerView recyclerView;

    @InstanceState
    ArrayList<String> listPicture = new ArrayList<>();

    @InstanceState
    ArrayList<ObjTopic> myTopics = new ArrayList<>();

    @InstanceState
    int topicStudy;

    @InstanceState
    int lengthTopics;

    @InstanceState
    String nameTopic;

    private ListPictureAdapter listPictureAdapter;

    private ContentAdapter adapterContent;

    private FirebaseDatabase fbEnglish;

    private DatabaseReference drEnglish;

    private ObjTopic myTopic;

    @InstanceState
    int posLike;

    @Click(R.id.activity_features_study_content_iv_home)
    public void clickHome() {
        Intent intentHome = new Intent(this, MainActivity_.class);
        startActivity(intentHome);
        finish();
    }

    @Click(R.id.activity_features_study_content_iv_like)
    public void clickLike() {
        if (myTopics.get(posLike).getIsLike().equals("false")) {
            ivLike.setImageResource(R.drawable.ic_dislike);
            myTopics.get(posLike).setIsLike("true");
            drEnglish.child(nameTopic + posLike).child("isLike").setValue("true");
        } else {
            ivLike.setImageResource(R.drawable.ic_like);
            myTopics.get(posLike).setIsLike("false");
            drEnglish.child(nameTopic + posLike).child("isLike").setValue("false");
        }
    }

    @AfterViews
    public void initContent() {
        fbEnglish = FirebaseDatabase.getInstance();

        selectTopic();  //Lựa chọn topic như animal, sport, job ....
        listPictures(); //Show list hình ảnh topic được chọn.
        loadData();     //Load dữ liệu trên firebase về bỏ vào content và list hình ảnh.

        listenViewPager();  //Lắng nghe sự thay đổi của viewPager.

        viewFlipper.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                viewFlipper.setInAnimation(ContentStudy.this, R.anim.anim_flipview_in);
                viewFlipper.setOutAnimation(ContentStudy.this, R.anim.anim_flipview_out);
                viewFlipper.showNext();

                return false;
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

    public void listPictures() {
        //Tạo recycleView
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
                    myTopic.setViWord(dataSnapshot
                            .child(nameTopic + i).child("mean").getValue().toString());
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

    private void listenViewPager() {
        vpPicture.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(final int position,
                                       float positionOffset,
                                       int positionOffsetPixels) {
                posLike = position;

                tvMean.setText(myTopics.get(position).getViWord());
                tvWord.setText(myTopics.get(position).getEnWord());

                if (myTopics.get(position).getIsLike().equals("false")) {
                    ivLike.setImageResource(R.drawable.ic_like);
                } else {
                    ivLike.setImageResource(R.drawable.ic_dislike);
                }

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

}