package com.persistXL.lbrick.Model;

/**
 * Created by persistXL on 18/6/17.
 */
/*
定义方块的颜色即位置的私有队形，即实体类
*/
public class BrickPiece {

    private int row;
    private int col;

    public BrickPiece(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
