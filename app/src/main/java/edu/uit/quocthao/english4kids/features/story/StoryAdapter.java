package edu.uit.quocthao.english4kids.features.story;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import edu.uit.quocthao.english4kids.R;

/**
 * Created by Quoc Thao on 2/21/2017.
 */

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.StoryViewHolder> {

    private Context contextStory;

    private ArrayList<ObjectStory> listStories = new ArrayList<>();

    public StoryAdapter(Context context, ArrayList<ObjectStory> listStories) {
        this.contextStory = context;
        this.listStories = listStories;
    }

    @Override
    public StoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_features_story_adapter, parent, false);
        return new edu.uit.quocthao.english4kids.features.story.
                StoryAdapter.StoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StoryAdapter.StoryViewHolder holder, int position) {
        Picasso.with(contextStory)
                .load(listStories.get(position).getUrlPicture())
                .into(holder.ivPicture);
        holder.tvWord.setText(listStories.get(position).getTitleVi());

        Animation animation1 = AnimationUtils.loadAnimation(contextStory, R.anim.anim_combine_like);
        holder.ivPicture.startAnimation(animation1);
        Animation animation2 = AnimationUtils.loadAnimation(contextStory, R.anim.anim_combine);
        holder.tvWord.startAnimation(animation2);
    }

    @Override
    public int getItemCount() {
        return listStories.size();
    }

    public class StoryViewHolder extends RecyclerView.ViewHolder {

        protected ImageView ivPicture;

        protected TextView tvWord;

        public StoryViewHolder(View itemView) {
            super(itemView);

            ivPicture = (ImageView) itemView
                    .findViewById(R.id.adapter_features_story_content_iv_picture);
            tvWord = (TextView) itemView
                    .findViewById(R.id.adapter_features_story_content_tv_title);
        }
    }
}
