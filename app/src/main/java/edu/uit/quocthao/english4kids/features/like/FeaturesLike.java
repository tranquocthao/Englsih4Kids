package edu.uit.quocthao.english4kids.features.like;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.hawk.Hawk;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.util.ArrayList;

import edu.uit.quocthao.english4kids.R;
import edu.uit.quocthao.english4kids.RecyclerItemClickListener;
import edu.uit.quocthao.english4kids.object.ObjTopic;
import edu.uit.quocthao.english4kids.services.ILikeUpdate;
import edu.uit.quocthao.english4kids.services.NetworkService;
import edu.uit.quocthao.english4kids.services.SQLiteEnglish;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

@Fullscreen
@EActivity(R.layout.activity_features_like)
public class FeaturesLike extends AppCompatActivity implements ILikeUpdate{

    @ViewById(R.id.activity_features_like_rv_view)
    RecyclerView recyclerView;

    @InstanceState
    ArrayList<ObjTopic> listLikes = new ArrayList<>();

    private LikeAdapter likeAdapter;

    private LinearLayoutManager linearManager;

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    @AfterViews
    public void initContent() {
        Hawk.init(this).build();
        loadData();

        //Tạo recycleView
        recyclerView.setHasFixedSize(true);
        linearManager = new LinearLayoutManager(this);
        linearManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearManager);

        likeAdapter = new LikeAdapter(FeaturesLike.this, listLikes);
        recyclerView.setAdapter(likeAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onLongItemClick(View view, int position) {
                Toast.makeText(FeaturesLike.this, "Deleted " +
                        listLikes.get(position).getEnWord(), Toast.LENGTH_SHORT).show();
                Hawk.put(listLikes.get(position).getEnWord(), "false");
                listLikes.remove(position);

                likeAdapter = new LikeAdapter(FeaturesLike.this, listLikes);
                recyclerView.setAdapter(likeAdapter);
            }
        }));
    }

    private void loadData() {
        //Lấy mảng animal cho vào listGame
        SQLiteEnglish sqLiteEnglish = new SQLiteEnglish(this);
        ArrayList<ObjTopic> listEnglish = sqLiteEnglish.getListEnglish();

        for (ObjTopic objLike : listEnglish) {
            if (Hawk.contains(objLike.getEnWord()) && Hawk.get(objLike.getEnWord()).equals("true")) {
                listLikes.add(objLike);
            }
        }
    }

    @Override
    public void updateLike(ArrayList<ObjTopic> listTopics) {
        likeAdapter = new LikeAdapter(FeaturesLike.this, listTopics);
        recyclerView.setAdapter(likeAdapter);
    }
}