package edu.uit.quocthao.english4kids.features.check;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import edu.uit.quocthao.english4kids.R;
import edu.uit.quocthao.english4kids.features.study.ContentStudy;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListenFragment extends Fragment {

    private ImageButton ibListen;

    private Intent intentListen;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_check_listen, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Inflate the layout for this fragment

        ibListen = (ImageButton) getActivity().findViewById(R.id.fragment_check_ib_listen);

        ibListen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentListen = new Intent(getActivity(), ContentListen_.class);
                startActivity(intentListen);
            }
        });
    }
}
