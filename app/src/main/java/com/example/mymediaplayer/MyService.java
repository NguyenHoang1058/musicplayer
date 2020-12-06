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

import java.util.ArrayList;
import java.util.List;

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
    public void fastForward(){player.forward(10000);}
    public void fastBackward(){player.backward(10000);}
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
    private List<Integer> songList = new ArrayList<>();
    private int currentSong = 0;

    public MyMediaPlayer(Context context){
        songList.add(R.raw.song);
        songList.add(R.raw.song2);
        songList.add(R.raw.song3);
        songList.add(R.raw.song4);
        if(songList.size() > 1){
            currentSong = 0;
            mediaPlayer = MediaPlayer.create(context, songList.get(currentSong + 0));
            currentSong = currentSong + 1;
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer = MediaPlayer.create(context, songList.get(currentSong));
                    mediaPlayer.start();
                }
            });
        }
    }

    //forward
    public void forward(int msec){
        int currPos = mediaPlayer.getCurrentPosition();
        if(currPos + msec <= mediaPlayer.getDuration()){
            mediaPlayer.seekTo(msec + currPos);
        }
        else {
            mediaPlayer.seekTo(mediaPlayer.getDuration());
        }
    }

    //backward
    public void backward(int msec){
        int currPos = mediaPlayer.getCurrentPosition();
        if(currPos - msec >= 0){
            mediaPlayer.seekTo(currPos - msec);
        }
        else {
            mediaPlayer.seekTo(0);
        }
    }

    //pause music
    public void pause(){
        if (mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
        else {
            int currPos = mediaPlayer.getCurrentPosition();
            mediaPlayer.seekTo(currPos);
            mediaPlayer.start();
        }

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
