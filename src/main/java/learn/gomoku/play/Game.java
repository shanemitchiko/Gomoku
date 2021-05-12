package learn.gomoku.play;

import learn.gomoku.game.Gomoku;
import learn.gomoku.game.Result;
import learn.gomoku.game.Stone;
import learn.gomoku.players.HumanPlayer;
import learn.gomoku.players.Player;
import learn.gomoku.players.RandomPlayer;
import java.util.ArrayList;
import java.util.Scanner;
import static learn.gomoku.game.Gomoku.WIDTH;

public class Game {
    private static Gomoku gomoku;
    public static String EMPTY_SLOT = "_";
    public static String BLACK_STONE = "X";
    public static String WHITE_STONE = "O";

    public static void run() {
        System.out.println("Welcome to Gomoku");
        System.out.println("=================");

        setUp();
    }

    private static void setUp() {
        Scanner console = new Scanner(System.in);
        Player currentPlayer;
        Boolean playAgain;

            Player playerOne = receivePlayerSelection(1);
            Player playerTwo = receivePlayerSelection(2);

            System.out.println("(Randomizing)");

            gomoku = new Gomoku(playerOne, playerTwo);
            currentPlayer = gomoku.getCurrent();
            System.out.println(currentPlayer.getName() + " goes first");

            while (!gomoku.isOver()) {
                getPlayerMove(gomoku);
            }
            Player winner = gomoku.getWinner();
            System.out.println(winner.getName() + " won!");
            System.out.println();
            System.out.println("Do you want to play again? [Y/N]");
            String confirm = console.nextLine().toUpperCase();
            if (confirm.equals("Y")) {
                run();
            } else {
                System.out.println("Thanks for playing!");
            }

        }

    public static Player receivePlayerSelection(int playerNumber) {
        Player player;
        Scanner console = new Scanner(System.in);

        System.out.println("Player " + playerNumber + " is:");
        System.out.println("1. Human");
        System.out.println("2. Random Player");
        System.out.println("Select [1 - 2]: ");

        String num = console.nextLine();

        if (num.equals("1")) {
            System.out.println("Player " + playerNumber + ", enter your name: ");
            String name = console.nextLine();
            player = new HumanPlayer(name);
        } else {
            player = new RandomPlayer();
        }
        return player;
    }

    public static Result getPlayerMove(Gomoku gomoku) {
        Result result;
        Stone stone;
        Player currentPlayer = gomoku.getCurrent();

         do {
            System.out.println(currentPlayer.getName() + "'s Turn");

            if (gomoku.getCurrent() instanceof HumanPlayer) {
                stone = new Stone(receiveRow(), receiveColumn(), gomoku.isBlacksTurn());
            } else {
                stone = gomoku.getCurrent().generateMove(gomoku.getStones());
            }
            result = gomoku.place(stone);
            if (!result.isSuccess()) {
                System.out.println(result.getMessage());
            }
            printBoard();
        } while (!result.isSuccess());
        return result;
    }

    public static int receiveRow() {
        Scanner console = new Scanner(System.in);

        System.out.println("Enter a row: ");
        int row = Integer.parseInt(console.nextLine());
        return row - 1;
    }

    public static int receiveColumn() {
        Scanner console = new Scanner(System.in);

        System.out.println("Enter a column: ");
        int col = Integer.parseInt(console.nextLine());
        return col - 1;
    }

    public static void printBoard() {

        ArrayList<Stone> stones = (ArrayList<Stone>) gomoku.getStones();

        System.out.print("   ");
        for(int col = 1 ; col <= WIDTH; col++) {
            System.out.printf("%02d ", col);
        }
        System.out.println();

        for(int row = 0; row < WIDTH; row++) {
            System.out.printf("%02d", row + 1);
            for(int col = 0; col < WIDTH; col++) {
                boolean foundStone = false;
                for (Stone s : stones) {
                    if (s.getRow() == row && s.getColumn() == col) {
                        foundStone = true;
                        if (s.isBlack()) {
                            System.out.print(" " + BLACK_STONE + " ");
                        } else {
                            System.out.print(" " + WHITE_STONE + " ");
                        }
                    }
                }
                if (!foundStone) {
                    System.out.print(" " + EMPTY_SLOT + " ");
                }
            }
            System.out.println();
        }
    }
}

