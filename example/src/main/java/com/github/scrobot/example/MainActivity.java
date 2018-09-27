package com.github.scrobot.example;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.github.scrobot.audiovisualizer.PlayerVisualizerView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = findViewById(R.id.button);
        final PlayerVisualizerView playerVisualizerView = findViewById(R.id.playerVisualizerView);

        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/raw/audio_sample");

        final MediaPlayer mediaPlayer = MediaPlayer.create(this, uri);

        InputStream XmlFileInputStream = getResources().openRawResource(R.raw.audio_sample);

        playerVisualizerView.updateVisualizer(readTextFile(XmlFileInputStream));

        Observable.interval(100, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        playerVisualizerView.updatePlayerPercent(aLong.floatValue() / 100f);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()) {
                    button.setText("Play");
                    mediaPlayer.stop();
                } else {
                    button.setText("Stop");
                    mediaPlayer.start();
                }
            }
        });

    }

    public byte[] readTextFile(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte buf[] = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {

        }
        return outputStream.toByteArray();
    }
}
