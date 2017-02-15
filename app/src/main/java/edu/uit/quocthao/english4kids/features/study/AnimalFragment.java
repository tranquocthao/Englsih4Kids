package edu.uit.quocthao.english4kids.features.study;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import edu.uit.quocthao.english4kids.R;

public class AnimalFragment extends Fragment{

    ImageButton ibAnimal;
    Bundle bundle;
    Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_animal, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Inflate the layout for this fragment

        ibAnimal = (ImageButton) getActivity().findViewById(R.id.fragment_animal_ib_topic);
        intent = new Intent(getActivity(), ContentStudy.class);
        bundle = new Bundle();
        bundle.putInt("topic", 0);
        intent.putExtra("topics", bundle);

        ibAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }
}
