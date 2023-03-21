package com.example.gamestate;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author Griselda
 * @author Katherine
 * @author Ruth
 * @author Nick
 * @author Ethan
 * @version 3.21.2023
 **/

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView textView; // text displayed in xml
    private Button runTest; // button runs test
    private int clicks; // clicks made on button
    private GameState firstInstance;
    private GameState secondInstance;
    private GameState thirdInstance;
    private GameState fourthInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.editText);
        runTest = findViewById(R.id.runTest);
        runTest.setOnClickListener(this::onClick);
        firstInstance = new GameState();
        secondInstance = new GameState(firstInstance);
        thirdInstance = new GameState();
        fourthInstance = new GameState(thirdInstance);
    }

    public void onClick(View view) {
        // Empties text
        if (view.getId() == runTest.getId()) {
            textView.setText("");
        }

        // Initial board
        if (clicks == 0) {
            textView.append("Initial Board:\n" + firstInstance);
            textView.append("Board is made.\n");
        }

        // First player will move piece in first instance
        int who = firstInstance.getTurn(); // 'who' is whoever's turn it is
        String player = "";
        if (who == 1) {
            player = "AI";
        } else {
            player = "Player";
        }

        // Change who's turn it is
        textView.append(player + "'s Turn\n");
        textView.append(player + " will move checker piece.\n");

        // Capture a piece if it is valid
        if(canMove()) {
            if(canCapture()) {
                textView.append(player + " has captured a piece!\n");
                makeCapture();
            }

            movePiece();
        }

        //check if piece can promote
        boolean promote = firstInstance.checkPromotion(firstInstance.getPieces(6,6),firstInstance.getPieces(6,6).getColors());
        if (promote == false) {
            textView.append(player + "'s piece cannot advance.\n");
        }

        clicks++; // keeps track of turn
        firstInstance.setTurn(who); // change turn

        // Displays updated board
        textView.append(firstInstance + "\n\n");
        if (clicks == 3) {
            textView.append("Click again.\n");
        }

        if (clicks > 3) {
            textView.append("" + secondInstance);
            textView.append("\n");
            textView.append("" + fourthInstance);
            textView.append("\n");
        }
    }

    /**
     * Checks if move is valid
     * @return true if the piece can move to desired spot
     **/
    public boolean canMove() {
        boolean move = false;
        if (clicks == 0) {
            // Red Moves
            move = firstInstance.checkPiece(firstInstance.getTurn(),firstInstance.getPieces(4,6))
            && firstInstance.checkMove(firstInstance.getTurn(),firstInstance.getPieces(4,6), firstInstance.getPieces(4,4));
        } else if (clicks == 1) {
            // Black Moves
            move = firstInstance.checkPiece(firstInstance.getTurn(),firstInstance.getPieces(3,1))
            && firstInstance.checkMove(firstInstance.getTurn(),firstInstance.getPieces(3,1), firstInstance.getPieces(3,3));
        } else if (clicks == 2) {
            // Red Captures Black
            move = firstInstance.checkPiece(firstInstance.getTurn(),firstInstance.getPieces(4,4))
            && firstInstance.checkMove(firstInstance.getTurn(),firstInstance.getPieces(4,4), firstInstance.getPieces(4,3));
        }

        return move;
    }

    /**
     * Moves actual piece
     **/
    public void movePiece() {
        if (clicks == 0) {
            firstInstance.setPieces(4, 4, firstInstance.getPieces(4, 6));
            firstInstance.setPieces(4, 6, firstInstance.emptyPiece);
        } else if (clicks == 1) {
            firstInstance.setPieces(3, 3, firstInstance.getPieces(3, 1));
            firstInstance.setPieces(3, 1, firstInstance.emptyPiece);
        } else if (clicks == 2) {
            firstInstance.setPieces(4, 4, firstInstance.getPieces(3, 3));
            firstInstance.setPieces(3, 3, firstInstance.emptyPiece);
        }
    }

    /**
     * Adds captured piece to captured arraylist
     **/
    public void makeCapture() {
        int currPlayer = firstInstance.getTurn();
        if (currPlayer == 0) {
            firstInstance.capturedRed().add(firstInstance.getPieces(3,3));
        }
    }

    /**
     * Checks if piece can capture at desired location
     * @return True if they can capture and move to location
     **/
    public boolean canCapture() {
        boolean capture = false;
        if (clicks == 0) {
            capture = firstInstance.checkCapture(firstInstance.getTurn(),firstInstance.getPieces(4,6),firstInstance.getPieces(4,4));
        } else if (clicks == 1) {
            capture = firstInstance.checkCapture(firstInstance.getTurn(),firstInstance.getPieces(3,1),firstInstance.getPieces(3,3));
        } else if (clicks == 2) {
            capture = firstInstance.checkCapture(firstInstance.getTurn(),firstInstance.getPieces(4,4),firstInstance.getPieces(3,3));
        }

        return capture;
    }
}
