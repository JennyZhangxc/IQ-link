package comp1110.ass2;

import java.util.HashSet;

/**
 * Created by Wei on 15/08/2016.
 */
public enum  Unit {
    BALL(false,false,false,false,false,false),
    RING(true,true,true,true,true,true),
    BALL_0(true,false,false,false,false,false),
    BALL_3(false,false,false,true,false,false),
    BALL_4(false,false,false,false,true,false),
    BALL_5(false,false,false,false,false,true),
    BALL_01(true,true,false,false,false,false),
    BALL_03(true,false,false,true,false,false),
    RING_HALF(true,false,false,true,true,true),
    RING_FULL(true,true,true,true,true,true),
    RING_0(false,true,true,true,true,true),
    RING_1(true,false,true,true,true,true),
    RING_2(true,true,false,true,true,true),
    RING_3(true,true,true,false,true,true),
    RING_4(true,true,true,true,false,true),
    RING_5(true,true,true,true,true,false);

    /* An boolean array representing the initial surrounding situation of this piece.*/
    boolean[]surrounding;
    /* An boolean array representing the surrounding situation after orientation input of this piece.*/
    boolean[]surrounding_orientation;

    /* Two separated Hashsets store all the ball or ring features, which helps to judge the feature of piece.*/
    static final HashSet<Unit> Balls=new HashSet<>();
    static final HashSet<Unit> Rings=new HashSet<>();

    /**
     * Constructor for Unit, which represents the surrounding situation of this unit.
     * boolean false means the surrounding is empty.
     * boolean true means the surrounding is occupied by this piece.
     *
     * @author Lei Huang
     * @param a boolean at index 0
     * @param b boolean at index 1
     * @param c boolean at index 2
     * @param d boolean at index 3
     * @param e boolean at index 4
     * @param f boolean at index 5
     */
    Unit(boolean a, boolean b, boolean c,boolean d,boolean e,boolean f)
    {
        surrounding = new boolean[6];
        surrounding[0] = a;
        surrounding[1] = b;
        surrounding[2] = c;
        surrounding[3] = d;
        surrounding[4] = e;
        surrounding[5] = f;
    }

    /**
     * The function that add all ball units into the Hashset balls.
     * @author Lei Huang
     */
    static void setBalls(){
        Balls.add(Unit.BALL);
        Balls.add(Unit.BALL_0);
        Balls.add(Unit.BALL_3);
        Balls.add(Unit.BALL_4);
        Balls.add(Unit.BALL_5);
        Balls.add(Unit.BALL_01);
        Balls.add(Unit.BALL_03);
    }
    /**
     * The function that add all ball units into the Hashset rings.
     * @author Lei Huang
     */
    static void setRings(){
        Rings.add(Unit.RING);
        Rings.add(Unit.RING_HALF);
        Rings.add(Unit.RING_FULL);
        Rings.add(Unit.RING_0);
        Rings.add(Unit.RING_1);
        Rings.add(Unit.RING_2);
        Rings.add(Unit.RING_3);
        Rings.add(Unit.RING_4);
        Rings.add(Unit.RING_5);
    }
    /**
     * The function that modify piece surrounding_orientation boolean array according to the input parameter.
     *
     * @author Lei Huang
     * @param adjust int value according to the orientation.
     *               If the adjust is not less than 6, means piece need to be flipped
     */
    void convert(int adjust){
        if(adjust>=6){
            this.flip();
        }else{
            surrounding_orientation=surrounding;
        }
            boolean[] newSurrounding = new boolean[6];
            for (int i = 0; i < surrounding.length; i++) {
                newSurrounding[(i + adjust) % 6] = surrounding_orientation[i];
            }
            this.surrounding_orientation = newSurrounding;
    }
    /**
     * The function that modifies the surrounding_orientation according to the flipped piece.
     * @author Lei Huang
     */
    void flip(){
        boolean[]flipped=new boolean[6];
        flipped[0]=surrounding[0];
        flipped[1]=surrounding[5];
        flipped[2]=surrounding[4];
        flipped[3]=surrounding[3];
        flipped[4]=surrounding[2];
        flipped[5]=surrounding[1];
        surrounding_orientation=flipped;
    }

}
