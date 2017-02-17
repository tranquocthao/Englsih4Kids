package edu.uit.quocthao.english4kids.features.story;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import edu.uit.quocthao.english4kids.R;

public class ContentStory extends AppCompatActivity {

    private TextView tvTitle;

    private TextView tvBody;

    private ObjectStory objStory = new ObjectStory();

    private ImageButton ibLanguage;

    private int statuStory = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_features_story_content);

        initStory();
        //lấy intent gọi Activity này
        Intent intentCaller = getIntent();
        //có intent rồi thì lấy Bundle dựa vào story
        Bundle bundle = intentCaller.getBundleExtra("stories");
        //có Bundle rồi lấy thông số dựa vào position
        objStory = (ObjectStory) bundle.getSerializable("story");
        //tiến hành tìm tựa đề truyện thích hợp
        tvTitle.setText(objStory.getTitleVi());
        //tiến hành tìm đoạn truyện tương thích
        tvBody.setText(objStory.getBodyVi());
        //nút Language
        ibLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //thay đổi icon và nội dung Việt và Anh
                if ((statuStory % 2) == 0) {
                    //tiến hành tìm tựa đề truyện thích hợp
                    tvTitle.setText(objStory.getTitleEn());
                    //tiến hành tìm đoạn truyện tương thích
                    tvBody.setText(objStory.getBodyEn());
                    //hình Logo
                    ibLanguage.setImageResource(R.drawable.ic_language_vietnam);
                } else {
                    //tiến hành tìm tựa đề truyện thích hợp
                    tvTitle.setText(objStory.getTitleVi());
                    //tiến hành tìm đoạn truyện tương thích
                    tvBody.setText(objStory.getBodyVi());
                    //hình Logo
                    ibLanguage.setImageResource(R.drawable.ic_language_american);
                }
                statuStory++;
            }
        });
    }

    private void initStory() {
        tvTitle = (TextView) findViewById(R.id.activity_feature_story_content_tv_title);
        tvBody = (TextView) findViewById(R.id.activity_feature_story_content_tv_body);
        ibLanguage = (ImageButton) findViewById(R.id.activity_feature_story_content_ib_language);
    }
}
