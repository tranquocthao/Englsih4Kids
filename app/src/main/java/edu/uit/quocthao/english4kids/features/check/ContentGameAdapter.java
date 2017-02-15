package edu.uit.quocthao.english4kids.features.check;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;

import edu.uit.quocthao.english4kids.R;

/**
 * Created by Quoc Thao on 2/14/2017.
 */

public class ContentGameAdapter extends PagerAdapter {

    String[] myPictures = {
            "https://firebasestorage.googleapis.com/v0/b/firebase-english4kids.appspot.com/o/animal%2Fpicture%2Fdog.jpg?alt=media&token=8d755ccd-bbc3-48c8-8812-b4046fae1f85",
            "https://firebasestorage.googleapis.com/v0/b/firebase-english4kids.appspot.com/o/animal%2Fpicture%2Fcat.jpg?alt=media&token=cb5ed859-9442-41ce-b3ba-31324cbd1e82",
            "https://firebasestorage.googleapis.com/v0/b/firebase-english4kids.appspot.com/o/animal%2Fpicture%2Fpig.jpg?alt=media&token=407f8a28-b519-46ca-8d3c-36708391fc66"
    };

    Context mContext;
    LayoutInflater mLayoutInflater;

    public ContentGameAdapter(Context context) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = mLayoutInflater.inflate(R.layout.adapter_content, container, false);


        //Show picture
        ImageView imageView = (ImageView) view.findViewById(R.id.activity_content_iv_picture);
        Picasso.with(view.getContext())
                .load(myPictures[position])
                .into(imageView);

        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return myPictures.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";

        switch (position) {
            case 0:
                title = "Dog";
                break;
            case 1:
                title = "Cat";
                break;
            case 2:
                title = "Pig";
        }

        return title;
    }

}

