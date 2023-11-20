import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static final int ROW = 3;
    public static final int COLUMN = 3;
    public static String[][] field = new String[ROW][COLUMN];
    public static boolean playerOnTurn = false;

    public static void main(String[] args) {
        /**
         * Start new Game
         */
        newGameStarted();
    }

    /**
     * Starts new game
     */
    public static void newGameStarted() {
        generate();
        do {
            update();
            input();
            if (isSolved()) {
                if (playerOnTurn) {
                    System.out.println("Congratulation, Player X WIN!");
                } else {
                    System.out.println("Congratulation, Player Y WIN!");
                }
                update();
                System.exit(0);
            } else if (checkDraw()) {
                System.out.println("The game ended in a draw!");
                update();
                System.exit(0);
            }
        } while(true);
    }

    /**
     * Generates playing field
     */
    public static void generate() {
        for (int rows = 0; rows < ROW; rows++) {
            for (int cols = 0; cols < COLUMN; cols++) {
                field[rows][cols] = "-";
            }
        }
    }

    /**
     * Prints playing field with specific size
     */
    public static void update() {
        System.out.print("\n");
        System.out.print("   ");
        for(int cols = 0; cols < COLUMN; cols++) {
            System.out.print(cols + "  ");
        }
        System.out.println("");
        for(int rows = 0; rows < ROW; rows++) {
            System.out.print(rows + " ");
            for(int fieldRow = 0; fieldRow < field.length; fieldRow++) {
                System.out.print(" " + field[rows][fieldRow] + " ");
            }
            System.out.println("");
        }
    }

    /**
     * Processing input from the Player
     * @param input from player
     */
    public static void handleInput(String input) {
        switch (input) {
            case "1" :
                fillField(0,0);
                break;
            case "2" :
                fillField(0,1);
                break;
            case "3" :
                fillField(0,2);
                break;
            case "4" :
                fillField(1,0);
                break;
            case "5" :
                fillField(1,1);
                break;
            case "6" :
                fillField(1,2);
                break;
            case "7" :
                fillField(2,0);
                break;
            case "8" :
                fillField(2,1);
                break;
            case "9" :
                fillField(2,2);
                break;
            default :
                System.out.println("Your choice out of range, please select again!");
                if (playerOnTurn) {
                    playerOnTurn = false;
                } else {
                    playerOnTurn = true;
                }
                update();
                input();
        }
    }

    /**
     * Player's input
     */
    public static void input() {
        System.out.println("");
        playerOnTurn = !playerOnTurn;
        if (playerOnTurn) {
            System.out.print("Player X, enter Your choice (1-" + ROW * COLUMN + "): ");
        } else {
            System.out.print("Player Y, enter Your choice (1-" + ROW * COLUMN + "): ");
        }
        Scanner in = new Scanner(System.in);
        try {
            handleInput(in.nextLine());
        } catch (InputMismatchException e) {
            e.getMessage();
        }
    }

    /**
     * Fills the field with player's choices
     * @param row count
     * @param column count
     * @return two-dimensional array
     */
    public static String[][] fillField(int row, int column) {
        if(field[row][column] == "-") {
            if(playerOnTurn) {
                field[row][column] = "X";
            } else {
                field[row][column] = "O";
            }
        } else {
            System.out.println("Position taken!");
            if (playerOnTurn) {
                playerOnTurn = false;
            } else {
                playerOnTurn = true;
            }
            update();
            input();
        }
        return field;
    }

    /**
     * Checks ending of the game
     * @return successful end of the game
     */
    public static boolean isSolved() {
      return checkRows() || checkColumns() || checkLeftToRightDiagonal() || checkRightToLeftDiagonal();
    }

    /**
     * Check if row is solved
     * @return solved state
     */
    public static boolean checkRows() {
        int countX = 0;
        int countY = 0;
        for (int rows = 0; rows < field.length; rows++) {
            for (int cols = 0; cols < field.length; cols++) {
                if (field[rows][cols].equals("X")) {
                    countX++;
                }
                if (field[rows][cols].equals("O")) {
                    countY++;
                }
            }
            if (countX == 3 || countY == 3) {
                return true;
            } else {
                countX = 0;
                countY = 0;
            }
        }
        return false;
    }

    /**
     * Check if column is solved
     * @return solved state
     */
    public static boolean checkColumns() {
        int countX = 0;
        int countY = 0;
        for (int cols = 0; cols < field.length; cols++) {
            for (int rows = 0; rows < field.length; rows++) {
                if (field[rows][cols].equals("X")) {
                    countX++;
                }
                if (field[rows][cols].equals("O")) {
                    countY++;
                }
            }
            if (countX == 3 || countY == 3) {
                return true;
            } else {
                countX = 0;
                countY = 0;
            }
        }
        return false;
    }

    /**
     * Check if diagonal line from left to right way is solved
     * @return solved state
     */
    public static boolean checkLeftToRightDiagonal() {
        int rows = 0;
        int cols = 0;
        int countX = 0;
        int countY = 0;
        do {
            if (field[rows][cols].equals("X") ) {
                countX++;
            }
            if (field[rows][cols].equals("O") ) {
                countY++;
            }
            rows++;
            cols++;
        }
        while(rows < field.length && cols < field.length);

        if (countX == 3 || countY == 3) {
            return true;
        }
        return false;
    }

    /**
     * Check if diagonal line from right to left way is solved
     * @return solved state
     */
    public static boolean checkRightToLeftDiagonal() {
        int rows = 0;
        int cols = field.length - 1;
        int countX = 0;
        int countY = 0;
        do {
            if (field[rows][cols].equals("X") ) {
                countX++;
            }
            if (field[rows][cols].equals("O") ) {
                countY++;
            }
            rows++;
            cols--;
        }
        while(rows < field.length);

        if (countX == 3 || countY == 3) {
            return true;
        }
        return false;
    }

    /**
     * Check if the game ended in a draw
     * @return draw status of a game
     */
    public static boolean checkDraw() {
        for (int rows = 0; rows < field.length; rows++) {
            for (int cols = 0; cols < field.length; cols++) {
                if (field[rows][cols].equals("-")) {
                    return false;
                }
            }
        }
        return true;
    }
}