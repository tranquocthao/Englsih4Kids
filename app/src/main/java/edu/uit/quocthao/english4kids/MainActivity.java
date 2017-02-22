package edu.uit.quocthao.english4kids;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import edu.uit.quocthao.english4kids.features.check.FeaturesCheck;
import edu.uit.quocthao.english4kids.features.like.FeaturesLike;
import edu.uit.quocthao.english4kids.features.story.FeaturesStory;
import edu.uit.quocthao.english4kids.features.study.FeaturesStudy;

public class MainActivity extends ActionBarActivity {

    private String arrFeatures[] = null;

    private FeaturesAdapter featuresAdapter = null;

    private RecyclerView recyclerView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
