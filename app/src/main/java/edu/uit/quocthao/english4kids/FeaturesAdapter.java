package edu.uit.quocthao.english4kids;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class FeaturesAdapter extends RecyclerView.Adapter<FeaturesAdapter.FeatureViewHolder> {

    private ArrayList<String> listFeatures = new ArrayList<>();

    public FeaturesAdapter(String[] arrFeatures) {
        for (int i = 0; i < arrFeatures.length; i++) {
            listFeatures.add(arrFeatures[i]);
        }
    }

    @Override
    public FeatureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_features, parent, false);
        return new FeatureViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FeatureViewHolder holder, int position) {

        holder.tvFeature.setText(listFeatures.get(position));
        switch (position) {
            case 0:
                holder.ivFeature.setImageResource(R.drawable.ic_features_study);
                break;

            case 1:
                holder.ivFeature.setImageResource(R.drawable.ic_features_like);
                break;

            case 2:
                holder.ivFeature.setImageResource(R.drawable.ic_features_story);
                break;

            case 3:
                holder.ivFeature.setImageResource(R.drawable.ic_features_check);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return listFeatures.size();
    }

    public class FeatureViewHolder extends RecyclerView.ViewHolder {

        protected ImageView ivFeature;

        protected TextView tvFeature;

        public FeatureViewHolder(View convertView) {
            super(convertView);

            ivFeature = (ImageView) convertView
                    .findViewById(R.id.adapter_features_iv_feature);
            tvFeature = (TextView) convertView
                    .findViewById(R.id.adapter_features_tv_feature);

        }
    }

}

