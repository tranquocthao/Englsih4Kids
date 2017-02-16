package edu.uit.quocthao.english4kids.features.study;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import edu.uit.quocthao.english4kids.R;

public class FeaturesStudy extends AppCompatActivity {

    private ViewPager vpTopic;

    private TabLayout tlTopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_features_study);


        getSupportActionBar().hide();
        vpTopic = (ViewPager) findViewById(R.id.activity_features_study_vp_topic);
        tlTopic = (TabLayout) findViewById(R.id.activity_features_study_tl_topic);
        TopicAdapter adapter = new TopicAdapter(getSupportFragmentManager());
        vpTopic.setAdapter(adapter);
        tlTopic.setupWithViewPager(vpTopic);
        vpTopic.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tlTopic));
        tlTopic.setTabsFromPagerAdapter(adapter);

    }
}
