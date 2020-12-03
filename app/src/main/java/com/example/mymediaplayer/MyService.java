package com.example.mymediaplayer;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyService extends Service {
private MyMediaPlayer player;
private IBinder binder;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("ServiceDemo", "Đã gọi onBind()");
        player.play();
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("ServiceDemo", "Đã gọi onUnbind()");
        player.stop();
        return super.onUnbind(intent);
    }
    public void fastForward(){
        player.forward(6000);
    }
    public void pause(){
        player.pause();
    }
    @Override
    public void onCreate() {
        super.onCreate();
        player = new MyMediaPlayer(this);
        binder = new MyBinder();
    }
    public class MyBinder extends Binder{
        public MyService getService(){
            return MyService.this;
        }
    }
}
class MyMediaPlayer {
    private MediaPlayer mediaPlayer;

    public MyMediaPlayer(Context context){
        mediaPlayer = MediaPlayer.create(context, R.raw.song);
        mediaPlayer.setLooping(true);
    }

    //forward
    public void forward(int msec){
        mediaPlayer.seekTo(msec);
    }

    //pause music
    public void pause(){
        mediaPlayer.pause();
    }

    //play music
    public void play(){
        if(mediaPlayer != null)
            mediaPlayer.start();
    }
    //stop music
    public void stop(){
        if(mediaPlayer != null){
            mediaPlayer.stop();
        }
    }
}
