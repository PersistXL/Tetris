package com.persistXL.lbrick.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.persistXL.lbrick.Custom.LConfig;
import com.persistXL.lbrick.Model.Brick;

/**
 * Created by persistXL on 18/6/17.
 */
public class CurrentBrickView extends View {

    private static int row = LConfig.rowsOfBricks;
    private static int col = LConfig.colsOfBricks;

    public float marginTop = 0.0f;
    public float marginLeft = 0.0f;

    public float lengthOfBrick = 0.0f;

    private static Paint p = new Paint();
    private static Paint bp = new Paint();

    private Brick currentBrick;

    private int[][] bricks;

    public void setBricks(int[][] bricks) {
        this.bricks = bricks;
    }

    private GestureDetector gestureDetector;

    public CurrentBrickView(Context context) {
        super(context);
    }

    public CurrentBrickView(final Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                // Fall down rapidly.
                float absDistanceY = Math.abs(convertPxToDip(context, distanceY));
                if (absDistanceY > 12) {
                    // Compute the distanceY to add in deed.
                    int addDistanceY = (int)absDistanceY / 6;

                    while (currentBrick.brickCanFallDownWithBricks(bricks) && addDistanceY > 0) {
                        currentBrick.fallOneRow();
                        --addDistanceY;
                    }

                    invalidate();

                    return true;
                }

                // Shift left or right.
                float convertedDistanceX = convertPxToDip(context, distanceX);
                if (Math.abs(distanceX) > 19) {
                    if (distanceX > 0) {
                        currentBrick.shiftLeftWithBricks(bricks);
                    } else {
                        currentBrick.shiftRightWithBricks(bricks);
                    }

                    invalidate();

                    return true;
                }

                return false;
            }
        });
    }

    public void setCurrentBrick(Brick currentBrick) {
        this.currentBrick = currentBrick;
    }

    private float convertPxToDip(Context context, float px) {
        float scale = context.getResources().getDisplayMetrics().density;
        return px / scale + 0.5f * (px >= 0 ? 1 : -1);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // Get width and height of this view.
        int width = this.getWidth();
        int heigth = this.getHeight();

        // Compute the size of each brick and margin.
        float estimateLen1 = (float)width / (float)col;
        float estimateLen2 = (float)heigth / (float)row;

        if (estimateLen1 > estimateLen2) {
            lengthOfBrick = estimateLen2;
            marginLeft = (width - (lengthOfBrick * col)) / 2.0f;
        } else {
            lengthOfBrick = estimateLen1;
            marginTop = (heigth - (lengthOfBrick * row)) / 2.0f;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (currentBrick == null) return;

        p.setColor(currentBrick.color);
        p.setStyle(Paint.Style.FILL);

        bp.setColor(Color.WHITE);
        bp.setStyle(Paint.Style.STROKE);
        bp.setStrokeWidth(1.7f);

        for (int i = 0; i < 4; ++i) {
            int bRow = currentBrick.brickPieces[i].getRow();
            int bCol = currentBrick.brickPieces[i].getCol();

            if (bRow < 0) continue;

            canvas.drawRect(marginLeft + lengthOfBrick * bCol,
                    marginTop + lengthOfBrick * bRow,
                    marginLeft + lengthOfBrick * bCol + lengthOfBrick,
                    marginTop + lengthOfBrick * bRow + lengthOfBrick,
                    p);

            canvas.drawRect(marginLeft + lengthOfBrick * bCol,
                    marginTop + lengthOfBrick * bRow,
                    marginLeft + lengthOfBrick * bCol + lengthOfBrick,
                    marginTop + lengthOfBrick * bRow + lengthOfBrick,
                    bp);
        }
    }
}
