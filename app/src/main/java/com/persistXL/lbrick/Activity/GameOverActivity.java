package com.persistXL.lbrick.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.persistXL.lbrick.R;

/**
 * Created by persistXL on 18/6/18.
 */
public class GameOverActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);

        // Remove status and title bar.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Get current score.
        int currentScore = this.getIntent().getIntExtra("currentScore", 0);

        // Get highest score.
        SharedPreferences sharedPreferences = getSharedPreferences("highestScore", 0);
        int highestScore = sharedPreferences.getInt("highestScore", 0);

        TextView highestScoreTextView = (TextView)findViewById(R.id.highestScore);
        TextView currentScoreTextView = (TextView)findViewById(R.id.currentScore);

        highestScoreTextView.setText("" + highestScore);
        currentScoreTextView.setText("" + currentScore);

        Button restartButton = (Button)findViewById(R.id.restart);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(GameOverActivity.this, GameActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
