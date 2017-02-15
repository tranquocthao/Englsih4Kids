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

public class JobFragment extends Fragment {

    ImageButton ibJob;
    Bundle bundle;
    Intent intent;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_job, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Inflate the layout for this fragment

        ibJob = (ImageButton) getActivity().findViewById(R.id.fragment_job_ib_topic);
        intent = new Intent(getActivity(), ContentStudy.class);
        bundle = new Bundle();
        bundle.putInt("topic", 2);
        intent.putExtra("topics", bundle);

        ibJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }
}
