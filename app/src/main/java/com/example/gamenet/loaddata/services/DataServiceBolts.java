package com.example.gamenet.loaddata.services;

import com.example.gamenet.loaddata.object.ObjAccount;
import com.example.gamenet.loaddata.object.ObjFullData;
import com.google.gson.Gson;

import org.androidannotations.annotations.EBean;

import bolts.Continuation;
import bolts.Task;
import bolts.TaskCompletionSource;
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
public class DataServiceBolts {

    private Retrofit retrofit;

    private IRestService iRestService;

    private ObjAccount account;

    private ObjFullData listData;

    private TaskCompletionSource<ObjAccount> taskToken;

    private TaskCompletionSource<ObjFullData> taskData;

    public DataServiceBolts() {
        account = new ObjAccount();
        listData = new ObjFullData();
        taskToken = new TaskCompletionSource<>();
        taskData = new TaskCompletionSource<>();
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

    public Task<ObjAccount> login(final String grant_type, final String username, final String password){

        iRestService.login(grant_type, username, password).enqueue(new Callback<ObjAccount>() {
            @Override
            public void onResponse(Call<ObjAccount> call, Response<ObjAccount> response) {
                taskToken.setResult(response.body());
            }

            @Override
            public void onFailure(Call<ObjAccount> call, Throwable t) {
                taskToken.setError((Exception) t);
            }
        });

        return taskToken.getTask();
    }

    public Task<ObjFullData> loadData(final String grant_type, final String username, final String password) {
        login(grant_type, username, password).continueWithTask(new Continuation<ObjAccount, Task<ObjAccount>>() {
            @Override
            public Task<ObjAccount> then(Task<ObjAccount> task) throws Exception {
                iRestService.loadData("Bearer " + task.getResult().getAccessToken()).enqueue(new Callback<ObjFullData>() {
                    @Override
                    public void onResponse(Call<ObjFullData> call, Response<ObjFullData> response) {
                        taskData.setResult(response.body());
                    }

                    @Override
                    public void onFailure(Call<ObjFullData> call, Throwable t) {
                        taskData.setError((Exception) t);
                    }
                });
                return null;
            }
        });

        return taskData.getTask();
    }
}
