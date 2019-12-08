package com.example.challenaudiodeepfake;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Point;
import android.location.GnssNavigationMessage;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    private Button submitButton;
    private LinearLayout popupLayout;
    private SeekBar seekBar;
    //private Button pauseButton;
    private Button startButton;
    private MediaPlayer mediaPlayer;
    private int timeLength;
    private int width;
    private int height;
    private ImageView challenmouthflap;
    private android.view.animation.Animation animation;

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

        popupLayout = findViewById(R.id.popupLayout);
        seekBar = findViewById(R.id.seekBar);
        //pauseButton = findViewById(R.id.pauseButton);
        startButton = findViewById(R.id.startButton);
        challenmouthflap = findViewById(R.id.challenmouthflap);



        popupLayout.setVisibility(View.GONE);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupLayout.setVisibility(View.VISIBLE);
            }
        });



        mediaPlayer = MediaPlayer.create(this, R.raw.hide);
        mediaPlayer.setLooping(true);
        mediaPlayer.seekTo(0);
        mediaPlayer.setVolume(5, 5);
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

            /*
            animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.down);
            animation.setDuration(timeLength);
            animation.setRepeatCount(-1);
            animation.setRepeatMode(Animation.REVERSE);
            animation.setInterpolator(new LinearInterpolator());
            challenmouthflap.startAnimation(animation);

             */

            /*
            ObjectAnimator animatorY = ObjectAnimator.ofFloat(challenmouthflap, "y", 30f);
            ObjectAnimator animatorYY = ObjectAnimator.ofFloat(challenmouthflap, "y", -300f);
            animatorY.setDuration(timeLength);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(animatorY, animatorYY);
            //animatorSet.play(animatorY);
            animatorSet.start();

             */
            /*
            challenmouthflap.setVisibility(View.GONE);
            challenmouthflap.setVisibility(View.VISIBLE);
            animation = new TranslateAnimation(0, 0, 0, 500);
            animation.setDuration(10000);
            animation.setFillAfter(true);
            animation.setRepeatCount(-1);
            animation.setRepeatMode(Animation.REVERSE);
            challenmouthflap.setAnimation(animation);

             */
            challenmouthflap.setVisibility(View.GONE);
            challenmouthflap.setVisibility(View.VISIBLE);
            animation = new TranslateAnimation(
                    TranslateAnimation.ABSOLUTE, 0f,
                    TranslateAnimation.ABSOLUTE, 0f,
                    TranslateAnimation.RELATIVE_TO_PARENT, 0f,
                    TranslateAnimation.RELATIVE_TO_PARENT, 0.1f);
            animation.setDuration(200);
            animation.setRepeatCount(-1);
            animation.setRepeatMode(Animation.REVERSE);
            animation.setInterpolator(new LinearInterpolator());
            challenmouthflap.setAnimation(animation);



            /*
            challenmouthflap.setVisibility(View.GONE);
            challenmouthflap.setVisibility(View.VISIBLE);
            Animation mAnimation = new TranslateAnimation(
                    TranslateAnimation.ABSOLUTE, 0f,
                    TranslateAnimation.ABSOLUTE, 0f,
                    TranslateAnimation.RELATIVE_TO_PARENT, 0f,
                    TranslateAnimation.RELATIVE_TO_PARENT, 3f);
            mAnimation.setDuration(timeLength);
            mAnimation.setRepeatCount(-1);
            mAnimation.setRepeatMode(Animation.INFINITE);
            mAnimation.setInterpolator(new LinearInterpolator());
            challenmouthflap.setAnimation(mAnimation);

             */


            mediaPlayer.start();
            startButton.setBackgroundResource(R.drawable.stop);



        } else {
            mediaPlayer.pause();
            animation.cancel();
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
