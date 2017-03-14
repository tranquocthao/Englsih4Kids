package com.example.gamenet.loaddata.services;

import com.example.gamenet.loaddata.object.ObjData;
import com.example.gamenet.loaddata.object.ObjFullData;

import java.util.ArrayList;

/**
 * Created by GameNet on 3/13/2017.
 */

public interface ILoadDataSuccessListener {

    void onSuccess(ObjFullData listData);
}
