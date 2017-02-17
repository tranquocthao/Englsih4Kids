package edu.uit.quocthao.english4kids.features.check;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import edu.uit.quocthao.english4kids.R;
import edu.uit.quocthao.english4kids.features.study.ContentAdapter;
import edu.uit.quocthao.english4kids.object.ObjTopic;

public class ContentListen extends AppCompatActivity {

    private ImageView ivPicture;

    private ImageButton ibAudioa;

    private ImageButton ibAudiob;

    private ImageButton ibAudioc;

    private Button btnAnswera;

    private Button btnAnswerb;

    private Button btnAnswerc;

    private FirebaseDatabase fdEnglish = FirebaseDatabase.getInstance();

    private DatabaseReference drEnglish = fdEnglish.getReference();

    private ObjTopic objGame;

    private ArrayList<ObjTopic> listGames;

    private int lengthGames;

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

    private void loadData() {

        //Lấy mảng animal cho vào listGame
        drEnglish.child("study").child("animals").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lengthGames = Integer.parseInt(dataSnapshot.child("length").getValue().toString());

                for (int i = 0; i < lengthGames; i++) {
                    objGame = new ObjTopic();
                    objGame.setUrlAudio(dataSnapshot.child("animal" + i)
                            .child("audio").getValue().toString());
                    objGame.setUrlPicture(dataSnapshot.child("animal" + i)
                            .child("picture").getValue().toString());
                    objGame.setEnWord(dataSnapshot.child("animal" + i)
                            .child("word").getValue().toString());

                    listGames.add(objGame);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
