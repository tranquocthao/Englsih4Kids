package edu.uit.quocthao.english4kids.features.study;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ViewById;

import edu.uit.quocthao.english4kids.R;

@Fullscreen
@EActivity(R.layout.activity_features_study)
public class FeaturesStudy extends AppCompatActivity {

    @ViewById(R.id.activity_features_study_vp_topic)
    ViewPager vpTopic;

    @ViewById(R.id.activity_features_study_tl_topic)
    TabLayout tlTopic;

    private TopicAdapter adapterTopic;

    @AfterViews
    public void initContent(){
        adapterTopic = new TopicAdapter(getSupportFragmentManager());
        vpTopic.setAdapter(adapterTopic);
        tlTopic.setupWithViewPager(vpTopic);
        vpTopic.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tlTopic));
        tlTopic.setTabsFromPagerAdapter(adapterTopic);

    }
}
