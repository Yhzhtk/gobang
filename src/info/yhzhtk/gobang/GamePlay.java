package info.yhzhtk.gobang;

import info.yhzhtk.gobang.GobangModel.Location;
import info.yhzhtk.gobang.GobangModel.PieceType;

public class GamePlay {

    public static void main(String[] args) {
        GobangModel gobang = new GobangModel();
        gobang.play(PieceType.MACHINE, new Location(4, 5));
        gobang.play(PieceType.PEOPLE, new Location(3, 5));
        gobang.play(PieceType.MACHINE, new Location(4, 4));
        gobang.play(PieceType.PEOPLE, new Location(4, 3));

        gobang.play(PieceType.PEOPLE, new Location(0, 0));
        gobang.play(PieceType.PEOPLE, new Location(0, 1));
        gobang.play(PieceType.PEOPLE, new Location(0, 2));
        gobang.play(PieceType.PEOPLE, new Location(0, 3));
        gobang.play(PieceType.PEOPLE, new Location(0, 4));
        gobang.play(PieceType.PEOPLE, new Location(0, 5));
        gobang.play(PieceType.PEOPLE, new Location(0, 6));
        gobang.play(PieceType.PEOPLE, new Location(0, 7));
        gobang.play(PieceType.PEOPLE, new Location(0, 8));
        gobang.play(PieceType.PEOPLE, new Location(0, 9));
        gobang.play(PieceType.PEOPLE, new Location(0, 10));
        gobang.play(PieceType.PEOPLE, new Location(0, 11));
        gobang.play(PieceType.PEOPLE, new Location(0, 12));
        gobang.play(PieceType.PEOPLE, new Location(0, 13));
        gobang.play(PieceType.PEOPLE, new Location(0, 14));
        gobang.print();

        GobangAI gobangAI = new GobangAI(gobang);

        Location location = gobangAI.calBestLocation(new Location(4, 4), new Location(4, 3));
        System.out.println(location);

    }
}
