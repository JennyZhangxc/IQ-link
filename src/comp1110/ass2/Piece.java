package comp1110.ass2;

import java.util.HashSet;

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

    final Unit[] units;


    Piece(Unit a, Unit b, Unit c)
    {
        units = new Unit[3];
        units[0] = a;
        units[1] = b;
        units[2] = c;
        Unit.setBalls();
        Unit.setRings();
    }

}
