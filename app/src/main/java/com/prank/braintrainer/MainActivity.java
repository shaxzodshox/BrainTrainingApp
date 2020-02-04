package com.prank.braintrainer;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity{

    private TextView tv_timer, tv_point, tv_question, resultTxt;
    private Button btnOpt1, btnOpt2, btnOpt3, btnOpt4, btnPlayAgain;

    private int A = 0;
    private int B = 0;

    private int score = 0;
    private int questionNumber = 0;

    int timer = 10000;

    private int locationOfCorrectAnswer;
    ArrayList<Integer> answers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        generateQuestions();

      startTimer();


        btnPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnOpt1.setEnabled(true);
                btnOpt2.setEnabled(true);
                btnOpt3.setEnabled(true);
                btnOpt4.setEnabled(true);


                tv_point.setText(score + "/" + questionNumber);
                btnPlayAgain.setVisibility(View.INVISIBLE);
                tv_timer.setText(String.valueOf(timer/1000));
                startTimer();
                generateQuestions();
            }
        });
    }

    private void startTimer() {
        new CountDownTimer(timer + 100,1000){
            @Override
            public void onTick(long millisUntilFinished) {
                tv_timer.setText(String.valueOf(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                tv_timer.setText("0");

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder
                        .setTitle("O'yin tugadi")
                        .setMessage("Siz " + questionNumber + " ta savoldan " +
                                score + " ta to'g'ri topdingiz.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();

                clearGame();

                btnOpt1.setEnabled(false);
                btnOpt2.setEnabled(false);
                btnOpt3.setEnabled(false);
                btnOpt4.setEnabled(false);

                btnPlayAgain.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    private void clearGame() {
        btnOpt1.setText("");
        btnOpt2.setText("");
        btnOpt3.setText("");
        btnOpt4.setText("");
        tv_question.setText("");
        score = 0;
        questionNumber = 0;
    }


    private void initViews() {
        tv_timer = findViewById(R.id.timerView);
        tv_point = findViewById(R.id.countView);
        tv_question = findViewById(R.id.questionView);
        resultTxt = findViewById(R.id.resultTxt);
        btnOpt1 = findViewById(R.id.opt1);
        btnOpt2 = findViewById(R.id.opt2);
        btnOpt3 = findViewById(R.id.opt3);
        btnOpt4 = findViewById(R.id.opt4);
        btnPlayAgain = findViewById(R.id.playAgain);
    }

    public void chooseAnswer(View view) {
        if(view.getTag().toString().equals(String.valueOf(locationOfCorrectAnswer))){
            score++;
            resultTxt.setVisibility(View.VISIBLE);
            resultTxt.setText("Javob to'g'ri!");

            new CountDownTimer(1000,1000){
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                resultTxt.setVisibility(View.INVISIBLE);
                }
            }.start();

        }
        else{
            resultTxt.setVisibility(View.VISIBLE);
            resultTxt.setTextColor(Color.parseColor("#CD5C5C"));
            resultTxt.setText("Javob noto'g'ri!");

            new CountDownTimer(1000,1000){
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    resultTxt.setVisibility(View.INVISIBLE);
                    resultTxt.setTextColor(Color.parseColor("#DDCECE"));
                }
            }.start();
        }

        questionNumber++;
        tv_point.setText(score + "/" + questionNumber);
        generateQuestions();
    }
    @SuppressLint("SetTextI18n")
    public void generateQuestions(){
        Random random = new Random();
        A = random.nextInt(21);
        B = random.nextInt(21);

        tv_question.setText(A + " + " + B);

        locationOfCorrectAnswer = random.nextInt(4);

        answers.clear();

        int incorrectAnswer;

        for(int i = 0; i < 4; i++){
            if(i == locationOfCorrectAnswer){
                answers.add(A + B);
            }
            else{
                incorrectAnswer = random.nextInt(41);
                while (incorrectAnswer == A + B){
                    incorrectAnswer = random.nextInt(41);
                }
                answers.add(incorrectAnswer);
            }
        }
        btnOpt1.setText(Integer.toString(answers.get(0)));
        btnOpt2.setText(Integer.toString(answers.get(1)));
        btnOpt3.setText(Integer.toString(answers.get(2)));
        btnOpt4.setText(Integer.toString(answers.get(3)));
    }
}

