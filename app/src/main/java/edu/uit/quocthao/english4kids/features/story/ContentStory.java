package edu.uit.quocthao.english4kids.features.story;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import edu.uit.quocthao.english4kids.R;

public class ContentStory extends AppCompatActivity {

    TextView tvTitle, tvBody;
    ObjectStory[] scBody = new ObjectStory[30];
    ImageButton ibNext, ibBack, ibLanguage;
    int k, t = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_features_story_content);

        tvTitle = (TextView)findViewById(R.id.activity_feature_story_content_tv_title);
        tvBody = (TextView)findViewById(R.id.activity_feature_story_content_tv_body);
        ibNext = (ImageButton)findViewById(R.id.activity_feature_story_content_ib_next);
        ibBack = (ImageButton)findViewById(R.id.activity_feature_story_content_ib_back);
        ibLanguage = (ImageButton)findViewById(R.id.activity_feature_story_content_ib_language);

        addStories();

        //lấy intent gọi Activity này
        Intent intentCaller = getIntent();
        //có intent rồi thì lấy Bundle dựa vào story
        Bundle bundle = intentCaller.getBundleExtra("story");
        //có Bundle rồi lấy thông số dựa vào position
        k = bundle.getInt("position");

        //tiến hành tìm tựa đề truyện thích hợp
        tvTitle.setText(scBody[k].getTitleVi());
        //tiến hành tìm đoạn truyện tương thích
        tvBody.setText(scBody[k].getBodyVi());


        //nút Back
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t = 0;
                //hình Logo
                ibLanguage.setImageResource(R.drawable.ic_language_american);

                k --;
                // có 10 truyện
                if(k < 0)
                    k = 29;

                //tiến hành tìm tựa đề truyện thích hợp
                tvTitle.setText(scBody[k % 30].getTitleVi());
                //tiến hành tìm đoạn truyện tương thích
                tvBody.setText(scBody[k % 30].getBodyVi());

            }
        });

        //nút Next
        ibNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t = 0;
                //hình Logo
                ibLanguage.setImageResource(R.drawable.ic_language_american);

                k ++;
                //tiến hành tìm tựa đề truyện thích hợp
                tvTitle.setText(scBody[k % 30].getTitleVi());
                //tiến hành tìm đoạn truyện tương thích
                tvBody.setText(scBody[k % 30].getBodyVi());

            }
        });

        //nút Language
        ibLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //thay đổi icon và nội dung Việt và Anh
                if((t % 2) == 0){
                    //tiến hành tìm tựa đề truyện thích hợp
                    tvTitle.setText(scBody[k % 30].getTitleEn());
                    //tiến hành tìm đoạn truyện tương thích
                    tvBody.setText(scBody[k % 30].getBodyEn());
                    //hình Logo
                    ibLanguage.setImageResource(R.drawable.ic_language_vietnam);
                }
                else {
                    //tiến hành tìm tựa đề truyện thích hợp
                    tvTitle.setText(scBody[k % 30].getTitleVi());
                    //tiến hành tìm đoạn truyện tương thích
                    tvBody.setText(scBody[k % 30].getBodyVi());
                    //hình Logo
                    ibLanguage.setImageResource(R.drawable.ic_language_american);
                }

                t ++;
            }
        });
    }

    public void addStories(){

        for(int i = 0; i < 30; i ++)
            scBody[i] = new ObjectStory();

        scBody[0].setTitleVi("Đến giờ ăn rồi sao ?");
        scBody[0].setTitleEn("Has The Dinner-Bell Rung ?");
        scBody[0].setBodyVi(
                " - Ông thật là quý hóa, ông làm tôi hãnh diện vì chịu khó nán lại nghe nốt câu chuyện " +
                        "tôi kể trong khi khác hàng khác lao vọt đi ngay khi chuyên báo giờ vang lên. " +
                        "- một du khách có tật nói năng dông dài, tẻ nhạt, bảo người lắng nghe độc nhất còn lại.\n" +
                        " - Cái gi! Đến giờ ăn rồi sao? - người kia kêu lên và đứng phắt dậy, lao về phía phòng ăn."
        );
        scBody[0].setBodyEn(
                " - My dear sir, you flatter me lingering to hear the remainder of my tale when the other passengers " +
                        "dashed away at the sound of the dinner-bell. Said the longwinded tourist to his one remaining listener.\n" +
                        " - What! Has the dinner-bell rung? asked the other, as he jumped to his feet ands dashed toward the dining room."
        );

        scBody[1].setTitleVi("Ca khúc nổi tiếng");
        scBody[1].setTitleEn("A Popular Song");
        scBody[1].setBodyVi(
                " - Vậy cái bài hắn đang hát là một ca khúc nổi tiếng ?\n" +
                        " - Nó từng nổi tiếng trước khi hắn bắt đầu hát bài ấy."
        );
        scBody[1].setBodyEn(
                " - So that is a popular song he is singing ?\n" +
                        " - It was before he sang it."
        );

        scBody[2].setTitleVi("Bức chân dung");
        scBody[2].setTitleEn("A Portrait");
        scBody[2].setBodyVi(
                " - Gia chủ (đang đưa khách đi giới thiệu quanh nhà): - Còn đây là chân dung ông cố ba đời của tôi.\n" +
                        " - Khách: - Hay thật là hay! Chà, trông ông cụ chẳng già hơn anh tí nào."
        );
        scBody[2].setBodyEn(
                " - Host (doing the honors): And that is a portrait of me great - great - grandfather.\n" +
                        " - Visitor: Wonferful! Why, he doesn't look any older than you !"
        );

        scBody[3].setTitleVi("Món quà của người con gái");
        scBody[3].setTitleEn("A Gift From Sister");
        scBody[3].setBodyVi(
                " - Nàng: Anh kiếm đâu ra cây dù ấy thế ?\n" +
                        " - Chàng: Đó là món quà của người em gái.\n" +
                        " - Nàng: Sao anh bảo em là anh chẳng có chị em gái nào cả.\n" +
                        " - Chàng: Thì đúng vậy. Nhưng đó là dòng chữ khắc trên cán dù."
        );
        scBody[3].setBodyEn(
                " - She: Where did you get that umbrella ?\n" +
                        " - He: It was a gilf from sister.\n" +
                        " - She: You told me you hadn't any sisters.\n" +
                        " - He: I know. But that's what engraved on the handle."
        );

        scBody[4].setTitleVi("Kẻ ăn xin");
        scBody[4].setTitleEn("Beggar");
        scBody[4].setBodyVi(
                " - Tại sao anh lại ăn xin ?\n" +
                        " - Sự thực là tôi xin tiền để uống rượu.\n" +
                        " - Tại sao anh lại uống rượu ?.\n" +
                        " - Để tôi có can đảm đi ăn xin."
        );
        scBody[4].setBodyEn(
                " - Why do you beg ?\n" +
                        " - The truth is I beg to get money for booze.\n" +
                        " - Why do you drink ?\n" +
                        " - To give ne the courage to beg."
        );

        scBody[5].setTitleVi("Áo cưới màu trắng");
        scBody[5].setTitleEn("The bride wore white");
        scBody[5].setBodyVi(
                " - Lần đầu tiên dự một lễ cưới, bé gái nói thầm với mẹ: \"Mẹ ơi tại sao cô dâu lại mặc đồ màu trắng?\"" +
                        " - \"Bởi vì màu trắng là màu của hạnh phúc, và ngày hôm nay là ngày hạnh phúc nhất" +
                        " trong đời cô dâu\" Để đơn giản bà mẹ giải thích.\n" +
                        " - Cô bé nghĩ một lúc và nói: \"Ơ thế thì tại sao chú rể lại mặc đồ đen hả mẹ?\""
        );
        scBody[5].setBodyEn(
                " - Attending a wedding for the first time, a little girl whispered to her mother, " +
                        "\"Why is the bride dressed in white?\"\n" +
                        " - Because white is the color of happiness, and today is the happiest day of her" +
                        " life, her mother tried to explain, keeping it simple.\n" +
                        " - The child thought about this for a moment, then said, \"So why's the groom wearing black?\""
        );

        scBody[6].setTitleVi("Thành công một nửa");
        scBody[6].setTitleEn("A half success");
        scBody[6].setBodyVi(
                " - \"Này, hẹn hò thế nào rồi?\"\n" +
                        " - \"Có thể nói là thành công một nửa\".\n" +
                        " - \"Anh nói vậy nghĩa là sao?\"\n" +
                        " - \"Tôi có đến còn cô ấy thì không\"."
        );
        scBody[6].setBodyEn(
                " - \"Hey, how about the render-vous?\"\n" +
                        " - \"It can be said that a half of success\".\n" +
                        " - \"What do you mean?\"\n" +
                        " - \"I came to the dated place but she did'nt\"."
        );

        scBody[7].setTitleVi("Sách viễn tưởng");
        scBody[7].setTitleEn("Fiction book");
        scBody[7].setBodyVi(
                " - Một ông khách hàng đi vào hiệu sách và hỏi cô bán sách:\n" +
                        "\"Cho tôi mua cuốn Đàn ông là chú tể của phụ nữ\".\n" +
                        " - \"Sách viễn tưởng bán ở gian bên cạnh nhé!\""
        );
        scBody[7].setBodyEn(
                " - A men walk into the book shop and asked book seller:\n" +
                        "\"I want to buy a book named Man is the ruler of woman.\"\n" +
                        " - \"Fiction book are sold in the next room!\"\n"
        );

        scBody[8].setTitleVi("Anh chỉ có mình em");
        scBody[8].setTitleEn("Only you");
        scBody[8].setBodyVi(
                " - Một gã trông rất bảnh trai tự nói:\n" +
                        "\"Cũng sắp đến ngày lễ tình yêu Valentine rồi nhỉ?\"\n" +
                        " - Thế rồi gã bước vào quầy bán bưu thiếp, nói với cô bán hàng:\n" +
                        " - \"Em ơi bán cho anh 9 cái bưu thiệp Anh chỉ có mình em\"."
        );
        scBody[8].setBodyEn(
                " - A handsome guy told to himself while walking into the postcard shop:\n" +
                        "\"It is near Valentine day\".\n" +
                        " - Then, he said to the postcard seller:\n" +
                        " - \"Give me nine Only you postcards\"."
        );

        scBody[9].setTitleVi("Đừng nói nữa");
        scBody[9].setTitleEn("Shut up");
        scBody[9].setBodyVi(
                " - Một cặp vợ chồng cãi nhau về vấn đề tiền bạc, cuối cùng người vợ hét lên:\n" +
                        "\"Nếu không có tiền của tôi thì cái tivi này ở đây không? Nếu không có tiền của tôi" +
                        " thì cái tủ lạnh có ở đây không?\"\n" +
                        " - \"Im đi!\", người chồng tức giận hầm hầm,\n" +
                        "\"Nếu không có tiền của cô thì tôi có ở đây không?\""
        );
        scBody[9].setBodyEn(
                " - A couple were quarreling about money, at last the wife shout loudly:\n" +
                        "\"Whether is this TV here without my money? Whether is this refrigeator here without my money?\n" +
                        " - \"Shut up!\", the husband got angry,\n" +
                        "\"Whether am I here without your money?\""
        );

        scBody[10].setTitleVi("Tham vọng thời trai trẻ");
        scBody[10].setTitleEn("Boying Ambition");
        scBody[10].setBodyVi(
                " - Những tham vọng thời trai trẻ của anh có cái nào thành hiện thực không?\n" +
                        " - Có chứ. Ngày xưa khi mẹ tôi cắt tóc cho tôi, tôi thường ao ước là mình sẽ hói đầu."
        );
        scBody[10].setBodyEn(
                " - Where any of your boyish ambitions ever realized?\n" +
                        " - Yes, when my mother used to cut my hair I often wished I might be bard headed."
        );

        scBody[11].setTitleVi("Tiền và bạn");
        scBody[11].setTitleEn("Money and Friends");
        scBody[11].setBodyVi(
                " - Từ ngày hắn mất tiền, phân nửa bạn bè của hắn không còn biết tới, hay thăm hỏi gì hắn nửa.\n" +
                        " - Còn nửa kia?\n" +
                        " - Họ chưa biết là hắn đã mất tiền."
        );
        scBody[11].setBodyEn(
                " - Since he lost his money, half his friends don't know him any more.\n" +
                        " - And the other half?\n" +
                        " - They don't know yet that has lost it."
        );

        scBody[12].setTitleVi("Dòng sông không sâu");
        scBody[12].setTitleEn("The River isn't deep");
        scBody[12].setBodyVi(
                " - Một lữ khách đi ngựa đến một dòng sông xa lạ. Ông ta hỏi một thiếu niên xem dòng sông ấy có sâu không.\n" +
                        " - \"Không đâu\" chú bé đáp và người kị mã bắt đầu vượt sông. Nhưng ngay sau đó ông" +
                        " nhận ra cả người lẫn ngựa đều phải bơi trối chết.\n" +
                        " - Khi người lữ khách đã tới bờ bên kia, ông quay lại hét lên: \"Tao cứ tưởng mày nói là không sâu\".\n" +
                        " - \"Đúng thế mà, nước sông này chỉ ngập ngang bụng lũ vịt của ông cháu thôi\" chú bé đáp."
        );
        scBody[12].setBodyEn(
                " - A stranger on horse back came to a river with which he was unfamiliar. The traveller asked a youngster if it was deep.\n" +
                        " - \"No\" replied the boy, and the rider started to cross," +
                        " but soon found that he and his horse had to swim for their lives.\n" +
                        " - When the traveller reached the other side he turned and shouted: \"I thought you said it wasn't deep?\".\n" +
                        " - \"It isn't, it only takes grandfather ducks up to their middles! \" the boys reply."
        );

        scBody[13].setTitleVi("Bò ăn cỏ");
        scBody[13].setTitleEn("A Cow grazing");
        scBody[13].setBodyVi(
                " - Họa sĩ: Bức tranh đó vẽ một con bò đang ăn cỏ đấy, thưa ông.\n" +
                        " - Khách: Có thấy cỏ đâu?\n" +
                        " - Họa sĩ: Con bò ăn hết rồi.\n" +
                        " - Khách: Thế còn con bò đâu?\n" +
                        " - Họa sĩ: Chứ bộ ông tưởng con bò lại ngu đến mức đứng ì ở đó sau khi đã ăn hết cỏ sao ông!"
        );
        scBody[13].setBodyEn(
                " - Artist: That, sir, is a cow grazing.\n" +
                        " - Visitor: Where is the grass?\n" +
                        " - Artist: The cow has eaten it.\n" +
                        " - Visitor: But where is the cow?\n" +
                        " - Artist: You don't suppose shed be fool enough to stay there after shed eaten all the grass, do you?"
        );

        scBody[14].setTitleVi("Bí mật khủng khiếp");
        scBody[14].setTitleEn("Great Mystery");
        scBody[14].setBodyVi(
                " - Chú bé bán báo: \"Bí mật khủng khiếp đây! Năm mươi nạn nhân! Mua báo không, thưa ông?\"\n" +
                        " - Khách qua đường: \"Lại đây, tao lấy một tờ\".\n" +
                        " Đọc qua một hồi ........\n" +
                        " \"Này, thằng nhóc kia, trong báo có thấy tin nào như vật đâu. Nó nằm ở chỗ nào chứ?\"\n" +
                        " - Chú bé bán báo: \"Đó chính là điều bí mật, thưa ông. Ông là nạn nhân thứ năm mươi mốt đấy!\""
        );
        scBody[14].setBodyEn(
                " - Newsboy: \"Great mystery! Fifty victims! Paper, mister?\"\n" +
                        " - Passerby: \"Here boy, I'll take one\".\n" +
                        " After reading a moment ........\n" +
                        " \"Say, boy, theres nothing of the kind in this paper. Where is it?\"\n" +
                        " - Newsboy: \"Thats the mystery, sir. You're the fifty victim\"."
        );

        scBody[15].setTitleVi("Ruồi");
        scBody[15].setTitleEn("Flies");
        scBody[15].setBodyVi(
                " - Một phụ nữ bước vào bếp thấy chồng đang đi loanh quanh trong đó với một cái vỉ đập ruồi.\n" +
                        " - \"Anh đang làm gì vậy?\" cô hỏi.\n" +
                        " - \"Săn ruồi\" anh chồng trả lời.\n" +
                        " - \"Ồ, đã giết được con nào chưa?\" cô vợ hỏi.\n" +
                        " - \"Có 3 con đực, hai con cái\" anh chồng đáp.\n" +
                        " - Khá ngạc nhiên, cô vợ hỏi: \"Sao anh biết?\"\n" +
                        " - Anh chồng trả lời: \"3 con đang ở trên can bia, hai con đang đậu trên điện thoại\"."
        );
        scBody[15].setBodyEn(
                " - A wonman walked into the kitchen to find her husband walking around with a fly swatter.\n" +
                        " - \"What are you doing?\" she asked.\n" +
                        " - \"Hunting flies\" He responded.\n" +
                        " - \"Oh, killing any?\" She asked.\n" +
                        " - \"Yep, three males, two females\" he replied.\n" +
                        " - Intrigued, she asked: \"How can you tell?\"\n" +
                        " - He responded: \"Three were on a beer can,two were on the phone\"."
        );

        scBody[16].setTitleVi("Làm sao để sống?");
        scBody[16].setTitleEn("How to live?");
        scBody[16].setBodyVi(
                " - \"Em yêu\" một người đàn ông trẻ nói với cô dâu mới. \"Vì rằng chúng ta cưới" +
                        " nhau, em có nghĩ em sẽ có thể sống bằng thu nhập khiêm tốn của anh?\"\n" +
                        " - \"Dĩ nhiên, anh yêu, không sao cả\" cô ta trả lời. \"Nhưng anh sẽ sống bằng gì?\""
        );
        scBody[16].setBodyEn(
                " - \"Darling\" said the young man to his new bride. \"Now that we are married, do you think" +
                        " you will be able to live on my modest income?\"\n" +
                        " - \"Of course, dearest, no trouble\" she answered. \"But what will you live on?\""
        );

        scBody[17].setTitleVi("Biết làm sao bây giờ?");
        scBody[17].setTitleEn("What will I do?");
        scBody[17].setBodyVi(
                " - Hàng xóm: Tháng trước tôi có mang cái máy cắt cỏ trả lại cho ông không?\n" +
                        " - Chủ nhà tức tối: Không, ông đâu có trả.\n" +
                        " - Hàng xóm: Khổ chưa? Thế mà bây giờ tôi lại muốn mượn cái máy ấy nước đấy!"
        );
        scBody[17].setBodyEn(
                " - Neighbor: Did I bring your lawn mower back last month?\n" +
                        " - Indignant Householder: No, you did not.\n" +
                        " - Neighbor: Now what will I do? I want to borrow it again?"
        );

        scBody[18].setTitleVi("Gà và Chó");
        scBody[18].setTitleEn("The hen and the dog");
        scBody[18].setBodyVi(
                " - Jones: Xin lỗi anh bạn vì con gà nhà tôi sút chuồng và bới nát khu vườn của anh.\n" +
                        " - Smith: Không sao đâu, con chó nhà tôi đã xơi tái con gà nhà anh rồi.\n" +
                        " - Jones: Hay quá! Tôi vừa cán chết con chó nhà anh đây này."
        );
        scBody[18].setBodyEn(
                " - Jones: Sorry, old man, that my hen got loose and scratched up your garden.\n" +
                        " - Smith: That's all right, my dog ate your hen.\n" +
                        " - Jones: Fine! I just ran over your dog and killed him."
        );

        scBody[19].setTitleVi("Vợ tôi đó");
        scBody[19].setTitleEn("She's my wife");
        scBody[19].setBodyVi(
                " -  Một vị khách quay sang một người ngồi bên và chê bai giọng ca của một phụ nữ đang hát giúp vui cho họ.\n" +
                        " - \"Giọng ca gì nghe mà khiếp! Anh có biết bà ta là ai không?\"\n" +
                        " - \"Biết chớ\" câu trả lời. \"Vợ tôi đó\".\n" +
                        " - \"Ái chà, xin lỗi anh. Thực ra thì không phải do giọng ca của chị ấy." +
                        " Chính cái thứ hổ lốn mà chị ta buộc lòng phải ca hát lêm mới là khiếp." +
                        " Tôi không hiểu đứa nào lại đi viết một bài ca kinh khủng như vậy?\"\n" +
                        " - \"Tôi viết đấy\"."
        );
        scBody[19].setBodyEn(
                " -  One of the guest turned to a man by his side to criticize the singing of the woman who was trying to entertain them.\n" +
                        " - \"What a teriible voice! Do you know who she is?\"\n" +
                        " - \"Yes\" was the answer. \"She's my wife\".\n" +
                        " - \"Oh, I beg your pardon. Of course, it isn't her voice, really." +
                        " It's the stuff she has to sing. I wonder who wrote that awful song?\"\n" +
                        " - \"I did, was the answer\"."
        );

        scBody[20].setTitleVi("Can trường và tế nhị");
        scBody[20].setTitleEn("The difference between valor and discretion");
        scBody[20].setBodyVi(
                " -  Can trường và tế nhị khác nhau ra làm sao?\n" +
                        " - À, đi ăn ở một nhà hàng xịn mà không buộc boa cho bồi bàn tức là can trường.\n" +
                        " - Ra thế. Còn tế nhị?\n" +
                        " - Tức là hôm sau nên chọn nhà hàng khác mà ăn."
        );
        scBody[20].setBodyEn(
                " -  What's the difference between valor and discretion?\n" +
                        " - Well, to go to a swell restaurant without tipping the waiter would be valor.\n" +
                        " - I see. And discretion?\n" +
                        " - That would be to dine at a different restaurant the next day."
        );

        scBody[21].setTitleVi("Nịnh bợ");
        scBody[21].setTitleEn("Flattering");
        scBody[21].setBodyVi(
                " -  Nhà phê bình: Ôi! Cái gì thế kia? Một bức tranh tuyệt vời! Quá sâu sắc! Quá tinh tế!\n" +
                        " - Họa sĩ: Cái gì? Đó là chỗ tôi cùi cọ cho sạch hơn đấy."
        );
        scBody[21].setBodyEn(
                " -  Critic: Ah! And what is this? It is superb! What soul! What expression!\n" +
                        " - Artist: What? That's where I clear the paint off my brushes."
        );

        scBody[22].setTitleVi("Thời gian");
        scBody[22].setTitleEn("Time");
        scBody[22].setBodyVi(
                " -  Anh có đồng ý rằng Thời Gian sẽ chữa lành mọi vết thương không?\n" +
                        " - Có thể đấy, nhưng chắc chắn Thời Gian không phải là chuyên gia thẩm mỹ rồi."
        );
        scBody[22].setBodyEn(
                " -  Don't you agree that Time is the greatest healer?\n" +
                        " - He maybe, but he's certainly no beauty specialist."
        );

        scBody[23].setTitleVi("Vay tiền");
        scBody[23].setTitleEn("Borrowing Money");
        scBody[23].setBodyVi(
                " -  Gặp anh thật quý hóa quá, anh bạn. Cho tôi vay mười đô được không?\n" +
                        " - Rất tiếc là hôm nay tôi không có một xu trong người.\n" +
                        " - Còn ở nhà thì sao?\n" +
                        " - Ở nhà ai cũng khỏe cả, cám ơn anh, khỏe lắm."
        );
        scBody[23].setBodyEn(
                " -  Glad to see you, old man. Can you lend me ten dollars?\n" +
                        " - Sorry, but I haven't a cent with me today.\n" +
                        " - And at home?\n" +
                        " - They're all very well, thank you, very well."
        );

        scBody[24].setTitleVi("Bao nhiêu kẻ bất lương?");
        scBody[24].setTitleEn("How many knaves live in this street?");
        scBody[24].setBodyVi(
                " -  Một kẻ thích đùa hỏi người bạn:\n" +
                        " - \"Theo anh thì ở phố này có bao nhiêu kẻ bất lương, không kể anh?\"\n" +
                        " - \"Không kể tôi!\" - người kia kêu lên. \"Bộ anh muốn sỉ nhục tôi đấy à?\"\n" +
                        " - Chà, vậy thì phố này có bao nhiêu kẻ bất lương, kể cả anh?"
        );
        scBody[24].setBodyEn(
                " -  A wag asked his friend:\n" +
                        " - \"How many knaves do you suppose live in this street besides yourself?\"\n" +
                        " - \"Besides myself!\" - replied. \"Do you mean to insult me?\"\n" +
                        " - Well, then? said the first, how many do you reckon including yourself?"
        );

        scBody[25].setTitleVi("Ảnh phóng lớn");
        scBody[25].setTitleEn("Life - Size Enlargements");
        scBody[25].setBodyVi(
                " -  Ở đây anh có nhận phóng ảnh lớn bằng kích thước thật không?\n" +
                        " - Đó là chuyên môn của chúng tôi.\n" +
                        " - Hay quá! Đây, phóng cho tôi tấm ảnh tôi chụp Kim Tự Tháp."
        );
        scBody[25].setBodyEn(
                " -  Do you make life-size enlargement of snapshot?\n" +
                        " - That's our specialty.\n" +
                        " - Fine! Here is a picture I took of the Pyramid."
        );

        scBody[26].setTitleVi("Kinh nghiệm khủng khiếp");
        scBody[26].setTitleEn("Terrible Experience");
        scBody[26].setBodyVi(
                " -  Cô Gushin: \"Làm người nhảy dù chắc hẳn phải tuyệt vời lắm. Tôi nghĩ là anh từng trải qua nhiều kinh nghiệm khủng khiếp\".\n" +
                        " - Người nhảy dù(đã chán ngấy những câu hỏi của cô ta): \"Đúng vậy, khủng khiếp lắm." +
                        " Chà, có lần tôi đáp xuống ngay nơi có cắm bảng ghi: Cấm Đi Trên Cỏ\"."
        );
        scBody[26].setBodyEn(
                " -  Miss Gushin: \"It must be wonderful to be a parachute jumper. I suppose you've had some terrible experience\".\n" +
                        " - Parachutist(fed up with her): \"Yes, miss, terrible." +
                        " Why, once I came down where there was a sign: Keep Off The Grass\"."
        );

        scBody[27].setTitleVi("Sư tử ăn thịt người");
        scBody[27].setTitleEn("Man - Eating Lion");
        scBody[27].setBodyVi(
                " -  Bà già(ở sở thú): \"Đó là sư tử ăn thịt người phải không?\"\n" +
                        " - Người giữ thú(chán ngấy bà cụ): \"Đúng đó, bà, nhưng tuần này chúng tôi hết sạch thịt người rồi nên nó chỉ ăn thịt bò thôi\"."
        );
        scBody[27].setBodyEn(
                " -  Old Lady(at the zoo): \"Is that a man - eating lion?\"\n" +
                        " - Fed-up Keeper: \"Yes, lady, but we are short of men this week, so all he gets is beef\"."
        );

        scBody[28].setTitleVi("Con xuống bơi được không?");
        scBody[28].setTitleEn("May I Go In To Swim?");
        scBody[28].setBodyVi(
                " -  Chuyện nghe được tại bãi tắm của một khu nghĩ mát ven biển.\n" +
                        " - Chú bé hỏi mẹ: \"Mẹ ơi, cho con xuống bơi được không?\"\n" +
                        " - \"Nhất định là không được, cưng à, nước sâu ghê lắm\".\n" +
                        " - \"Nhưng ba đang bơi kia kìa\".\n" +
                        " - \"Ba con bơi thì được, ổng có bảo hiểm rồi\"."
        );
        scBody[28].setBodyEn(
                " -  Overheard on the beach at a coast resort.\n" +
                        " - Small boy to his mother: \"Mummy, may I go in to swim?\"\n" +
                        " - \"Certainly not, my dear, it's far too deep\".\n" +
                        " - \"But daddy is swimming\".\n" +
                        " - \"Yes, dear, but he's insured\"."
        );

        scBody[29].setTitleVi("Xin chúc mừng!");
        scBody[29].setTitleEn("Congratulations!");
        scBody[29].setBodyVi(
                " -  Năm rồi tôi có vẽ một bức cho Viện Hàn Lâm.\n" +
                        " - Thế nó có được treo không?\n" +
                        " - Có chứ. Treo gần cổng chính, ai cũng có thể nhìn thấy nó.\n" +
                        " - Xin chúc mừng! Anh vẽ cái gì vậy?\n" +
                        " - Một tấm bảng đề: Hãy Đi Phía Bên Trái."
        );
        scBody[29].setBodyEn(
                " -  I painted something for the Academy last year.\n" +
                        " - Was it hung?\n" +
                        " - Yes, near the entrance where everybody could see it.\n" +
                        " - Congratulation! What was it?\n" +
                        " - A board saying: Keep To The Left."
        );
    }
}
