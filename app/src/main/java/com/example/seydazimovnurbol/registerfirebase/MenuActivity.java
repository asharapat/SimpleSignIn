package com.example.seydazimovnurbol.registerfirebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    private Button playButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        playButton = (Button) findViewById(R.id.playButton);
        playButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == playButton){
            finish();
            startActivity(new Intent(this, PlayActivity.class));
        }
    }
}
