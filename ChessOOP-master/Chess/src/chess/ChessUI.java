package chess;

import java.util.Scanner;

public class ChessUI {
    private ChessGame game;
    private Scanner scanner;

    public ChessUI(ChessGame game) {
        this.game = game;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        System.out.println("Welcome to Chess!");
        while (true) {
            printBoard();
            System.out.println("Type 'reset' to reroll setup, or 'quit' to exit.");
            System.out.print("> ");
            String cmd = scanner.nextLine().trim().toLowerCase();
            if (cmd.equals("quit")) break;
            if (cmd.equals("reset")) {
                game.setupBoard();
                System.out.println("Board reset.");
            }
        }
    }

    private void printBoard() {
        String[][] board = game.getBoard();
        System.out.println();
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                String p = board[r][c];
                System.out.print((p == null ? " . " : p) + " ");
            }
            System.out.println();
        }
    }
}