package com.example.challenaudiodeepfake;

import androidx.appcompat.app.AppCompatActivity;

import android.location.GnssNavigationMessage;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    private Button submitButton;
    private EditText inputText;
    private String inputTextAsString;
    private LinearLayout popupLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        submitButton = findViewById(R.id.submitButton);
        inputText = findViewById(R.id.inputText);
        popupLayout = findViewById(R.id.popupLayout);

        popupLayout.setVisibility(View.GONE);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputTextAsString = inputText.getText().toString();
                popupLayout.setVisibility(View.VISIBLE);

            }
        });
    }

}
