package edu.uit.quocthao.english4kids.features.check;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import edu.uit.quocthao.english4kids.R;

public class ContentWrite extends AppCompatActivity {

    ViewPager vpWrite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_features_check_content_write);

        vpWrite = (ViewPager) findViewById(R.id.activity_features_check_content_write_vp_picture);
        final ContentGameAdapter adapter = new ContentGameAdapter(this);
        vpWrite.setAdapter(adapter);
    }
}
