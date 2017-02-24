package edu.uit.quocthao.english4kids.features.check;

import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import edu.uit.quocthao.english4kids.R;
import edu.uit.quocthao.english4kids.object.ObjTopic;

public class ContentListen extends AppCompatActivity {

    private ImageView ivPicture;

    private ImageButton ibAudioFirst;

    private ImageButton ibAudioSecond;

    private ImageButton ibAudioThird;

    private Button btnAnswerFirst;

    private Button btnAnswerSecond;

    private Button btnAnswerThird;

    private TextView tvAnswer;

    private TextView tvTime;

    private FirebaseDatabase fdEnglish = FirebaseDatabase.getInstance();

    private DatabaseReference drEnglish = fdEnglish.getReference();

    private ObjTopic objGame;

    private ArrayList<ObjTopic> listGames = new ArrayList<>();

    private int lengthGames;

    private String[] arrTopic = {"animal", "sport", "job"};

    private int postionCorrect;

    private MediaPlayer mediaContent;

    private String answerCorrect;

    private int sumCorrect = 0;

    private int tempCorect = 0;

    private int sumAnswer = 0;

    private CountDownTimer countTime;

    private int numQuestion = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_features_check_content_listen);

        //Ánh xạ giá trị.
        initContent();

        //Load giá trị.
        loadData();

        //Đếm ngược thời gian.
        countTimes();


        //Đáp án chọn.
        clickCorrect(btnAnswerFirst);
        clickCorrect(btnAnswerSecond);
        clickCorrect(btnAnswerThird);

    }

    private void countTimes() {
        drEnglish.child("check").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                numQuestion = Integer.parseInt(dataSnapshot.child("timeOut").getValue().toString());
                tvAnswer.setText("0/" + numQuestion);

                tvTime.setText((5000 * numQuestion) + "(s)");

                countTime = new CountDownTimer((5000 * numQuestion), 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        tvTime.setText((millisUntilFinished / 1000) + "(s)");
                    }

                    @Override
                    public void onFinish() {
                        sumAnswer = 0;
                        sumCorrect = tempCorect;
                        tempCorect = 0;
                        tvAnswer.setText("0/" + numQuestion);
                        tvTime.setText("0(s)");
                        showResult();
                    }
                }.start();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void initContent() {
        ivPicture = (ImageView) findViewById(
                R.id.activity_features_check_content_listen_iv_picture);
        ibAudioFirst = (ImageButton) findViewById(
                R.id.activity_features_check_content_listen_ib_audio_first);
        ibAudioSecond = (ImageButton) findViewById(
                R.id.activity_features_check_content_listen_ib_audio_second);
        ibAudioThird = (ImageButton) findViewById(
                R.id.activity_features_check_content_listen_ib_audio_third);
        btnAnswerFirst = (Button) findViewById(
                R.id.activity_features_check_content_listen_btn_answer_first);
        btnAnswerSecond = (Button) findViewById(
                R.id.activity_features_check_content_listen_btn_answer_second);
        btnAnswerThird = (Button) findViewById(
                R.id.activity_features_check_content_listen_btn_answer_third);
        tvAnswer = (TextView) findViewById(
                R.id.activity_features_check_content_listen_tv_answer);
        tvTime = (TextView) findViewById(
                R.id.activity_features_check_content_listen_tv_time);

    }

    private void loadData() {
        //Lấy mảng animal cho vào listGame
        drEnglish.child("study").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Lấy giá trị trong animals, sports, jobs
                for (int i = 0; i < arrTopic.length; i++) {
                    lengthGames = Integer.parseInt(dataSnapshot
                            .child(arrTopic[i] + "s").child("length").getValue().toString());

                    for (int j = 0; j < lengthGames; j++) {
                        objGame = new ObjTopic();
                        objGame.setUrlAudio(dataSnapshot.child(arrTopic[i] + "s")
                                .child(arrTopic[i] + j).child("audio").getValue().toString());
                        objGame.setUrlPicture(dataSnapshot.child(arrTopic[i] + "s")
                                .child(arrTopic[i] + j).child("picture").getValue().toString());
                        objGame.setEnWord(dataSnapshot.child(arrTopic[i] + "s")
                                .child(arrTopic[i] + j).child("word").getValue().toString());

                        listGames.add(objGame);
                    }
                }
                loadQuestion();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void loadQuestion() {
        Random r = new Random();
        int mPicure = r.nextInt(listGames.size()); //Load ảnh.
        postionCorrect = r.nextInt(3); //Vị trí câu trả lời đúng.
        int mFirst, mSecond; //Load thêm 2 audio khác.

        do {
            mFirst = r.nextInt(listGames.size()); //Load audio thứ 1.
        } while (mFirst == mPicure);

        do {
            mSecond = r.nextInt(listGames.size()); //Load audio thứ 1.
        } while ((mSecond == mPicure) || (mSecond == mFirst));

        Picasso.with(ContentListen.this)
                .load(listGames.get(mPicure).getUrlPicture())
                .into(ivPicture);

        Animation animation = AnimationUtils.loadAnimation(ContentListen.this, R.anim.anim_bounce);
        ivPicture.startAnimation(animation);

        switch (postionCorrect) {
            case 0:
                answerCorrect = "Câu A";
                mediaPlay(ibAudioFirst, mPicure);
                mediaPlay(ibAudioSecond, mFirst);
                mediaPlay(ibAudioThird, mSecond);
                break;
            case 1:
                answerCorrect = "Câu B";
                mediaPlay(ibAudioFirst, mFirst);
                mediaPlay(ibAudioSecond, mPicure);
                mediaPlay(ibAudioThird, mSecond);
                break;
            case 2:
                answerCorrect = "Câu C";
                mediaPlay(ibAudioFirst, mFirst);
                mediaPlay(ibAudioSecond, mSecond);
                mediaPlay(ibAudioThird, mPicure);
                break;
        }
    }

    private void mediaPlay(final ImageButton ibClick, final int k) {
        ibClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ibClick.setImageResource(R.drawable.ic_check_listen_pause);
                mediaContent = new MediaPlayer();
                mediaContent.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {
                    mediaContent.setDataSource(listGames.get(k).getUrlAudio());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaContent.prepareAsync();
                mediaContent.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp3) {
                        mp3.start();

                    }

                });
                mediaContent.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                            ibClick.setImageResource(R.drawable.ic_check_listen_play);
                    }
                });

            }

        });
    }

    private void clickCorrect(final Button btnClick) {
        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sumAnswer++;
                if (btnClick.getText().toString().equals(answerCorrect)) {
                    tempCorect++;
                    MediaPlayer mediaCorrect = MediaPlayer.create(ContentListen.this, R.raw.check_correct);
                    mediaCorrect.start();
                } else {
                    MediaPlayer mediaFail = MediaPlayer.create(ContentListen.this, R.raw.check_fail);
                    mediaFail.start();
                }

                tvAnswer.setText(sumAnswer + "/" + numQuestion);
                if (sumAnswer >= numQuestion) {
                    sumAnswer = 0;
                    sumCorrect = tempCorect;
                    tempCorect = 0;
                    tvAnswer.setText("0/" + numQuestion);
                    showResult();
                }
                loadQuestion();
            }
        });
    }

    private void showResult() {
        countTime.cancel();
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(ContentListen.this);
        alertDialog.setTitle("Kết quả");
        alertDialog.setMessage("Bạn trả lời đúng: " + sumCorrect + "/" + numQuestion);
        alertDialog.setIcon(R.drawable.ic_result);
        alertDialog.setNegativeButton("Làm lại",
                new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, int which) {
                        countTime.start();
                        dialog.cancel();
                    }
                });
        alertDialog.setPositiveButton("Kết thúc",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        alertDialog.show().setCanceledOnTouchOutside(false);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        countTime.cancel();
        finish();
    }
}
