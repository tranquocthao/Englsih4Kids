package edu.uit.quocthao.english4kids.features.study;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

import edu.uit.quocthao.english4kids.R;

@EFragment(R.layout.fragment_job)
public class JobFragment extends Fragment {

    private Bundle bundleJob;

    private Intent intentJob;

    @AfterViews
    public void initContent() {
        intentJob = new Intent(getActivity(), ContentStudy_.class);
        bundleJob = new Bundle();
        bundleJob.putInt("topic", 2);
        intentJob.putExtra("topics", bundleJob);
    }

    @Click(R.id.fragment_job_ib_topic)
    public void clickJob(){
        startActivity(intentJob);
    }

}
