package edu.uit.quocthao.english4kids.features.check;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import edu.uit.quocthao.english4kids.R;

public class ContentRead extends AppCompatActivity {

    ViewPager vpRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_features_check_content_read);

        vpRead = (ViewPager) findViewById(R.id.activity_features_check_content_read_vp_picture);
        final ContentGameAdapter adapter = new ContentGameAdapter(this);
        vpRead.setAdapter(adapter);
    }
}
