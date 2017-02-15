package edu.uit.quocthao.english4kids.features.check;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import edu.uit.quocthao.english4kids.R;
import edu.uit.quocthao.english4kids.features.study.ContentAdapter;

public class ContentListen extends AppCompatActivity {

    ViewPager vpListen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_features_check_content_listen);

        vpListen = (ViewPager) findViewById(R.id.activity_features_check_content_listen_vp_picture);
        final ContentGameAdapter adapter = new ContentGameAdapter(this);
        vpListen.setAdapter(adapter);

    }
}
