package com.example.gamenet.loaddata.services;

import android.util.Log;

import com.example.gamenet.loaddata.object.ObjAccount;
import com.example.gamenet.loaddata.object.ObjData;
import com.example.gamenet.loaddata.object.ObjFullData;
import com.google.gson.Gson;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.androidannotations.annotations.EBean.Scope.Singleton;

/**
 * Created by GameNet on 3/13/2017.
 */

@EBean(scope = Singleton)
public class DataService {

    private Retrofit retrofit;

    private IRestService iRestService;

    private ObjAccount account = new ObjAccount();

    private ObjFullData listData = new ObjFullData();

    public DataService() {

        retrofit = new Retrofit.Builder().baseUrl("http://ibss.io:5599/")
                .addConverterFactory(GsonConverterFactory.create(new Gson())).build();
        iRestService = retrofit.create(IRestService.class);
    }

    public ObjAccount getAccount() {
        return account;
    }

    public ObjFullData getListData(){
        return listData;
    }

    public void login(final String grant_type, final String username, final String password,
                      final ILoginSuccessListener loginSuccessListener){

        iRestService.login(grant_type, username, password).enqueue(new Callback<ObjAccount>() {
            @Override
            public void onResponse(Call<ObjAccount> call, Response<ObjAccount> response) {
                account = response.body();
                if (loginSuccessListener != null)
                    loginSuccessListener.onSuccess(account);
            }

            @Override
            public void onFailure(Call<ObjAccount> call, Throwable t) {

            }
        });
    }

    public void loadData(final String header, final ILoadDataSuccessListener loadDataSuccessListener) {
        iRestService.loadData("Bearer " + header).enqueue(new Callback<ObjFullData>() {
            @Override
            public void onResponse(Call<ObjFullData> call, Response<ObjFullData> response) {
                listData = response.body();
                if (loadDataSuccessListener != null)
                    loadDataSuccessListener.onSuccess(listData);
            }

            @Override
            public void onFailure(Call<ObjFullData> call, Throwable t) {

            }
        });
    }
}
