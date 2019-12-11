package com.example.challenaudiodeepfake;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.graphics.Point;
import android.location.GnssNavigationMessage;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
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

import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;

import org.jtransforms.fft.DoubleFFT_1D;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
    static double bytesToDouble(byte firstByte, byte secondByte) {
        // convert two bytes to one short (little endian)
        int s = (secondByte << 8) | firstByte;
        // convert to range from -1 to (just below) 1
        return s / 32768.0;
    }

    // Returns left and right double arrays. 'right' will be null if sound is mono.
    public void openWav(String filename, double[] left, double[] right) throws IOException
    {

            byte[] wav = Files.readAllBytes(Paths.get(filename));

        // Determine if mono or stereo
        int channels = wav[22];     // Forget byte 23 as 99.999% of WAVs are 1 or 2 channels

        // Get past all the other sub chunks to get to the data subchunk:
        int pos = 12;   // First Subchunk ID from 12 to 16

        // Keep iterating until we find the data chunk (i.e. 64 61 74 61 ...... (i.e. 100 97 116 97 in decimal))
        while(!(wav[pos]==100 && wav[pos+1]==97 && wav[pos+2]==116 && wav[pos+3]==97)) {
            pos += 4;
            int chunkSize = wav[pos] + wav[pos + 1] * 256 + wav[pos + 2] * 65536 + wav[pos + 3] * 16777216;
            pos += 4 + chunkSize;
        }
        pos += 8;

        // Pos is now positioned to start of actual sound data.
        int samples = (wav.length - pos)/2;     // 2 bytes per sample (16 bit sound mono)
        if (channels == 2) samples /= 2;        // 4 bytes per sample (16 bit stereo)

        // Allocate memory (right will be null if only mono sound)
        left = new double[samples];
        if (channels == 2) right = new double[samples];
        else right = null;

        // Write to double array/s:
        int i=0;
        while (pos < left.length) {
            left[i] = bytesToDouble(wav[pos], wav[pos + 1]);
            pos += 2;
            if (channels == 2) {
                right[i] = bytesToDouble(wav[pos], wav[pos + 1]);
                pos += 2;
            }
            i++;
        }
    }
    //$ ffmpeg -i somefile.wav -f segment -segment_time 3 -c copy out%03d.wav
    public String[] getSplitCommand(String inputFileUrl, String outputFileUrl,
                                  String start, String end) {
        if ((TextUtils.isEmpty(inputFileUrl) && (TextUtils.isEmpty(outputFileUrl)))) {
            return null;
        }
        String[] stringBuilder = {"-i", inputFileUrl, "-f", "segment", "-segment_time", "0.01", "-c", "copy", "out%03d.wav"};
        /*stringBuilder.append("-y ")
                .append("-i ")
                .append(inputFileUrl).append(" ")
                .append("-ss ")
                .append(start).append(" ")
                .append("-codec ")
                .append("copy ")
                .append("-t ")
                .append(end).append(" ")
                .append(outputFileUrl);*/
        return stringBuilder;
    }

    public void executeBinaryCommand(FFmpeg ffmpeg, final ProgressDialog progressDialog, final String[] command) {
        if (command != null && command.length != 0) {
            try {
                ffmpeg.execute(command, new ExecuteBinaryResponseHandler() {
                    @Override
                    public void onFailure(String response) {
                        progressDialog.setMessage(response);
                    }

                    @Override
                    public void onSuccess(String response) {
                        progressDialog.setMessage(response);

                    }

                    @Override
                    public void onProgress(String response) {
                        progressDialog.setMessage(response);
                    }

                    @Override
                    public void onStart() {
                        progressDialog.show();
                    }

                    @Override
                    public void onFinish() {
                        progressDialog.dismiss();
                    }
                });
            } catch (FFmpegCommandAlreadyRunningException exception) {
                exception.printStackTrace();
            }
        }
    }


}
