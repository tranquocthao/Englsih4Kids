package edu.uit.quocthao.english4kids.features.check;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import edu.uit.quocthao.english4kids.R;
import edu.uit.quocthao.english4kids.features.study.ContentAdapter;

public class ContentListen extends AppCompatActivity {

    private ImageView ivPicture;

    private ImageButton ibAudioa;

    private ImageButton ibAudiob;

    private ImageButton ibAudioc;

    private Button btnAnswera;

    private Button btnAnswerb;

    private Button btnAnswerc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_features_check_content_listen);

        //Ánh xạ giá trị.
        initContent();

    }

    private void initContent() {
        ivPicture = (ImageView) findViewById(
                R.id.activity_features_check_content_listen_iv_picture);
        ibAudioa = (ImageButton) findViewById(
                R.id.activity_features_check_content_listen_ib_audio_a);
        ibAudiob = (ImageButton) findViewById(
                R.id.activity_features_check_content_listen_ib_audio_b);
        ibAudioc = (ImageButton) findViewById(
                R.id.activity_features_check_content_listen_ib_audio_c);
        btnAnswera = (Button) findViewById(
                R.id.activity_features_check_content_listen_btn_answer_a);
        btnAnswerb = (Button) findViewById(
                R.id.activity_features_check_content_listen_btn_answer_b);
        btnAnswerc = (Button) findViewById(
                R.id.activity_features_check_content_listen_btn_answer_c);
    }
}
