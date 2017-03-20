package edu.uit.quocthao.english4kids.features.study;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import edu.uit.quocthao.english4kids.R;

public class ListPictureAdapter extends RecyclerView.Adapter<ListPictureAdapter.ListPictureViewHolder> {

    private ArrayList<String> listFeatures = new ArrayList<>();

    private Context contextStudy;

    public ListPictureAdapter(Context context, ArrayList<String> arrFeatures) {
        contextStudy = context;
        listFeatures = arrFeatures;
    }

    @Override
    public ListPictureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_features_study_content_list_picture, parent, false);
        return new ListPictureViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListPictureViewHolder holder, int position) {

        Picasso.with(contextStudy)
                .load(listFeatures.get(position))
                .into(holder.ivPicture);

        Animation animation = AnimationUtils.loadAnimation(contextStudy, R.anim.anim_combine_like);
        holder.ivPicture.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return listFeatures.size();
    }

    public class ListPictureViewHolder extends RecyclerView.ViewHolder {

        protected ImageView ivPicture;

        public ListPictureViewHolder(View convertView) {
            super(convertView);

            ivPicture = (ImageView) convertView
                    .findViewById(R.id.activity_content_iv_picture);

        }
    }

}
