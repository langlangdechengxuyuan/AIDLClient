package com.example.aidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ainirobot.robotlog.RobotLog;
import com.example.aidlservice.ICallbackApi;
import com.example.aidlservice.IMyAidlCallback;
import com.example.aidlservice.IRemoteService;
import com.example.aidlservice.Person;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    IRemoteService mIRemoteService;

    private ServiceConnection mConnection = new ServiceConnection() {
        // Called when the connection with the service is established
        public void onServiceConnected(ComponentName className, IBinder service) {
            // Following the example above for an AIDL interface,
            // this gets an instance of the IRemoteInterface, which we can use to call on the service
            mIRemoteService = IRemoteService.Stub.asInterface(service);
        }

        // Called when the connection with the service disconnects unexpectedly
        public void onServiceDisconnected(ComponentName className) {
            Log.e(TAG, "Service has unexpectedly disconnected");
            mIRemoteService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        RobotLog.init(this);

        bindService(createExplicitIntent("com.example.aidlservice",
                "com.example.aidlservice.RemoteService",
                "com.example.aidlservice.REMOTESERVICE"), mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
    }

    public void callRmType(View view) throws RemoteException  {
            mIRemoteService.basicTypes(1, 2, true, 3, 4, "NICE");
    }

    public void callRmgetPid(View view) throws RemoteException {
        int remotePid = mIRemoteService.getPid(new ICallbackApi(){
            @Override
            public void onCallback(String str) throws RemoteException {
                Log.e(TAG, str);
            }
        });
        Toast.makeText(this, "remotePid = " + remotePid, Toast.LENGTH_SHORT).show();
        Person person = mIRemoteService.getPerson();
        Toast.makeText(this, "person = " + person.getName() + " " + person.getId(), Toast.LENGTH_SHORT).show();
    }

    public Intent createExplicitIntent(String pkgName, String className, String action) {
        ComponentName component = new ComponentName(pkgName, className);
        Intent intent = new Intent(action);
        intent.setComponent(component);
        return intent;
    }
}
