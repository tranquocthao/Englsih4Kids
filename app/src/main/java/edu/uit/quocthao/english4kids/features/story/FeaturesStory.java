package edu.uit.quocthao.english4kids.features.story;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

import edu.uit.quocthao.english4kids.R;

public class FeaturesStory extends AppCompatActivity {

    private ListView lvNames;

    private ArrayList<String> arrNames;

    private ArrayAdapter<String> adapterNames;

    private Bundle bundleStory;

    private Intent intentStory;

    private FirebaseDatabase fdStory = FirebaseDatabase.getInstance();

    private DatabaseReference drStory = fdStory.getReference();

    private ObjectStory objStory;

    private ArrayList<ObjectStory> listStories = new ArrayList<>();

    private int lengthStory = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_features_story);

        lvNames = (ListView) findViewById(R.id.activity_features_story_lv_names);

        loadStory();

        intentStory = new Intent(FeaturesStory.this, ContentStory.class);
        arrNames = new ArrayList<String>();

        lvNames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bundleStory = new Bundle();
                bundleStory.putSerializable("story", listStories.get(position));
                intentStory.putExtra("stories", bundleStory);
                startActivity(intentStory);
            }
        });
    }

    private void loadStory() {
        drStory.child("story").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lengthStory = Integer.parseInt(dataSnapshot.child("length").getValue().toString());

                for (int i = 0; i < lengthStory; i++) {
                    objStory = new ObjectStory();

                    objStory.setTitleVi(dataSnapshot.child("story" + i)
                            .child("vietnam").child("title").getValue().toString());
                    objStory.setTitleEn(dataSnapshot.child("story" + i)
                            .child("english").child("title").getValue().toString());
                    objStory.setBodyVi(dataSnapshot.child("story" + i)
                            .child("vietnam").child("body").getValue().toString());
                    objStory.setBodyEn(dataSnapshot.child("story" + i)
                            .child("english").child("body").getValue().toString());

                    listStories.add(objStory);
                }

                for (int i = 0; i < lengthStory; i++) {
                    arrNames.add(listStories.get(i).getTitleVi());
                }
                adapterNames = new ArrayAdapter<String>(
                        FeaturesStory.this, android.R.layout.simple_list_item_1, arrNames);

                lvNames.setAdapter(adapterNames);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
