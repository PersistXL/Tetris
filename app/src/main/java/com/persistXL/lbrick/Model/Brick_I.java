package com.persistXL.lbrick.Model;

import com.persistXL.lbrick.Custom.LColor;
import com.persistXL.lbrick.Custom.LConfig;

/**
 * Created by persistXL on 18/6/18.
 */
public class Brick_I extends Brick {

    public Brick_I() {
        this.color = LColor.red();

        int centerCol = (LConfig.colsOfBricks - 1) / 2;

        this.leftBorder = centerCol;
        this.rightBorder = centerCol;

        this.brickPieces[0] = new BrickPiece(-4, centerCol);
        this.brickPieces[1] = new BrickPiece(-3, centerCol);
        this.brickPieces[2] = new BrickPiece(-2, centerCol);
        this.brickPieces[3] = new BrickPiece(-1, centerCol);

        this.currentOrientation = orientation.original;
    }

    @Override
    public boolean rotateBrickWithBricks(int[][] bricks) {
        switch (this.currentOrientation) {
            case original: {
                Brick testBrick = new Brick_I();
                testBrick.leftBorder = this.leftBorder - 1;
                testBrick.rightBorder = this.rightBorder + 2;
                testBrick.brickPieces[0] = new BrickPiece(this.brickPieces[0].getRow(), this.brickPieces[0].getCol() - 1);
                testBrick.brickPieces[1] = new BrickPiece(this.brickPieces[1].getRow() - 1, this.brickPieces[1].getCol());
                testBrick.brickPieces[2] = new BrickPiece(this.brickPieces[2].getRow() - 2, this.brickPieces[2].getCol() + 1);
                testBrick.brickPieces[3] = new BrickPiece(this.brickPieces[3].getRow() - 3, this.brickPieces[3].getCol() + 2);

                if (brickCanRotate(testBrick, bricks)) {
                    --this.leftBorder;
                    this.rightBorder += 2;
                    this.brickPieces[0].setCol(this.brickPieces[0].getCol() - 1);
                    this.brickPieces[1].setRow(this.brickPieces[1].getRow() - 1);
                    this.brickPieces[2].setRow(this.brickPieces[2].getRow() - 2);
                    this.brickPieces[2].setCol(this.brickPieces[2].getCol() + 1);
                    this.brickPieces[3].setRow(this.brickPieces[3].getRow() - 3);
                    this.brickPieces[3].setCol(this.brickPieces[3].getCol() + 2);

                    this.currentOrientation = orientation.rotate90Degree;

                    return true;
                }
            } break;

            default: {
                Brick testBrick = new Brick_I();
                testBrick.leftBorder = this.leftBorder + 1;
                testBrick.rightBorder = this.rightBorder - 2;
                testBrick.brickPieces[0] = new BrickPiece(this.brickPieces[0].getRow(), this.brickPieces[0].getCol() + 1);
                testBrick.brickPieces[1] = new BrickPiece(this.brickPieces[1].getRow() + 1, this.brickPieces[1].getCol());
                testBrick.brickPieces[2] = new BrickPiece(this.brickPieces[2].getRow() + 2, this.brickPieces[2].getCol() - 1);
                testBrick.brickPieces[3] = new BrickPiece(this.brickPieces[3].getRow() + 3, this.brickPieces[3].getCol() - 2);

                if (brickCanRotate(testBrick, bricks)) {
                    ++this.leftBorder;
                    this.rightBorder -= 2;
                    this.brickPieces[0].setCol(this.brickPieces[0].getCol() + 1);
                    this.brickPieces[1].setRow(this.brickPieces[1].getRow() + 1);
                    this.brickPieces[2].setRow(this.brickPieces[2].getRow() + 2);
                    this.brickPieces[2].setCol(this.brickPieces[2].getCol() - 1);
                    this.brickPieces[3].setRow(this.brickPieces[3].getRow() + 3);
                    this.brickPieces[3].setCol(this.brickPieces[3].getCol() - 2);

                    this.currentOrientation = orientation.original;

                    return true;
                }
            } break;
        }

        return false;
    }
}
