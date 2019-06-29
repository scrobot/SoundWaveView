package com.github.scrobot.audiovisualizer.player;

import android.content.Context;
import android.net.Uri;

import java.io.IOException;

public interface SoundViewPlayer {

    public void preparePlayer();
    public SoundViewPlayer setOnPrepariedListener(SoundViewPlayerOnPreparedListener onPrepariedListener);
    public SoundViewPlayer setOnPauseListener(SoundViewPlayerOnPauseListener onPauseListener);
    public SoundViewPlayer setOnPlayListener(SoundViewPlayerOnPlayListener onPlayListener);
    public SoundViewPlayer setOnDurationListener(SoundViewPlayerOnDurationListener onDurationListener);
    public SoundViewPlayer setOnCompleteListener(SoundViewPlayerOnCompleteListener onCompleteListener);
    public void setAudioSource(Context context, Uri uri) throws IOException;
    public void setAudioSource(String url) throws IOException;

    public void play();
    public void pause();
    public void stop();
    public void toggle();
    public boolean isPlaying();
    public long getDuration();

}
