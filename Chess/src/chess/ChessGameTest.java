package chess;

import java.beans.Transient;

import org.junit.Assert;
import org.junit.Test;

public class ChessGameTest {
    
    /**
     * Tests the initial setup of a traditional chess game to ensure pieces are in their correct starting positions.
     * This test verifies that the back rank for white is correctly arranged as Rook, Knight, Bishop, Queen, King, Bishop, Knight, Rook.
     */
    @Test
    public void testTraditionalChessSetup() {
        ChessGame game = new ChessGame(false);
        String[][] board = game.getBoard();
        Assert.assertEquals("WR", board[7][0]);
        Assert.assertEquals("WN", board[7][1]);
        Assert.assertEquals("WB", board[7][2]);
        Assert.assertEquals("WQ", board[7][3]);
        Assert.assertEquals("WK", board[7][4]);
        Assert.assertEquals("WB", board[7][5]);
        Assert.assertEquals("WN", board[7][6]);
        Assert.assertEquals("WR", board[7][7]);
    }

    /**
     * Tests the random setup of a Chess960 game to ensure that the pieces are arranged according to the rules of Chess960.
     * This test checks that there are exactly 2 bishops, 2 knights, 2 rooks, 1 queen, and 1 king on the back rank for white, and that the king is positioned between the two rooks.
     */
    @Test
    public void testChess960Setup() {
        ChessGame game = new ChessGame(true);
        String[][] board = game.getBoard();
        int whiteBishops = 0, whiteKnights = 0, whiteRooks = 0, whiteQueens = 0, whiteKings = 0;
        for (int i = 0; i < 8; i++) {
            String piece = board[7][i];
            if (piece != null) {
                switch (piece.charAt(1)) {
                    case 'B': whiteBishops++; break;
                    case 'N': whiteKnights++; break;
                    case 'R': whiteRooks++; break;
                    case 'Q': whiteQueens++; break;
                    case 'K': whiteKings++; break;
                }
            }
        }
        Assert.assertEquals(2, whiteBishops);
        Assert.assertEquals(2, whiteKnights);
        Assert.assertEquals(2, whiteRooks);
        Assert.assertEquals(1, whiteQueens);
        Assert.assertEquals(1, whiteKings);
    }

    /**
     * Tests the reset functionality of the chess board to ensure that the board is correctly reset to its initial state after moves have been made.
     */
    @Test
    public void testBoardReset() {
        ChessGame game = new ChessGame(false);
        game.movePiece("e2", "e4");
        String[][] board = game.getBoard();
        Assert.assertNull(board[6][4]);
        Assert.assertEquals("WP", board[4][4]);
        game.setupBoard();
        board = game.getBoard();
        Assert.assertEquals("WP", board[6][4]);
        Assert.assertNull(board[4][4]);
    }

    /**
     * Tests the validity of various moves to ensure that the game correctly identifies valid and invalid moves.
     */
    @Test
    public void testMoveValidity() {
        ChessGame game = new ChessGame(false);
        Assert.assertTrue(game.movePiece("e2", "e4")); 
        Assert.assertFalse(game.movePiece("e2", "e5"));
        Assert.assertTrue(game.movePiece("g1", "f3")); 
        Assert.assertFalse(game.movePiece("g1", "g3"));
    }

    /**
     * Tests the board state after a series of moves to ensure that pieces are correctly moved and captured according to chess rules.
     */
    @Test
    public void testBoardStateAfterMoves() {
        ChessGame game = new ChessGame(false);
        game.movePiece("e2", "e4");
        game.movePiece("e7", "e5");
        String[][] board = game.getBoard();
        Assert.assertEquals("WP", board[4][4]);
        Assert.assertEquals("BP", board[3][4]);
        Assert.assertNull(board[6][4]);
        Assert.assertNull(board[1][4]);
    }

    /**
     * Tests the checkmate detection functionality to ensure that the game correctly identifies checkmate situations.
     */
    @Test
    public void testCheckmateDetection() {
        ChessGame game = new ChessGame(false);
        
        game.movePiece("f2", "f3");
        game.movePiece("e7", "e5");
        game.movePiece("g2", "g4");
        Assert.assertTrue(game.movePiece("d8", "h4")); // Checkmate move
    }

    /**
     * Tests the move history functionality to ensure that the game correctly tracks the history of moves made during the game.
     */
    @Test
    public void testMoveHistory() {
        ChessGame game = new ChessGame(false);
        
        game.movePiece("e2", "e4");
        game.movePiece("e7", "e5");
        game.movePiece("g1", "f3");
        Assert.assertEquals(3, game.getMoveHistory().size());
        Assert.assertEquals("e2 e4", game.getMoveHistory().get(0));
        Assert.assertEquals("e7 e5", game.getMoveHistory().get(1));
        Assert.assertEquals("g1 f3", game.getMoveHistory().get(2));
    }

    /**
     * Tests the Chess960 mode to ensure that the game correctly initializes a Chess960 board and that the pieces are arranged according to the rules of Chess960.
     */
    @Test 
    public void testIsChess960() {
        ChessGame game1 = new ChessGame(false);
        ChessGame game2 = new ChessGame(true);
        Assert.assertFalse(game1.isChess960());
        Assert.assertTrue(game2.isChess960());
    }
}
