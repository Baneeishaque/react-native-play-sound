package com.soundapp;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.AudioManager;
import android.media.AudioAttributes;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class SoundModule extends ReactContextBaseJavaModule {
    
    private ReactApplicationContext context;
    private MediaPlayer mediaPlayer;
    
    public SoundModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.context = reactContext;
    }
    
    @Override
    public String getName() {
        return "SoundManager";
    }
    
    @ReactMethod
    public void playSound(String soundPath, String streamType) {
        try {
            int resource = context.getResources().getIdentifier(soundPath, "raw", context.getPackageName());
            if(mediaPlayer != null) {
                mediaPlayer.release();
                mediaPlayer = null;
            }
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            int sessionId = audioManager.generateAudioSessionId();
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setLegacyStreamType(this.getStream(streamType))
                    .build();
            mediaPlayer = MediaPlayer.create(this.getReactApplicationContext(), resource, attributes, sessionId);
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ReactMethod
    public void stopSound() {
        try {
            if(mediaPlayer != null) {
                mediaPlayer.release();
                mediaPlayer = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ReactMethod
    public void playSoundRepeat(String soundPath) {
        try {
            int resource = context.getResources().getIdentifier(soundPath, "raw", context.getPackageName());
            if(mediaPlayer != null) {
                mediaPlayer.release();
                mediaPlayer = null;
            }
            mediaPlayer = MediaPlayer.create(this.getReactApplicationContext(), resource);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ReactMethod
    public void playSoundMusicVolume(final Float left, final Float right){
        if(mediaPlayer != null) {
            mediaPlayer.setVolume(left, right);
        }
    }

    private int getStream(final String stream) {
        switch (stream) {
            case "SYSTEM": return AudioManager.STREAM_SYSTEM;
            case "MUSIC": return AudioManager.STREAM_MUSIC;
            case "NOTIFICATION": return AudioManager.STREAM_NOTIFICATION;
            case "ALARM": return AudioManager.STREAM_ALARM;
            default: return AudioManager.STREAM_MUSIC;
        }
    }

    @ReactMethod
    public float setStreamVolume(final Float value, final String stream) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int streamId = this.getStream(stream);
        int maxVolume = audioManager.getStreamMaxVolume(streamId);
        int oldVolume = audioManager.getStreamVolume(streamId);
        int volume = Math.round(maxVolume * value);
        audioManager.setStreamVolume(streamId, volume, 0);
        return (float)oldVolume / maxVolume;
    }
}
