package com.example.armoryandmachine;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class JobActivity extends AppCompatActivity {

    JobViewModel jobViewModel;
    int position;
    static public boolean active = false;
    Enemy enemy;
    double totalTimeForMove = 0;
    double currentTimeForMove = 0;
    ProgressBar enemyHealthBar;
    TextView enemyHealthCounter;
    ProgressBar enemyMoveBar;
    TextView enemyMoveNameView;
    ProgressBar playerHealthBar;
    TextView playerHealthCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs);
        jobViewModel =
                new ViewModelProvider(this).get(JobViewModel.class);
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 1);
        String enemyName = getEnemyName(position);
        TextView enemyNameView = findViewById(R.id.enemyName);
        enemyHealthBar = findViewById(R.id.enemyHealthBar);
        playerHealthBar = findViewById(R.id.playerHealthBar);
        playerHealthCounter = findViewById(R.id.playerHealthCounter);
        enemyHealthCounter = findViewById(R.id.enemyHealthCounter);
        enemyMoveBar = findViewById(R.id.moveBar);
        enemyMoveNameView = findViewById(R.id.enemyMoveName);
        enemyNameView.setText(enemyName);
        enemy = new Enemy(position);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(1>0) {
                    if(jobViewModel.isPlayerAlive()==false) {
                        finish();
                    }
                    if(enemy.isAlive()==false) {
                        finish();
                    }
                    if(currentTimeForMove <= 0) {
                        jobViewModel.reduceTotalHealth(enemy.getCurrentMoveDamage());
                        totalTimeForMove = enemy.getNextMoveTime(position);
                        currentTimeForMove = totalTimeForMove;
                        new Handler(Looper.getMainLooper()).post(new Runnable(){
                            @Override
                            public void run() {
                                enemyMoveNameView.setText(enemy.getNextMoveName(position));
                            }
                        });
                    }
                    currentTimeForMove -= 6;
                    double portionOfTotalMoveLeft = (currentTimeForMove/totalTimeForMove) * 100;
                    enemyMoveBar.setProgress((int) portionOfTotalMoveLeft);
                    jobViewModel.playerHealth -= .01;
                    new Handler(Looper.getMainLooper()).post(new Runnable(){
                        @Override
                        public void run() {
                            playerHealthCounter.setText(String.valueOf((int)jobViewModel.playerHealth));
                            playerHealthBar.setProgress((int) jobViewModel.getPortionHealth());
                            enemyHealthCounter.setText(String.valueOf(enemy.currentHealth));
                            enemyHealthBar.setProgress((int) enemy.currentHealth);
                        }
                    });
                    SystemClock.sleep(5);
                }
            }
        }).start();

    }

    public void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void executeMove1(View v) {
        System.out.println("Execute order 66");
        TextView view = (TextView) v;
        Move move = new Move();
        enemy.hurt(move.getMoveDamage((String) view.getText()));
    }

    public void executeMove2(View v) {

    }

    public void executeMove3(View v) {

    }

    public void executeMove4(View v) {

    }

    private String getEnemyName(int postion) {
        switch(position) {
            case 0: return "Mobile App Consult";
            case 1: return "Small Game Mod";
            case 2: return "Indie Game Consult";
            case 3: return "Mobile App Co-Creator";
            case 4: return "Basic Video Game Consult";
            case 5: return "Indie Game Co-Creator";
            case 6: return "Medium Game Mod";
            case 7: return "Solo mobile App";
            case 8: return "Polished Video Game Consult";
            case 9: return "Large Mod";
            case 10: return "AAA Video Game Consult";
            case 11: return "Basic Video Game Co-Creator";
            case 12: return "Solo Indie Game";
            case 13: return "Polished Video Game Co-Creator";
            case 14: return "Solo Basic Video Game";
            case 15: return "AAA Video Game Co-Creator";
            case 16: return "Solo Polished Video Game";
            case 17: return "Solo AAA Video Game";
            case 18: return "Solo Industry Defining Game";
            default: return "Default Name";
        }
    }
}
