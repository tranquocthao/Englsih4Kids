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

/**
 * A simple {@link Fragment} subclass.
 */
public class WriteFragment extends Fragment {

    private ImageButton ibWrite;

    private Intent intentWrite;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_check_write, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Inflate the layout for this fragment
        ibWrite = (ImageButton) getActivity().findViewById(R.id.fragment_check_ib_write);

        ibWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentWrite = new Intent(getActivity(), ContentWrite_.class);
                startActivity(intentWrite);
            }
        });
    }

}
