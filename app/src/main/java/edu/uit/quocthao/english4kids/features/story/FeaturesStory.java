package edu.uit.quocthao.english4kids.features.story;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import edu.uit.quocthao.english4kids.R;
import edu.uit.quocthao.english4kids.RecyclerItemClickListener;
import edu.uit.quocthao.english4kids.object.ObjStory;
import edu.uit.quocthao.english4kids.services.SQLiteStory;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

@Fullscreen
@EActivity(R.layout.activity_features_story)
public class FeaturesStory extends AppCompatActivity {

    @ViewById(R.id.activity_features_story_rv_view)
    RecyclerView recyclerView;

    @InstanceState
    ArrayList<String> listNames = new ArrayList<>();

    @InstanceState
    ArrayList<ObjStory> listStories = new ArrayList<>();

    @InstanceState
    int lengthStory = 0;

    @InstanceState
    int statuStory = 0;

    private ObjStory objStory;

    private StoryAdapter storyAdapter;

    private AlertDialog.Builder alertBuilder;

    private LinearLayoutManager linearManager;

    private SQLiteStory sqLiteStory;

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    @AfterViews
    public void initContent() {

        sqLiteStory = new SQLiteStory(this);

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
        statuStory++;  //Click
    }

    private void loadStory() {
        listStories = sqLiteStory.getListStories();
        storyAdapter = new StoryAdapter(FeaturesStory.this, listStories);
        recyclerView.setAdapter(storyAdapter);
    }
}
