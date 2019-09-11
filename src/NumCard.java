/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tvtuttle
 */
public class NumCard extends Card{
    public NumCard(int i){
        value = i;
    }
    
    public String getType(){
        return "num";
    }
}
