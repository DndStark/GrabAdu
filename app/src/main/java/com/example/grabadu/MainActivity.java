package com.example.grabadu;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private Button bPlay, bSave;
    private String path = "";
    MediaRecorder mRecor;
    Boolean stateMediaR = true;

    MediaPlayer mPlayer;
    Boolean stateMediaP = true;

    final int REQUEST_PER_CODE = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bPlay = (Button) findViewById(R.id.bPlay);
        bSave = (Button) findViewById(R.id.bSave);

        bSave.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(stateMediaR) {
                     bSave.setText("Parar");
                     path = Environment.getExternalStorageDirectory().getAbsolutePath()
                             + "/" + UUID.randomUUID().toString() + "dnpa.3gp";
                     mRecor = new MediaRecorder();
                     mRecor.setAudioSource(MediaRecorder.AudioSource.MIC);
                     mRecor.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                     mRecor.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                     mRecor.setOutputFile(path);
                     try {
                         mRecor.prepare();
                         mRecor.start();
                     } catch (IOException e) {
                         e.printStackTrace();
                         Toast.makeText(MainActivity.this,"mal", Toast.LENGTH_LONG );
                     }
                     stateMediaR=false;
                 }else{
                     mRecor.stop();
                     bSave.setText("Grabar");
                     bPlay.setEnabled(true);
                     stateMediaR = true;
                 }
             }
        });

        bPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer = new MediaPlayer();
                if(stateMediaP) {
                    bPlay.setText("Pausar");
                    try {
                        mPlayer.setDataSource(path);
                        mPlayer.prepare();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    mPlayer.start();
                }else{
                    bPlay.setText("Reproducir");
                }
            }
        });
    }

}