package com.example.gamestate;

import java.util.ArrayList;

/**
 * @author Griselda
 * @author Katherine
 * @author Ruth
 * @author Nick
 * @author Ethan
 * @version 3.21.2023
 */

public class GameState {

    private Pieces[][] pieces; //2D array to represent board
    private ArrayList<Pieces> capturedBlack; //arrayList for captured Black pieces
    private ArrayList<Pieces> capturedRed; //arrayList for captured Red pieces
    private int turn; //indicate who's turn it is

    public Pieces emptyPiece;

    public GameState() {
        //make 8x8 array of pieces
        pieces = new Pieces[8][8];

        //make arrayLists for captured pieces
        capturedBlack = new ArrayList<Pieces>();
        capturedRed = new ArrayList<Pieces>();

        //for loop to iterate through 2D Pieces array
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
                    if (row % 2 == 0) {
                        pieces[row][col] = new Pieces(0, Pieces.Colors.RED, row, col);
                    }
                    //fill rest of sixth and eighth row with empty pieces
                    else {
                        pieces[row][col] = new Pieces(0, Pieces.Colors.EMPTY, row, col);
                    }
                }

                //fill seventh row with red pieces
                else if (col == 6) {
                    if (row % 2 != 0) {
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

        // set the turn
        turn = 0;
    }

    //deep copy constructor
    public GameState(GameState GS) {
        //transfer board to new gameState
        pieces = new Pieces[8][8];
        for (int row = 0; row < pieces.length; row++) {
            for (int col = 0; col < pieces[row].length; col++) {
                pieces[row][col] = GS.pieces[row][col];
            }
        }

        turn = GS.turn;
        emptyPiece = GS.emptyPiece;
    }

    //return piece at certain index of board
    public Pieces getPieces(int row, int col) {
        return pieces[row][col];
    }

    //set piece at a certain point in board
    public void setPieces(int row, int col, Pieces piece) {
        piece.setX(row);
        piece.setY(col);
        pieces[row][col] = piece;
    }

    //get turn of player
    public int getTurn() {
        return turn;
    }

    //set turn of player
    public void setTurn(int num) {
        if (num == 0) {
            turn = 1;
        } else {
            turn = 0;
        }
    }

    public ArrayList<Pieces> capturedBlack() {
        return capturedBlack;
    }

    public ArrayList<Pieces> capturedRed() {
        return capturedRed;
    }

    //check if selected piece belongs to player
    public boolean checkPiece(int num, Pieces piece) {
        return true;
    }

    //checks if the selected piece is able to go the new position
    public boolean checkMove(int num, Pieces currPos, Pieces newPos) {
        // checks if black piece is able to go to the new position they want to move to
        if (num == 0 && currPos.getColors() == Pieces.Colors.BLACK && newPos.getColors() != Pieces.Colors.BLACK) {
            Pieces.Colors color = Pieces.Colors.BLACK;
            return movePiece(currPos, newPos, color);

            // checks if red piece is able to go to the new position they want to move to
        } else if (num == 1 && currPos.getColors() == Pieces.Colors.RED && newPos.getColors() != Pieces.Colors.RED) {
            Pieces.Colors color = Pieces.Colors.RED;
            return movePiece(currPos, newPos, color);
        }

        return false;
    }

    //checks if the piece can move to the designated position
    public boolean movePiece(Pieces currPos, Pieces newPos, Pieces.Colors colors) {

        //Move piece for BLACK
        if (colors == Pieces.Colors.BLACK) {

            //If a black piece is on left edge of board, it can only move diagonal up right
            if (currPos.getY() == 0) {
                if (newPos.getY() == currPos.getY() + 1 && newPos.getX() == currPos.getX() - 1) {
                    return true;

                    //If a black piece is on right edge of board, it can only move diagonal up left
                } else if (currPos.getY() == 7) {
                    if (newPos.getY() == currPos.getY() - 1 && newPos.getX() == currPos.getX() - 1) {
                        return true;
                    }

                    //Otherwise, the black piece can move diagonal up left or right
                } else {
                    if ((newPos.getY() == currPos.getY() + 1 && newPos.getX() == currPos.getX() - 1) || (newPos.getY() == currPos.getY() - 1 && newPos.getX() == currPos.getX() - 1)) {
                        return true;
                    }
                }
            }

            //Move piece for RED
        } else if (colors == Pieces.Colors.RED) {

                //If a red piece is on left edge of board, it can only move diagonal down right
            if (currPos.getY() == 0) {
                if (newPos.getY() == currPos.getY() + 1 && newPos.getX() == currPos.getX() + 1) {
                    return true;

                    //If a red piece is on right edge of board, it can only move diagonal down left
                } else if (currPos.getY() == 7) {
                    if (newPos.getY() == currPos.getY() - 1 && newPos.getX() == currPos.getX() + 1) {
                        return true;
                    }
                    //If a red piece is on left edge of board, it can only move diagonal down right and left
                } else {
                    if ((newPos.getY() == currPos.getY() + 1 && newPos.getX() == currPos.getX() + 1) || (newPos.getY() == currPos.getY() - 1 && newPos.getX() == currPos.getX() + 1)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    //check if piece can be promoted at its current position
    public boolean checkPromotion(Pieces pieces, Pieces.Colors colors) {
        //determines current position of piece
        int x_coord = pieces.getX();

        if (colors == Pieces.Colors.BLACK) {
            //if x coordinate of piece is at end of board return true
            if (x_coord == 0) {
                return true;
            }

        } else if (colors == Pieces.Colors.RED) {
            //if x coordinate of red piece is at end of board return true;
            if (x_coord == 7) {
                return true;
            }
        }

        return false;
    }

    //check if a piece can be captured
    //num indicates player
    public boolean checkCapture(int num, Pieces selectedPiece, Pieces capturePiece) {
        // make sure the pieces are different in color, or else they can't capture
        // because they belong to the same player
        if (selectedPiece.getColors() == capturePiece.getColors()) {
            return false;
        }

        // now we know the pieces are different players
        int x_coord_selected = selectedPiece.getX();
        int y_coord_selected = selectedPiece.getY();
        int x_coord_captured = capturePiece.getX();
        int y_coord_captured = capturePiece.getY();

        // if the x coordinate of the captured piece is 0 or 7, the piece can't be captured
        if (x_coord_captured == 7 || x_coord_captured == 0) {
            return false;
        }

        // if the y coordinate of the captured piece is 0 or 7, the piece can't be captured
        if (y_coord_captured == 7 || y_coord_captured == 0) {
            return false;
        }

        // make sure the pieces are diagonal
        if (!checkDiagonal(x_coord_selected, y_coord_selected, x_coord_captured, y_coord_captured)) {
            return false;
        }

        // now just make sure the space to jump to isn't occupied
        // first check if the selected piece is just a regular piece vs a king
        if (selectedPiece.getType() == 0) {
            if (num == 0) {
                // the player is trying to capture
                if (y_coord_captured > y_coord_selected) {
                    // direction capture is up and to the right
                    if (this.pieces[x_coord_selected - 2][y_coord_selected + 2].getColors() != Pieces.Colors.EMPTY) {
                        // capture space is occupied
                        return false;
                    } else {
                        // capture space isn't occupied
                        return true;
                    }
                } else {
                    // direction capture is up and to the left
                    if (this.pieces[x_coord_selected - 2][y_coord_selected - 2].getColors() != Pieces.Colors.EMPTY) {
                        return false;
                    } else {
                        return true;
                    }
                }
            } else {
                // the AI is trying to capture
                if (y_coord_captured > y_coord_selected) {
                    // direction capture is down and to the right
                    if (this.pieces[x_coord_selected + 2][y_coord_selected + 2].getColors() != Pieces.Colors.EMPTY) {
                        // capture space is occupied
                        return false;
                    } else {
                        // capture space isn't occupied
                        return true;
                    }
                } else {
                    // direction capture is down and to the left
                    if (this.pieces[x_coord_selected + 2][y_coord_selected - 2].getColors() != Pieces.Colors.EMPTY) {
                        return false;
                    } else {
                        return true;
                    }
                }
            }

        } else {
            // this piece is a king
            return false;
        }
    }

    public boolean checkDiagonal(int selX, int selY, int capX, int capY) {
        if (selX + 1 == capX && selY + 1 == capY) {
            return true;
        } else if (selX + 1 == capX && selY - 1 == capY) {
            return true;
        } else if (selX - 1 == capX && selY - 1 == capY) {
            return true;
        } else if (selX - 1 == capX && selY + 1 == capY) {
            return true;
        }

        return false;
    }

    //toString method prints board
    @Override
    public String toString() {

        //String variable to append
        String toReturn = "";

        //for loop to iterate through entire board and print each piece
        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[i].length; j++) {
                toReturn += (pieces[j][i] + " ");
            }
            toReturn += "\n";
        }

        return toReturn;
    }
}
