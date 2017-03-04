package edu.uit.quocthao.english4kids.features.check;

import android.content.Intent;
import android.support.v4.app.Fragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

import edu.uit.quocthao.english4kids.R;

@EFragment(R.layout.fragment_check_read)
public class ReadFragment extends Fragment {

    private Intent intentRead;

    @AfterViews
    public void initContent() {
        intentRead = new Intent(getActivity(), ContentRead_.class);
    }

    @Click(R.id.fragment_check_ib_read)
    public void clickRead() {
        startActivity(intentRead);
    }

}
