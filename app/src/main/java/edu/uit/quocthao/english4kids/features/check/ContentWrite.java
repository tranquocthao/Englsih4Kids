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
import android.widget.Button;
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
import com.orhanobut.hawk.Hawk;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Random;

import edu.uit.quocthao.english4kids.KeyboardUtil;
import edu.uit.quocthao.english4kids.R;
import edu.uit.quocthao.english4kids.object.ObjTopic;
import edu.uit.quocthao.english4kids.services.NetworkService;
import edu.uit.quocthao.english4kids.services.SQLiteEnglish;

@Fullscreen
@EActivity(R.layout.activity_features_check_content_write)
public class ContentWrite extends AppCompatActivity {

    @ViewById(R.id.activity_features_check_content_write)
    LinearLayout layoutWrite;

    @ViewById(R.id.activity_features_check_content_write_iv_picture)
    ImageView ivPicture;

    @ViewById(R.id.activity_features_check_content_write_tv_answer)
    TextView tvAnswer;

    @ViewById(R.id.activity_features_check_content_write_tv_time)
    TextView tvTime;

    @ViewById(R.id.activity_features_check_content_write_et_answer)
    EditText etAnswer;

    @ViewById(R.id.activity_features_check_content_write_ib_reset)
    ImageButton ibReset;

    @ViewById(R.id.activity_features_check_content_write_ib_submit)
    ImageButton ibSubmit;

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

    @InstanceState
    int mPicure = 0;

    private DatabaseReference drEnglish;

    private ObjTopic objGame;

    private CountDownTimer countTime;

    private KeyboardUtil kbWrite;

    private SQLiteEnglish sqLiteEnglish;

    @AfterViews
    public void initContent() {
        Hawk.init(this).build();
        drEnglish = FirebaseDatabase.getInstance().getReference();
        arrTopics = getResources().getStringArray(R.array.topics);

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
        mPicure = r.nextInt(listGames.size()); //Load ảnh.

        Picasso.with(this)
                .load(listGames.get(mPicure).getUrlPicture())
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(ivPicture, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.with(ContentWrite.this)
                                .load(listGames.get(mPicure).getUrlPicture())
                                .into(ivPicture);
                    }
                });

        Animation animation = AnimationUtils.loadAnimation(ContentWrite.this, R.anim.anim_slide_out);
        ivPicture.startAnimation(animation);
    }

    private void clickCorrect(final ImageButton ibClick) {
        ibClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!NetworkService.isNetworkAvailable(ContentWrite.this)) {
                    Toast.makeText(ContentWrite.this, "You had to connect the Internet!", Toast.LENGTH_SHORT).show();
                } else {
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