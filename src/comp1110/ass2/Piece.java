package comp1110.ass2;

/**
 * Created by Wei on 15/08/2016.
 */
public enum  Piece
{
    A(Unit.BALL_3, Unit.RING_HALF, Unit.BALL_0),
    B(Unit.BALL_3, Unit.RING_FULL, Unit.RING_2),
    C(Unit.BALL_3, Unit.RING_FULL, Unit.RING_1),
    D(Unit.BALL_3, Unit.RING_FULL, Unit.RING_4),
    E(Unit.BALL_3, Unit.RING_FULL, Unit.RING_0),
    F(Unit.BALL_3, Unit.RING_FULL, Unit.RING_1),
    G(Unit.BALL_3, Unit.RING_5, Unit.BALL_5),
    H(Unit.RING_1, Unit.RING_FULL, Unit.RING_2),
    I(Unit.BALL_3, Unit.BALL_01, Unit.RING_1),
    J(Unit.BALL_3, Unit.BALL_01, Unit.RING_0),
    K(Unit.BALL_3, Unit.RING_4, Unit.BALL_4),
    L(Unit.BALL_3, Unit.RING_4, Unit.RING_3);

    /** An unit array representing the units of this piece.
     * The first unit represents the left unit in original orientation.
     * The second unit represents the middle unit in original orientation.
     * The third unit represents the last unit in original orientation.
     */
    final Unit[] units;

    /**
     * Constructor for piece, which can have three ball or ring feature.
     *
     * @author Lei Huang
     * @param a Unit at index 0
     * @param b Unit at index 1
     * @param c Unit at index 2
     */

    Piece(Unit a, Unit b, Unit c)
    {
        units = new Unit[3];
        units[0] = a;
        units[1] = b;
        units[2] = c;
        Unit.setBalls();
        Unit.setRings();
    }

    /**
     * The function which converts the units array of this piece according to the input Orientation
     *
     * @author Lei Huang
     * @param i Orientation of the piece
     */
    void orientation(Orientation i){
        int adjust=(int)(i.toString().charAt(0))-65;
        this.units[0].convert(adjust);
        this.units[1].convert(adjust);
        this.units[2].convert(adjust);
    }

}
