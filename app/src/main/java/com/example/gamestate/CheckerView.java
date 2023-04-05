package com.example.gamestate;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;

public class CheckerView extends SurfaceView implements View.OnTouchListener{


    //paint variables
    protected Paint imagePaint;
    private Paint squareColor;
    private Paint highLight;
    private Paint dotPaint;

    //variables for creating board with dimensions
    private float top;
    private float left;
    private float bottom;
    private float right;
    private float size;

    //variables for piece images
    protected Bitmap blackPawn;
    protected Bitmap redPawn;
    protected Bitmap blackKing;
    protected Bitmap redKing;

    //Variables to manipulate board
    private Pieces[][] pieces;
    private int[][] board;

    //Arraylists to store possible moves
    private ArrayList<Integer> xMoves = new ArrayList<>();
    private ArrayList<Integer> yMoves = new ArrayList<>();

    //dimensions of board
    private int row = 7;
    private int col = 7;

    public CheckerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);

        pieces = new Pieces[8][8];
        board = new int[8][8];

        size = 115;
        left = top = 40;
        right = left + size;
        bottom = top + size;

        squareColor = new Paint();
        squareColor.setColor(Color.BLACK);
        highLight = new Paint();
        highLight.setColor(Color.YELLOW);
        dotPaint = new Paint();
        dotPaint.setColor(Color.GREEN);

        //decode resources of piece images
        blackPawn = BitmapFactory.decodeResource(getResources(), R.drawable.bp);
        blackKing = BitmapFactory.decodeResource(getResources(), R.drawable.bk);
        redPawn = BitmapFactory.decodeResource(getResources(), R.drawable.rp);
        redKing = BitmapFactory.decodeResource(getResources(), R.drawable.rk);

        //scale the images of the pieces
        blackPawn = Bitmap.createScaledBitmap(blackPawn, 115, 115, false);
        blackKing = Bitmap.createScaledBitmap(blackKing, 115, 115, false);
        redPawn = Bitmap.createScaledBitmap(redPawn, 115, 115, false);
        redKing = Bitmap.createScaledBitmap(redKing, 115, 115, false);

        placePieces();
        setBackgroundColor(Color.LTGRAY);
    }


    public void placePieces() {
        for (int row = 0; row < pieces.length; row++) {
            for (int col = 0; col < pieces[row].length; col++) {

                //fill first and third row with black pieces
                if (row == 0 || row == 2) {
                    if (col % 2 == 0) {
                        pieces[row][col] = new Pieces(0, Pieces.Colors.BLACK, row, col);
                    }

                    //fill rest of first and third row with empty pieces
                    else {
                        pieces[row][col] = new Pieces(0, Pieces.Colors.EMPTY, row, col);
                    }
                }

                //fill second row with black pieces
                else if (row == 1) {
                    if (col % 2 != 0) {
                        pieces[row][col] = new Pieces(0, Pieces.Colors.BLACK, row, col);
                    }
                    //fill rest of second row with empty pieces
                    else {
                        pieces[row][col] = new Pieces(0, Pieces.Colors.EMPTY, row, col);
                    }
                }

                //fill sixth and eighth row with red pieces
                else if (row == 5 || row == 7) {
                    if (col % 2 != 0) {
                        pieces[row][col] = new Pieces(0, Pieces.Colors.RED, row, col);
                    }
                    //fill rest of sixth and eighth row with empty pieces
                    else {
                        pieces[row][col] = new Pieces(0, Pieces.Colors.EMPTY, row, col);
                    }
                }

                //fill seventh row with red pieces
                else if (row == 6) {
                    if (col % 2 == 0) {
                        pieces[row][col] = new Pieces(0, Pieces.Colors.RED, row, col);
                    }
                    //fill rest of seventh row with empty pieces
                    else {
                        pieces[row][col] = new Pieces(0, Pieces.Colors.EMPTY, row, col);
                    }
                }
                //fill rest of board with empty pieces
                else {
                    pieces[row][col] = new Pieces(0, Pieces.Colors.EMPTY, row, col);
                }
            }
        }
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //board initialization
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                //alternate colors of squares to create checkerboard pattern
                if ((i % 2 == 0 && j % 2 != 0) || (j % 2 == 0 && i % 2 != 0)) {
                    squareColor.setColor(Color.BLACK);
                } else {
                    squareColor.setColor(Color.WHITE);
                }

                //draw rectangle
                canvas.drawRect(left + (right - left) * i, top + (bottom - top) * j, right +
                        (right - left) * i, bottom + (bottom - top) * j, squareColor);
            }
        }

        //draw all the pieces
        for(int row = 0; row < pieces.length; row++) {
            for (int col = 0; col < pieces[row].length; col++) {

                //draw black pawns
                if(pieces[row][col].getType() == 0 && pieces[row][col].getColors() == Pieces.Colors.BLACK) {
                    canvas.drawBitmap(blackPawn, 40 + (col * 115), 40 + (row * 115), imagePaint);
                }

                //draw black kings
                if(pieces[row][col].getType() == 1 && pieces[row][col].getColors() == Pieces.Colors.BLACK) {
                    canvas.drawBitmap(blackKing, 40 + (col * 115), 40 + (row * 115), imagePaint);
                }

                //draw red pawns
                if(pieces[row][col].getType() == 0 && pieces[row][col].getColors() == Pieces.Colors.RED) {
                    canvas.drawBitmap(redPawn, 40 + (col * 115), 40 + (row * 115), imagePaint);
                }

                //draw red kings
                if(pieces[row][col].getType() == 1 && pieces[row][col].getColors() == Pieces.Colors.RED) {
                    canvas.drawBitmap(redKing, 40 + (col * 115), 40 + (row * 115), imagePaint);
                }
            }
        }
    }

    /**
     * Method to determine what kind of move a piece should make based on its status
     */
    public void movePiece(){
        if (pieces[row][col].getType() == 0) {
            movePawn();
        }
        else {
            moveKing();
        }
    }

    /**
     * Method to move a regular pawn
     */
    public void movePawn() {
        //check pawn on left most side of the board
        if (col == 0) {
            if(pieces[row - 1][col + 1].getColors() == Pieces.Colors.EMPTY){
                xMoves.add(row - 1);
                yMoves.add(col + 1);
            }

            //check if it can capture a piece
            if (pieces[row - 1][col + 1].getColors() == Pieces.Colors.BLACK && pieces[row - 2][col + 2].getColors() == Pieces.Colors.EMPTY) {
                xMoves.add(row - 2);
                yMoves.add(col + 2);
            }
        }

        //check pawn on right most side of the board
        else if (col == 7) {
            if(pieces[row - 1][col - 1].getColors() == Pieces.Colors.EMPTY) {
                xMoves.add(row - 1);
                yMoves.add(col - 1);
            }

            //check if it can capture a piece
            if(pieces[row - 1][col - 1].getColors() == Pieces.Colors.BLACK && pieces[row - 2][col - 2].getColors() == Pieces.Colors.EMPTY) {
                xMoves.add(row - 2);
                yMoves.add(col - 2);
            }
        }

        //pawn is not on the border of the board
        else {
            if (pieces[row - 1][col - 1].getColors() == Pieces.Colors.EMPTY) {
                xMoves.add(row - 1);
                yMoves.add(col - 1);
            }
            if(pieces[row - 1][col + 1].getColors() == Pieces.Colors.EMPTY){
                xMoves.add(row - 1);
                yMoves.add(col + 1);
            }
        }

        //check remaining captures
        if (col == 1) {
            if(pieces[row - 1][col + 1].getColors() == Pieces.Colors.BLACK && pieces[row - 2][col + 2].getColors() == Pieces.Colors.EMPTY) {
                xMoves.add(row - 2);
                yMoves.add(col + 2);
            }
        }

        else if (col == 6) {
            if(pieces[row - 1][col - 1].getColors() == Pieces.Colors.BLACK && pieces[row - 2][col - 2].getColors() == Pieces.Colors.EMPTY) {
                xMoves.add(row - 2);
                yMoves.add(col - 2);
            }
        }
/*
        else {
            if (pieces[row - 1][col - 1].getColors() == Pieces.Colors.BLACK && pieces[row - 2][col - 2].getColors() == Pieces.Colors.EMPTY) {
                xMoves.add(row - 2);
                yMoves.add(col - 2);
            }*/
            //if(pieces[row - 1][col + 1].getColors() == Pieces.Colors.BLACK && pieces[row - 2][col + 2].getColors() == Pieces.Colors.EMPTY){
              //  xMoves.add(row - 1);
                //yMoves.add(col + 2);
           // }
        }


    //movement for kings
    public void moveKing() {

    }

    //movement for AI
    public void opponentMove() {

    }


    @Override
    public boolean onTouch(View v, MotionEvent motionEvent) {
        /*
        int touchedX = (int)event.getX() - 50;
        int touchedY = (int)event.getY() - 50;

        int x_idx = touchedY / 115;
        int y_idx = touchedX / 115;

        Log.i("X INDEX", Integer.toString(x_idx));
        Log.i("Y INDEX", Integer.toString(y_idx));
        Log.i("MSG", pieces[x_idx][y_idx].toString());

*/

        if(motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN) {
            for(int i = 0; i < board.length; i++) {
                for(int j = 0; j < board[i].length; j++) {
                    if (motionEvent.getX() > 20 + (i * 115) && motionEvent.getX() < 175 + (i * 115)) {
                        if (motionEvent.getY() > 20 + (j * 115) && motionEvent.getY() < 175 + (j * 115)) {
                            for (int index = 0; index < xMoves.size(); index++) {
                                if(xMoves.get(index) == j && yMoves.get(index) == i) {
                                    pieces[j][i] = pieces [row][col];
                                    pieces[row][col] = new Pieces(0,Pieces.Colors.EMPTY, row, col);
                                    board[row][col] = 0;
                                    for (int k = 0; k < xMoves.size(); k++){
                                        board[yMoves.get(k)][xMoves.get(k)] = 0;
                                    }
                                    xMoves.clear();
                                    yMoves.clear();
                                    invalidate();
                                    return true;
                                }
                            }
                            if (row != 8 && col != 8) {
                                board[row][col] = 0;
                                for (int index = 0; index < xMoves.size(); index++) {
                                    board[xMoves.get(index)][yMoves.get(index)] = 0;
                                }
                                row = 7;
                                col = 7;
                                xMoves.clear();
                                yMoves.clear();
                                invalidate();
                                if (pieces[j][i].getColors() == Pieces.Colors.BLACK || pieces[i][j].getColors() == Pieces.Colors.EMPTY){
                                    return true;
                                }
                            }
                            if(pieces[j][i].getColors() == Pieces.Colors.BLACK || pieces[i][j].getColors() == Pieces.Colors.EMPTY) {
                                return true;
                            }
                            row = j;
                            col = i;
                            board[row][col] = 1;

                            movePiece();

                            for(int index = 0; index < xMoves.size(); index++) {
                                board[xMoves.get(index)][yMoves.get(index)] = 2;
                            }
                            invalidate();
                            return true;
                        }
                    }
                }
            }

        }

        return false;
    }
}
