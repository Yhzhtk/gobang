package info.yhzhtk.gobang;

public class GobangModel {

    private final int BOARD_SIZE = 15;

    private final PIECE_TYPE[][] BOARD = new PIECE_TYPE[BOARD_SIZE][BOARD_SIZE];

    public void reset() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                BOARD[i][j] = PIECE_TYPE.EMPTY;
            }
        }
    }

    public void play(PIECE_TYPE type, Location loc) {
        if (type == PIECE_TYPE.EMPTY) {
            throw new RuntimeException(loc.toString() + " can't play empty");
        }
        if (BOARD[loc.row][loc.col] != PIECE_TYPE.EMPTY) {
            throw new RuntimeException(loc.toString() + " not empty");
        }
        BOARD[loc.row][loc.col] = type;
    }

    public boolean calculate(PIECE_TYPE type, Location loc, PIECE_STATE state) {
        switch (state) {
        case COME5:
            return toCOME5(type, loc);
        case LIVE4:
            return toLIVE4(type, loc);
        case DOUBLE_DIE4:
            return toDOUBLE_DIE4(type, loc);
        case DIE4_LIVE3:
            return toDIE4_LIVE3(type, loc);
        case DOUBLE_LIVE3:
            return toDOUBLE_LIVE3(type, loc);
        case DIE3_LIVE3:
            return toDIE3_LIVE3(type, loc);
        case DIE4:
            return toDIE4(type, loc);
        case LIVE3:
            return toLIVE3(type, loc);
        case DOUBLE_LIVE2:
            return toDOUBLE_LIVE2(type, loc);
        case LIVE2:
            return toLIVE2(type, loc);
        case DIE2:
            return toDIE2(type, loc);
        }
        throw new RuntimeException(state.name() + "not complete");
    }

    public static enum PIECE_TYPE {
        EMPTY, MACHINE, PEOPLE
    }

    public static enum PIECE_STATE {
        COME5, LIVE4, DOUBLE_DIE4, DIE4_LIVE3, DOUBLE_LIVE3, // 会赢
        DIE3_LIVE3, DIE4, LIVE3, DOUBLE_LIVE2, LIVE2, DIE2 // 待定
    }

    public static class Location {
        private int row;
        private int col;

        public Location(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }

        public String toString() {
            return row + ":" + col;
        }
    }

    private boolean toCOME5(PIECE_TYPE type, Location loc) {
        return true;
    }

    private boolean toLIVE4(PIECE_TYPE type, Location loc) {
        return true;
    }

    private boolean toDOUBLE_DIE4(PIECE_TYPE type, Location loc) {
        return true;
    }

    private boolean toDIE4_LIVE3(PIECE_TYPE type, Location loc) {
        return true;
    }

    private boolean toDOUBLE_LIVE3(PIECE_TYPE type, Location loc) {
        return true;
    }

    private boolean toDIE3_LIVE3(PIECE_TYPE type, Location loc) {
        return true;
    }

    private boolean toDIE4(PIECE_TYPE type, Location loc) {
        return true;
    }

    private boolean toLIVE3(PIECE_TYPE type, Location loc) {
        return true;
    }

    private boolean toDOUBLE_LIVE2(PIECE_TYPE type, Location loc) {
        return true;
    }

    private boolean toLIVE2(PIECE_TYPE type, Location loc) {
        return true;
    }

    private boolean toDIE2(PIECE_TYPE type, Location loc) {
        return true;
    }

}
