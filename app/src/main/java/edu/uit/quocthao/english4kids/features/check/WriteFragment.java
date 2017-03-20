package edu.uit.quocthao.english4kids.features.check;

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

@EFragment(R.layout.fragment_check_write)
public class WriteFragment extends Fragment {

    private Intent intentWrite;

    @AfterViews
    public void initContent() {
        intentWrite = new Intent(getActivity(), ContentWrite_.class);
    }

    @Click(R.id.fragment_check_ib_write)
    public void clickWrite() {
        startActivity(intentWrite);
    }

}
