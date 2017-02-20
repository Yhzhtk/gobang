package info.yhzhtk.gobang;

import java.util.ArrayList;
import java.util.List;

/**
 * 五子棋领域
 * 
 * @author gudh
 * @create 2017年2月19日
 */
public class GobangModel {

    // 棋盘大小
    public final static int BOARD_SIZE = 15;
    // 棋盘情况
    private final PieceType[][] BOARD = new PieceType[BOARD_SIZE][BOARD_SIZE];

    public GobangModel() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                BOARD[i][j] = PieceType.EMPTY;
            }
        }
    }

    /**
     * 获取位置 loc 附近 distance 的所有可下节点
     * 
     * 
     * @author gudh
     * @param loc
     * @param distance
     * @return
     */
    public List<Location> getNearLocation(Location loc, int distance) {
        if (distance < 1 || distance > BOARD_SIZE) {
            throw new RuntimeException("distance " + distance + " beyond limit");
        }
        List<Location> list = new ArrayList<Location>();

        for (int i = 1; i <= distance; i++) {
            list.addAll(getSingleNearLocation(loc, distance));
        }

        return list;
    }

    /**
     * 是否可以下
     * 
     * @author gudh
     * @param type
     * @param loc
     * @return
     */
    public boolean canPlay(Location loc) {
        if (BOARD[loc.row][loc.col] != PieceType.EMPTY) {
            return false;
        }
        return true;
    }

    /**
     * 下棋
     * 
     * @author gudh
     * @param type
     * @param loc
     */
    public void play(PieceType type, Location loc) {
        if (type == PieceType.EMPTY) {
            throw new RuntimeException(loc.toString() + " can't play empty");
        }
        if (!canPlay(loc)) {
            throw new RuntimeException(loc.toString() + " not empty");
        }
        BOARD[loc.row][loc.col] = type;
    }

    /**
     * 收回下棋
     * 
     * 
     * @author gudh
     * @param loc
     */
    public void replay(Location loc) {
        if (BOARD[loc.row][loc.col] == PieceType.EMPTY) {
            throw new RuntimeException("replay " + loc.toString() + " has be empty");
        }
        BOARD[loc.row][loc.col] = PieceType.EMPTY;
    }

    /**
     * 打印棋盘
     * 
     * @author gudh
     */
    public void print() {
        for (PieceType[] cols : BOARD) {
            for (PieceType col : cols) {
                System.out.print(col.text() + " ");
            }
            System.out.println();
        }
    }

    /**
     * 判断棋谱是否满足某个条件
     * 
     * @author gudh
     * @param type
     * @param loc
     * @param state
     * @return
     */
    public boolean calculate(PieceType type, Location loc, PieceState state) {
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
        case DIE3:
            return toDIE3(type, loc);
        case DOUBLE_LIVE2:
            return toDOUBLE_LIVE2(type, loc);
        case LIVE2:
            return toLIVE2(type, loc);
        case DIE2:
            return toDIE2(type, loc);
        }
        throw new RuntimeException(state.name() + "not complete");
    }

    public static enum PieceType {
        EMPTY, MACHINE, PEOPLE;

        public PieceType next() {
            if (this == MACHINE) {
                return PEOPLE;
            }
            return MACHINE;
        }

        public String text() {
            if (this == EMPTY) {
                return "·";
            } else if (this == MACHINE) {
                return "●";
            } else if (this == PEOPLE) {
                return "○";
            }
            return "";
        }
    }

    public static enum PieceState {
        COME5, LIVE4, DOUBLE_DIE4, DIE4_LIVE3, DOUBLE_LIVE3, // 会赢
        DIE3_LIVE3, DIE4, LIVE3, DIE3, DOUBLE_LIVE2, LIVE2, DIE2; // 待定
    }

    public static class Location {
        private int row;
        private int col;

        public final static Location CENTER = new Location(BOARD_SIZE / 2, BOARD_SIZE / 2);

        public Location(int row, int col) {
            if (row < 0 || row >= BOARD_SIZE || col < 0 || col >= BOARD_SIZE) {
                throw new RuntimeException(row + ":" + col + " beyond limit " + BOARD_SIZE);
            }
            this.row = row;
            this.col = col;
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + col;
            result = prime * result + row;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Location other = (Location) obj;
            if (col != other.col)
                return false;
            if (row != other.row)
                return false;
            return true;
        }

        public String toString() {
            return row + ":" + col;
        }
    }

    private List<Location> getSingleNearLocation(Location loc, int distance) {
        List<Location> list = new ArrayList<Location>();
        int start_row = loc.row - distance;
        int end_row = loc.row + distance;
        int start_col = loc.col - distance;
        int end_col = loc.col + distance;

        start_row = start_row < 0 ? 0 : start_row;
        end_row = end_row >= BOARD_SIZE ? BOARD_SIZE - 1 : end_row;
        start_col = start_col < 0 ? 0 : start_col;
        end_col = end_col >= BOARD_SIZE ? BOARD_SIZE - 1 : end_col;

        // 左右两列
        for (int i = start_row; i <= end_row; i++) {
            if (BOARD[i][start_col] == PieceType.EMPTY) {
                list.add(new Location(i, start_col));
            }
            if (BOARD[i][end_col] == PieceType.EMPTY) {
                list.add(new Location(i, end_col));
            }
        }

        // 上下两行
        for (int i = start_col; i <= end_col; i++) {
            if (BOARD[start_row][i] == PieceType.EMPTY) {
                list.add(new Location(start_row, i));
            }
            if (BOARD[end_row][i] == PieceType.EMPTY) {
                list.add(new Location(end_row, i));
            }
        }

        return list;
    }

    private boolean toCOME5(PieceType type, Location loc) {
        return true;
    }

    private boolean toLIVE4(PieceType type, Location loc) {
        return true;
    }

    private boolean toDOUBLE_DIE4(PieceType type, Location loc) {
        return true;
    }

    private boolean toDIE4_LIVE3(PieceType type, Location loc) {
        return true;
    }

    private boolean toDOUBLE_LIVE3(PieceType type, Location loc) {
        return true;
    }

    private boolean toDIE3_LIVE3(PieceType type, Location loc) {
        return true;
    }

    private boolean toDIE4(PieceType type, Location loc) {
        return true;
    }

    private boolean toLIVE3(PieceType type, Location loc) {
        return true;
    }

    private boolean toDIE3(PieceType type, Location loc) {
        return true;
    }

    private boolean toDOUBLE_LIVE2(PieceType type, Location loc) {
        return true;
    }

    private boolean toLIVE2(PieceType type, Location loc) {
        return true;
    }

    private boolean toDIE2(PieceType type, Location loc) {
        return true;
    }

}
