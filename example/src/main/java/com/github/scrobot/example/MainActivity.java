package com.github.scrobot.example;

import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.github.scrobot.audiovisualizer.SoundWaveView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        final SoundWaveView view = findViewById(R.id.vWaveView);

        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/raw/audio_sample");

        try {
            view.addAudioFileUri(uri);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        soundVisualizerBarView.updateVisualizer(readTextFile(XmlFileInputStream));

//        Observable.interval(100, TimeUnit.MILLISECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(Long aLong) throws Exception {
//                        soundVisualizerBarView.updatePlayerPercent(aLong.floatValue() / 100f);
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        throwable.printStackTrace();
//                    }
//                });
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(mediaPlayer.isPlaying()) {
//                    button.setText("Play");
//                    mediaPlayer.stop();
//                } else {
//                    button.setText("Stop");
//                    mediaPlayer.start();
//                }
//            }
//        });

    }

}
