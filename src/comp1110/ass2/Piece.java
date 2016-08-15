package comp1110.ass2;

/**
 * Created by Wei on 15/08/2016.
 */
public enum  Piece
{
    A(Unit.BALL, Unit.RING, Unit.BALL),
    B(Unit.BALL, Unit.RING, Unit.RING),
    C(Unit.BALL, Unit.RING, Unit.RING),
    D(Unit.BALL, Unit.RING, Unit.RING),
    E(Unit.BALL, Unit.RING, Unit.RING),
    F(Unit.BALL, Unit.RING, Unit.RING),
    G(Unit.BALL, Unit.RING, Unit.BALL),
    H(Unit.RING, Unit.RING, Unit.RING),
    I(Unit.BALL, Unit.BALL, Unit.RING),
    J(Unit.BALL, Unit.BALL, Unit.RING),
    K(Unit.BALL, Unit.RING, Unit.BALL),
    L(Unit.BALL, Unit.RING, Unit.RING);

    final Unit[] units;

    Piece(Unit a, Unit b, Unit c)
    {
        units = new Unit[3];
        units[0] = a;
        units[1] = b;
        units[2] = c;
    }



}
