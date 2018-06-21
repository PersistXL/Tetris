package com.persistXL.lbrick.Model;

import com.persistXL.lbrick.Custom.LColor;
import com.persistXL.lbrick.Custom.LConfig;

/**
 * Created by persistXL on 18/6/18.
 * This is the abstract Brick class. All seven bricks must extend this class.
 */
/*
定义砖块旋转及是否已降落
 */
public abstract class Brick {

    public int color = LColor.black();

    public int leftBorder;
    public int rightBorder;

    public BrickPiece[] brickPieces = new BrickPiece[4];

    /*
    旋转度数
     */
    public enum orientation {
        original, rotate90Degree, rotate180Degree, rotate270Degree
    }
    public orientation currentOrientation;

    public abstract boolean rotateBrickWithBricks(int[][] bricks);

    /*
    方块尚未完全堕落
     */
    private boolean brickHasntTotalyFallen(Brick brick) {
        for (int i = 0; i < 4; ++i) {
            if (brick.brickPieces[i].getRow() < 0)
                return true;
        }

        return false;
    }

    /*
    砖可以旋转
     */
    public boolean brickCanRotate(Brick brick, int[][] bricks) {
        if (brick.leftBorder < 0 || brick.rightBorder >= LConfig.colsOfBricks)
            return false;

        if (brickHasntTotalyFallen(brick)) return true;

        for (int i = 0; i < 4; ++i) {
            if (brick.brickPieces[i].getRow() >= LConfig.rowsOfBricks)
                return false;

            if (bricks[brick.brickPieces[i].getRow()][brick.brickPieces[i].getCol()] != LColor.black())
                return false;
        }

        return true;
    }

    /*
    使得方块迅速下降
     */
    public boolean brickCanFallDownWithBricks(int[][] bricks) {
        for (int i = 0; i < 4; ++i) {
            if (brickPieces[i].getRow() + 1 == LConfig.rowsOfBricks) {
                return false;
            }
        }

        for (int i = 0; i < 4; ++i) {
            if (brickPieces[i].getRow() + 1 >= 0 &&
                    bricks[brickPieces[i].getRow() + 1][brickPieces[i].getCol()] != LColor.black())
                return false;
        }

        return true;
    }

    public void fallOneRow() {
        for (int i = 0; i < 4; ++i) {
            brickPieces[i].setRow(brickPieces[i].getRow() + 1);
        }
    }

    private boolean testBlockWithDelta(int delta, int[][] bricks) {
        for (int i = 0; i < 4; ++i) {
            if (bricks[brickPieces[i].getRow()][brickPieces[i].getCol() + delta] != LColor.black())
                return false;
        }

        return true;
    }

    private void shiftBrickWithDelta(int delta) {
        leftBorder += delta;
        rightBorder += delta;

        for (int i = 0; i < 4; ++i) {
            brickPieces[i].setCol(brickPieces[i].getCol() + delta);
        }
    }

    public void shiftLeftWithBricks(int[][] bricks) {
        if (leftBorder >= 1) {
            if (brickHasntTotalyFallen(this)) {
                shiftBrickWithDelta(-1);

                return;
            }

            if (!testBlockWithDelta(-1, bricks)) return;

            shiftBrickWithDelta(-1);
        }
    }

    public void shiftRightWithBricks(int[][] bricks) {
        if (rightBorder <= LConfig.colsOfBricks - 2) {
            if (brickHasntTotalyFallen(this)) {
                shiftBrickWithDelta(1);

                return;
            }

            if (!testBlockWithDelta(1, bricks)) return;

            shiftBrickWithDelta(1);
        }
    }
}
