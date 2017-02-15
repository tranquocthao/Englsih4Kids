package edu.uit.quocthao.english4kids.features.study;

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
import java.util.ArrayList;

import edu.uit.quocthao.english4kids.R;

/**
 * Created by Quoc Thao on 2/13/2017.
 */

public class ContentAdapter extends PagerAdapter {

    String[] myPicAnimals = {
            "https://firebasestorage.googleapis.com/v0/b/firebase-english4kids.appspot.com/o/animal%2Fpicture%2Fdog.jpg?alt=media&token=8d755ccd-bbc3-48c8-8812-b4046fae1f85",
            "https://firebasestorage.googleapis.com/v0/b/firebase-english4kids.appspot.com/o/animal%2Fpicture%2Fcat.jpg?alt=media&token=cb5ed859-9442-41ce-b3ba-31324cbd1e82",
            "https://firebasestorage.googleapis.com/v0/b/firebase-english4kids.appspot.com/o/animal%2Fpicture%2Fpig.jpg?alt=media&token=407f8a28-b519-46ca-8d3c-36708391fc66"
    };

    String[] myPicSports = {
            "https://firebasestorage.googleapis.com/v0/b/firebase-english4kids.appspot.com/o/sport%2Fpicture%2Fbasketball.png?alt=media&token=2c0f7cb5-6349-44c3-8ab5-3b91ab75676d",
            "https://firebasestorage.googleapis.com/v0/b/firebase-english4kids.appspot.com/o/sport%2Fpicture%2Fchess.png?alt=media&token=5a021732-3688-4b93-ae3a-62f3ec099531",
            "https://firebasestorage.googleapis.com/v0/b/firebase-english4kids.appspot.com/o/sport%2Fpicture%2Ffootball.png?alt=media&token=071e187a-9cba-433e-8858-2d0b800a028c"
    };

    String[] myPicJobs = {
            "https://firebasestorage.googleapis.com/v0/b/firebase-english4kids.appspot.com/o/job%2Fpicture%2Fdoctor.jpeg?alt=media&token=a83dd78b-e93f-4e0b-adee-9b00d983dd0a",
            "https://firebasestorage.googleapis.com/v0/b/firebase-english4kids.appspot.com/o/job%2Fpicture%2Fpolice.jpg?alt=media&token=e66d1e3b-30d5-4524-ac84-c82dc01e27a4",
            "https://firebasestorage.googleapis.com/v0/b/firebase-english4kids.appspot.com/o/job%2Fpicture%2Fteacher.jpg?alt=media&token=0761cd1d-c0ba-477d-ae52-484fba892e88"
    };

    String[] myAudios = {
            "https://firebasestorage.googleapis.com/v0/b/firebase-english4kids.appspot.com/o/animal%2Faudio%2Fdog.mp3?alt=media&token=a495140c-b1e7-456b-93f1-77e914f85dea",
            "https://firebasestorage.googleapis.com/v0/b/firebase-english4kids.appspot.com/o/animal%2Faudio%2Fcat.mp3?alt=media&token=6a8cd9b3-58f3-400c-814d-a27d149a8610",
            "https://firebasestorage.googleapis.com/v0/b/firebase-english4kids.appspot.com/o/animal%2Faudio%2Fpig.mp3?alt=media&token=2ffdd1c9-fcbc-4d97-8410-84d78d75ee60"
    };


    ArrayList<String> myPictures = new ArrayList<>();

    Context mContext;
    LayoutInflater mLayoutInflater;
    int mTopic;
    MediaPlayer media;


    public ContentAdapter(Context context, int topic) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mTopic = topic;

        switch (mTopic){
            case 0:
                for (int i = 0; i < myPicAnimals.length; i ++){
                    myPictures.add(myPicAnimals[i]);
                }
                break;
            case 1:
                for (int i = 0; i < myPicSports.length; i ++){
                    myPictures.add(myPicSports[i]);
                }
                break;
            case 2:
                for (int i = 0; i < myPicJobs.length; i ++){
                    myPictures.add(myPicJobs[i]);
                }
                break;
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = mLayoutInflater.inflate(R.layout.adapter_content, container, false);


        //Show picture
        ImageView imageView = (ImageView) view.findViewById(R.id.activity_content_iv_picture);
        Picasso.with(view.getContext())
                .load(myPictures.get(position))
                .into(imageView);

        Toast.makeText(view.getContext(), position + "", Toast.LENGTH_LONG).show();

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                media = new MediaPlayer();
                media.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {
                    media.setDataSource(myAudios[position]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                media.prepareAsync();
                media.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
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
        return myPictures.size();
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
        if(mTopic == 0) {
            switch (position) {
                case 0:
                    title = "Dog\n(Con chó)";
                    break;
                case 1:
                    title = "Cat\n(Con mèo)";
                    break;
                case 2:
                    title = "Pig\n(Con heo)";
            }
        }

        if(mTopic == 1) {
            switch (position) {
                case 0:
                    title = "Basketball\n(Bóng rổ)";
                    break;
                case 1:
                    title = "Chess\n(Cờ vua)";
                    break;
                case 2:
                    title = "Football\n(Bóng đá)";
            }
        }

        if(mTopic == 2) {
            switch (position) {
                case 0:
                    title = "Doctor\n(Bác sĩ)";
                    break;
                case 1:
                    title = "Police\n(Công an)";
                    break;
                case 2:
                    title = "Teacher\n(Giáo viên)";
            }
        }

        return title;
    }


}
