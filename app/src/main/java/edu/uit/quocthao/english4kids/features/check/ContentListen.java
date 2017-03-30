package edu.uit.quocthao.english4kids.features.check;

import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import com.orhanobut.hawk.Hawk;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import edu.uit.quocthao.english4kids.R;
import edu.uit.quocthao.english4kids.object.ObjTopic;
import edu.uit.quocthao.english4kids.services.NetworkService;
import edu.uit.quocthao.english4kids.services.SQLiteEnglish;

@Fullscreen
@EActivity(R.layout.activity_features_check_content_listen)
public class ContentListen extends AppCompatActivity {

    @ViewById(R.id.activity_features_check_content_listen_iv_picture)
    ImageView ivPicture;

    @ViewById(R.id.activity_features_check_content_listen_ib_audio_first)
    ImageButton ibAudioFirst;

    @ViewById(R.id.activity_features_check_content_listen_ib_audio_second)
    ImageButton ibAudioSecond;

    @ViewById(R.id.activity_features_check_content_listen_ib_audio_third)
    ImageButton ibAudioThird;

    @ViewById(R.id.activity_features_check_content_listen_btn_answer_first)
    Button btnAnswerFirst;

    @ViewById(R.id.activity_features_check_content_listen_btn_answer_second)
    Button btnAnswerSecond;

    @ViewById(R.id.activity_features_check_content_listen_btn_answer_third)
    Button btnAnswerThird;

    @ViewById(R.id.activity_features_check_content_listen_tv_answer)
    TextView tvAnswer;

    @ViewById(R.id.activity_features_check_content_listen_tv_time)
    TextView tvTime;

    @InstanceState
    ArrayList<ObjTopic> listGames = new ArrayList<>();

    @InstanceState
    int lengthGames;

    @InstanceState
    int postionCorrect;

    @InstanceState
    int numQuestion;

    @InstanceState
    String answerCorrect;

    @InstanceState
    String[] arrTopics;

    @InstanceState
    int sumCorrect = 0;

    @InstanceState
    int tempCorect = 0;

    @InstanceState
    int sumAnswer = 0;

    private DatabaseReference drEnglish;

    private ObjTopic objGame;

    private MediaPlayer mediaContent;

    private CountDownTimer countTime;

    private SQLiteEnglish sqLiteEnglish;

    @AfterViews
    public void initContent() {
        Hawk.init(this).build();
        drEnglish = FirebaseDatabase.getInstance().getReference();
        arrTopics = getResources().getStringArray(R.array.topics);

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
        numQuestion = Hawk.get("numQuestion");
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

    private void loadData() {
        sqLiteEnglish = new SQLiteEnglish(this);
        listGames = sqLiteEnglish.getListEnglish();

        loadQuestion();
    }

    private void loadQuestion() {
        Random r = new Random();
        final int mPicure = r.nextInt(listGames.size());  //Load ảnh.
        postionCorrect = r.nextInt(3);              //Vị trí câu trả lời đúng.
        int mFirst, mSecond;                        //Load thêm 2 audio khác.

        do {
            mFirst = r.nextInt(listGames.size());   //Load audio thứ 1.
        } while (mFirst == mPicure);

        do {
            mSecond = r.nextInt(listGames.size());  //Load audio thứ 2.
        } while ((mSecond == mPicure) || (mSecond == mFirst));

        Picasso.with(this)
                .load(listGames.get(mPicure).getUrlPicture())
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(ivPicture, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.with(ContentListen.this)
                                .load(listGames.get(mPicure).getUrlPicture())
                                .into(ivPicture);
                    }
                });

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
                if (!NetworkService.isNetworkAvailable(ContentListen.this)) {
                    Toast.makeText(ContentListen.this, "You had to connect the Internet!", Toast.LENGTH_SHORT).show();
                } else {
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
