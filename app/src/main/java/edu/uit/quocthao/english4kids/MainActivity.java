package edu.uit.quocthao.english4kids;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
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
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.hawk.Hawk;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import edu.uit.quocthao.english4kids.features.check.FeaturesCheck_;
import edu.uit.quocthao.english4kids.features.like.FeaturesLike_;
import edu.uit.quocthao.english4kids.features.story.FeaturesStory_;
import edu.uit.quocthao.english4kids.features.study.FeaturesStudy_;
import edu.uit.quocthao.english4kids.object.ObjTopic;
import edu.uit.quocthao.english4kids.services.LoadDataService;
import edu.uit.quocthao.english4kids.services.NetworkService;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.widget.Toast.makeText;

@Fullscreen
@EActivity(R.layout.activity_main)
public class MainActivity extends ActionBarActivity {

    @InstanceState
    String arrFeatures[] = null;

    FeaturesAdapter featuresAdapter = null;

    @ViewById(R.id.activity_main_rv_feature)
    RecyclerView recyclerView;

    private TextView tvSetting;

    private CrystalSeekbar sbSetting;

    private Button btnSetting;

    private int numQuestion;

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        LayoutInflater li = LayoutInflater.from(this);
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
                tvSetting.setText("* Lựa chọn số câu hỏi: (" + value + ")");
                numQuestion = Integer.parseInt(value.toString());
            }
        });

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hawk.init(MainActivity.this).build();

                Hawk.put("numQuestion", numQuestion);
                dialog.cancel();
            }
        });
        return super.onOptionsItemSelected(item);
    }

    @AfterViews
    public void initContent(){

        arrFeatures = getResources().getStringArray(R.array.features);

        //Tạo recycleView
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
                intent = new Intent(this, FeaturesStudy_.class);
                startActivity(intent);
                break;
            case 1:
                intent = new Intent(this, FeaturesLike_.class);
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(this, FeaturesStory_.class);
                startActivity(intent);
                break;
            case 3:
                intent = new Intent(this, FeaturesCheck_.class);
                startActivity(intent);
                break;
        }
    }
}
