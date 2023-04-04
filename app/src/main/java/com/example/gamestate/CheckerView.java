package com.example.gamestate;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

public class CheckerView extends SurfaceView implements View.OnTouchListener{

    protected Paint imagePaint;
    private Paint squareColor;
    private Paint highLight;
    private Paint dotPaint;

    private float top;
    private float left;
    private float bottom;
    private float right;
    private float size;

    protected Bitmap blackPawn;
    protected Bitmap redPawn;
    protected Bitmap blackKing;
    protected Bitmap redKing;

    private Pieces[][] pieces;
    private int[][] board;

    public CheckerView(Context context) {
        super(context);
        setWillNotDraw(false);

        pieces = new Pieces[8][8];
        board = new int[8][8];

        size = 100;
        left = top = 50;
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
        blackPawn = Bitmap.createScaledBitmap(blackPawn, 100, 100, false);
        blackKing = Bitmap.createScaledBitmap(blackKing, 100, 100, false);
        redPawn = Bitmap.createScaledBitmap(redPawn, 100, 100, false);
        redKing = Bitmap.createScaledBitmap(redKing, 100, 100, false);

        placePieces();
    }


    public void placePieces() {
        for (int row = 0; row < pieces.length; row++) {
            for (int col = 0; col < pieces[row].length; col++) {

                //fill first and third row with black pieces
                if (col == 0 || col == 2) {
                    if (row % 2 == 0) {
                        pieces[row][col] = new Pieces(0, Pieces.Colors.BLACK, row, col);
                    }

                    //fill rest of first and third row with empty pieces
                    else {
                        pieces[row][col] = new Pieces(0, Pieces.Colors.EMPTY, row, col);
                    }
                }

                //fill second row with black pieces
                else if (col == 1) {
                    if (row % 2 != 0) {
                        pieces[row][col] = new Pieces(0, Pieces.Colors.BLACK, row, col);
                    }
                    //fill rest of second row with empty pieces
                    else {
                        pieces[row][col] = new Pieces(0, Pieces.Colors.EMPTY, row, col);
                    }
                }

                //fill sixth and eighth row with red pieces
                else if (col == 5 || col == 7) {
                    if (row % 2 != 0) {
                        pieces[row][col] = new Pieces(0, Pieces.Colors.RED, row, col);
                    }
                    //fill rest of sixth and eighth row with empty pieces
                    else {
                        pieces[row][col] = new Pieces(0, Pieces.Colors.EMPTY, row, col);
                    }
                }

                //fill seventh row with red pieces
                else if (col == 6) {
                    if (row % 2 == 0) {
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
                    canvas.drawBitmap(blackPawn, 50 + (row * 100), 50 + (col * 100), imagePaint);
                }

                //draw black kings
                if(pieces[row][col].getType() == 1 && pieces[row][col].getColors() == Pieces.Colors.BLACK) {
                    canvas.drawBitmap(blackKing, 50 + (row * 100), 50 + (col * 100), imagePaint);
                }

                //draw red pawns
                if(pieces[row][col].getType() == 0 && pieces[row][col].getColors() == Pieces.Colors.RED) {
                    canvas.drawBitmap(redPawn, 50 + (row * 100), 50 + (col * 100), imagePaint);
                }

                //draw red kings
                if(pieces[row][col].getType() == 1 && pieces[row][col].getColors() == Pieces.Colors.RED) {
                    canvas.drawBitmap(redKing, 50 + (row * 100), 50 + (col * 100), imagePaint);
                }
            }
        }
    }



    @Override
    public boolean onTouch(View v, MotionEvent event) {

        return false;
    }
}
