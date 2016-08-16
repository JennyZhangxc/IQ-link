package comp1110.ass2;

import java.util.ArrayList;

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
//        return null;
        ArrayList<Integer> peg_locations=new ArrayList<>();
        String[] sublist = new String[piecePlacement.length()/3];
        for(int position=0; position<piecePlacement.length()/3;position++) {
            sublist[position] = piecePlacement.substring(position * 3, (position + 1) * 3);
            int location_o = Character.getNumericValue(piecePlacement.charAt(0))-10;
            int o_column = location_o%6;
            int o_row = location_o/6;
            int l_a;//stands for the position of left above
            int l_b;//stands for the position of left below
            int r_a;//stands for the position of right above
            int r_b;//stands for the position of right below
            if (o_row%2==0){
                l_a = -7;
                l_b = 5;
                r_a = -6;
                r_b = 6;
            }
            else{
                l_a = -6;
                l_b = 6;
                r_a = -5;
                r_b = 7;
            }
            int[] positions = new int[3];
            if (sublist[position].charAt(1) >= 'A' && sublist[position].charAt(1) <= 'C') {
                switch (sublist[position].charAt(2)) {
                    case ('A'):
                    case ('G'):
                        positions[0] = location_o - 1;
                        positions[1] = location_o;
                        positions[2] = location_o + 1;
                        break;
                    case ('B'):
                    case ('H'):
                        positions[0] = location_o + l_a;
                        positions[1] = location_o;
                        positions[2] = location_o + r_b;
                        break;
                    case ('C'):
                    case ('I'):
                        positions[0] = location_o + r_a;
                        positions[1] = location_o;
                        positions[2] = location_o + l_b;
                        break;
                    case ('D'):
                    case ('J'):
                        positions[0] = location_o + 1;
                        positions[1] = location_o;
                        positions[2] = location_o - 1;
                        break;
                    case ('E'):
                    case ('K'):
                        positions[0] = location_o + r_b;
                        positions[1] = location_o;
                        positions[2] = location_o + l_a;
                        break;
                    case ('F'):
                    case ('L'):
                        positions[0] = location_o + l_b;
                        positions[1] = location_o;
                        positions[2] = location_o + r_a;
                        break;
                }
                for(int i=0;i<positions.length;i++){
                    if (Math.abs(positions[i]%6-o_column)<=1){
                        peg_locations.add(positions[i]);
                    }
                    else{
                        peg_locations.add(-1);
                    }
                }
            }
            if (sublist[position].charAt(1) >= 'D' && sublist[position].charAt(1) <= 'H') {
                switch (sublist[position].charAt(2)) {
                    case ('A'):
                        positions[0] = location_o - 1;
                        positions[1] = location_o;
                        positions[2] = location_o +r_a;
                        break;
                    case ('I'):
                        positions[2] = location_o - 1;
                        positions[1] = location_o;
                        positions[0] = location_o +r_a;
                        break;
                    case ('B'):
                        positions[0] = location_o + l_a;
                        positions[1] = location_o;
                        positions[2] = location_o + 1;
                        break;
                    case ('J'):
                        positions[2] = location_o + l_a;
                        positions[1] = location_o;
                        positions[0] = location_o + 1;
                        break;
                    case ('C'):
                        positions[0] = location_o + r_a;
                        positions[1] = location_o;
                        positions[2] = location_o + r_b;
                        break;
                    case ('K'):
                        positions[2] = location_o + r_a;
                        positions[1] = location_o;
                        positions[0] = location_o + r_b;
                        break;
                    case ('D'):
                        positions[0] = location_o + 1;
                        positions[1] = location_o;
                        positions[2] = location_o + l_b;
                    case ('L'):
                        positions[2] = location_o + 1;
                        positions[1] = location_o;
                        positions[0] = location_o + l_b;
                        break;
                    case ('E'):
                        positions[0] = location_o + r_b;
                        positions[1] = location_o;
                        positions[2] = location_o - 1;
                    case ('G'):
                        positions[2] = location_o + r_b;
                        positions[1] = location_o;
                        positions[0] = location_o - 1;
                        break;
                    case ('F'):
                        positions[0] = location_o + l_b;
                        positions[1] = location_o;
                        positions[2] = location_o + l_a;
                        break;
                    case ('H'):
                        positions[2] = location_o + l_b;
                        positions[1] = location_o;
                        positions[0] = location_o + l_a;
                        break;
                }
                for(int i=0;i<positions.length;i++){
                    if (Math.abs(positions[i]%6-o_column)<=1){
                        peg_locations.add(positions[i]);
                    }
                    else{
                        peg_locations.add(-1);
                    }
                }
            }
            if (sublist[position].charAt(1) >= 'I' && sublist[position].charAt(1) <= 'L') {
                switch (sublist[position].charAt(2)) {
                    case ('A'):
                        positions[0] = location_o - 1;
                        positions[1] = location_o;
                        positions[2] = location_o +l_a;
                        break;
                    case ('H'):
                        positions[2] = location_o - 1;
                        positions[1] = location_o;
                        positions[0] = location_o +l_a;
                        break;
                    case ('B'):
                        positions[0] = location_o + l_a;
                        positions[1] = location_o;
                        positions[2] = location_o + r_a;
                        break;
                    case ('I'):
                        positions[2] = location_o + l_a;
                        positions[1] = location_o;
                        positions[0] = location_o + r_a;
                        break;
                    case ('C'):
                        positions[0] = location_o + r_a;
                        positions[1] = location_o;
                        positions[2] = location_o + 1;
                        break;
                    case ('J'):
                        positions[2] = location_o + r_a;
                        positions[1] = location_o;
                        positions[0] = location_o + 1;
                        break;
                    case ('D'):
                        positions[0] = location_o + 1;
                        positions[1] = location_o;
                        positions[2] = location_o + r_b;
                    case ('K'):
                        positions[2] = location_o + 1;
                        positions[1] = location_o;
                        positions[0] = location_o + r_b;
                        break;
                    case ('E'):
                        positions[0] = location_o + r_b;
                        positions[1] = location_o;
                        positions[2] = location_o + l_b;
                    case ('L'):
                        positions[2] = location_o + r_b;
                        positions[1] = location_o;
                        positions[0] = location_o + l_b;
                        break;
                    case ('F'):
                        positions[0] = location_o + l_b;
                        positions[1] = location_o;
                        positions[2] = location_o - 1;
                        break;
                    case ('G'):
                        positions[2] = location_o + l_b;
                        positions[1] = location_o;
                        positions[0] = location_o - 1;
                        break;
                }
                for(int i=0;i<positions.length;i++){
                    if (Math.abs(positions[i]%6-o_column)<=1){
                        peg_locations.add(positions[i]);
                    }
                    else{
                        peg_locations.add(-1);
                    }
                }
            }
        }
        int[]output =new int[peg_locations.size()];
        for(int i =0;i<peg_locations.size();i++){
            if(peg_locations.get(i)<0||peg_locations.get(i)>24){
                output[i]=-1;
            }
            else{
                output[i]=peg_locations.get(i);
            }
        }
        return output;
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
