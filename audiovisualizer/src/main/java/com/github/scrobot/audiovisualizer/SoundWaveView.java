package com.github.scrobot.audiovisualizer;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.github.scrobot.audiovisualizer.player.DefaultSoundViewPlayer;
import com.github.scrobot.audiovisualizer.player.SoundViewPlayer;
import com.github.scrobot.audiovisualizer.player.SoundViewPlayerOnCompleteListener;
import com.github.scrobot.audiovisualizer.player.SoundViewPlayerOnDurationListener;
import com.github.scrobot.audiovisualizer.player.SoundViewPlayerOnPauseListener;
import com.github.scrobot.audiovisualizer.player.SoundViewPlayerOnPlayListener;
import com.github.scrobot.audiovisualizer.player.SoundViewPlayerOnPreparedListener;
import com.github.scrobot.audiovisualizer.utils.ConverterUtil;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class SoundWaveView extends FrameLayout implements SoundViewPlayerOnPlayListener,
        SoundViewPlayerOnDurationListener,
        SoundViewPlayerOnPauseListener,
        SoundViewPlayerOnPreparedListener,
        SoundViewPlayerOnCompleteListener {

    protected final Context context;
    protected SoundViewPlayer player = new DefaultSoundViewPlayer();
    protected int layout = R.layout.sounwave_view;

    private SoundVisualizerBarView visualizerBar;
    private TextView timer;
    private ImageView actionButton;
    private AtomicInteger duration = new AtomicInteger();

    private final String TAG = SoundWaveView.class.getCanonicalName();

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

    public void setPlayer(SoundViewPlayer player) {
        this.player = player;
    }

    public void addAudioFileUri(final Uri audioFileUri) throws IOException {
        player.setAudioSource(context, audioFileUri);

        visualizerBar.updateVisualizer(audioFileUri);
    }

    public void addAudioFileUrl(String audioFileUrl) throws IOException {
        player.setAudioSource(audioFileUrl);

        visualizerBar.updateVisualizer(audioFileUrl);
    }

    protected void init(final Context context) {
        View view = LayoutInflater.from(context).inflate(layout, this);

        player.setOnCompleteListener(this)
                .setOnDurationListener(this)
                .setOnPauseListener(this)
                .setOnPlayListener(this)
                .setOnPrepariedListener(this);

        visualizerBar = view.findViewById(R.id.vSoundBar);
        timer = view.findViewById(R.id.vTimer);
        actionButton = view.findViewById(R.id.vActionButton);

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.toggle();
            }
        });
    }


    @Override
    public void onDurationProgress(SoundViewPlayer player, Long duration, Long currentTimestamp) {
        visualizerBar.updatePlayerPercent(currentTimestamp / (float) duration);
        timer.setText(ConverterUtil.convertMillsToTime(duration - currentTimestamp));
    }

    @Override
    public void onPause(SoundViewPlayer player) {
        actionButton.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_play));
    }

    @Override
    public void onPlay(SoundViewPlayer player) {
        actionButton.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_pause));
    }

    @Override
    public void onPrepared(SoundViewPlayer player) {
        timer.setText(ConverterUtil.convertMillsToTime(player.getDuration()));
    }

    @Override
    public void onComplete(SoundViewPlayer player) {
        actionButton.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_play));
        visualizerBar.updatePlayerPercent(0);
    }
}
