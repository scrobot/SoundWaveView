package com.github.scrobot.audiovisualizer;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.io.IOException;

public class SoundWaveView extends FrameLayout {

    protected final Context context;
    protected final MediaPlayer mediaPlayer = new MediaPlayer();
    protected int layout = R.layout.sounwave_view;

    private View view;
    private SoundVisualizerBarView visualizerBar;
    private TextView timer;
    private ImageView actionButton;

    public SoundWaveView(Context context) {
        super(context);
        this.context = context;

        init(context);
    }

    public SoundWaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        init(context);
    }

    public SoundWaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        init(context);
    }

    public void addAudioFileUri(Uri audioFileUri) throws IOException {
        mediaPlayer.setDataSource(context, audioFileUri);
        mediaPlayer.prepareAsync();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                timer.setText(String.valueOf(mp.getDuration()));
            }
        });

        visualizerBar.updateVisualizer(audioFileUri);
    }

    public void addAudioFileUrl(String audioFileUrl) throws IOException {
        mediaPlayer.setDataSource(audioFileUrl);
        mediaPlayer.prepareAsync();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                timer.setText(String.valueOf(mp.getDuration()));
            }
        });

        visualizerBar.updateVisualizer(audioFileUrl);
    }

    protected void init(final Context context) {
        view = LayoutInflater.from(context).inflate(layout, this);

        visualizerBar = view.findViewById(R.id.vSoundBar);
        timer = view.findViewById(R.id.vTimer);
        actionButton = view.findViewById(R.id.vActionButton);

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()) {
                    actionButton.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_play));
                    mediaPlayer.stop();
                } else {
                    actionButton.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_pause));
                    mediaPlayer.start();
                }
            }
        });
    }


}
