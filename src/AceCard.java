/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tvtuttle
 */
public class AceCard extends Card{
    public AceCard(){
        value = 11;
    }
    
    public void swapState(){
//        System.out.println("is this thing on?");
        if (value == 11){
            value = 1;
        }
        else if (value == 1){
            value = 11;
        }
    }
    public boolean isHard(){
        return (value == 1);
    }
    
    public String getType(){
        return "ace";
    }
}
