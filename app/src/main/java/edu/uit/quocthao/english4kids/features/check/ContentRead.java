package edu.uit.quocthao.english4kids.features.check;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

import edu.uit.quocthao.english4kids.R;
import edu.uit.quocthao.english4kids.object.ObjTopic;

public class ContentRead extends AppCompatActivity {

    private ImageView ivPicture;

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

    private int mPicure = 0;

    private int sumCorrect = 0;

    private int tempCorect = 0;

    private int sumAnswer = 0;

    private CountDownTimer countTime;

    private Handler handler = new Handler();

    private int numQuestion = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_features_check_content_read);

        initContent();

        loadData();

        countTimes();


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
                R.id.activity_features_check_content_read_iv_picture);
        btnAnswerFirst = (Button) findViewById(
                R.id.activity_features_check_content_read_btn_first);
        btnAnswerSecond = (Button) findViewById(
                R.id.activity_features_check_content_read_btn_second);
        btnAnswerThird = (Button) findViewById(
                R.id.activity_features_check_content_read_btn_third);
        tvAnswer = (TextView) findViewById(
                R.id.activity_features_check_content_read_tv_answer);
        tvTime = (TextView) findViewById(
                R.id.activity_features_check_content_read_tv_time);

    }

    private void loadData() {
        //Lấy mảng animal cho vào listGame
        drEnglish.child("study").addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void loadQuestion() {

        btnAnswerFirst.setBackground(getResources().getDrawable(R.drawable.btn_answer));
        btnAnswerSecond.setBackground(getResources().getDrawable(R.drawable.btn_answer));
        btnAnswerThird.setBackground(getResources().getDrawable(R.drawable.btn_answer));

        Random r = new Random();
        mPicure = r.nextInt(listGames.size()); //Load ảnh.
        postionCorrect = r.nextInt(3); //Vị trí câu trả lời đúng.
        int mFirst, mSecond; //Load thêm 2 từ khác.

        do {
            mFirst = r.nextInt(listGames.size()); //Load audio thứ 1.
        } while (mFirst == mPicure);

        do {
            mSecond = r.nextInt(listGames.size()); //Load audio thứ 1.
        } while ((mSecond == mPicure) || (mSecond == mFirst));

        Picasso.with(ContentRead.this)
                .load(listGames.get(mPicure).getUrlPicture())
                .into(ivPicture);
        Animation animation = AnimationUtils.loadAnimation(ContentRead.this, R.anim.anim_combine);
        ivPicture.startAnimation(animation);


        switch (postionCorrect) {
            case 0:
                btnAnswerFirst.setText(listGames.get(mPicure).getEnWord());
                btnAnswerSecond.setText(listGames.get(mFirst).getEnWord());
                btnAnswerThird.setText(listGames.get(mSecond).getEnWord());
                break;
            case 1:
                btnAnswerFirst.setText(listGames.get(mFirst).getEnWord());
                btnAnswerSecond.setText(listGames.get(mPicure).getEnWord());
                btnAnswerThird.setText(listGames.get(mSecond).getEnWord());
                break;
            case 2:
                btnAnswerFirst.setText(listGames.get(mFirst).getEnWord());
                btnAnswerSecond.setText(listGames.get(mSecond).getEnWord());
                btnAnswerThird.setText(listGames.get(mPicure).getEnWord());
                break;
        }
    }

    private void clickCorrect(final Button btnClick) {
        btnClick.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                sumAnswer++;
                if (btnClick.getText().toString().equals(listGames.get(mPicure).getEnWord())) {
                    tempCorect++;
                    btnClick.setBackground(getResources().getDrawable(R.drawable.btn_correct));
                    MediaPlayer mediaCorrect = MediaPlayer.create(ContentRead.this, R.raw.check_correct);
                    mediaCorrect.start();
                } else {
                    btnClick.setBackground(getResources().getDrawable(R.drawable.btn_fail));
                    MediaPlayer mediaFail = MediaPlayer.create(ContentRead.this, R.raw.check_fail);
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

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (btnClick.getText().toString().equals(listGames.get(mPicure).getEnWord())) {
                            btnClick.setBackground(getResources().getDrawable(R.drawable.btn_correct));
                        } else {
                            btnClick.setBackground(getResources().getDrawable(R.drawable.btn_fail));
                        }
                        loadQuestion();
                    }
                }, 100);
            }
        });
    }

    private void showResult() {
        countTime.cancel();
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ContentRead.this);
        alertDialog.setTitle("Kết quả");
        alertDialog.setMessage("Bạn trả lời đúng: " + sumCorrect + "/10");
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
                }).show().setCanceledOnTouchOutside(false);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        countTime.cancel();
        finish();
    }
}
