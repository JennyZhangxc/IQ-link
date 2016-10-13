package comp1110.ass2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

/**
 * Build code for get starting placements randomly.
 * @author Lei Huang,Wei Wei
 */
public class Starting_placements {
    public static void main(String[] args) {
        Random random= new Random();
        Boolean[] used= new Boolean[12];
        for (int i = 0; i < 12; i++) {
            used[i]=false;
        }
        ArrayList<HashSet>temp=new ArrayList();
        for (int i = 0; i < 6; i++) {
            int current= Math.abs(random.nextInt(12));
            while (used[current]){
                current= Math.abs(random.nextInt(12));
            }

            temp.add(LinkGame.Placements.get(current));
        }

        String s_temp="-";

            if(!LinkGame.isPlacementValid(s_temp)){
                s_temp="";
                for (int i = 0; i < temp.size(); i++) {
                    Object[] a=temp.get(i).toArray();
                    int index=0;
                    for (int j = 0; j < a.length; j++) {
                        index=j;
                        String temp2=s_temp+a[j];
//                    System.out.println("temp2:"+temp2);
                        if(LinkGame.isPlacementValid(temp2)){
                            break;
                        }
                    }
                    s_temp+=a[index];
//                    System.out.println(s_temp);
                }
            }
        System.out.println(s_temp);
        System.out.println(LinkGame.isPlacementValid(s_temp));
        System.out.println(LinkGame.getSolutions(s_temp).length);
    }
}

