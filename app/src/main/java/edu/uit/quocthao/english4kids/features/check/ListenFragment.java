package edu.uit.quocthao.english4kids.features.check;

import android.content.Intent;
import android.support.v4.app.Fragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

import edu.uit.quocthao.english4kids.R;

@EFragment(R.layout.fragment_check_listen)
public class ListenFragment extends Fragment {

    private Intent intentListen;

    @AfterViews
    public void initContent() {
        intentListen = new Intent(getActivity(), ContentListen_.class);
    }

    @Click(R.id.fragment_check_ib_listen)
    public void clickListen() {
        startActivity(intentListen);
    }
}
