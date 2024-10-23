package year2021.day4;

import java.util.List;

public class Board {
    private Integer[][] board = new Integer[5][5];
    private boolean isBingo = false;

    public Board(List<String> input) {
        for (int i = 0; i < 5; i++) {
            String[] temp = input.get(i).split(" +");
            for (int j = 0; j < 5; j++) {
                board[i][j] = Integer.parseInt(temp[j]);
            }
        }

    }


    public Integer[][] getBoard() {
        return board;
    }

    public boolean checkBoard() {
        if (isBingo) {
            return false;
        }

        isBingo = true;
        for (int i = 0; i < 5; i++) {
            if (board[i][0] == -1 && board[i][1] == -1 && board[i][2] == -1 && board[i][3] == -1 && board[i][4] == -1) {
                return true;
            }
        }

        for (int i = 0; i < 5; i++) {
            if (board[0][i] == -1 && board[1][i] == -1 && board[2][i] == -1 && board[3][i] == -1 && board[4][i] == -1) {
                return true;
            }
        }

        isBingo = false;

        return false;
    }

    public void setNumberMinus(int number) {

        if (isBingo) {
            return;
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (board[i][j] == number) {
                    board[i][j] = -1;
                }
            }
        }
    }

    public Integer sum() {
        int sum = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (board[i][j] != -1) {
                    sum += board[i][j];
                }
            }
        }

        return sum;
    }
}
