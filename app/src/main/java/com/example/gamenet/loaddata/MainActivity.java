package com.example.gamenet.loaddata;

import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gamenet.loaddata.object.ObjAccount;
import com.example.gamenet.loaddata.object.ObjData;
import com.example.gamenet.loaddata.object.ObjFullData;
import com.example.gamenet.loaddata.services.DataService;
import com.example.gamenet.loaddata.services.DataServiceBolts;
import com.example.gamenet.loaddata.services.ILoadDataSuccessListener;
import com.example.gamenet.loaddata.services.ILoginSuccessListener;
import com.example.gamenet.loaddata.services.IRestService;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.api.BackgroundExecutor;

import java.util.ArrayList;

import bolts.Continuation;
import bolts.Task;

@Fullscreen
@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById(R.id.et_user)
    EditText etUser;

    @ViewById(R.id.et_pass)
    EditText etPass;

    @ViewById(R.id.lv_data)
    ListView lvData;

    @ViewById(R.id.btn_login)
    Button btnLogin;

    @Bean
    protected DataService dataService;

    @Bean
    protected DataServiceBolts dataServiceBolts;

    private QuickAdapter adapterData;

    private String user, pass;

    @Click(R.id.btn_login)
    protected void clickLoadDataBolts(){
        user = etUser.getText().toString();
        pass = etPass.getText().toString();

        adapterData = new QuickAdapter<ObjData>(MainActivity.this, R.layout.item_data) {
            @Override
            protected void convert(BaseAdapterHelper helper, ObjData item) {
                helper.setText(R.id.data_titem_tv_id, "Id: " + item.getId())
                        .setText(R.id.data_titem_tv_content, "Content: " + item.getContent())
                        .setText(R.id.data_titem_tv_started_date, "Start Date: " + item.getStartedDate())
                        .setText(R.id.data_titem_tv_end_date, "End Date: " + item.getEndDate());
            }
        };

        lvData.setAdapter(adapterData);
        loadDataBolts();
    }

    private void loadDataBolts() {
        dataServiceBolts = new DataServiceBolts();
        dataServiceBolts.loadData("password", user, pass).continueWith(new Continuation<ObjFullData, Void>() {
            @Override
            public Void then(Task<ObjFullData> task) throws Exception {
                adapterData.addAll(task.getResult().getData());
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);
    }

//    @Click(R.id.btn_login)
//    protected void clickLoadData(){
//
//        user = etUser.getText().toString();
//        pass = etPass.getText().toString();
//
//        adapterData = new QuickAdapter<ObjData>(MainActivity.this, R.layout.item_data) {
//            @Override
//            protected void convert(BaseAdapterHelper helper, ObjData item) {
//                helper.setText(R.id.data_titem_tv_id, "Id: " + item.getId())
//                        .setText(R.id.data_titem_tv_content, "Content: " + item.getContent())
//                        .setText(R.id.data_titem_tv_started_date, "Start Date: " + item.getStartedDate())
//                        .setText(R.id.data_titem_tv_end_date, "End Date: " + item.getEndDate());
//            }
//        };
//
//        lvData.setAdapter(adapterData);
//        login();
//    }
//
//    @Background
//    protected void login() {
//        dataService.login("password", user, pass, new ILoginSuccessListener() {
//            @Override
//            public void onSuccess(ObjAccount account) {
//                //uiThread(account);
//                dataService.loadData(account.getAccessToken(), new ILoadDataSuccessListener() {
//
//                    @Override
//                    public void onSuccess(ObjFullData listData) {
//                        uiThread(listData);
//                    }
//                });
//            }
//        });
//    }
//
//    @UiThread
//    protected void uiThread(ObjFullData listData) {
//        adapterData.addAll(listData.getData());
//
//    }

}
