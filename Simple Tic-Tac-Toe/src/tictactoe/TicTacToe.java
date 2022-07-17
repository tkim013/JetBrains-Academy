package tictactoe;

import java.util.Scanner;

public class TicTacToe {
    public static void main(String[] args) {

        int a = 0;
        int b = 0;

        StringBuilder gameState = new StringBuilder();
        Scanner scanner = new Scanner(System.in);

//        try {
//            System.out.print("Enter cells: ");
//            gameState.append(scanner.nextLine());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        //set empty field
        gameState.append("         ");

        showBoard(gameState.toString());

        try {
            int index;
            boolean turn = true;

            String coords;

            while(true) {
                do {

                    index = -1;
                    coords = "";

                    System.out.print("Enter the coordinates: ");
                    coords = scanner.nextLine();

                    try {
                        a = Integer.parseInt(String.valueOf(coords.charAt(0)));
                        b = Integer.parseInt(String.valueOf(coords.charAt(2)));
                    } catch (NumberFormatException e) {
                        System.out.println("You should enter numbers!");
                    } catch (StringIndexOutOfBoundsException e) {
                        System.out.println("Enter coordinates in \"x x\" format!");
                    }

                    if (a < 1 || a > 3 || b < 1 || b > 3) {
                        System.out.println("Coordinates should be from 1 to 3!");
                        continue;
                    }

                    index = findIndex(a, b, gameState);

                    if (gameState.charAt(index) == 'X' || gameState.charAt(index) == 'O') {
                        System.out.println("This cell is occupied! Choose another one!");
                        continue;
                    }

                    break;
                } while (true);

                gameState.setCharAt(index, turn ? 'X' : 'O');
                turn = !turn;
                showBoard(gameState.toString());
                if (checkState(gameState.toString()) == 1) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static int checkState(String s) {

        boolean xState = false;
        boolean oState = false;
        boolean empty = false;
        int xCount = 0;
        int oCount = 0;
        int xoDiff = 0;

//        showBoard(s);

        //check 3 X
        if ((s.charAt(0) == 'X' && s.charAt(1) == 'X' && s.charAt(2) == 'X') ||
                (s.charAt(3) == 'X' && s.charAt(4) == 'X' && s.charAt(5) == 'X') ||
                (s.charAt(6) == 'X' && s.charAt(7) == 'X' && s.charAt(8) == 'X') ||
                (s.charAt(0) == 'X' && s.charAt(4) == 'X' && s.charAt(8) == 'X') ||
                (s.charAt(6) == 'X' && s.charAt(4) == 'X' && s.charAt(2) == 'X') ||
                (s.charAt(0) == 'X' && s.charAt(3) == 'X' && s.charAt(6) == 'X') ||
                (s.charAt(1) == 'X' && s.charAt(4) == 'X' && s.charAt(7) == 'X') ||
                (s.charAt(2) == 'X' && s.charAt(5) == 'X' && s.charAt(8) == 'X')
        )  {
            xState = true;
        }

        //check 3 O
        if ((s.charAt(0) == 'O' && s.charAt(1) == 'O' && s.charAt(2) == 'O') ||
                (s.charAt(3) == 'O' && s.charAt(4) == 'O' && s.charAt(5) == 'O') ||
                (s.charAt(6) == 'O' && s.charAt(7) == 'O' && s.charAt(8) == 'O') ||
                (s.charAt(0) == 'O' && s.charAt(4) == 'O' && s.charAt(8) == 'O') ||
                (s.charAt(6) == 'O' && s.charAt(4) == 'O' && s.charAt(2) == 'O') ||
                (s.charAt(0) == 'O' && s.charAt(3) == 'O' && s.charAt(6) == 'O') ||
                (s.charAt(1) == 'O' && s.charAt(4) == 'O' && s.charAt(7) == 'O') ||
                (s.charAt(2) == 'O' && s.charAt(5) == 'O' && s.charAt(8) == 'O')
        )  {
            oState = true;
        }

        //check empty spaces
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ' || s.charAt(i) == '_') {
                empty = true;
                break;
            }
        }

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'X') {
                xCount++;
            }
        }

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'O') {
                oCount++;
            }
        }

        xoDiff = xCount - oCount;

        if ((xState == true && oState == true) || Math.abs(xoDiff) > 1) {
            System.out.println("Impossible");
        } else if (xState == false && oState == false && empty == false) {
            System.out.println("Draw");
            return 1;
        } else if (xState == false && oState == false && empty == true) {
            System.out.println("Game not finished");
        } else if (xState == true) {
            System.out.println("X wins");
            return 1;
        } else if (oState == true) {
            System.out.println("O wins");
            return 1;
        }

        return -1;
    }

    public static void showBoard(String s) {
        try {
            System.out.println("---------");
            System.out.printf("| %c %c %c |%n", s.charAt(0), s.charAt(1), s.charAt(2));
            System.out.printf("| %c %c %c |%n", s.charAt(3), s.charAt(4), s.charAt(5));
            System.out.printf("| %c %c %c |%n", s.charAt(6), s.charAt(7), s.charAt(8));
            System.out.println("---------");
        } catch (StringIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    public static int findIndex(int a, int b, StringBuilder sb) {

        int index = -1;

        try {
            if (a == 1) {
                switch (b) {
                    case 1:
                        index = 0;
                        break;
                    case 2:
                        index = 1;
                        break;
                    case 3:
                        index = 2;
                        break;
                }
            }

            if (a == 2) {
                switch (b) {
                    case 1:
                        index = 3;
                        break;
                    case 2:
                        index = 4;
                        break;
                    case 3:
                        index = 5;
                        break;
                }
            }

            if (a == 3) {
                switch (b) {
                    case 1:
                        index = 6;
                        break;
                    case 2:
                        index = 7;
                        break;
                    case 3:
                        index = 8;
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return index;
    }
}
