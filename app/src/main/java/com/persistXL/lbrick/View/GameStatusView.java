package com.persistXL.lbrick.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.persistXL.lbrick.Custom.LConfig;
import com.persistXL.lbrick.Model.Brick;

/**
 * Created by persistXL on 18/6/15.
 */
public class GameStatusView extends View {

    private Brick nextBrick;

    private float marginTop = 0.0f;
    private float marginLeft = 0.0f;
    private float lengthOfBrick = 0.0f;

    private static Paint p = new Paint();
    private static Paint bp = new Paint();

    public GameStatusView(Context context) {
        super(context);
    }

    public GameStatusView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        marginTop = dip2px(context, 100);
        marginLeft = dip2px(context, 10);
        lengthOfBrick = dip2px(context, 60) / 4;
    }

    public void setNextBrick(Brick nextBrick) {
        this.nextBrick = nextBrick;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        p.setColor(nextBrick.color);
        p.setStyle(Paint.Style.FILL);

        bp.setColor(Color.WHITE);
        bp.setStyle(Paint.Style.STROKE);
        bp.setStrokeWidth(1.7f);

        // Draw next brick.
        for (int i = 0; i < 4; ++i) {
            int bRow = nextBrick.brickPieces[i].getRow() + 4;
            int bCol = nextBrick.brickPieces[i].getCol() - (LConfig.colsOfBricks - 1) / 2 + 1;

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

    private float dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return dpValue * scale + 0.5f;
    }
}
