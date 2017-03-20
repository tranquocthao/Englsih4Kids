package edu.uit.quocthao.english4kids.features.study;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

import edu.uit.quocthao.english4kids.R;

@EFragment(R.layout.fragment_sport)
public class SportFragment extends Fragment {

    private Bundle bundleSport;

    private Intent intentSport;

    @AfterViews
    public void initContent() {
        intentSport = new Intent(getActivity(), ContentStudy_.class);
        bundleSport = new Bundle();
        bundleSport.putInt("topic", 1);
        intentSport.putExtra("topics", bundleSport);
    }

    @Click(R.id.fragment_sport_ib_topic)
    public void clickSport(){
        startActivity(intentSport);
    }
}
