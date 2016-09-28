package comp1110.ass2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static comp1110.ass2.Piece.A;

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
        return piecePlacement.length() == 3 &&
                (       piecePlacement.charAt(0) >= 'A' &&
                        piecePlacement.charAt(0) <= 'X' &&
                        piecePlacement.charAt(1) > 'A' &&
                        piecePlacement.charAt(1) <= 'L' &&
                        piecePlacement.charAt(2) >= 'A' &&
                        piecePlacement.charAt(2) <= 'L' ||
                                piecePlacement.charAt(0) >= 'A' &&
                                piecePlacement.charAt(0) <= 'X' &&
                                piecePlacement.charAt(1) == 'A' &&
                                piecePlacement.charAt(2) >= 'A' &&
                                piecePlacement.charAt(2) <= 'F');
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
        if(placement != null && !placement.isEmpty()) {
            if (placement.length() % sublength != 0)
                return false;
            int length = placement.length() / sublength;
            String[] sublist = new String[length];
            String[] testlist = new String[length];
            for (int i = 0; i < length; i++) {
                sublist[i] = placement.substring(sublength * i, sublength * (i + 1));
                for (int j = i + 1; j < length; j++) {
                    testlist[j] = placement.substring(sublength * j + 1, sublength * j + 2);
                    if (sublist[i].substring(1, 2).equals(testlist[j])) {
                        return false;
                    }
                }
                if (!isPiecePlacementWellFormed(sublist[i])) {
                    return false;
                }
            }
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Return a array of peg locations according to which pegs the given piece placement touches.
     * The values in the array should be ordered according to the units that constitute the
     * piece.
     * The code needs to account for the origin of the piece, the piece shape, and the piece
     * orientation.
     * @param piecePlacement A valid string describing a piece placement
     * @return An array of integers corresponding to the pegs which the piece placement touches,
     * listed in the normal order of units for that piece.   The value 0 corresponds to
     * peg 'A', 1 to peg 'B', etc.
     */
    static int[] getPegsForPiecePlacement(String piecePlacement) {
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
            positions[1] = location_o;
            if (sublist[position].charAt(1) >= 'A' && sublist[position].charAt(1) <= 'C') {
                switch (sublist[position].charAt(2)) {
                    case ('A'):
                    case ('G'):
                        positions[0] = location_o - 1;
                        positions[2] = location_o + 1;
                        break;
                    case ('B'):
                    case ('H'):
                        positions[0] = location_o + l_a;
                        positions[2] = location_o + r_b;
                        break;
                    case ('C'):
                    case ('I'):
                        positions[0] = location_o + r_a;
                        positions[2] = location_o + l_b;
                        break;
                    case ('D'):
                    case ('J'):
                        positions[0] = location_o + 1;
                        positions[2] = location_o - 1;
                        break;
                    case ('E'):
                    case ('K'):
                        positions[0] = location_o + r_b;
                        positions[2] = location_o + l_a;
                        break;
                    case ('F'):
                    case ('L'):
                        positions[0] = location_o + l_b;
                        positions[2] = location_o + r_a;
                        break;
                }
            }
            if (sublist[position].charAt(1) >= 'D' && sublist[position].charAt(1) <= 'H') {
                switch (sublist[position].charAt(2)) {
                    case ('A'):
                        positions[0] = location_o - 1;
                        positions[2] = location_o +r_a;
                        break;
                    case ('I'):
                        positions[2] = location_o - 1;
                        positions[0] = location_o +r_a;
                        break;
                    case ('B'):
                        positions[0] = location_o + l_a;
                        positions[2] = location_o + 1;
                        break;
                    case ('J'):
                        positions[2] = location_o + l_a;
                        positions[0] = location_o + 1;
                        break;
                    case ('C'):
                        positions[0] = location_o + r_a;
                        positions[2] = location_o + r_b;
                        break;
                    case ('K'):
                        positions[2] = location_o + r_a;
                        positions[0] = location_o + r_b;
                        break;
                    case ('D'):
                        positions[0] = location_o + 1;
                        positions[2] = location_o + l_b;
                        break;
                    case ('L'):
                        positions[2] = location_o + 1;
                        positions[0] = location_o + l_b;
                        break;
                    case ('E'):
                        positions[0] = location_o + r_b;
                        positions[2] = location_o - 1;
                        break;
                    case ('G'):
                        positions[2] = location_o + r_b;
                        positions[0] = location_o - 1;
                        break;
                    case ('F'):
                        positions[0] = location_o + l_b;
                        positions[2] = location_o + l_a;
                        break;
                    case ('H'):
                        positions[2] = location_o + l_b;
                        positions[0] = location_o + l_a;
                        break;
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
                        break;
                    case ('K'):
                        positions[2] = location_o + 1;
                        positions[1] = location_o;
                        positions[0] = location_o + r_b;
                        break;
                    case ('E'):
                        positions[0] = location_o + r_b;
                        positions[1] = location_o;
                        positions[2] = location_o + l_b;
                        break;
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
            }
            for(int i:positions){
                if (Math.abs(i%6-o_column)<=1){
                    peg_locations.add(i);
                }
                else{
                    peg_locations.add(-1);
                }
            }
        }
        int[]output =new int[peg_locations.size()];
        for(int i =0;i<peg_locations.size();i++){
            if(peg_locations.get(i)<0||peg_locations.get(i)>=24){
                output[i]=-1;
            }
            else{
                output[i]=peg_locations.get(i);
            }
        }
        return output;
    }

    /**
     * Determine whether the peg is out of bound or not.
     * @param value the test case value, pegPosition the peg position
     * @return True if the peg is outside the bound
      */
    public static boolean isPegOutsideRange(int value, int pegPosition)
    {
        if((pegPosition == 5) || (pegPosition == 17) || (value == pegPosition + 1)) return true;
        else if(((pegPosition == 11) || (pegPosition == 23)) && ((value == pegPosition - 5) || (value == pegPosition + 1) || (value == pegPosition + 7))) return true;
        else if((value < 0) || (value > 23)) return true;
        else return false;
    }

    /**
     * Determine whether a placement is valid.  To be valid, the placement must be well-formed
     * and each piece must correctly connect with each other.
     *
     * @param placement A placement string
     * @return True if the placement is valid
     */
    // FIXME Task 7: determine whether a placement is valid
    final static boolean[]PEGS_BALL=new boolean[24];
    final static boolean[][]PEGS_SURROUNDING=new boolean[24][6];
    final static boolean[]used_piece=new boolean[12];
    public static boolean isPlacementValid(String placement) {
        //Initialize PEGS_BALL and PEGS_RING;
        Arrays.fill(PEGS_BALL,false);
        Arrays.fill(used_piece,false);
        for(int i=0;i<24;i++){
        Arrays.fill(PEGS_SURROUNDING[i],false);}

        //First judge whether the placement is well formed.
        if(!LinkGame.isPlacementWellFormed(placement)){return false;}

        //Break the placement into pieces(for each piece) and assign them into string array placements
        String[]placements=new String[placement.length()/3];
        for(int i=0;i<placement.length()/3;i++){
            placements[i]=placement.substring(i*3,(i+1)*3);}

        //test the validity of each piece
        for (String piece:placements) {
            //Use param piece_this to represent current piece.
            Piece piece_this=Piece.valueOf(Character.toString(piece.charAt(1)));
            //Use param orientation_this to represent current piece orientation.
            Orientation orientation_this=Orientation.valueOf(Character.toString(piece.charAt(2)));

            //Judge whether the placement is out of bound.
            for (int i:LinkGame.getPegsForPiecePlacement(piece)) {
                if(i==-1)return false;}

            //test whether the piece is used or not.
            int piece_number=(int)(piece.charAt(1))-65;
            if(!used_piece[piece_number]) {used_piece[piece_number]=true;}
            else{return false;}

            int[] positions=LinkGame.getPegsForPiecePlacement(piece);
            //check whether the peg is occupied or not.
            for (int i=0;i<piece_this.units.length;i++) {
                if (Unit.Balls.contains(piece_this.units[i])) {
                    if (!PEGS_BALL[positions[i]]) {
                        PEGS_BALL[positions[i]] = true;
                    } else {
                        return false;
                    }
                }
            }
            //check whether the surrounding is occupied or not.
            piece_this.orientation(orientation_this);
            for (int i=0;i<piece_this.units.length;i++) {
                for(int j=0;j<6;j++) {
                    if (piece_this.units[i].surrounding_orientation[j]) {
                        if (!PEGS_SURROUNDING[positions[i]][j]) {
                            PEGS_SURROUNDING[positions[i]][j] = true;
                        } else {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Return an array of all solutions given a starting placement.
     *
     * @param placement  A valid piece placement string.
     * @return An array of strings, each describing a solution to the game given the
     * starting point provied by placement.
     */
    final static boolean[]SOLUTION_PEGS_BALL=new boolean[24];
    final static boolean[][]SOLUTION_PEGS_SURROUNDING=new boolean[24][6];
    final static boolean[]SOLUTION_used_piece=new boolean[12];
    static String[] getSolutions(String placement) {
        // FIXME Task 10: determine all solutions to the game, given a particular starting placement
        //Initialize PEGS_BALL, PEGS_RING and used_piece;
        Arrays.fill(SOLUTION_PEGS_BALL,false);
        Arrays.fill(SOLUTION_used_piece,false);
        for(int i=0;i<24;i++){
            Arrays.fill(SOLUTION_PEGS_SURROUNDING[i],false);}

        //First judge whether the placement is well formed.
        if(!LinkGame.isPlacementValid(placement)){
            System.out.println("Invalid input placement");
            return null;
        }

        //Break the placement into pieces(for each piece) and assign them into string array placements
        final int sublength=3;
        String[]placements=new String[placement.length()/sublength];
        for(int i=0;i<placement.length()/sublength;i++){
            placements[i]=placement.substring(i*sublength,(i+1)*sublength);}

        //Set the initial map situation for input placement
        for (String piece:placements) {
            //Use param piece_this to represent current piece.
            Piece piece_this=Piece.valueOf(Character.toString(piece.charAt(1)));
            //Use param orientation_this to represent current piece orientation.
            Orientation orientation_this=Orientation.valueOf(Character.toString(piece.charAt(2)));

            int piece_number=(int)(piece.charAt(1))-65;
            SOLUTION_used_piece[piece_number]=true;

            int[] positions=LinkGame.getPegsForPiecePlacement(piece);

            for (int i=0;i<piece_this.units.length;i++) {
                if (Unit.Balls.contains(piece_this.units[i])) {
                    SOLUTION_PEGS_BALL[positions[i]] = true;
                }
            }
            piece_this.orientation(orientation_this);
            for (int i=0;i<piece_this.units.length;i++) {
                for(int j=0;j<6;j++)
                    SOLUTION_PEGS_SURROUNDING[positions[i]][j] = true;
            }
        }

        //use a arraylist to instore the sub-solutions
        ArrayList<String>sub_solutions_0=new ArrayList<>();

        sub_solutions_0.add(placement);
        ArrayList<String>sub_solutions_1=FindNextSubSolutions(sub_solutions_0);
        ArrayList<String>sub_solutions_2=FindNextSubSolutions(sub_solutions_1);
        ArrayList<String>sub_solutions_3=FindNextSubSolutions(sub_solutions_2);
        ArrayList<String>sub_solutions_4=FindNextSubSolutions(sub_solutions_3);
        ArrayList<String>sub_solutions_5=FindNextSubSolutions(sub_solutions_4);
        ArrayList<String>sub_solutions_6=FindNextSubSolutions(sub_solutions_5);
        ArrayList<String>sub_solutions_7=FindNextSubSolutions(sub_solutions_6);
        ArrayList<String>sub_solutions_8=FindNextSubSolutions(sub_solutions_7);
        ArrayList<String>sub_solutions_9=FindNextSubSolutions(sub_solutions_8);
        ArrayList<String>sub_solutions_10=FindNextSubSolutions(sub_solutions_9);
        ArrayList<String>sub_solutions_11=FindNextSubSolutions(sub_solutions_10);

        ArrayList<String> Final_solutions=sub_solutions_11;

        String[]output=new String[Final_solutions.size()];
        for (int i = 0; i <Final_solutions.size(); i++) {
            output[i]=Final_solutions.get(i);
        }

        return output;
    }
    static ArrayList<String> FindNextValidPieces(String[] placement_nextPiece){
            String placement=placement_nextPiece[0];
            String nextPiece=placement_nextPiece[1];
            ArrayList<String>output=new ArrayList<>();

            //Initialize PEGS_BALL and PEGS_RING;
            Arrays.fill(PEGS_BALL,false);
            Arrays.fill(used_piece,false);
            for(int i=0;i<24;i++){
                Arrays.fill(PEGS_SURROUNDING[i],false);}

            //Break the placement into pieces(for each piece) and assign them into string array placements
            String[]placements=new String[placement.length()/3];
            for(int i=0;i<placement.length()/3;i++){
                placements[i]=placement.substring(i*3,(i+1)*3);}

            for (String piece:placements) {
                //Use param piece_this to represent current piece.
                Piece piece_this=Piece.valueOf(Character.toString(piece.charAt(1)));
                //Use param orientation_this to represent current piece orientation.
                Orientation orientation_this=Orientation.valueOf(Character.toString(piece.charAt(2)));

                int[] positions=LinkGame.getPegsForPiecePlacement(piece);

                for (int i=0;i<3;i++) {
                    if (Unit.Balls.contains(piece_this.units[i])) {
                        PEGS_BALL[positions[i]] = true;
                    }
                }

                piece_this.orientation(orientation_this);
                for (int i=0;i<3;i++) {
                    for(int j=0;j<6;j++) {
                        if (piece_this.units[i].surrounding_orientation[j]) {
                            PEGS_SURROUNDING[positions[i]][j] = true;
                        }
                    }
                }
            }
        for (int j = 0; j < 24; j++) {
            for (int k = 0; k < 12; k++) {
                String test_sub =(char) ('A' + j) + nextPiece + (char) ('A' + k);
                if (isNextPiecePlacementValid(test_sub)) {
                    output.add(placement+test_sub);
                }
            }
        }
        return output;
    }


    static boolean isNextPiecePlacementValid(String test_sub){

            //Use param piece_this to represent current piece.
            Piece piece_this=Piece.valueOf(Character.toString(test_sub.charAt(1)));
            //Use param orientation_this to represent current piece orientation.
            Orientation orientation_this=Orientation.valueOf(Character.toString(test_sub.charAt(2)));

            int[] positions=LinkGame.getPegsForPiecePlacement(test_sub);

            for (int i:positions) {
                if(i==-1)return false;}

            for (int i=0;i<piece_this.units.length;i++) {
                if (Unit.Balls.contains(piece_this.units[i])) {
                    if (PEGS_BALL[positions[i]]) {
                        return false;
                    }
                }
            }

            piece_this.orientation(orientation_this);
            for (int i=0;i<piece_this.units.length;i++) {
                for(int j=0;j<6;j++) {
                    if (piece_this.units[i].surrounding_orientation[j]) {
                        if (PEGS_SURROUNDING[positions[i]][j]) {
                            return false;
                        }
                    }
                }
            }
            return true;
        }
    public static boolean isPlacementComplete(String placement){
        return (placement.length()/3)==12;
    }
    static boolean isAllPieceUsed(){
        boolean output=true;
        for (boolean i: SOLUTION_used_piece) {
            if (!i)
                output=false;
        }
        return output;
    }

    static ArrayList<String> FindNextSubSolutions(ArrayList<String>subsolution){
        ArrayList<String>NextSubsolutions=new ArrayList<>();
        int current_piece = 0;
        for (String solution:subsolution) {
            if(isAllPieceUsed()){
                NextSubsolutions=subsolution;
                break;
            }

            for (int i = 0; i < 12; i++) {
                if(!SOLUTION_used_piece[i]) {
                    current_piece = i;
                    String[]placement_nextPiece=new String[2];
                    placement_nextPiece[0]=solution;
                    placement_nextPiece[1]=""+(char)('A'+i);

                    for (String j:FindNextValidPieces(placement_nextPiece)) {
                        NextSubsolutions.add(j);
                    }
                    break;
                }

            }
        }
        SOLUTION_used_piece[current_piece]=true;
        return NextSubsolutions;
    }

}
