package com.anki.ibinderdemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Intent serviceIntent;
    private ServiceConnection serviceConnection;
    private TextView mTextView;
    private boolean isServiceBound;
    private MyService myService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("MyActivity","In onCreate ");

        Button start = (Button)findViewById(R.id.start);
        start.setOnClickListener(this);
        Button stop = (Button)findViewById(R.id.stop);
        stop.setOnClickListener(this);
        Button bind = (Button)findViewById(R.id.bind);
        bind.setOnClickListener(this);
        Button unbind = (Button)findViewById(R.id.unbind);
        unbind.setOnClickListener(this);
        Button getRandomNumber = (Button)findViewById(R.id.get_number);
        getRandomNumber.setOnClickListener(this);

        mTextView = (TextView) findViewById(R.id.textView);

        serviceIntent = new Intent(this,MyService.class);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.start:
                startService();
                break;
            case R.id.stop:
                stopService();
                break;
            case R.id.bind:
                bindService();
                break;
            case R.id.unbind:
                unBindService();
                break;
            case R.id.get_number:
                setRandomNumber();
                break;
        }
    }

    void startService(){
        startService(serviceIntent);
    }

    void stopService(){
        stopService(serviceIntent);
    }

    void bindService(){
        Log.i("MyActivity","In Bind Service");
        if(serviceConnection == null) {
            serviceConnection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    isServiceBound = true;
                    myService = ((MyService.MyBinder) iBinder).getService();
                }

                @Override
                public void onServiceDisconnected(ComponentName componentName) {
                    isServiceBound = false;

                }
            };
        }
        bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);

    }

    void unBindService(){
        if(isServiceBound) {
            unbindService(serviceConnection);
            isServiceBound = false;
        }
    }

    void setRandomNumber(){
        if(isServiceBound) {
            mTextView.setText("Random Number = " + myService.getNumber());
        }else{
            mTextView.setText("Service not bound");
        }
    }



}
