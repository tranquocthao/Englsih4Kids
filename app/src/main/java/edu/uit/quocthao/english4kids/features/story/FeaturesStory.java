package edu.uit.quocthao.english4kids.features.story;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import edu.uit.quocthao.english4kids.R;

public class FeaturesStory extends AppCompatActivity {

    private ListView lvNames;

    private ArrayList<String> arrNames;

    private ArrayAdapter<String> adapterNames;

    private Bundle bundleStory;

    private Intent intentStory;

    private String arrVi[] = {
            "Đến giờ ăn rồi sao ?", "Ca khúc nổi tiếng",
            "Bức chân dung", "Món quà của người con gái",
            "Kẻ ăn xin", "Áo cưới màu trắng", "Thành công một nửa",
            "Sách viễn tưởng", "Anh chỉ có mình em", "Đừng nói nữa",
            "Tham vọng thời trai trẻ", "Tiền và bạn", "Dòng sông không sâu",
            "Bò ăn cỏ", "Bí mật khủng khiếp", "Ruồi", "Làm sao để sống?",
            "Biết làm sao bây giờ?", "Gà và Chó", "Vợ tôi đó",
            "Can trường và tế nhị", "Nịnh bợ", "Thời gian", "Vay tiền",
            "Bao nhiêu kẻ bất lương?", "Ảnh phóng lớn", "Kinh nghiệm khủng khiếp",
            "Sư tử ăn thịt người", "Con xuống bơi được không?", "Xin chúc mừng!"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_features_story);

        lvNames = (ListView) findViewById(R.id.activity_features_story_lv_names);

        bundleStory = new Bundle();
        intentStory = new Intent(FeaturesStory.this, ContentStory.class);
        arrNames = new ArrayList<String>();

        for (int i = 0; i < 30; i++) {
            arrNames.add(arrVi[i]);
        }

        adapterNames = new ArrayAdapter<String>(
                FeaturesStory.this, android.R.layout.simple_list_item_1, arrNames);
        lvNames.setAdapter(adapterNames);

        lvNames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bundleStory.putInt("position", position);
                intentStory.putExtra("story", bundleStory);
                startActivity(intentStory);
            }
        });
    }
}
