package com.example.seydazimovnurbol.registerfirebase;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seydazimovnurbol.registerfirebase.Adapter.GridViewAnswerAdapter;
import com.example.seydazimovnurbol.registerfirebase.Adapter.GridViewSuggestAdapter;
import com.example.seydazimovnurbol.registerfirebase.Common.Common;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

public class PlayActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    public List<String> suggestSource = new ArrayList<>();

    public GridViewAnswerAdapter answerAdapter;
    public GridViewSuggestAdapter suggestAdapter;

    public Button btnSubmit;
    public Button btnHelp;


    public GridView gridViewAnswer, gridViewSuggest;
    public ImageView imgViewQuestion;
    static int cnt = 0;



    public char[] answer;

    String correct_answer;


    private int cntOfAnsweredQuestions = 0;

    private Stopwatch timer;
    TextView tryNumber;
    public int numberOfTry;

    private ArrayList<Fighter> fighters = new ArrayList<Fighter>();



    TextView timerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        cnt = 0;

        timerView = (TextView) findViewById(R.id.timerView);
        tryNumber = (TextView) findViewById(R.id.tryNumber);
        myModel.sharedData.setCount(3);
        int n = myModel.sharedData.getCount();
        tryNumber.setText("Number of life: "+Integer.toString(n));



        System.out.println(FirebaseDatabase.getInstance().getReference());

        FirebaseDatabase.getInstance().getReference().child("fighters").addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        System.out.println("it works.");
                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                            String name = dsp.getKey();
                            String imageUrl = dsp.getValue().toString();
                            System.out.println("result " + name + " " + imageUrl);
                            fighters.add(new Fighter(name, imageUrl));
                        }


                        // do it
                        CountUpTimer timerOnView = new CountUpTimer(30000000) {
                            public void onTick(int second) {
                                timerView.setText(String.valueOf(second));
                            }
                        };

                        timerOnView.start();

                        cntOfAnsweredQuestions = 0;
                        timer = new Stopwatch();
                        timer.start();
                        initView();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );

    }




    private void initView(){
        gridViewAnswer = (GridView) findViewById(R.id.gridViewAnswer);
        gridViewSuggest = (GridView) findViewById(R.id.gridViewSuggest);
        imgViewQuestion = (ImageView) findViewById(R.id.imageLogo);

        // add setupList here
        setupList();



            }



    public void submit(){
        String result = "";
        for(int i = 0; i< Common.user_submit_answer.length; i++){
            result+= String.valueOf(Common.user_submit_answer[i]);
        }

        if(result.equals(correct_answer )) {
            Toast.makeText(getApplicationContext(), "Finish! This is " + result, Toast.LENGTH_SHORT).show();

            // reset
            //Common.count = 0;
            Common.user_submit_answer = new char[correct_answer.length()];
            // Set Adapter
            GridViewAnswerAdapter answerAdapter = new GridViewAnswerAdapter(setupNullList(), getApplicationContext());
            gridViewAnswer.setAdapter(answerAdapter);
            answerAdapter.notifyDataSetChanged();

            suggestAdapter = new GridViewSuggestAdapter(suggestSource, getApplicationContext(), PlayActivity.this);
            gridViewSuggest.setAdapter(suggestAdapter);
            suggestAdapter.notifyDataSetChanged();

            System.out.println("cntOfAnsweredQuestions " + cntOfAnsweredQuestions);
            System.out.println(fighters.size());
            System.out.println(cntOfAnsweredQuestions);


            if (cntOfAnsweredQuestions == (fighters.size() - 1)) {

                Intent myIntent = new Intent(PlayActivity.this, UserResultActivity.class);
                long resTime = timer.getElapsedTime();

                myIntent.putExtra("finished_time", resTime);

                System.out.println(resTime);

                startActivity(myIntent);
            }
            cntOfAnsweredQuestions++;

            setupList();

        }
    }

    private void setupList() {
        // Random logo
        Random random = new Random();



        System.out.println("random number = " + cnt);
        if(cnt <= 2) {
            Fighter pickedFighter = fighters.get(cnt);
            Picasso.with(this).load(pickedFighter.getImageUrl()).into(imgViewQuestion);

            cnt++;

            correct_answer = pickedFighter.getName();
            answer = correct_answer.toCharArray();

            System.out.println(correct_answer);
            System.out.println(answer);

        }

        Common.user_submit_answer = new char[answer.length];
        // add answer character to list
        suggestSource.clear();
        for(char item: answer){
            // add logo name to list
            suggestSource.add(String.valueOf(item));
        }
        // Random add some character to list
        for(int i = answer.length; i<answer.length*2; i++){
            suggestSource.add(Common.alphabet_character[random.nextInt(Common.alphabet_character.length)]);

            // Sort random
            Collections.shuffle(suggestSource);

            //Set for GridView
            answerAdapter = new GridViewAnswerAdapter(setupNullList(),this);
            suggestAdapter = new GridViewSuggestAdapter(suggestSource, this,this);

            answerAdapter.notifyDataSetChanged();
            suggestAdapter.notifyDataSetChanged();

            gridViewSuggest.setAdapter(suggestAdapter);
            gridViewAnswer.setAdapter(answerAdapter);
        }
        int num = myModel.sharedData.getCount();
        System.out.println("number of try counts = "+num);
    }
    public void getCount(){
        int s = myModel.sharedData.getCount();
        if(s == 0){
            Intent myIntent = new Intent(PlayActivity.this, MenuActivity.class);
            Toast.makeText(PlayActivity.this, "Game Over!!!",Toast.LENGTH_SHORT).show();
            startActivity(myIntent);
            finish();
        }
        tryNumber.setText("Number of life: "+Integer.toString(s));
    }

    public void getHelp(){

    }


    private char[] setupNullList(){
        char result[] = new char[answer.length];
        for(int i = 0; i<answer.length; i++){
            result[i] = ' ';
        }
        return result;
    }

}