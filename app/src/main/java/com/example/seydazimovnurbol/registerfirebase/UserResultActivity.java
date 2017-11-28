package com.example.seydazimovnurbol.registerfirebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserResultActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView resultTextView;
    private Button shareButton;
    private Button startAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_result);
        resultTextView = (TextView) findViewById(R.id.userResultTextView);
        shareButton = (Button) findViewById(R.id.shareButton);
        startAgain = (Button) findViewById(R.id.startButton);


        long resTime = getIntent().getLongExtra("finished_time", 10000000);

        System.out.println("resTime = " + resTime);


        final String myText = "Congrats! You finished in " + Double.toString(resTime * 0.001) + " seconds";

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, myText);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });


        resultTextView.setText(myText);
        startAgain.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == startAgain){
            finish();
            startActivity(new Intent(this, PlayActivity.class));
        }
    }
}
