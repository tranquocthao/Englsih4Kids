package edu.uit.quocthao.english4kids.features.check;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ViewById;

import edu.uit.quocthao.english4kids.R;

@Fullscreen
@EActivity(R.layout.activity_features_check)
public class FeaturesCheck extends AppCompatActivity {

    @ViewById(R.id.activity_features_check_vp_game)
    ViewPager vpGame;

    @ViewById(R.id.activity_features_check_tl_game)
    TabLayout tlGame;

    private GameAdapter adapterGame;
    @AfterViews
    public void initContent() {
        adapterGame = new GameAdapter(getSupportFragmentManager());
        vpGame.setAdapter(adapterGame);

        //Hiện TabLayout
        tlGame.setupWithViewPager(vpGame);
        vpGame.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tlGame));
        tlGame.setTabsFromPagerAdapter(adapterGame);
    }
}
