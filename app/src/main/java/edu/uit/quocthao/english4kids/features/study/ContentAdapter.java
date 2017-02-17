package edu.uit.quocthao.english4kids.features.study;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import edu.uit.quocthao.english4kids.R;
import edu.uit.quocthao.english4kids.object.ObjTopic;

/**
 * Created by Quoc Thao on 2/13/2017.
 */

public class ContentAdapter extends PagerAdapter {

    private Context contextContent;

    private LayoutInflater inflaterContent;

    private ArrayList<ObjTopic> arrTopics = new ArrayList<>();

    private MediaPlayer mediaContent;

    public ContentAdapter(final Context context, ArrayList<ObjTopic> myTopics) {
        contextContent = context;
        inflaterContent =
                (LayoutInflater) contextContent.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        arrTopics = myTopics;

    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = inflaterContent.inflate(R.layout.adapter_content, container, false);

        //Show picture
        ImageView imageView = (ImageView) view.findViewById(R.id.activity_content_iv_picture);
        Picasso.with(view.getContext())
                .load(arrTopics.get(position).getUrlPicture())
                .into(imageView);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaContent = new MediaPlayer();
                mediaContent.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {
                    mediaContent.setDataSource(arrTopics.get(position).getUrlAudio());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaContent.prepareAsync();
                mediaContent.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp3) {
                        mp3.start();
                    }
                });
            }
        });

        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return arrTopics.size();
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
        return arrTopics.get(position).getEnWord();
    }

}
