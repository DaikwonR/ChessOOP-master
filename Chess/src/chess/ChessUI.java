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
        printHelp();
        while (true) {
            printBoard();
            System.out.println("Type a command (type 'help' for options).");
            System.out.print("> ");
            String cmd = scanner.nextLine().trim().toLowerCase();
            if (cmd.equals("quit")) {
                System.out.println("Exiting the game. Goodbye!");
                break;
            }
            if (cmd.equals("help")) {
                printHelp();
                System.out.println("Help: Lists all available commands.");
                continue;
            }
            if (cmd.equals("reset")) {
                game.setupBoard();
                System.out.println("Board reset. New setup generated.");
                continue;
            }
            if (cmd.equals("show setup")) {
                printSetup();
                System.out.println("Description: Shows the current starting position for both sides.");
                continue;
            }
            if (cmd.equals("show moves")) {
                System.out.println("Move format: e2 e4 (from-to in algebraic notation)");
                System.out.println("Description: Enter moves as 'from to', e.g., 'e2 e4'.");
                continue;
            }
            String[] parts = cmd.split(" ");
            if (parts.length == 2) {
                boolean moved = game.movePiece(parts[0], parts[1]);
                if (moved) {
                    System.out.println("Move executed: " + parts[0] + " to " + parts[1]);
                } else {
                    System.out.println("Invalid move. Try again. (Description: Move not allowed by chess rules or input error.)");
                }
            } else {
                System.out.println("Unknown command. Type 'help' for options. (Description: Command not recognized.)");
            }
        }
    }

    private void printHelp() {
        System.out.println("\nCommands:");
        System.out.println("  help         - Show this help message");
        System.out.println("  reset        - Reroll setup (Chess960 or classic)");
        System.out.println("  show setup   - Show the current starting position");
        System.out.println("  show moves   - Show move input format");
        System.out.println("  quit         - Exit the game");
    }

    private void printSetup() {
        String[][] board = game.getBoard();
        System.out.print("White back rank: ");
        for (int i = 0; i < 8; i++) {
            System.out.print((board[7][i] == null ? "." : board[7][i].substring(1)) + " ");
        }
        System.out.println();
        System.out.print("Black back rank: ");
        for (int i = 0; i < 8; i++) {
            System.out.print((board[0][i] == null ? "." : board[0][i].substring(1)) + " ");
        }
        System.out.println();
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