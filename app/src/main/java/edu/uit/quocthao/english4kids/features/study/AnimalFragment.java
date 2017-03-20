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

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

import edu.uit.quocthao.english4kids.R;

@EFragment(R.layout.fragment_animal)
public class AnimalFragment extends Fragment {

    private Bundle bundleAnimal;

    private Intent intentAnimal;

    @AfterViews
    public void initContent() {
        intentAnimal = new Intent(getActivity(), ContentStudy_.class);
        bundleAnimal = new Bundle();
        bundleAnimal.putInt("topic", 0);
        intentAnimal.putExtra("topics", bundleAnimal);
    }

    @Click(R.id.fragment_animal_ib_topic)
    public void clickAnimal() {
        startActivity(intentAnimal);
    }
}
