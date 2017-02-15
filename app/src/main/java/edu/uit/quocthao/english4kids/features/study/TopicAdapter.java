package edu.uit.quocthao.english4kids.features.study;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Quoc Thao on 2/12/2017.
 */

public class TopicAdapter extends FragmentPagerAdapter {
    public TopicAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag=null;
        switch (position){
            case 0:
                frag=new AnimalFragment();
                break;
            case 1:
                frag=new SportFragment();
                break;
            case 2:
                frag=new JobFragment();
                break;
        }
        return frag;
    }

    @Override
    public int getCount() { return 3;}

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title="ANIMAL";
                break;
            case 1:
                title="SPORT";
                break;
            case 2:
                title="JOB";
                break;
        }

        return title;
    }
}
