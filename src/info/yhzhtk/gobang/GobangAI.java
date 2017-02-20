package info.yhzhtk.gobang;

import info.yhzhtk.gobang.GobangModel.Location;
import info.yhzhtk.gobang.GobangModel.PieceState;
import info.yhzhtk.gobang.GobangModel.PieceType;

import java.util.List;

/**
 * 五指棋AI程序
 * 
 * @author gudh
 * @create 2017年2月20日
 */
public class GobangAI {

    public final static int SCAN_DISTANCE = 4;
    public final static int SCAN_DEPTH = 5;

    private GobangModel gobang = new GobangModel();

    /**
     * 计算最优的下一步
     * 
     * @author gudh
     * @param lastMathineLoc
     * @param lastPeopleLoc
     * @return
     */
    public Location calBestLocation(Location lastMathineLoc, Location lastPeopleLoc) {
        if (lastPeopleLoc == null) {
            return Location.CENTER;
        }

        // 可以下的范围
        List<Location> list = gobang.getNearLocation(lastPeopleLoc, SCAN_DISTANCE);
        list.addAll(gobang.getNearLocation(lastMathineLoc, SCAN_DISTANCE));

        CalResult result = calLocationScore(list, PieceType.MACHINE, 0);

        return result.loc;
    }

    /**
     * 计算最有解
     * 
     * @author gudh
     * @param list
     * @param type
     * @param level
     * @return
     */
    private CalResult calLocationScore(List<Location> list, PieceType type, int level) {
        CalResult maxResult = null;
        // 递归6层
        if (level > SCAN_DEPTH) {
            return maxResult;
        }

        // 对每一个位置递归，寻找最有解
        for (Location loc : list) {
            CalResult result = null;

            for(StateScore state : StateScore.values()) {
                if (!gobang.canPlay(loc)) {
                    continue;
                }
                if (gobang.calculate(type, loc, state.state)) {
                    result = new CalResult(type, loc, state.score);
                    break;
                }
            }
            if (result == null) {
                result = new CalResult(type, loc, 0);
            }

            gobang.play(type, loc);

            CalResult cResult = calLocationScore(list, type.next(), level + 1);
            if (cResult != null) {
                result.score = result.score + cResult.score;
            }

            if (maxResult == null || result.score > maxResult.score) {
                maxResult = result;
            }

            gobang.replay(loc);
        }

        return maxResult;
    }

    private class CalResult {
        Location loc = null;
        int score = 0;

        public CalResult(PieceType type, Location loc, int score) {
            this.loc = loc;
            this.score = type == PieceType.PEOPLE ? -score : score;
        }
    }

    private enum StateScore {
        COME5(PieceState.COME5, 100000), //
        LIVE4(PieceState.LIVE4, 10000), //
        DOUBLE_DIE4(PieceState.DOUBLE_DIE4, 10000), //
        DIE4_LIVE3(PieceState.DIE4_LIVE3, 10000), //
        DOUBLE_LIVE3(PieceState.DOUBLE_LIVE3, 5000), //
        DIE3_LIVE3(PieceState.DIE3_LIVE3, 1000), //
        DIE4(PieceState.DIE4, 500), //
        LIVE3(PieceState.LIVE3, 200), //
        DIE3(PieceState.DIE3, 50), //
        DOUBLE_LIVE2(PieceState.DOUBLE_LIVE2, 10), //
        LIVE2(PieceState.LIVE2, 5), //
        DIE2(PieceState.DIE2, 3); //

        StateScore(PieceState state, int score) {
            this.state = state;
            this.score = score;
        }

        static StateScore first = StateScore.COME5;

        PieceState state;
        int score;

        public StateScore next() {
            for (int i = 0; i < StateScore.values().length - 1; i++) {
                if (StateScore.values()[i] == this) {
                    return StateScore.values()[i + 1];
                }
            }
            return null;
        }
    }
}
