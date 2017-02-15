package edu.uit.quocthao.english4kids.features.study;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import java.io.IOException;

import edu.uit.quocthao.english4kids.MainActivity;
import edu.uit.quocthao.english4kids.R;

public class ContentStudy extends AppCompatActivity {

    TextView tvWord;
    ViewPager vpPicture;
    int topic = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_features_study_content);

        vpPicture = (ViewPager) findViewById(R.id.activity_features_study_content_vp_picture);
        tvWord = (TextView) findViewById(R.id.activity_features_study_content_tv_word);

        selectTopic();

        final ContentAdapter adapter = new ContentAdapter(this, topic);
        vpPicture.setAdapter(adapter);

        selectTopic();

        vpPicture.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvWord.setText(adapter.getPageTitle(position) + "");

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public void selectTopic(){
        Intent intentCaller = getIntent();
        //có intent rồi thì lấy topics trong Bundle.
        Bundle topics = intentCaller.getBundleExtra("topics");
        //lấy giá trị topic trong topics.
        topic = topics.getInt("topic");

        switch (topic) {
            case 0:
                tvWord.setText("Dog\n(Con chó)");
                break;
            case 1:
                tvWord.setText("Basketball\n(Bóng rổ)");
                break;
            case 2:
                tvWord.setText("Doctor\n(Bác sĩ)");
                break;
        }
    }
}