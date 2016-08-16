package comp1110.ass2;

/**
 * This class provides the text interface for the Link Game
 *
 * The game is based directly on Smart Games' IQ-Link game
 * (http://www.smartgames.eu/en/smartgames/iq-link)
 */
public class LinkGame {

    /**
     * Determine whether a piece placement is well-formed according to the following:
     * - it consists of exactly three characters
     * - the first character is in the range A .. X
     * - the second character is in the range A .. L
     * - the third character is in the range A .. F if the second character is A, otherwise
     *   in the range A .. L
     *
     * @param piecePlacement A string describing a piece placement
     * @return True if the piece placement is well-formed
     */
    static boolean isPiecePlacementWellFormed(String piecePlacement) {
        // FIXME Task 3: determine whether a piece placement is well-formed

        if (piecePlacement.length()!=3){
        return false;}
        else{
            if (piecePlacement.charAt(0)>='A'&&piecePlacement.charAt(0)<='X'&&
                    piecePlacement.charAt(1)>'A'&&piecePlacement.charAt(1)<='L'&&
                    piecePlacement.charAt(2)>='A'&&piecePlacement.charAt(2)<='L'){
                return true;
            }
            else if(piecePlacement.charAt(0)>='A'&&piecePlacement.charAt(0)<='X'&&
                    piecePlacement.charAt(1)=='A'&&
                    piecePlacement.charAt(2)>='A'&&piecePlacement.charAt(2)<='F'){
                return true;}
            else{
                return false;
            }
        }

    }

    /**
     * Determine whether a placement string is well-formed:
     *  - it consists of exactly N three-character piece placements (where N = 1 .. 12);
     *  - each piece placement is well-formed
     *  - no piece appears more than once in the placement
     *
     * @param placement A string describing a placement of one or more pieces
     * @return True if the placement is well-formed
     */
    static boolean isPlacementWellFormed(String placement) {
        // FIXME Task 4: determine whether a placement is well-formed
        int sublength = 3;
        Boolean a = true;
        if(placement != null && !placement.isEmpty()) {
            if (placement.length() % sublength != 0)
                a = false;
            int length = placement.length() / sublength;
            String[] sublist = new String[length];
            String[] testlist = new String[length];
            for (int i = 0; i < length; i++) {
                sublist[i] = placement.substring(sublength * i, sublength * (i + 1));
                for (int j = i + 1; j < length; j++) {
                    testlist[j] = placement.substring(sublength * j + 1, sublength * j + 2);
                    if (sublist[i].substring(1, 2).equals(testlist[j])) {
                        a = false;
                    }
                }
                if (!isPiecePlacementWellFormed(sublist[i])) {
                    a = false;
                }
            }
            return a;
        }
        else{
            return false;
        }
    }

    /**
     * Return a array of peg locations according to which pegs the given piece placement touches.
     * The values in the array should be ordered according to the links that constitute the
     * piece.
     * The code needs to account for the origin of the piece, the piece shape, and the piece
     * orientation.
     * @param piecePlacement A valid string describing a piece placement
     * @return An array of integers corresponding to the pegs which the piece placement touches,
     * listed in the normal order of links for that piece.
     */
    static int[] getPegsForPiecePlacement(String piecePlacement) {
        // FIXME Task 6: determine the pegs touched by a piece placement
        return null;
    }


    /**
     * Determine whether a placement is valid.  To be valid, the placement must be well-formed
     * and each piece must correctly connect with each other.
     *
     * @param placement A placement string
     * @return True if the placement is valid
     */
    static boolean isPlacementValid(String placement) {
        // FIXME Task 7: determine whether a placement is valid
        return false;
    }

    /**
     * Return an array of all solutions given a starting placement.
     *
     * @param placement  A valid piece placement string.
     * @return An array of strings, each describing a solution to the game given the
     * starting point provied by placement.
     */
    static String[] getSolutions(String placement) {
        // FIXME Task 10: determine all solutions to the game, given a particular starting placement
        return null;
    }
}
