package edu.uit.quocthao.english4kids.features.check;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ViewById;

import edu.uit.quocthao.english4kids.R;
import edu.uit.quocthao.english4kids.features.study.TopicAdapter;

public class FeaturesCheck extends AppCompatActivity {

    private ViewPager vpGame;

    private TabLayout tlGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_features_check);

        //getSupportActionBar().hide();
        vpGame = (ViewPager) findViewById(R.id.activity_features_check_vp_game);
        tlGame = (TabLayout) findViewById(R.id.activity_features_check_tl_game);
        GameAdapter adapter = new GameAdapter(getSupportFragmentManager());
        vpGame.setAdapter(adapter);

        //Hiện TabLayout
        tlGame.setupWithViewPager(vpGame);
        vpGame.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tlGame));
        tlGame.setTabsFromPagerAdapter(adapter);
    }
}
