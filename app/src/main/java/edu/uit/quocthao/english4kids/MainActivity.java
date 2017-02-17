package edu.uit.quocthao.english4kids;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import edu.uit.quocthao.english4kids.features.check.FeaturesCheck;
import edu.uit.quocthao.english4kids.features.story.FeaturesStory;
import edu.uit.quocthao.english4kids.features.study.FeaturesStudy;

public class MainActivity extends ActionBarActivity {

    private String arrFeatures[] = null;

    private ListView lvFeatures = null;

    private FeaturesAdapter featuresAdapter = null;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Toast.makeText(this, "You click setting", Toast.LENGTH_SHORT).show();

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrFeatures = getResources().getStringArray(R.array.features);

        lvFeatures = (ListView) findViewById(R.id.activity_main_lv_features);
        //show các tính năng chính lên listview.
        featuresAdapter = new FeaturesAdapter(this, R.layout.adapter_features, arrFeatures);
        lvFeatures.setAdapter(featuresAdapter);

        //các sự lựa chọn tính năng.
        lvFeatures.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;

                switch (position) {
                    case 0:
                        intent = new Intent(MainActivity.this, FeaturesStudy.class);
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
        });

    }
}
