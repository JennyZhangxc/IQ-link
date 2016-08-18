package comp1110.ass2;

/**
 * Created by Wei on 15/08/2016.
 */
public class PiecePlacement {
    private int origin;
    private Piece piece;
    private Orientation orientation;
    private LinkGame linkGame;

    PiecePlacement(String pieceplacement) {
        origin = pieceplacement.charAt(0)-'A';
        piece = Piece.valueOf(pieceplacement.substring(1,2));
        orientation = Orientation.valueOf(pieceplacement.substring(2,3));
//        if (origin < 0 || origin > Puzzle.PLACES - 1 || piece == null || orientation == null)
//            throw new IllegalArgumentException("Bad piece placement string: "+pieceplacement);
    }

    void updateCoverageAndErrors(Puzzle puzzle, int[] coverage, boolean[] errors) {
    }

    static PiecePlacement[] getPlacements(String placement) {
//        if (!isPlacementWellFormed(placement)) throw new IllegalArgumentException("Bad placement string: "+placement);
//
//        PiecePlacement[] placements = new PiecePlacement[placement.length()/3];
//        for (int p = 0; p < placements.length; p++) {
//            placements[p] = new PiecePlacement(placement.substring(p*3,(p+1)*3));
//        }
        return null;
    }

}
