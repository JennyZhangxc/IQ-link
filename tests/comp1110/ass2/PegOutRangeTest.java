package comp1110.ass2;

import org.junit.Test;
import java.util.Random;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Wei on 22/09/2016.
 */
public class PegOutRangeTest
{
    @Test
    public void insidePegTestCase()
    {
        int row, column, pegPosition, testCase;
        Random random = new Random();

        for(int i = 0; i <= 200; i++)
        {
            row = random.nextInt(4);
            column = random.nextInt(5);
            pegPosition = (row * 6) + column;
            testCase = random.nextInt(24);

            assertFalse("Piece at " + testCase + " and Position at " + pegPosition + "is in bound, but failed ", LinkGame.isPegOutRange(testCase, pegPosition));
        }
    }

    @Test
    public void offsidePegUpTestCase()
    {
        int r, pegPosition, testCase;
        Random random = new Random();

        for(int i = 0; i <= 200; i++)
        {
            r = random.nextInt(2);
            pegPosition = random.nextInt(5);

            if(r == 0)
            {
                testCase = pegPosition - 5;
            }
            else
            {
                testCase = pegPosition - 6;
            }

            assertTrue("Piece at " + testCase + " and Position at " + pegPosition + "is out bound, but passed ", LinkGame.isPegOutRange(testCase, pegPosition));
        }

    }

    @Test
    public void offsidePegDownTestCase()
    {
        int r, pegPosition, testCase;
        Random random = new Random();

        for(int i = 0; i <= 200; i++)
        {
            r = random.nextInt(2);
            pegPosition = random.nextInt(5) + 18;

            if(r == 0)
            {
                testCase = pegPosition + 6;
            }
            else
            {
                testCase = pegPosition + 7;
            }

            assertTrue("Piece at " + testCase + " and Position at " + pegPosition + "is out bound, but passed ", LinkGame.isPegOutRange(testCase, pegPosition));
        }
    }

    @Test
    public void offsidePegTestCase()
    {
        int r, pegPosition, testCase;
        Random random = new Random();

        for(int i = 0; i <= 200; i++)
        {
            r = random.nextInt(2);

            if(r == 0)
            {
                pegPosition = 5;
            }
            else
            {
                pegPosition = 17;
            }

            testCase = pegPosition + 1;

            assertTrue("Piece at " + testCase + " and Position at " + pegPosition + "is out bound, but passed ", LinkGame.isPegOutRange(testCase, pegPosition));
        }
    }
}