package com.example.challenaudiodeepfake;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.location.GnssNavigationMessage;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    private Button submitButton;
    private EditText inputText;
    private String inputTextAsString;
    private LinearLayout popupLayout;
    private SeekBar seekBar;
    //private Button pauseButton;
    private Button startButton;
    private MediaPlayer mediaPlayer;
    private int timeLength;
    private int width;
    private int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        submitButton = findViewById(R.id.submitButton);
        inputText = findViewById(R.id.inputText);
        popupLayout = findViewById(R.id.popupLayout);
        seekBar = findViewById(R.id.seekBar);
        //pauseButton = findViewById(R.id.pauseButton);
        startButton = findViewById(R.id.startButton);

        popupLayout.setVisibility(View.GONE);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputTextAsString = inputText.getText().toString();
                popupLayout.setVisibility(View.VISIBLE);
            }
        });



        mediaPlayer = MediaPlayer.create(this, R.raw.hide);
        mediaPlayer.setLooping(true);
        mediaPlayer.seekTo(0);
        mediaPlayer.setVolume(1, 1);
        timeLength = mediaPlayer.getDuration();

        seekBar.setMax(timeLength);
        seekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser == true) {
                            mediaPlayer.seekTo(progress);
                            seekBar.setProgress(progress);
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                }
        );

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mediaPlayer != null) {
                    try {
                        Message msg = new Message();
                        msg.what = mediaPlayer.getCurrentPosition();
                        handler.sendMessage(msg);
                        Thread.sleep(1000);
                    } catch (InterruptedException e){ }
                }
            }
        }).start();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int currentPosition = msg.what;
            seekBar.setProgress(currentPosition);
        }
    };

    public void playButton(View view) {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            startButton.setBackgroundResource(R.drawable.stop);
        } else {
            mediaPlayer.pause();
            startButton.setBackgroundResource(R.drawable.play);
        }
    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
}
