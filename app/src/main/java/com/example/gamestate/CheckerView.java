package com.example.gamestate;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

public class CheckerView extends SurfaceView implements View.OnTouchListener{

    protected Paint image;
    private Paint squareColor;
    private Paint highLight;

    private float top;
    private float left;
    private float bottom;
    private float right;
    private float size;

    private Pieces[][] pieces;
    private int[][] board;

    public CheckerView(Context context) {
        super(context);
        pieces = new Pieces[8][8];
        board = new int[8][8];
        size = 100;
        left = top = 50;
        right = left + size;
        bottom = top + size;

        squareColor = new Paint();
        squareColor.setColor(Color.BLACK);
        highLight = new Paint();
        highLight.setColor(Color.GREEN);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i % 2 == 0 && j % 2 != 0) || (j % 2 == 0 && i % 2 != 0)) {
                    squareColor.setColor(Color.WHITE);
                } else {
                    squareColor.setColor(Color.BLACK);
                }
                canvas.drawRect(left+(right-left) * i, top+(bottom-top)*j,right+
                        (right-left)*i, bottom+(bottom-top)*j,squareColor);
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
