package edu.uit.quocthao.english4kids;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalSeekbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.uit.quocthao.english4kids.features.check.FeaturesCheck;
import edu.uit.quocthao.english4kids.features.like.FeaturesLike;
import edu.uit.quocthao.english4kids.features.story.FeaturesStory;
import edu.uit.quocthao.english4kids.features.study.FeaturesStudy;

import static android.widget.Toast.makeText;

public class MainActivity extends ActionBarActivity {

    private String arrFeatures[] = null;

    private FeaturesAdapter featuresAdapter = null;

    private RecyclerView recyclerView;

    private TextView tvSetting;

    private CrystalSeekbar sbSetting;

    private Button btnSetting;

    private String timeOut = "10";

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_main, menu);
        return true;
    }

    ProgressDialog progressBar;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        LayoutInflater li = LayoutInflater.from(MainActivity.this);
        View view = li.inflate(R.layout.activity_features_setting, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();

        tvSetting = (TextView) view.findViewById(R.id.tv_setting);
        sbSetting = (CrystalSeekbar) view.findViewById(R.id.rangeSeekbar);
        btnSetting = (Button) view.findViewById(R.id.btn_setting);

        sbSetting.setOnSeekbarChangeListener(new OnSeekbarChangeListener() {
            @Override
            public void valueChanged(Number value) {
                tvSetting.setText("* Option number question: (" + value + ")");
                timeOut = value.toString();
            }
        });

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference drEnglish = FirebaseDatabase
                        .getInstance().getReference().child("check");

                drEnglish.child("timeOut").setValue(timeOut);
                dialog.cancel();
            }
        });
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!isNetworkAvailable(this)){
            makeText(this, "Network is not available!", Toast.LENGTH_LONG).show();
        }

        arrFeatures = getResources().getStringArray(R.array.features);

        //Tạo recycleView
        recyclerView = (RecyclerView) findViewById(R.id.activity_main_rv_feature);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        featuresAdapter = new FeaturesAdapter(arrFeatures);
        recyclerView.setAdapter(featuresAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                choiceFeatures(position);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }

    private void choiceFeatures(int position) {
        Intent intent;
        switch (position) {
            case 0:
                intent = new Intent(MainActivity.this, FeaturesStudy.class);
                startActivity(intent);
                break;
            case 1:
                intent = new Intent(MainActivity.this, FeaturesLike.class);
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(MainActivity.this, FeaturesStory.class);
                startActivity(intent);
                break;
            case 3:
                intent = new Intent(MainActivity.this, FeaturesCheck.class);
                startActivity(intent);
                break;
        }
    }
}