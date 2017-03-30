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
import com.orhanobut.hawk.Hawk;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import edu.uit.quocthao.english4kids.R;
import edu.uit.quocthao.english4kids.features.check.ContentListen;
import edu.uit.quocthao.english4kids.object.ObjTopic;
import edu.uit.quocthao.english4kids.services.ILikeUpdate;
import edu.uit.quocthao.english4kids.services.NetworkService;

public class LikeAdapter extends RecyclerView.Adapter<LikeAdapter.LikeViewHolder> {

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

    private ILikeUpdate iLikeUpdate;

    public LikeAdapter(Context context, ArrayList<ObjTopic> listLikes) {
        this.contextLike = context;
        this.listLikes = listLikes;

        iLikeUpdate = (ILikeUpdate) context;
    }

    @Override
    public LikeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_features_like_adapter, parent, false);
        return new edu.uit.quocthao.english4kids.features.like.LikeAdapter.LikeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final LikeViewHolder holder, final int position) {

        Picasso.with(contextLike)
                .load(listLikes.get(position).getUrlPicture())
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(holder.ivPicture, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.with(contextLike)
                                .load(listLikes.get(position).getUrlPicture())
                                .into(holder.ivPicture);
                    }
                });

        Animation animation1 = AnimationUtils.loadAnimation(contextLike, R.anim.anim_combine_like);
        holder.ivPicture.startAnimation(animation1);

        holder.tvWord.setText(listLikes.get(position).getEnWord());
        Animation animation2 = AnimationUtils.loadAnimation(contextLike, R.anim.anim_combine);
        holder.tvWord.startAnimation(animation2);

        holder.layoutSurface.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!NetworkService.isNetworkAvailable(contextLike)) {
                    Toast.makeText(contextLike, "You had to connect the Internet!", Toast.LENGTH_SHORT).show();
                } else {
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
            }
        });

        holder.layoutBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(contextLike, "Deleted " +
                        listLikes.get(position).getEnWord(), Toast.LENGTH_SHORT).show();
                Hawk.put(listLikes.get(position).getEnWord(), "false");
                listLikes.remove(position);

                iLikeUpdate.updateLike(listLikes);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listLikes.size();
    }

}

