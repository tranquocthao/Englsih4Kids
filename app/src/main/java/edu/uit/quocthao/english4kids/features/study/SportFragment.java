package edu.uit.quocthao.english4kids.features.study;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import edu.uit.quocthao.english4kids.R;

public class SportFragment extends Fragment {

    private ImageButton ibSport;

    private Bundle bundleSport;

    private Intent intentSport;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sport, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Inflate the layout for this fragment

        ibSport = (ImageButton) getActivity().findViewById(R.id.fragment_sport_ib_topic);
        intentSport = new Intent(getActivity(), ContentStudy.class);
        bundleSport = new Bundle();
        bundleSport.putInt("topic", 1);
        intentSport.putExtra("topics", bundleSport);

        ibSport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentSport);
            }
        });
    }

}
