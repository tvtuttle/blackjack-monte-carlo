/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tvtuttle
 */
public abstract class Card {
    int value;
    
    public int getValue(){
        return value;
    }
    
    // the following 2 methods exist only to be overridden by AceCard
    public void swapState(){}
    public boolean isHard(){return true;}
    
    abstract String getType();
    
}
