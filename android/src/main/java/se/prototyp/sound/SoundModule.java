package se.prototyp.sound;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.AudioManager;
import android.media.AudioAttributes;
import android.util.Log;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;

public class SoundModule extends ReactContextBaseJavaModule {

    private static String TAG = "se.prototyp.sound.SoundModule";

    private ReactApplicationContext context;
    private MediaPlayer mediaPlayer;

    public SoundModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.context = reactContext;
    }

    @Override
    public String getName() {
        return "PlaySound";
    }

    @ReactMethod
    public void playSound(String soundPath, String streamType) {
        try {
            int resource = context.getResources().getIdentifier(soundPath, "raw", context.getPackageName());
            if (mediaPlayer != null) {
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
    public void pauseSound() {
        try {
            if(mediaPlayer != null) {
                mediaPlayer.pause();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ReactMethod
    public void resumeSound() {
        try {
            if(mediaPlayer != null) {
                mediaPlayer.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ReactMethod
    public void stopSound() {
        try {
            if (mediaPlayer != null) {
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
            if (mediaPlayer != null) {
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
    public void playSoundMusicVolume(final Float left, final Float right) {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(left, right);
        }
    }

    private int getStream(final String stream) {
        switch (stream) {
            case "SYSTEM":
                return AudioManager.STREAM_SYSTEM;
            case "MUSIC":
                return AudioManager.STREAM_MUSIC;
            case "NOTIFICATION":
                return AudioManager.STREAM_NOTIFICATION;
            case "ALARM":
                return AudioManager.STREAM_ALARM;
            case "RING":
                return AudioManager.STREAM_RING;
            default:
                return AudioManager.STREAM_MUSIC;
        }
    }

    @ReactMethod
    public void setStreamVolume(final Float value, final String stream, final Promise promise) {
        try {
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            int streamId = this.getStream(stream);
            int maxVolume = audioManager.getStreamMaxVolume(streamId);
            int oldVolume = audioManager.getStreamVolume(streamId);
            int volume = Math.round(maxVolume * value);
            audioManager.setStreamVolume(streamId, volume, 0);
            float normalisedOldVolume = ((float) oldVolume / (float) maxVolume);
            promise.resolve(normalisedOldVolume);
        } catch (Exception e) {
            promise.reject(e);
        }
    }

    @ReactMethod
    public void adjustStreamMute(final Boolean shouldMute, final String stream) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int streamId = this.getStream(stream);
        audioManager.adjustStreamVolume(streamId,
                shouldMute ? AudioManager.ADJUST_MUTE : AudioManager.ADJUST_UNMUTE,
                0);
    }
}
