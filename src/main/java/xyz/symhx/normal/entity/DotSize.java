package xyz.symhx.normal.entity;

public class DotSize {
    public static final DotSize SIZE_1_1 = new DotSize(1, 1);
    public static final DotSize SIZE_2_1 = new DotSize(2, 1);
    public static final DotSize SIZE_1_2 = new DotSize(1, 2);
    public static final DotSize SIZE_2_2 = new DotSize(2, 2);

    private int row;
    private int col;

    public int size() {
        return row * col;
    }

    public static DotSize create(int row, int col) {
        if (row == 1 && col == 1) {
            return SIZE_1_1;
        } else if (row == 2 && col == 1) {
            return SIZE_2_1;
        } else if (row == 1 && col == 2) {
            return SIZE_1_2;
        } else if (row == 2 && col == 2) {
            return SIZE_2_2;
        } else {
            return new DotSize(row, col);
        }
    }

    public DotSize() {
    }

    public DotSize(int row, int col) {
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