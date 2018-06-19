package com.persistXL.lbrick.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.persistXL.lbrick.Custom.LColor;
import com.persistXL.lbrick.Custom.LConfig;
import com.persistXL.lbrick.Model.Brick;

/**
 * Created by persistXL on 18/6/15.
 */
public class AllBricksView extends View {

    private static int row = LConfig.rowsOfBricks;
    private static int col = LConfig.colsOfBricks;

    private float marginTop = 0.0f;
    private float marginLeft = 0.0f;

    private float lengthOfBrick = 0.0f;

    public int[][] bricks = new int[LConfig.rowsOfBricks][LConfig.colsOfBricks];

    private static Paint p = new Paint();
    private static Paint bp = new Paint();
    private static Paint bap = new Paint();

    public AllBricksView (Context context) {
        super(context);
    }

    public AllBricksView (Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        for (int i = 0; i < LConfig.rowsOfBricks; ++i)
            for (int j = 0; j < LConfig.colsOfBricks; ++j)
                bricks[i][j] = LColor.black();
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

        p.setStyle(Paint.Style.FILL);

        bp.setColor(Color.WHITE);
        bp.setStyle(Paint.Style.STROKE);
        bp.setStrokeWidth(1.7f);

        bap.setStyle(Paint.Style.FILL_AND_STROKE);
        bap.setColor(Color.rgb(79, 83, 89));

        canvas.drawRect(marginLeft, marginTop, marginLeft + this.getWidth(), this.getHeight() - marginTop, bap);

        for (int i = 0; i < row; ++i)
            for (int j = 0; j < col; ++j) {
                if (bricks[i][j] != LColor.black()) {
                    p.setColor(bricks[i][j]);

                    canvas.drawRect(marginLeft + lengthOfBrick * j,
                            marginTop + lengthOfBrick * i,
                            marginLeft + lengthOfBrick * j + lengthOfBrick,
                            marginTop + lengthOfBrick * i + lengthOfBrick,
                            p);

                    canvas.drawRect(marginLeft + lengthOfBrick * j,
                            marginTop + lengthOfBrick * i,
                            marginLeft + lengthOfBrick * j + lengthOfBrick,
                            marginTop + lengthOfBrick * i + lengthOfBrick,
                            bp);
                }
            }
    }

    public void setupCurrentBrick(Brick brick) {
        for (int i = 0; i < 4; ++i) {
            bricks[brick.brickPieces[i].getRow()][brick.brickPieces[i].getCol()] = brick.color;
        }
    }
}
