package edu.uit.quocthao.english4kids.features.story;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

import edu.uit.quocthao.english4kids.R;
import edu.uit.quocthao.english4kids.RecyclerItemClickListener;

public class FeaturesStory extends AppCompatActivity {

    private TextView tvTitle;

    private TextView tvContent;

    private ImageView ivLanguage;

    private ArrayList<String> listNames;

    private Bundle bundleStory;

    private Intent intentStory;

    private FirebaseDatabase fdStory = FirebaseDatabase.getInstance();

    private DatabaseReference drStory = fdStory.getReference();

    private ObjectStory objStory;

    private StoryAdapter storyAdapter;

    private RecyclerView recyclerView;

    private ArrayList<ObjectStory> listStories = new ArrayList<>();

    private int lengthStory = 0;

    private int statuStory = 0;

    private AlertDialog.Builder alertDialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_features_story);

        loadStory();
        listNames = new ArrayList<String>();

        //Tạo recycleView
        recyclerView = (RecyclerView) findViewById(R.id.activity_features_story_rv_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                clickItem(position);

            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }

    private void clickItem(final int position) {
        LayoutInflater li = LayoutInflater.from(FeaturesStory.this);
        // Bỏ 1 dialog vào layout hiện tại đó.
        View customDialogView = li.inflate(
                R.layout.activity_features_story_content_dialog, null);

        alertDialogBuilder = new AlertDialog.Builder(FeaturesStory.this);
        alertDialogBuilder.setView(customDialogView);

        tvTitle = (TextView) customDialogView.findViewById(
                R.id.activity_features_story_content_dialog_tv_title);
        tvContent = (TextView) customDialogView.findViewById(
                R.id.activity_features_story_content_dialog_tv_content);
        ivLanguage = (ImageView) customDialogView.findViewById(
                R.id.activity_features_story_content_dialog_iv_language);

        tvTitle.setText(listStories.get(position).getTitleVi());
        //tiến hành tìm đoạn truyện tương thích
        tvContent.setText(listStories.get(position).getBodyVi());
        //nút Language
        ivLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showContent(tvTitle, position, tvContent, ivLanguage);

            }
        });
        alertDialogBuilder.show();
    }

    private void showContent(TextView tvTitle, int position,
                             TextView tvContent, ImageView ivLanguage) {
        //thay đổi icon và nội dung Việt và Anh
        if ((statuStory % 2) == 0) {
            //tiến hành tìm tựa đề truyện thích hợp
            tvTitle.setText(listStories.get(position).getTitleEn());
            //tiến hành tìm đoạn truyện tương thích
            tvContent.setText(listStories.get(position).getBodyEn());
            //hình Logo
            ivLanguage.setImageResource(R.drawable.ic_language_vietnam);
        } else {
            //tiến hành tìm tựa đề truyện thích hợp
            tvTitle.setText(listStories.get(position).getTitleVi());
            //tiến hành tìm đoạn truyện tương thích
            tvContent.setText(listStories.get(position).getBodyVi());
            //hình Logo
            ivLanguage.setImageResource(R.drawable.ic_language_american);
        }
        statuStory ++;
    }

    private void loadStory() {
        drStory.child("story").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lengthStory = Integer.parseInt(dataSnapshot.child("length").getValue().toString());

                for (int i = 0; i < lengthStory; i++) {
                    objStory = new ObjectStory();

                    objStory.setUrlPicture(dataSnapshot.child("story" + i)
                            .child("picture").getValue().toString());
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

                storyAdapter = new StoryAdapter(FeaturesStory.this, listStories);
                recyclerView.setAdapter(storyAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
