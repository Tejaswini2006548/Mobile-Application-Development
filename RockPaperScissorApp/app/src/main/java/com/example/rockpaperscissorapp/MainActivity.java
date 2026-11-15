package com.example.rockpaperscissorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Button;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView tvUserScore;
    private TextView tvComputerScore;
    private TextView tvTitle;
    private TextView tvComputerChoice;
    private TextView tvUserChoice;
    private TextView tvResult;

    private ImageView imgRock;
    private ImageView imgPaper;
    private ImageView imgScissors;
    private Button btnReset;
    private int userScore=0;
    private int ComputerScore=0;
    private String[] options={"Rock","Paper","Scissors"};
    private final Random random=new Random();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvUserScore=findViewById(R.id.tvUserScore);
        tvComputerScore=findViewById(R.id.tvComputerScore);
        tvComputerChoice=findViewById(R.id.tvComputerChoice);
        tvUserChoice=findViewById(R.id.tvUserChoice);
        tvResult=findViewById(R.id.tvResult);
        tvTitle=findViewById(R.id.title);
        imgRock=findViewById(R.id.imgRock);
        imgPaper=findViewById(R.id.imgPaper);
        imgScissors=findViewById(R.id.imgScissors);
        btnReset=findViewById(R.id.btnReset);
        imgRock.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                playGame("Rock");
            }
          });


        imgPaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playGame("Paper");
            }
        });
        imgScissors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                playGame("Scissors");
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                resetGame();
            }
        });

    }

    private void playGame(String UserChoice) {
        int randomNumber= random.nextInt(3);
        String computerChoice=options[randomNumber];


        tvUserChoice.setText("User Choice:"+UserChoice);
        tvComputerChoice.setText("Computer Choice: "+computerChoice);
        tvResult.setText("Computer Win!!");
        if(UserChoice.equals(computerChoice))
        {
            tvResult.setText("It is Drawn!!!");

        }
        else if(UserChoice.equals("Rock")&& computerChoice.equals("Scissors")
                || UserChoice.equals("Paper") && computerChoice.equals("Rock")
                || UserChoice.equals("Scissors") && computerChoice.equals("Paper"))
        {
            userScore++;
            tvResult.setText("You win!!");
        }
        else {
            ComputerScore++;
            tvResult.setText("Computer win!!");
        }
        updateScore();





    }
    private void updateScore(){
        tvUserScore.setText("You :"+userScore);
        tvComputerScore.setText("Computer :"+ComputerScore);
    }
    private void resetGame(){
        userScore=0;
        ComputerScore=0;
        tvUserScore.setText("You : 0");
        tvComputerScore.setText("Computer : 0");
        tvUserChoice.setText("User choice: Not selected");
        tvComputerChoice.setText("Computer choice: Not selected");
        tvResult.setText("Results Appear Here!!");

    }
}
