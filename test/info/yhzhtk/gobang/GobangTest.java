package info.yhzhtk.gobang;

import info.yhzhtk.gobang.GobangModel.Location;
import info.yhzhtk.gobang.GobangModel.PieceType;

/**
 * 代码测试
 * 
 * @author gudh
 * @create 2017年2月22日
 */
public class GobangTest {

    public static void main(String[] args) {
        testToCOME5();
    }

    public static void testToCOME5() {
        String board = "" //
                + "  0 1 2 3 4 5 6 7 8 9 a b c d e \n" //
                + "0 · · · · · · · · · · · · · · · \n" //
                + "1 · · · · · · · · · · · · · · · \n" //
                + "2 · · · · · · · · · · · · · · · \n" //
                + "3 · · ○ ○ ○ ○ · · · · · · · · · \n" //
                + "4 · · ● ● · ● · · · · · · · · · \n" //
                + "5 · · · · ● · · · · · · · · · · \n" //
                + "6 · · · · · · · · · · · · · · · \n" //
                + "7 · · · · · · · · · · · · · · · \n" //
                + "8 · · · · · · · · · · · · · · · \n" //
                + "9 · · · · · · · · · · · · · · · \n" //
                + "a · · · · · · · · · · · · · · · \n" //
                + "b · · · · · · · · · · · · · · · \n" //
                + "c · · · · · · · · · · · · · · · \n" //
                + "d · · · · · · · · · · · · · · · \n" //
                + "e · · · · · · · · · · · · · · · \n" //

        ;

        boolean res = initGobang(board).toCOME5(PieceType.MACHINE, new Location(4, 6));
        System.out.println(res);

    }

    public static GobangModel initGobang(String boardStr) {
        GobangModel model = new GobangModel();
        String[] lines = boardStr.split("\n");
        for (int i = 0; i < GobangModel.BOARD_SIZE; i++) {
            String[] vals = lines[i + 1].split(" ");
            for (int j = 0; j < GobangModel.BOARD_SIZE; j++) {
                model.play(i, j, PieceType.of(vals[j + 1]));
            }
        }
        return model;
    }
}
