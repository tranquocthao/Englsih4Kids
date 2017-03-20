package edu.uit.quocthao.english4kids.features.story;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import edu.uit.quocthao.english4kids.R;
import edu.uit.quocthao.english4kids.RecyclerItemClickListener;

@Fullscreen
@EActivity(R.layout.activity_features_story)
public class FeaturesStory extends AppCompatActivity {

    @ViewById(R.id.activity_features_story_rv_view)
    RecyclerView recyclerView;

    @InstanceState
    ArrayList<String> listNames = new ArrayList<>();

    @InstanceState
    ArrayList<ObjectStory> listStories = new ArrayList<>();

    @InstanceState
    int lengthStory = 0;

    @InstanceState
    int statuStory = 0;

    private DatabaseReference drStory;

    private ObjectStory objStory;

    private StoryAdapter storyAdapter;

    private AlertDialog.Builder alertBuilder;

    private LinearLayoutManager linearManager;

    @AfterViews
    public void initContent() {
        drStory = FirebaseDatabase.getInstance().getReference();

        loadStory();

        //Tạo recycleView
        recyclerView.setHasFixedSize(true);
        linearManager = new LinearLayoutManager(this);
        linearManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearManager);

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

        alertBuilder = new AlertDialog.Builder(FeaturesStory.this);
        alertBuilder.setView(customDialogView);

        final TextView tvTitle = (TextView) customDialogView.findViewById(
                R.id.activity_features_story_content_dialog_tv_title);

        final TextView tvContent = (TextView) customDialogView.findViewById(
                R.id.activity_features_story_content_dialog_tv_content);

        final ImageView ivLanguage = (ImageView) customDialogView.findViewById(
                R.id.activity_features_story_content_dialog_iv_language);

        tvTitle.setText(listStories.get(position).getTitleVi());

        tvContent.setText(listStories.get(position).getBodyVi());

        ivLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showContent(tvTitle, position, tvContent, ivLanguage);

            }
        });

        alertBuilder.show();
    }

    private void showContent(TextView tvTitle, int position,
                             TextView tvContent, ImageView ivLanguage) {

        if ((statuStory % 2) == 0) {    //English
            tvTitle.setText(listStories.get(position).getTitleEn());
            tvContent.setText(listStories.get(position).getBodyEn());
            ivLanguage.setImageResource(R.drawable.ic_language_vietnam);

        } else {    //Vietnam
            tvTitle.setText(listStories.get(position).getTitleVi());
            tvContent.setText(listStories.get(position).getBodyVi());
            ivLanguage.setImageResource(R.drawable.ic_language_american);

        }
        statuStory ++;  //Click
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
