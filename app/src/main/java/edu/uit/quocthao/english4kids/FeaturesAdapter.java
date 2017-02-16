package edu.uit.quocthao.english4kids;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class FeaturesAdapter extends ArrayAdapter<String> {

    private Activity contextFeatures = null;

    private int layoutId;

    private String[] arrFeatures;

    public FeaturesAdapter(Activity context, int layoutId, String[] arrFeatures) {
        super(context, layoutId, arrFeatures);

        this.contextFeatures = context;
        this.layoutId = layoutId;
        this.arrFeatures = arrFeatures;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = contextFeatures.getLayoutInflater();
        convertView = inflater.inflate(R.layout.adapter_features, null);

        ImageView ivFeature = (ImageView) convertView
                .findViewById(R.id.adapter_features_iv_feature);
        TextView tvFeature = (TextView) convertView
                .findViewById(R.id.adapter_features_tv_feature);

        switch (position) {
            case 0:
                ivFeature.setImageResource(R.drawable.ic_features_study);
                tvFeature.setText(arrFeatures[0]);
                break;

            case 1:
                ivFeature.setImageResource(R.drawable.ic_features_like);
                tvFeature.setText(arrFeatures[1]);
                break;

            case 2:
                ivFeature.setImageResource(R.drawable.ic_features_story);
                tvFeature.setText(arrFeatures[2]);
                break;

            case 3:
                ivFeature.setImageResource(R.drawable.ic_features_check);
                tvFeature.setText(arrFeatures[3]);
                break;

            case 4:
                ivFeature.setImageResource(R.drawable.ic_features_setting);
                tvFeature.setText(arrFeatures[4]);
                break;
        }

        return convertView;
    }
}
