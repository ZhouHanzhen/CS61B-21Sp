package game2048;

import java.lang.reflect.Member;
import java.util.Formatter;
import java.util.Observable;


/** The state of a game of 2048.
 *  @author TODO: YOUR NAME HERE
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private Board board;
    /** Current score. */
    private int score;
    /** Maximum score so far.  Updated when game ends. */
    private int maxScore;
    /** True iff game is ended. */
    private boolean gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = maxScore = 0;
        gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        int size = rawValues.length;
        board = new Board(rawValues, score);
        this.score = score;
        this.maxScore = maxScore;
        this.gameOver = gameOver;
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     *  */
    public Tile tile(int col, int row) {
        return board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (gameOver) {
            maxScore = Math.max(score, maxScore);
        }
        return gameOver;
    }

    /** Return the current score. */
    public int score() {
        return score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        gameOver = false;
        board.clear();
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /** Tilt the board toward SIDE. Return true iff this changes the board.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     * */
    public boolean tilt(Side side) {
        boolean changed, flag;
        changed = false;

        //设置side这个方向
        board.setViewingPerspective(side);

        // TODO: Modify this.board (and perhaps this.score) to account
        // for the tilt to the Side SIDE. If the board changed, set the
        // changed local variable to true.

        //下面所有的移动都是将side这个方向当作North往上移动
        for (int col = 0; col < board.size(); col += 1) {
            //每次移动一列
            flag = OneColumnTilesMoveUp(col);
            //只要有一次move,flag为一次true，changed就变为true;
            if (flag) {
                changed = true;
            }
        }

        //向某个方向移动后将方向设置回来原有的方向
        board.setViewingPerspective(Side.NORTH);

        checkGameOver();
        if (changed) {
            setChanged();
        }
        return changed;
    }

    /**
     * 假设方向为North,处理同一列中tiles都往上移动up的情况
     * @param col
     */
    public boolean OneColumnTilesMoveUp(int col) {
        int rMoveTo;
        boolean flagMerge;
        boolean fMove;
        boolean fChanged = false;
        boolean[] tileMergeFlag = {false, false, false, false};

        //遍历同一列不同行的tile；假设board的size其实一直是4？？
        //从row = 2开始；row = 3时tile不需要移动
        for (int row = 2; row >= 0; row -= 1) {
            Tile t = board.tile(col,row);

            //首先确定tile(col,row)是否为空;
            if (t != null) {
                //不为空--然后判断t是否需要移动；
                fMove = MoveFlag(col, row, t, tileMergeFlag);

                if (fMove) {
                    //如果移动，changed的值变为true;
                    fChanged = true;
                    //确定t最终会移动至哪一行
                    rMoveTo = rowMoveUpTo(col, row, t, tileMergeFlag);
                    //将t移动至这一行;
                    flagMerge = board.move(col,rMoveTo,t);
                    //如果有merge,则加上两倍t的值;并且merge后的tile需要做标志，在这一列的遍历里不能再merge
                    if (flagMerge) {
                        score += (t.value()) * 2;
                        tileMergeFlag[rMoveTo] = true;
                    }

                }// end if(fMove)
            }//end if (t != null)
        }//end for

        return fChanged;
    }

    /**
     * 判断一个tile是否可以移动；
     * 如果Tile t的上面一个tile不为空，且值不相等，则不能移动；否则就可以移动
     * @return true需要移动;false不需要移动
     */
    public boolean MoveFlag(int col, int row, Tile t, boolean[] MergeFlag) {
        Tile tA = board.tile(col,row + 1);
        if (tA != null) {
            if (t.value() != tA.value()) {
                return false;
            }
            else {//值相等，tA是merge后的Tile
                if (MergeFlag[row + 1]) {
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * 确定Tile t最终移动的行
     * @param col t所在的列
     * @param row t所在的行
     * @param t 需要移动的Tile
     * @return t最终移动去的行
     */
    public int rowMoveUpTo(int col, int row, Tile t, boolean[] MergeFlag) {
        int rowValue = -1;
        Tile tA = board.tile(col, row + 1);
        Tile t3 = board.tile(col, 3);
        Tile t2 = board.tile(col, 2);

        //t上面一个tA不为空，则值相等且tA不是merge后的tile
        //tA不空的情况下能够move说明t和tA值相等且tA不是merge后的tile
        if (tA != null) {
            //merge
            rowValue = row + 1;
        }
        else {
            //tA为null,往上移动
            //根据t在不同行有不同的移动可能性
            if (row == 2) {
                rowValue = 3;
            } //row == 2
            else if (row == 1) {
                //2为空，判断是否可以移动至3
                if (ToTilFlag(t3, t, 3, MergeFlag)) {//可以移动至3行
                    rowValue = 3;
                }
                else {
                    rowValue = 2;
                }
            }//row == 1
            else if (row == 0) {
                //1为空，判断2是否为空
                if (t2 == null) { //2为空
                    //判断是否可以移至3行
                    if(ToTilFlag(t3, t, 3, MergeFlag)) {
                        rowValue = 3;
                    }
                    else {
                        rowValue = 2;
                    }
                }
                else { //2不为空，判断是否可以移至2
                    if (ToTilFlag(t2, t, 2, MergeFlag)) {//可以移至2行
                        rowValue = 2;
                    }
                    else {
                        rowValue = 1;
                    }

                }
            }//row == 0

        }//end else(tA == null)

        return rowValue;
    }


    /**
     * 判断tSrc是否可以移至tDes
     * 如果tDes为空，则可以；tDes不为空，若值相等，且tDes不是merge后的结果，则可以；其他则不可以
     * @param tDes
     * @param tSrc
     * @param Des
     * @param MergeFlag
     * @return
     */
    public boolean ToTilFlag(Tile tDes, Tile tSrc, int Des, boolean[] MergeFlag) {
        if (tDes == null) {
            return true;
        }
        else {
            if (tDes.value() == tSrc.value()) {
                //没有merge
                return !MergeFlag[Des];
            }
        }
        return false;
    }




    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        gameOver = checkGameOver(board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     * */
    public static boolean emptySpaceExists(Board b) {
        // TODO: Fill in this function.
        //遍历Board b 中每一个tile,判断是否为empty,即tile的值为null;如果是return true,最终遍历完所有tile后没有，则return false;
        int size = b.size();
        for (int col = 0; col < size; col++) {
            for (int row = 0; row < size; row++) {
                Tile t = b.tile(col, row);
                if (t == null) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        // TODO: Fill in this function.
        int size = b.size();
        for (int col = 0; col < size; col++) {
            for (int row = 0; row < size; row++) {
                Tile t = b.tile(col, row);
                if (t != null) {
                    int value = t.value();
                    if (value == MAX_PIECE) {
                        return true;
                    }
                }//end t != null
            }//end row
        }//end col
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean atLeastOneMoveExists(Board b) {
        // TODO: Fill in this function.

        // 1.There is at least one empty space on the board
        if (emptySpaceExists(b)) {
            return true;
        }
        //2.There are two adjacent tiles with the same value.
        //相同列，相邻行是否有相同值的一对tile
        int size = b.size();
        for (int col = 0; col < size; col++) {
            for (int row = 0; row < size - 1; row++) {
                Tile tDown = b.tile(col, row);
                Tile tUp = b.tile(col, row + 1);
                int valueD = tDown.value();
                int valueU = tUp.value();
                if (valueD == valueU) {
                    return true;
                }
            }//end row
        }//end col

        //相同行，相邻列是否有相同值的一对tile
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size - 1; col++) {
                Tile tLeft = b.tile(col, row);
                Tile tRight = b.tile(col + 1, row);
                int valueL = tLeft.value();
                int valueR = tRight.value();
                if (valueL == valueR) {
                    return true;
                }
            }//end col
        }//end row


        return false;
    }


    @Override
     /** Returns the model as a string, used for debugging. */
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    @Override
    /** Returns whether two models are equal. */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    @Override
    /** Returns hash code of Model’s string. */
    public int hashCode() {
        return toString().hashCode();
    }
}
