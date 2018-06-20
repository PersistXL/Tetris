package com.persistXL.lbrick.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.persistXL.lbrick.Custom.LColor;
import com.persistXL.lbrick.Custom.LConfig;
import com.persistXL.lbrick.Model.Brick;
import com.persistXL.lbrick.Model.Brick_I;
import com.persistXL.lbrick.Model.Brick_J;
import com.persistXL.lbrick.Model.Brick_L;
import com.persistXL.lbrick.Model.Brick_O;
import com.persistXL.lbrick.Model.Brick_S;
import com.persistXL.lbrick.Model.Brick_T;
import com.persistXL.lbrick.Model.Brick_Z;
import com.persistXL.lbrick.R;
import com.persistXL.lbrick.View.AllBricksView;
import com.persistXL.lbrick.View.CurrentBrickView;
import com.persistXL.lbrick.View.GameStatusView;

import java.util.Random;

/**
 * Created by persistXL on 18/6/17.
 */
public class GameActivity extends Activity {

    /*
    当前方块
    下一个方块
     */
    private Brick currentBrick;
    private Brick nextBrick;

    private int gameSpeed = 1000;
    private int currentScore = 0;
    private int currentLevel = 1;
    private int currentLevelScore = 0;  //当前等级的分数

    private CurrentBrickView currentBrickView;
    private AllBricksView allBricksView;
    private GameStatusView gameStatusView;
    private TextView currentScoreTextView;
    private TextView currentLevelTextView;
    private Button pauseAndResume;

    private int elimateRowEnd = 0;
    private int elimateRowsLen = 0;

    private boolean gameIsRunning = true;

    Handler handler = new Handler();


