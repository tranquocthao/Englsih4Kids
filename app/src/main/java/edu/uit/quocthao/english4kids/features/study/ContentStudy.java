package edu.uit.quocthao.english4kids.features.study;

import android.content.Context;
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
import com.orhanobut.hawk.Hawk;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import edu.uit.quocthao.english4kids.MainActivity_;
import edu.uit.quocthao.english4kids.R;
import edu.uit.quocthao.english4kids.RecyclerItemClickListener;
import edu.uit.quocthao.english4kids.object.ObjTopic;
import edu.uit.quocthao.english4kids.services.SQLiteEnglish;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

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
    ArrayList<String> listPicture;

    @InstanceState
    ArrayList<ObjTopic> myTopics;

    @InstanceState
    int topicStudy;

    @InstanceState
    int lengthTopics;

    @InstanceState
    String nameTopic;

    private ListPictureAdapter listPictureAdapter;

    private ContentAdapter adapterContent;

    private SQLiteEnglish sqLiteEnglish;

    private ObjTopic myTopic;

    @InstanceState
    int posLike;

    private boolean checkLike1;
    private String checkLike2;

    @Click(R.id.activity_features_study_content_iv_home)
    public void clickHome() {
        Intent intentHome = new Intent(this, MainActivity_.class);
        startActivity(intentHome);
        finish();
    }

    @Click(R.id.activity_features_study_content_iv_like)
    public void clickLike() {
        checkLike1 = Hawk.contains(myTopics.get(posLike).getEnWord());
        checkLike2 = Hawk.get(myTopics.get(posLike).getEnWord());

        if (!checkLike1|| checkLike2.equals("false")) {
            ivLike.setImageResource(R.drawable.ic_dislike);
            Hawk.put(myTopics.get(posLike).getEnWord(), "true");

        } else {
            ivLike.setImageResource(R.drawable.ic_like);
            Hawk.put(myTopics.get(posLike).getEnWord(), "false");
        }
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    @AfterViews
    public void initContent() {
        Hawk.init(this).build();
        listPicture = new ArrayList<>();
        myTopics = new ArrayList<>();

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
                nameTopic = "animal";
                break;
            case 1:
                nameTopic = "sport";
                break;
            case 2:
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

    /*Error*/
    public void loadData() {
        myTopics = new ArrayList<>();
        listPicture = new ArrayList<>();
        sqLiteEnglish = new SQLiteEnglish(this);

        ArrayList<ObjTopic> newList = new ArrayList<>();
        newList = sqLiteEnglish.getListEnglish();
        switch (nameTopic) {
            case "animal":

                for (int i = 0; i < 10; i++) {
                    myTopics.add(newList.get(i));
                    listPicture.add(newList.get(i).getUrlPicture());
                }
                break;
            case "sport":

                for (int i = 10; i < 20; i++) {
                    myTopics.add(newList.get(i));
                    listPicture.add(newList.get(i).getUrlPicture());
                }
                break;
            case "job":

                for (int i = 20; i < 30; i++) {
                    myTopics.add(newList.get(i));
                    listPicture.add(newList.get(i).getUrlPicture());
                }
                break;
        }

        adapterContent = new ContentAdapter(this, myTopics);
        vpPicture.setAdapter(adapterContent);

        listPictureAdapter = new ListPictureAdapter(ContentStudy.this, listPicture);
        recyclerView.setAdapter(listPictureAdapter);

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

                checkLike1 = Hawk.contains(myTopics.get(position).getEnWord());
                checkLike2 = Hawk.get(myTopics.get(position).getEnWord());

                if (!checkLike1|| checkLike2.equals("false")) {
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