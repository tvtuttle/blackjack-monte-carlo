
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tvtuttle
 */
public class CardList extends ArrayList<Card>{
    public int sum(){
        int out=0;
        for (Card thi : this) {
            out += thi.getValue();
        }
        return out;
    }
    
    public boolean hasSoftAce(){
        for (Card thi : this) {
            if (!thi.isHard()){
                return true;
            }
        }
        return false;
    }
    public void softToHard(){
        // sets first soft ace found to hard
        for (Card thi : this) {
            if (!thi.isHard()){
                thi.swapState();
                break;
            }
        }
    }
    public void hardToSoft(){
        for (Card thi : this) {
            if (thi.isHard()){
                thi.swapState();
            }
        }
    }
}