    Runnable update_thread = new Runnable()
    {
        /*
        静态代码块
        */
        public void run()
        {
            if (currentBrick.brickCanFallDownWithBricks(allBricksView.bricks)) {
                currentBrick.fallOneRow();
                currentBrickView.invalidate();
            } else {
                if (gameIsOver()) {
                    resetHighestScore();

                    // Show game over activity.
                    Intent intent = new Intent();
                    intent.setClass(GameActivity.this, GameOverActivity.class);
                    intent.putExtra("currentScore", currentScore);
                    startActivity(intent);
                    finish();

                    return;
                }

                allBricksView.setupCurrentBrick(currentBrick);
                allBricksView.invalidate();

                currentBrickView.setCurrentBrick(null);
                currentBrickView.invalidate();

                if (hasRowsToElimate(allBricksView.bricks)) {
                    elimateRows(allBricksView.bricks);
                    increaseScore();
                    currentScoreTextView.setText("" + currentScore);

                    if (reachLevelUpScore()) {
                        levelUp();
                        currentLevelTextView.setText("" + currentLevel);
                    }
                }

                generalUpdate();
            }

            handler.postDelayed(update_thread, gameSpeed);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Remove status and title bar.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        allBricksView = (AllBricksView)findViewById(R.id.allBricksView);
        currentBrickView = (CurrentBrickView)findViewById(R.id.currentBrickView);
        currentBrickView.setBricks(allBricksView.bricks);

        currentBrick = generateNextBrick();
        currentBrickView.setCurrentBrick(currentBrick);

        nextBrick = generateNextBrick();

        gameStatusView = (GameStatusView)findViewById(R.id.gameStatus);
        gameStatusView.setNextBrick(nextBrick);

        currentScoreTextView = (TextView)findViewById(R.id.currentScore);
        currentLevelTextView = (TextView)findViewById(R.id.currentLevel);

        Button rotateBrickButton = (Button)findViewById(R.id.rotateBrick);
        rotateBrickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentBrick.rotateBrickWithBricks(allBricksView.bricks))
                    currentBrickView.invalidate();
            }
        });

        pauseAndResume = (Button)findViewById(R.id.pauseAndResume);
        pauseAndResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gameIsRunning) {
                    pauseAndResume.setBackgroundResource(R.drawable.resume);
                    gameIsRunning = false;

                    handler.removeCallbacks(update_thread);
                } else {
                    pauseAndResume.setBackgroundResource(R.drawable.pause);
                    gameIsRunning =true;

                    handler.post(update_thread);
                }
            }
        });

        handler.post(update_thread);
    }

    /*
    暂停
     */
    @Override
    protected void onPause() {
        super.onPause();

        pauseAndResume.setBackgroundResource(R.drawable.resume);
        gameIsRunning = false;

        handler.removeCallbacks(update_thread);
    }

    /*
    判断销毁条件
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();

        resetHighestScore();
    }

    /*
    生成下一个方块
     */
    private Brick generateNextBrick() {
        Brick brick;

        Random random = new Random();
        switch (random.nextInt(7)) {
            case 0:
                brick = new Brick_L(); break;
            case 1:
                brick = new Brick_I(); break;
            case 2:
                brick = new Brick_O(); break;
            case 3:
                brick = new Brick_S(); break;
            case 4:
                brick = new Brick_T(); break;
            case 5:
                brick = new Brick_Z(); break;
            default:
                brick = new Brick_J(); break;
        }

        return brick;
    }

    private void generalUpdate() {
        currentBrick = nextBrick;
        currentBrickView.setCurrentBrick(currentBrick);
        nextBrick = generateNextBrick();

        gameStatusView.setNextBrick(nextBrick);
        gameStatusView.invalidate();
    }

    /*
    判断是否有行消除
     */
    private boolean hasRowsToElimate(int[][] bricks) {
        elimateRowEnd = 0;
        elimateRowsLen = 0;

        while (elimateRowEnd < LConfig.rowsOfBricks && elimateRowsLen <=4) {
            for (int i = 0; i < LConfig.colsOfBricks; ++i) {
                if (bricks[elimateRowEnd][i] == LColor.black() && elimateRowsLen != 0) return true;
                else if (bricks[elimateRowEnd][i] == LColor.black() && elimateRowsLen == 0) {
                    ++elimateRowEnd;
                    break;
                } else if (bricks[elimateRowEnd][i] != LColor.black() && i == LConfig.colsOfBricks - 1) {
                    ++elimateRowEnd;
                    ++elimateRowsLen;
                }
            }
        }

        if (elimateRowsLen != 0) return true;

        return false;
    }

    /*
    消除行
     */
    private void elimateRows(int[][] bricks) {
        int tempLen = elimateRowsLen;

        // Reset elimate rows.
        while (tempLen != 0) {
            for (int i = 0; i < LConfig.colsOfBricks; ++i) {
                bricks[elimateRowEnd - tempLen][i] = LColor.black();
            }

            --tempLen;
        }

        for (int i = elimateRowEnd - elimateRowsLen - 1; i >= 0; --i) {
            for (int j = 0; j < LConfig.colsOfBricks; ++j) {
                bricks[i + elimateRowsLen][j] = bricks[i][j];
            }
        }
    }

    /*
    加分
     */
    private void increaseScore() {
        switch (elimateRowsLen) {
            case 1: {
                currentScore += 100;
                currentLevelScore += 100;
                break;
            }
            case 2: {
                currentScore += 300;
                currentLevelScore += 300;
                break;
            }
            case 3: {
                currentScore += 500;
                currentLevelScore += 500;
                break;
            }
            default: {
                currentScore += 700;
                currentLevelScore += 700;
                break;
            }
        }
    }

    /*
    达到等级提高分数
     */
    private boolean reachLevelUpScore() {
        int levelUpScore = 1000 * currentLevel;
        if (currentLevelScore >= levelUpScore) return true;

        return false;
    }

    /*
    升级
     */
    private void levelUp() {
        ++currentLevel;
        currentLevelScore = 0;

        // Reduse game speed.
        if (gameSpeed == 100) return;
        gameSpeed -= 50 * (currentLevel - 1);
    }

    /*
    游戏结束
     */
    private boolean gameIsOver() {
        for (int i = 0; i < 4; ++i) {
            if (currentBrick.brickPieces[i].getRow() <= 0) return true;
        }

        return false;
    }

    /*
    重置最高分
     */
    private void resetHighestScore() {
        SharedPreferences sharedPreferences = getSharedPreferences("highestScore", 0);
        int highestScore = sharedPreferences.getInt("highestScore", 0);

        if (currentScore <= highestScore) return;

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("highestScore", currentScore);
        editor.commit();
    }
}
