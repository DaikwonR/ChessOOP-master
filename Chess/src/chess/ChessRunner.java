package chess;

import java.util.Scanner;

public class ChessRunner {
    public static void main(String[] args) {
        Scanner modeScanner = new Scanner(System.in);
        System.out.println("Choose game mode:");
        System.out.println("1. Traditional Chess");
        System.out.println("2. Chess960");
        System.out.print("Enter 1 or 2: ");
        String modeInput = modeScanner.nextLine().trim();
        boolean chess960 = modeInput.equals("2");
        ChessGame game = new ChessGame(chess960);
        ChessUI ui = new ChessUI(game);
        ui.run();
        modeScanner.close();
    }
}
