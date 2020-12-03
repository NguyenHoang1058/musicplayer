package com.example.mymediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.Image;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private MyService myService;
    private boolean isBound = false;
    private ServiceConnection connection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton bt_backward = (ImageButton) findViewById(R.id.bt_backforward);
        ImageButton bt_play = (ImageButton) findViewById(R.id.btPlay);
        ImageButton bt_pause = (ImageButton) findViewById(R.id.btPause);
        ImageButton bt_forward = (ImageButton) findViewById(R.id.btforward);

        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                MyService.MyBinder binder = (MyService.MyBinder) iBinder;
                myService = binder.getService();
                isBound = true;
            }
            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                isBound = false;
            }
        };
        final Intent intent =
                new Intent(MainActivity.this,
                        MyService.class);
        bt_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bindService(intent, connection, Context.BIND_AUTO_CREATE);
            }
        });
        bt_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isBound){
                    unbindService(connection);
                    isBound = false;
                }
            }
        });
        bt_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isBound){
                    myService.fastForward();
                }
                else {
                    Toast.makeText(MainActivity.this, "Service inactive", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}