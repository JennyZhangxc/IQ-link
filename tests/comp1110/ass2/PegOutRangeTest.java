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
    public void pegOutsideTopTestCase()
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

            assertTrue("Piece at: " + testCase + ", Position at: " + pegPosition + " is outside the top bound, but passed the ", LinkGame.isPegOutsideRange(testCase, pegPosition));
        }
    }

    @Test
    public void pegOutsideBottomTestCase()
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

            assertTrue("Piece at: " + testCase + ", Position at: " + pegPosition + " is outside the bottom bound, but passed the ", LinkGame.isPegOutsideRange(testCase, pegPosition));
        }
    }
}