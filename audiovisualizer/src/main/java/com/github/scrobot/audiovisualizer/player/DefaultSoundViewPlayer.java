package com.github.scrobot.audiovisualizer.player;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

public class DefaultSoundViewPlayer implements SoundViewPlayer {

    private final MediaPlayer mediaPlayer = new MediaPlayer();
    private Handler handler = new Handler();
    private Runnable runnable;
    private AtomicLong durationCounter = new AtomicLong();

    private SoundViewPlayerOnPreparedListener onPrepariedListener;
    private SoundViewPlayerOnPauseListener onPauseListener;
    private SoundViewPlayerOnPlayListener onPlayListener;
    private SoundViewPlayerOnDurationListener onDurationListener;
    private SoundViewPlayerOnCompleteListener onCompleteListener;

    private final long INTERVAL = 32;

    @Override
    public void preparePlayer() {
        mediaPlayer.prepareAsync();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                onPrepariedListener.onPrepared(DefaultSoundViewPlayer.this);
            }
        });

        runnable = new Runnable() {
            @Override
            public void run() {
                onDurationListener.onDurationProgress(DefaultSoundViewPlayer.this, getDuration(), durationCounter.addAndGet(INTERVAL));

                if (mediaPlayer.isPlaying()) {
                    handler.postDelayed(this, INTERVAL);
                }
            }
        };

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                onCompleteListener.onComplete(DefaultSoundViewPlayer.this);

                durationCounter.set(0);

                handler.removeCallbacks(runnable);
            }
        });
    }

    @Override
    public SoundViewPlayer setOnPrepariedListener(SoundViewPlayerOnPreparedListener onPrepariedListener) {
        this.onPrepariedListener = onPrepariedListener;

        return this;
    }

    @Override
    public SoundViewPlayer setOnPauseListener(SoundViewPlayerOnPauseListener onPauseListener) {
        this.onPauseListener = onPauseListener;

        return this;
    }

    @Override
    public SoundViewPlayer setOnPlayListener(SoundViewPlayerOnPlayListener onPlayListener) {
        this.onPlayListener = onPlayListener;

        return this;
    }

    @Override
    public SoundViewPlayer setOnDurationListener(SoundViewPlayerOnDurationListener onDurationListener) {
        this.onDurationListener = onDurationListener;

        return this;
    }

    @Override
    public SoundViewPlayer setOnCompleteListener(SoundViewPlayerOnCompleteListener onCompleteListener) {
        this.onCompleteListener = onCompleteListener;

        return this;
    }

    @Override
    public void setAudioSource(Context context, Uri uri) throws IOException {
        mediaPlayer.setDataSource(context, uri);

        preparePlayer();
    }

    @Override
    public void setAudioSource(String url) throws IOException {
        mediaPlayer.setDataSource(url);

        preparePlayer();
    }

    @Override
    public void play() {
        mediaPlayer.start();
        onPlayListener.onPlay(this);

        handler.postDelayed(runnable, INTERVAL);
    }

    @Override
    public void pause() {
        mediaPlayer.pause();
        onPauseListener.onPause(this);
    }

    @Override
    public void stop() {
        mediaPlayer.stop();
        try {
            mediaPlayer.prepare();
            onPrepariedListener.onPrepared(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void toggle() {
        if(mediaPlayer.isPlaying()) {
            pause();
        } else {
            play();
        }
    }

    @Override
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    @Override
    public long getDuration() {
        return mediaPlayer.getDuration();
    }
}
