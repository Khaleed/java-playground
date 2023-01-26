import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;

enum User {
    PLAYER,
    COMPUTER
}

/** Class that implements TicTacToe game without UI interface. */
public class TicTacToe {
    private static final List<Integer> playerPositions = new ArrayList<>();
    private static final List<Integer> computerPositions = new ArrayList<>();
    public static void main(String[] args) {
        // Model.
        char[][] gameBoard = {{' ', '|', ' ', '|', ' '},
                {'-', '+', '-', '+', '-'},
                {' ', '|', ' ', '|', ' '},
                {'-', '+', '-', '+', '-'},
                {' ', '|', ' ', '|', ' '}};
        // Game loop.
        while(true) {
            List<List<Integer>> winningCombos = getWinningCombos();
            System.out.println("Enter your position 0 - 8");
            Scanner scanner = new Scanner(System.in);
            int playerPos = getPlayerPos(scanner);
            placeOnBoard(gameBoard, playerPos, User.PLAYER);
            String result = checkWinner(winningCombos);
            if (isGameOver(result)) {
                showResult(result);
                break;
            }
            Random rand = new Random();
            int computerPos = getComputerPos(rand);
            placeOnBoard(gameBoard, computerPos, User.COMPUTER);
            showGameBoard(gameBoard);
            result = checkWinner(winningCombos);
            if (isGameOver(result)) {
                showResult(result);
                break;
            }
        }
    }

    public static String checkWinner(List<List<Integer>> combos) {
        for (List<Integer> combo : combos) {
            if (playerPositions.containsAll(combo)) {
                return User.PLAYER.toString() + " won!";
            } else if (computerPositions.containsAll(combo)) {
                return User.COMPUTER.toString() + " won!";
            } else if (playerPositions.size() + computerPositions.size() == 9) {
                return "Tie game!";
            }
        }
        return " ";
    }

    public static void placeOnBoard(char[][] board, int pos, User user) {
        System.out.println("Pos" + pos);
        char move = ' ';
        if (user == User.PLAYER) {
            playerPositions.add(pos);
            move = 'X';
        } else if (user == User.COMPUTER) {
            computerPositions.add(pos);
            move = 'O';
        }

        switch (pos) {
            case 0 -> board[0][0] = move;
            case 1 -> board[0][2] = move;
            case 2 -> board[0][4] = move;
            case 3 -> board[2][0] = move;
            case 4 -> board[2][2] = move;
            case 5 -> board[2][4] = move;
            case 6 -> board[4][0] = move;
            case 7 -> board[4][2] = move;
            case 8 -> board[4][4] = move;
            default -> {
            }
        }
    }

    public static void showGameBoard(char[][] board) {
        for(char[] row : board) {
            for(char c : row) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    /* TODO:(Khalid) - Refactor using Streams. */
    private static List<List<Integer>> getWinningCombos() {
        List<List<Integer>> winningCombos = new ArrayList<>();
        List<Integer> topRow = Arrays.asList(0, 1, 2);
        List<Integer> midRow = Arrays.asList(3, 4, 5);
        List<Integer> bottomRow = Arrays.asList(6, 7, 8);
        List<Integer> leftCol = Arrays.asList(0, 3, 6);
        List<Integer> midCol = Arrays.asList(1, 4, 7);
        List<Integer> rightCol = Arrays.asList(2, 5, 8);
        List<Integer> leftDiagonal = Arrays.asList(0, 4, 8);
        List<Integer> rightDiagonal = Arrays.asList(2, 4, 6);
        winningCombos.add(topRow);
        winningCombos.add(midRow);
        winningCombos.add(bottomRow);
        winningCombos.add(leftCol);
        winningCombos.add(midCol);
        winningCombos.add(rightCol);
        winningCombos.add(leftDiagonal);
        winningCombos.add(rightDiagonal);
        return winningCombos;
    }

    private static int getPlayerPos(Scanner scanner) {
        int pos = scanner.nextInt();
        while (playerPositions.contains(pos) || computerPositions.contains(pos)) {
            System.out.println("Position already taken!");
            pos = scanner.nextInt();
        }
        return pos;
    }

    private static int getComputerPos(Random rand) {
        int pos = rand.nextInt(8) + 1;
        while (playerPositions.contains(pos) || computerPositions.contains(pos)) {
            pos = rand.nextInt(8) + 1;
        }
        return pos;
    }

    private static void showResult(String result) {
        System.out.println(result);
    }

    private static boolean isGameOver(String result) {
        return result.length() > 1;
    }
}
