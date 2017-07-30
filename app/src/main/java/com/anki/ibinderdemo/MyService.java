package com.anki.ibinderdemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Random;

/**
 * Created by Ankita on 30-07-2017.
 */

public class MyService extends Service {

    private int MIN = 0;
    private int MAX = 100;

    private boolean mRandomNumberGenerator;
    private int mRandomNumber;

    MyService service;

    private IBinder myBinder = new MyBinder();

    class MyBinder extends Binder {

       public MyService getService(){
            return MyService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("MyService","In onStartCommand ");
        mRandomNumberGenerator = true;

        new Thread(){
            @Override
            public void run() {
                startRandomNumberGenerator();
                super.run();
            }
        }.start();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("MyService","In onBind");
        return myBinder;
    }

    private void startRandomNumberGenerator(){
        while(mRandomNumberGenerator) {
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){

            }
            mRandomNumber = new Random().nextInt(MAX) + MIN;
            Log.i("MyService","Random Number = "+mRandomNumber);
        }
    }

    @Override
    public void onDestroy() {
        Log.i("MyService","In onDestroy");
        mRandomNumberGenerator = false;
        super.onDestroy();
    }
    public int getNumber(){
        return mRandomNumber;
    }
}
