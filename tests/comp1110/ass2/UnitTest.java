package comp1110.ass2;

import org.junit.Test;

import java.util.Arrays;
import java.util.Objects;

import static org.junit.Assert.*;

/**
 * Created by huanglei on 16/9/19.
 */
public class UnitTest {
    @Test
    public void convert() throws Exception {
        //mid part of piece A
        Unit convert_test1= Unit.RING_HALF;
        convert_test1.convert(4);
        boolean[] test1=new boolean[6];
        for (int i = 0; i < test1.length; i++) {
            test1[i]=true;
        }
        test1[0]=false;
        test1[5]=false;
        assertTrue(convert_test1.toString()+" could not convert correctly",Objects.deepEquals(test1,convert_test1.surrounding_orientation));

        //right part of piece B
        Unit convert_test2=Unit.RING_2;
        convert_test2.convert(11);
        boolean[]test2=new boolean[6];
        for (int i = 0; i < test2.length; i++) {
            test2[i]=true;
        }
        test2[3]=false;
        assertTrue(convert_test2.toString()+" could not convert correctly",Objects.deepEquals(test2,convert_test2.surrounding_orientation));

        //left part of piece C
        Unit convert_test3=Unit.BALL_3;
        convert_test3.convert(7);
        boolean[]test3=new boolean[6];
        test3[4]=true;
        assertTrue(convert_test3.toString()+" could not convert correctly",Objects.deepEquals(test3,convert_test3.surrounding_orientation));


    }

    @Test
    public void flip() throws Exception {

        Unit[]test_units=new Unit[16];
        boolean[][]test_boolean=new boolean[16][];

        test_units[0]=Unit.BALL;
        test_boolean[0]=new boolean[6];

        test_units[1]=Unit.RING;
        test_boolean[1]=new boolean[6];
        for (int i = 0; i < test_boolean[1].length; i++)
            test_boolean[1][i]=true;

        test_units[2]=Unit.BALL_0;
        test_boolean[2]=new boolean[6];
        test_boolean[2][0]=true;

        test_units[3]=Unit.BALL_3;
        test_boolean[3]=new boolean[6];
        test_boolean[3][3]=true;

        test_units[4]=Unit.BALL_4;
        test_boolean[4]=new boolean[6];
        test_boolean[4][2]=true;

        test_units[5]=Unit.BALL_5;
        test_boolean[5]=new boolean[6];
        test_boolean[5][1]=true;

        test_units[6]=Unit.BALL_01;
        test_boolean[6]=new boolean[6];
        test_boolean[6][0]=true;
        test_boolean[6][5]=true;

        test_units[7]=Unit.BALL_03;
        test_boolean[7]=new boolean[6];
        test_boolean[7][0]=true;
        test_boolean[7][3]=true;

        test_units[8]=Unit.RING_HALF;
        test_boolean[8]=new boolean[6];
        test_boolean[8][0]=true;
        test_boolean[8][1]=true;
        test_boolean[8][2]=true;
        test_boolean[8][3]=true;

        test_units[9]=Unit.RING_FULL;
        test_boolean[9]=new boolean[6];
        for (int i = 0; i < test_boolean[9].length; i++)
            test_boolean[9][i]=true;

        test_units[10]=Unit.RING_0;
        test_boolean[10]=new boolean[6];
        for (int i = 0; i < test_boolean[10].length; i++)
            test_boolean[10][i]=true;
        test_boolean[10][0]=false;

        test_units[11]=Unit.RING_1;
        test_boolean[11]=new boolean[6];
        for (int i = 0; i < test_boolean[11].length; i++)
            test_boolean[11][i]=true;
        test_boolean[11][5]=false;

        test_units[12]=Unit.RING_2;
        test_boolean[12]=new boolean[6];
        for (int i = 0; i < test_boolean[12].length; i++)
            test_boolean[12][i]=true;
        test_boolean[12][4]=false;

        test_units[13]=Unit.RING_3;
        test_boolean[13]=new boolean[6];
        for (int i = 0; i < test_boolean[13].length; i++)
            test_boolean[13][i]=true;
        test_boolean[13][3]=false;

        test_units[14]=Unit.RING_4;
        test_boolean[14]=new boolean[6];
        for (int i = 0; i < test_boolean[14].length; i++)
            test_boolean[14][i]=true;
        test_boolean[14][2]=false;

        test_units[15]=Unit.RING_5;
        test_boolean[15]=new boolean[6];
        for (int i = 0; i < test_boolean[15].length; i++)
            test_boolean[15][i]=true;
        test_boolean[15][1]=false;

        for (int i = 0; i < test_units.length; i++) {
            test_units[i].flip();
            assertTrue(test_units[i].toString() + " could not flip correctly", Objects.deepEquals(test_boolean[i],test_units[i].surrounding_orientation));
        }
    }

}