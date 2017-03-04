package edu.uit.quocthao.english4kids.features.check;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Quoc Thao on 2/14/2017.
 */

public class GameAdapter extends FragmentPagerAdapter {
    public GameAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fr = null;

        switch (position) {
            case 0:
                fr = new ListenFragment_();
                break;
            case 1:
                fr = new ReadFragment_();
                break;
            case 2:
                fr = new WriteFragment_();
                break;
        }

        return fr;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "LISTEN";
                break;
            case 1:
                title = "READ";
                break;
            case 2:
                title = "WRITE";
                break;
        }

        return title;
    }
}
