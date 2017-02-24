package edu.uit.quocthao.english4kids.features.like;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import edu.uit.quocthao.english4kids.R;
import edu.uit.quocthao.english4kids.features.check.ContentListen;
import edu.uit.quocthao.english4kids.object.ObjTopic;

public class LikeAdapter extends RecyclerView.Adapter<LikeAdapter.LikeViewHolder> {
    private int lengthLikes;

    private String[] arrTopic = {"animal", "sport", "job"};

    private DatabaseReference drEnglish;

    private String enWord;

    public class LikeViewHolder extends RecyclerView.ViewHolder {

        protected ImageView ivPicture;

        protected TextView tvWord;

        protected LinearLayout layoutSurface;

        protected LinearLayout layoutBottom;

        public LikeViewHolder(View convertView) {
            super(convertView);

            layoutSurface = (LinearLayout) convertView.findViewById(R.id.like_surface_wrapper);
            layoutBottom = (LinearLayout) convertView.findViewById(R.id.like_bottom_wrapper);
            ivPicture = (ImageView) convertView
                    .findViewById(R.id.adapter_features_like_cardview_iv_pivture);
            tvWord = (TextView) convertView
                    .findViewById(R.id.adapter_features_like_cardview_tv_word);

        }
    }

    private ArrayList<ObjTopic> listLikes = new ArrayList<>();

    private Context contextLike;

    public LikeAdapter(Context context, ArrayList<ObjTopic> listLikes) {
        this.contextLike = context;
        this.listLikes = listLikes;
    }

    @Override
    public LikeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_features_like_adapter, parent, false);
        return new edu.uit.quocthao.english4kids.features.like.LikeAdapter.LikeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LikeViewHolder holder, final int position) {
        Picasso.with(contextLike)
                .load(listLikes.get(position).getUrlPicture())
                .into(holder.ivPicture);
        Animation animation1 = AnimationUtils.loadAnimation(contextLike, R.anim.anim_combine_like);
        holder.ivPicture.startAnimation(animation1);

        holder.tvWord.setText(listLikes.get(position).getEnWord());
        Animation animation2 = AnimationUtils.loadAnimation(contextLike, R.anim.anim_combine);
        holder.tvWord.startAnimation(animation2);

        holder.layoutSurface.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mediaContent = new MediaPlayer();
                mediaContent.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {
                    mediaContent.setDataSource(listLikes.get(position).getUrlAudio());
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

        holder.layoutBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(contextLike, "đã xóa " + position, Toast.LENGTH_SHORT).show();
                deleteLike(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listLikes.size();
    }

    public void deleteLike(final int position) {
        drEnglish = FirebaseDatabase.getInstance().getReference();
        enWord = listLikes.get(position).getEnWord();

        drEnglish.child("study").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Lấy giá trị trong animals, sports, jobs
                for (int i = 0; i < arrTopic.length; i++) {
                    lengthLikes = Integer.parseInt(dataSnapshot
                            .child(arrTopic[i] + "s").child("length").getValue().toString());

                    for (int j = 0; j < lengthLikes; j++) {
                        String checkLike = dataSnapshot.child(arrTopic[i] + "s")
                                .child(arrTopic[i] + j).child("word").getValue().toString();

                        if (checkLike.equals(enWord)) {

                            drEnglish.child("study").child(arrTopic[i] + "s")
                                    .child(arrTopic[i] + j).child("isLike").setValue("false");

                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}

