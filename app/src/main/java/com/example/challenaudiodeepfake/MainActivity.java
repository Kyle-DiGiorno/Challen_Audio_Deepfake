package com.example.challenaudiodeepfake;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.location.GnssNavigationMessage;
import android.media.MediaPlayer;
import android.os.Bundle;
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
    private Button pauseButton;
    private Button startButton;
    private MediaPlayer mediaPlayer;
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
        pauseButton = findViewById(R.id.pauseButton);
        startButton = findViewById(R.id.startButton);

        popupLayout.setVisibility(View.GONE);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputTextAsString = inputText.getText().toString();
                popupLayout.setVisibility(View.VISIBLE);
            }
        });


    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
}
