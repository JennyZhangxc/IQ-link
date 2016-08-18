package comp1110.ass2;

/**
 * Created by Wei on 15/08/2016.
 */
public class PiecePlacement {
    private int origin;
    private Piece piece;
    private Orientation orientation;

    PiecePlacement(String pieceplacement) {
        origin = pieceplacement.charAt(0)-'A';
        piece = Piece.valueOf(pieceplacement.substring(1,2));
        orientation = Orientation.valueOf(pieceplacement.substring(2,3));
//        if (origin < 0 || origin > Puzzle.PLACES - 1 || piece == null || orientation == null)
//            throw new IllegalArgumentException("Bad piece placement string: "+pieceplacement);
    }

    void updateCoverageAndErrors(Puzzle puzzle, int[] coverage, boolean[] errors) {
    }
}
