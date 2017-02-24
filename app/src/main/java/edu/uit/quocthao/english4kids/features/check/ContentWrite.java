package edu.uit.quocthao.english4kids.features.check;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import edu.uit.quocthao.english4kids.KeyboardUtil;
import edu.uit.quocthao.english4kids.R;
import edu.uit.quocthao.english4kids.object.ObjTopic;

public class ContentWrite extends AppCompatActivity {

    private LinearLayout layoutWrite;

    private ImageView ivPicture;

    private ImageButton ibReset;

    private ImageButton ibSubmit;

    private EditText etAnswer;

    private TextView tvAnswer;

    private TextView tvTime;

    private FirebaseDatabase fdEnglish = FirebaseDatabase.getInstance();

    private DatabaseReference drEnglish = fdEnglish.getReference();

    private ObjTopic objGame;

    private ArrayList<ObjTopic> listGames = new ArrayList<>();

    private int lengthGames;

    private String[] arrTopic = {"animal", "sport", "job"};

    private int mPicure = 0;

    private int sumCorrect = 0;

    private int tempCorect = 0;

    private int sumAnswer = 0;

    private CountDownTimer countTime;

    private KeyboardUtil kbWrite;

    private int numQuestion = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_features_check_content_write);

        initContent();

        loadData();

        countTimes();

        clickCorrect(ibReset);
        clickCorrect(ibSubmit);

        layoutWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kbWrite = new KeyboardUtil();
                kbWrite.hideSoftKeyboard(ContentWrite.this);
            }
        });
    }

    private void initContent() {

        layoutWrite = (LinearLayout) findViewById(R.id.activity_features_check_content_write);

        ivPicture = (ImageView) findViewById(
                R.id.activity_features_check_content_write_iv_picture);
        ibReset = (ImageButton) findViewById(
                R.id.activity_features_check_content_write_ib_reset);
        ibSubmit = (ImageButton) findViewById(
                R.id.activity_features_check_content_write_ib_submit);
        etAnswer = (EditText) findViewById(
                R.id.activity_features_check_content_write_et_answer);
        tvAnswer = (TextView) findViewById(
                R.id.activity_features_check_content_read_tv_answer);
        tvTime = (TextView) findViewById(
                R.id.activity_features_check_content_read_tv_time);


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
        mPicure = r.nextInt(listGames.size()); //Load ảnh.

        Picasso.with(ContentWrite.this)
                .load(listGames.get(mPicure).getUrlPicture())
                .into(ivPicture);
        Animation animation = AnimationUtils.loadAnimation(ContentWrite.this, R.anim.anim_slide_out);
        ivPicture.startAnimation(animation);
    }

    private void clickCorrect(final ImageButton ibClick) {
        ibClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.activity_features_check_content_write_ib_submit:
                        sumAnswer++;
                        if (etAnswer.getText().toString().trim().toLowerCase()
                                .equals(listGames.get(mPicure).getEnWord().toLowerCase())) {
                            tempCorect++;
                            MediaPlayer mediaCorrect = MediaPlayer.create(
                                    ContentWrite.this, R.raw.check_correct);
                            mediaCorrect.start();
                        } else {
                            MediaPlayer mediaFail = MediaPlayer.create(
                                    ContentWrite.this, R.raw.check_fail);
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
                        break;
                    case R.id.activity_features_check_content_write_ib_reset:
                        etAnswer.requestFocus();
                        break;
                }
                etAnswer.setText("");
            }
        });
    }

    private void showResult() {
        countTime.cancel();
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ContentWrite.this);
        alertDialog.setTitle("Kết quả");
        alertDialog.setMessage("Bạn trả lời đúng: " + sumCorrect + "/10");
        alertDialog.setIcon(R.drawable.ic_result);
        alertDialog.setNegativeButton("Làm lại",
                new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, int which) {
                        countTime.start();
                        dialog.cancel();
                    }
                }).setPositiveButton("Kết thúc",
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