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

        final SoundWaveView view = findViewById(R.id.vWaveView);

        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/raw/audio_sample");

        try {
            view.addAudioFileUri(uri);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
