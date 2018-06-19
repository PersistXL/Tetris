package com.persistXL.lbrick.Model;

import com.persistXL.lbrick.Custom.LColor;
import com.persistXL.lbrick.Custom.LConfig;

/**
 * Created by persistXL on 18/6/18.
 */
public class Brick_T extends Brick {

    public Brick_T() {
        this.color = LColor.yellow();

        int centerCol = (LConfig.colsOfBricks - 1) / 2;

        this.leftBorder = centerCol;
        this.rightBorder = centerCol + 2;

        this.brickPieces[0] = new BrickPiece(-2, centerCol + 1);
        this.brickPieces[1] = new BrickPiece(-1, centerCol);
        this.brickPieces[2] = new BrickPiece(-1, centerCol + 1);
        this.brickPieces[3] = new BrickPiece(-1, centerCol + 2);

        this.currentOrientation = orientation.original;
    }

    @Override
    public boolean rotateBrickWithBricks(int[][] bricks) {
        switch (this.currentOrientation) {
            case original: {
                Brick testBrick = new Brick_T();
                testBrick.leftBorder = this.leftBorder + 1;
                testBrick.rightBorder = this.rightBorder;
                testBrick.brickPieces[0] = new BrickPiece(this.brickPieces[0].getRow() + 1, this.brickPieces[0].getCol() + 1);
                testBrick.brickPieces[1] = new BrickPiece(this.brickPieces[1].getRow() - 1, this.brickPieces[1].getCol() + 1);
                testBrick.brickPieces[2] = new BrickPiece(this.brickPieces[2].getRow(), this.brickPieces[2].getCol());
                testBrick.brickPieces[3] = new BrickPiece(this.brickPieces[3].getRow() + 1, this.brickPieces[3].getCol() - 1);

                if (brickCanRotate(testBrick, bricks)) {
                    ++this.leftBorder;
                    this.brickPieces[0].setRow(this.brickPieces[0].getRow() + 1);
                    this.brickPieces[0].setCol(this.brickPieces[0].getCol() + 1);
                    this.brickPieces[1].setRow(this.brickPieces[1].getRow() - 1);
                    this.brickPieces[1].setCol(this.brickPieces[1].getCol() + 1);
                    this.brickPieces[3].setRow(this.brickPieces[3].getRow() + 1);
                    this.brickPieces[3].setCol(this.brickPieces[3].getCol() - 1);

                    this.currentOrientation = orientation.rotate90Degree;

                    return true;
                }
            } break;

            case rotate90Degree: {
                Brick testBrick = new Brick_T();
                testBrick.leftBorder = this.leftBorder - 1;
                testBrick.rightBorder = this.rightBorder;
                testBrick.brickPieces[0] = new BrickPiece(this.brickPieces[0].getRow() + 1, this.brickPieces[0].getCol() - 1);
                testBrick.brickPieces[1] = new BrickPiece(this.brickPieces[1].getRow() + 1, this.brickPieces[1].getCol() - 1);
                testBrick.brickPieces[2] = new BrickPiece(this.brickPieces[2].getRow(), this.brickPieces[2].getCol());
                testBrick.brickPieces[3] = new BrickPiece(this.brickPieces[3].getRow() - 1, this.brickPieces[3].getCol() + 1);

                if (brickCanRotate(testBrick, bricks)) {
                    --this.leftBorder;
                    this.brickPieces[0].setRow(this.brickPieces[0].getRow() + 1);
                    this.brickPieces[0].setCol(this.brickPieces[0].getCol() - 1);
                    this.brickPieces[1].setRow(this.brickPieces[1].getRow() + 1);
                    this.brickPieces[1].setCol(this.brickPieces[1].getCol() - 1);
                    this.brickPieces[3].setRow(this.brickPieces[3].getRow() - 1);
                    this.brickPieces[3].setCol(this.brickPieces[3].getCol() + 1);

                    this.currentOrientation = orientation.rotate180Degree;

                    return true;
                }
            } break;

            case rotate180Degree: {
                Brick testBrick = new Brick_T();
                testBrick.leftBorder = this.leftBorder;
                testBrick.rightBorder = this.rightBorder - 1;
                testBrick.brickPieces[0] = new BrickPiece(this.brickPieces[0].getRow() - 1, this.brickPieces[0].getCol() - 1);
                testBrick.brickPieces[1] = new BrickPiece(this.brickPieces[1].getRow() - 1, this.brickPieces[1].getCol() + 1);
                testBrick.brickPieces[2] = new BrickPiece(this.brickPieces[2].getRow(), this.brickPieces[2].getCol());
                testBrick.brickPieces[3] = new BrickPiece(this.brickPieces[3].getRow() + 1, this.brickPieces[3].getCol() - 1);

                if (brickCanRotate(testBrick, bricks)) {
                    --this.rightBorder;
                    this.brickPieces[0].setRow(this.brickPieces[0].getRow() - 1);
                    this.brickPieces[0].setCol(this.brickPieces[0].getCol() - 1);
                    this.brickPieces[1].setRow(this.brickPieces[1].getRow() - 1);
                    this.brickPieces[1].setCol(this.brickPieces[1].getCol() + 1);
                    this.brickPieces[3].setRow(this.brickPieces[3].getRow() + 1);
                    this.brickPieces[3].setCol(this.brickPieces[3].getCol() - 1);

                    this.currentOrientation = orientation.rotate270Degree;

                    return true;
                }
            } break;

            default: {
                Brick testBrick = new Brick_T();
                testBrick.leftBorder = this.leftBorder;
                testBrick.rightBorder = this.rightBorder + 1;
                testBrick.brickPieces[0] = new BrickPiece(this.brickPieces[0].getRow() - 1, this.brickPieces[0].getCol() + 1);
                testBrick.brickPieces[1] = new BrickPiece(this.brickPieces[1].getRow() + 1, this.brickPieces[1].getCol() - 1);
                testBrick.brickPieces[2] = new BrickPiece(this.brickPieces[2].getRow(), this.brickPieces[2].getCol());
                testBrick.brickPieces[3] = new BrickPiece(this.brickPieces[3].getRow() - 1, this.brickPieces[3].getCol() + 1);

                if (brickCanRotate(testBrick, bricks)) {
                    ++this.rightBorder;
                    this.brickPieces[0].setRow(this.brickPieces[0].getRow() - 1);
                    this.brickPieces[0].setCol(this.brickPieces[0].getCol() + 1);
                    this.brickPieces[1].setRow(this.brickPieces[1].getRow() + 1);
                    this.brickPieces[1].setCol(this.brickPieces[1].getCol() - 1);
                    this.brickPieces[3].setRow(this.brickPieces[3].getRow() - 1);
                    this.brickPieces[3].setCol(this.brickPieces[3].getCol() + 1);

                    this.currentOrientation = orientation.original;

                    return true;
                }
            } break;
        }

        return false;
    }
    
}
