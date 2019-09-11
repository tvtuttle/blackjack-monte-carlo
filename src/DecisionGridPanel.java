
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tvtuttle
 */
public class DecisionGridPanel extends javax.swing.JPanel {

    /**
     * Creates new form DecisionGridPanel
     */
    ArrayList<JButton> buttons;
    public DecisionGridPanel() {
        setLayout(new java.awt.GridLayout(40,11));
        buttons = new ArrayList();
        // first row of labels
        add(new JLabel("Hard", SwingConstants.CENTER));
        for (int i = 0; i < 10; i ++){
            add(new JLabel("" + (i + 2), SwingConstants.CENTER));
        }
        // hard: label followed by buttons for each row
        for (int i = 0; i < 5; i ++){
            add(new JLabel("" + (i+4), SwingConstants.CENTER));
            for (int j = 0; j < 10; j ++){
                JButton button = new JButton("H");
                button.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        buttonActionPerformed(evt);
                    }
                });
            add(button);
            buttons.add(button);
            }
        }
        // only hard 9-11 allow double down (can't have soft 9-11)
        for (int i = 5; i < 8; i ++){
            add(new JLabel("" + (i+4), SwingConstants.CENTER));
            for (int j = 0; j < 10; j ++){
                JButton button = new JButton("H");
                button.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        doubleButtonActionPerformed(evt);
                    }
                });
            add(button);
            buttons.add(button);
            }
        }
        // remaining hard values
        for (int i = 8; i < 18; i ++){
            add(new JLabel("" + (i+4), SwingConstants.CENTER));
            for (int j = 0; j < 10; j ++){
                JButton button = new JButton("H");
                button.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        buttonActionPerformed(evt);
                    }
                });
            add(button);
            buttons.add(button);
            }
        }
        // second row of labels
        add(new JLabel("Soft", SwingConstants.CENTER));
        for (int i = 0; i < 10; i ++){
            add(new JLabel("" + (i + 2), SwingConstants.CENTER));
        }
        // soft: label followed by buttons for each row
        for (int i = 0; i < 9; i ++){
            add(new JLabel("" + (i+13), SwingConstants.CENTER));
            for (int j = 0; j < 10; j ++){
                JButton button = new JButton("H");
                button.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        buttonActionPerformed(evt);
                    }
                });
            add(button);
            buttons.add(button);
            }
        }
        
        //last row of labels
        add(new JLabel("Split", SwingConstants.CENTER));
        for (int i = 0; i < 10; i ++){
            add(new JLabel("" + (i + 2), SwingConstants.CENTER));
        }
        // split: label followed by buttons for each row
        for (int i = 0; i < 10; i ++){
            add(new JLabel("" + (i+2), SwingConstants.CENTER));
            for (int j = 0; j < 10; j ++){
                JButton button = new JButton("N");
                button.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        splitButtonActionPerformed(evt);
                    }
                });
            add(button);
            buttons.add(button);
            }
        }
        setDefault();
        setVisible(true);
    }
    
    private void buttonActionPerformed(java.awt.event.ActionEvent evt){
        JButton button = (JButton) evt.getSource();
        switch (button.getText()){
            case "H": button.setText("S"); break;
            case "S": button.setText("H"); break;
            
        }
    }

    private void splitButtonActionPerformed(java.awt.event.ActionEvent evt){
        JButton button = (JButton) evt.getSource();
        switch (button.getText()){
            case "Y": button.setText("N"); break;
            case "N": button.setText("Y"); break;
        }
    }
    
    private void doubleButtonActionPerformed(java.awt.event.ActionEvent evt){
        JButton button = (JButton) evt.getSource();
        switch (button.getText()){
            case "H": button.setText("S"); break;
            case "S": button.setText("D"); break;
            case "D": button.setText("H"); break;
        }
    }
    
    public int[] getValues(){
        // returns an array of numeric representations of the button values
        int[] out = new int[buttons.size()];
        for (int i = 0; i < 270; i ++){
            switch (buttons.get(i).getText()){
                case "H": out[i] = 0; break;
                case "S": out[i] = 1; break;
                case "D": out[i] = 2; break;
            }
        }
        for (int i = 270; i < buttons.size(); i ++){
            switch (buttons.get(i).getText()){
                case "N": out[i] = 0; break;
                case "Y": out[i] = 1; break;
            }
        }
        return out;
    }
    
    // so i don't have to click so many buttons
    public void setDefault(){
        // let's just default to the dealer's rules: no doubles, no splits, stand on all 17s
        for (int i = 130; i < 180; i ++){
            buttons.get(i).setText("S");
        }
        for (int i = 220; i < 270; i ++){
            buttons.get(i).setText("S");
        }
//        for (int i = 270; i < 370; i ++){
//            buttons.get(i).setText("Y");
//        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.GridLayout());
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
