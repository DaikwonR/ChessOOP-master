package chess;

import java.util.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ChessGame {	
    private String[][] board;
    // For move history
    private List<String> moveHistory = new ArrayList<>();
	private boolean isChess960;

	public ChessGame(boolean isChess960) {
		this.isChess960 = isChess960;
		this.board = new String[8][8];
		setupBoard();
	}

	public void setupBoard() {
		for (String[] row : board) Arrays.fill(row, null);
		initializeBoard();
	}

	private void initializeBoard() {
		String[] backRank = isChess960 ? getRandomChess960Rank() : new String[]{"R", "N", "B", "Q", "K", "B", "N", "R"};

        // Back Ranks
		for (int i = 0; i < 8; i++) {
			board[7][i] = "W" + backRank[i];
			board[0][i] = "B" + backRank[i];
		}
        // Pawns
		Arrays.fill(board[6], "WP");
		Arrays.fill(board[1], "BP");
	}

	private String[] getRandomChess960Rank() {
		Random rand = new Random();
		String[] rank = new String[8];
		List<Integer> freeSquares = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7));

        // Bishops

        // White Squares
		int b1 = rand.nextInt(4) * 2;
        //Black Squares
		int b2 = rand.nextInt(4) * 2 + 1;
		rank[b1] = "B";
		rank[b2] = "B";
		freeSquares.remove(Integer.valueOf(b1));
		freeSquares.remove(Integer.valueOf(b2));

        // Space for Queen and Knights
		rank[freeSquares.remove(rand.nextInt(freeSquares.size()))] = "Q";

		rank[freeSquares.remove(rand.nextInt(freeSquares.size()))] = "N";
		rank[freeSquares.remove(rand.nextInt(freeSquares.size()))] = "N";

        // Space for Rooks and King
		Collections.sort(freeSquares);
		rank[freeSquares.get(0)] = "R";
		rank[freeSquares.get(1)] = "K";
		rank[freeSquares.get(2)] = "R";

		return rank;
	}

    // Move pieces

    public boolean movePiece(String from, String to)
    {
        int[] startPos = parseSquare(from);
        int[] endPos = parseSquare(to);

        if (startPos == null || endPos == null) return false;

        String piece = board[startPos[0]][startPos[1]];
        if (piece == null) return false;

        // Only allow moving to an empty square or capturing an opponent's piece
        String targetPos = board[endPos[0]][endPos[1]];
        if (targetPos != null && targetPos.charAt(0) == piece.charAt(0)) return false;

        // Validate moves
        String type = piece.substring(1);
        int rowDir = endPos[0] - startPos[0];
        int colDir = endPos[1] - startPos[1];
        boolean valid = false;
        
        switch (type)
        {
            case "P":
                valid = validatePawnMove(piece.charAt(0), rowDir, colDir, targetPos);
                break;
            case "N":
                valid = (Math.abs(rowDir) == 2 && Math.abs(colDir) == 1) || (Math.abs(rowDir) == 1 && Math.abs(colDir) == 2);
                break;
            case "B":
                valid = Math.abs(rowDir) == Math.abs(colDir) && isPathClear(startPos, endPos);
                break;
            case "R":
                valid = (rowDir == 0 || colDir == 0) && isPathClear(startPos, endPos);
                break;
            case "Q":
                valid = (Math.abs(rowDir) == Math.abs(colDir) || rowDir == 0 || colDir == 0) && isPathClear(startPos, endPos);
                break;
            case "K":
                valid = Math.abs(rowDir) <= 1 && Math.abs(colDir) <= 1;
                break;
        }
        
        if (!valid) return false;
        board[endPos[0]][endPos[1]] = piece;
        board[startPos[0]][startPos[1]] = null;
        // Record move in history
        moveHistory.add(from + " " + to);
        return true;
    }

    private int[] parseSquare(String sq)
    {
        if (sq.length() != 2) return null;

		char file = sq.charAt(0);
		char rank = sq.charAt(1);
		int col = file - 'a';
		int row = 8 - (rank - '0');

		if (col < 0 || col > 7 || row < 0 || row > 7) return null;

		return new int[]{row, col};
    }

    private boolean validatePawnMove(char color, int rowDir, int colDir, String targetPos)
    {
        int forward = (color == 'W') ? -1 : 1;
        if (colDir == 0) {
            if (rowDir == forward && targetPos == null) return true;
            if ((rowDir == 2 * forward) && ((color == 'W' && rowDir == -2 && targetPos == null) || (color == 'B' && rowDir == 2 && targetPos == null))) {
                return true;
            }
        } else if (Math.abs(colDir) == 1 && rowDir == forward && targetPos != null && targetPos.charAt(0) != color) {
            return true;
        }
        return false;
    }

    private boolean isPathClear(int[] start, int[] end)
    {
        int rowDir = Integer.signum(end[0] - start[0]);
        int colDir = Integer.signum(end[1] - start[1]);
        int row = start[0] + rowDir;
        int col = start[1] + colDir;

        while (row != end[0] || col != end[1]) {
            if (board[row][col] != null) return false;
            row += rowDir;
            col += colDir;
        }
        return true;
    }

	public String[][] getBoard() {
		return board;
	}    

	public boolean isChess960() {
		return isChess960;
	}
    
    public List<String> getMoveHistory() {
        return moveHistory;
    }
}