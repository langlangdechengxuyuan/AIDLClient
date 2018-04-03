package com.example.aidlservice;

import android.os.RemoteException;

/**
 * Created by 15901 on 2018/4/3.
 */

public class ICallbackApi extends IMyAidlCallback.Stub{
    @Override
    public void onCallback(String str) throws RemoteException {

    }
}
