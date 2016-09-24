package comp1110.ass2;

import org.junit.Test;
import sun.awt.image.ImageWatched;

import static org.junit.Assert.*;

/**
 * Created by huanglei on 16/9/24.
 */
public class LinkGameTest {
    @Test
    public void isPlacementValid() throws Exception {
//        for (int i:LinkGame.getPegsForPiecePlacement("EFE")
//             ) {
//            System.out.println(i);
//        }
//
//        System.out.println(LinkGame.isPlacementValid("KAF"));
//        System.out.println(LinkGame.isPlacementValid("KAFUBA"));
//        System.out.println(LinkGame.isPlacementValid("KAFUBAICC"));
//        System.out.println(LinkGame.isPlacementValid("KAFUBAICCPDA"));
//        System.out.println(LinkGame.isPlacementValid("KAFUBAICCPDALEF"));
//        System.out.println(LinkGame.isPlacementValid("KAFUBAICCPDALEFEFE"));
//        System.out.println(LinkGame.isPlacementValid("KAFUBAICCPDALEFEFEQGH"));
//        System.out.println(LinkGame.isPlacementValid("KAFUBAICCPDALEFEFEQGHSHB"));
//        System.out.println(LinkGame.isPlacementValid("KAFUBAICCPDALEFEFEQGHSHBNIB"));
        System.out.println(LinkGame.isPlacementValid("KAFCBGUCAGDFLEFPFBBGESHBOIAJJB"));
        for (String i:LinkGame.getSolutions("KAFCBGUCAGDFLEFPFBBGESHBOIA")
             ) {
            System.out.println(i);
        }
    }

}