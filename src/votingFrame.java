/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package voting;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author sozaki19
 */
public class votingFrame extends javax.swing.JFrame {

    // started voting
    boolean starting = true;
    // positions
    int POSITIONS = 20;
    // candidates
    int CANDIDATES = 100;
    // array that contains [positions][candidates]
    String[][] list = new String[POSITIONS][CANDIDATES];
    int[][] votes = new int[POSITIONS][CANDIDATES];
    int position = 0;
    int numVoters = 0;
    boolean waitNextVoter = false;
    char[] admin = {'I','l','i','k','e','p','i','e'};
    boolean others = true; // whether to have others or not
    
    /**
     * Creates new form votingFrame
     */
    public votingFrame() {
        initComponents();
    }
    
    private void initCand(){
        // takes the given JSON file and grab all of the candidates and
        // position.
        JSONParser parser = new JSONParser();

        try {     
            JSONArray a = (JSONArray) parser.parse(new FileReader("./text.txt"));
                int i = 0;
                int j;
              for (Object o : a)
              {

                JSONObject person = (JSONObject) o;

                String title = (String) person.get("Position");
                list[i][0] = title;
//                jLabel1.setText(title + ":");
//                System.out.println(title+":");

                JSONArray pos = (JSONArray) person.get("Candidates");

                // skip over the position name.
                j = 1;
                
                // returns all candidates for this position
                for (Object p : pos) {
                    list[i][j] = p+"";
                    j++;
                }
                i++;
              }

              jLabel1.setText(list[0][0]);
              jList1.setListData(candidate(list[0]));

        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException | ParseException e) {
            System.out.println(e);
        }
    }
    
    private void voting(int cand){
        // votes for the candidate
        votes[position][cand]++;
        System.out.println(list[position][cand] + " has " + votes[position][cand]
                + " for " + list[position][0]);
        if(list[position+1][0] != null){
            // if we have another position
//                System.out.println(jList1.getSelectedIndex());
            position++;
            jLabel1.setText(list[position][0]);
            jList1.setListData(candidate(list[position]));
        } else {
            // run out of positions to vote for
//                System.out.println("finished");
            numVoters++;
            totalVoters.setText(numVoters + " have voted.");
            position = 0;
            waitNextVoter = true; // keeps someone from voting too many times
            // thanks the voter and calls for the next one
            jLabel1.setText("Next voter.");
            String[] end = {"Thank you for voting!"};
            jList1.setListData(end);
            vote.setText("Next Voter");

        }
        
    }
    
    private void printAll() throws IOException{
        String message = "";
        for(int i = 0; i < POSITIONS; i++){
            if(list[i][0] != null){
                System.out.println(list[i][0] + ":");
                message = message + "\n" + list[i][0] + ":\n";
                
            } // if
                for(int j = 1; j < CANDIDATES; j++){
                    if(list[i][j] != null){
                        System.out.println(list[i][j] + " has " + votes[i][j] + " votes.");
                        message = message + list[i][j] + " has " + votes[i][j] + " votes.\n";
                    } // if
            } // for
        } // for
        try {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("./votes.txt"))) {
                writer.write(message);
            } // try writing to a file
        } catch(IOException e) {
            System.out.println("Could not find that file!");
        } // catch
    } // printAllOthers
    
    private boolean matchingPass(char[] pass){
        boolean result = true;
        
        if(admin.length != pass.length){
            // if they do not have the same length, then password provided
            // is incorrect.
            
            result = false;
        } else {
            // checks every char of the provided password to see if it
            // matches the admin password word for word.
            for(int i = 0; i < admin.length; i++){
                    if(admin[i] != pass[i]){
                        result = false;
                    } // if
            } // for
        } // else
        
        return result;
    }
    
    private String[] candidate(String[] a){
        int len = 0;
        
        // skips the title
        int i = 1;
        while(true){
            if(a[i] != null){
                len++;
                i++;
            } else {
                break;
            } // else

        } // while
        
        String[] temp = new String[len];
        //System.out.println(len);
        for(i = 0; i < len; i++){
            temp[i] = a[i + 1];
        }
              

        return temp;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jOther1 = new javax.swing.JTextField();
        vote = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        begining = new javax.swing.JButton();
        jPass1 = new javax.swing.JPasswordField();
        totalVoters = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jOther1.setText("other");

        vote.setText("vote");
        vote.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                voteMousePressed(evt);
            }
        });

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Candidates" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jList1);

        jLabel1.setText("Position");

        begining.setText("Start voting");
        begining.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                beginingMouseClicked(evt);
            }
        });

        jPass1.setText("something long");

        totalVoters.setText("0 have voted.");

        jLabel2.setText("Do you want others?");

        jTextField1.setText("yes or no?");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(137, 137, 137)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jOther1, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPass1))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(123, 123, 123)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(totalVoters, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(vote))
                                .addGap(240, 240, 240))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(begining))))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(187, 187, 187))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(totalVoters))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addComponent(vote)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jOther1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jPass1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(begining)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void voteMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_voteMousePressed
        
        if(waitNextVoter){
            if(matchingPass(jPass1.getPassword())){
                System.out.println("Access Granted");
                jLabel1.setText(list[position][0]);
                jList1.setListData(candidate(list[position]));
                waitNextVoter = false;
                jPass1.setText("Something else!");
                vote.setText("vote");
                
            } else {
                jLabel1.setText("Access Denied!");
                String[] err = {"ErroR","ERroE","erroR","ERRRODRJDOR"};
                jList1.setListData(err);
            }
                
        } else {
            if((!(jOther1.getText().matches("other"))) && !(jOther1.getText().matches(""))){
                // if the others box is changed to something, then use that name and vote for that person
                
                if(others){
                    // if we want this option
                    
                    // convert the name into all lower case
                    String nameCand = jOther1.getText().toLowerCase();
                    
                    // then captialize the first letter
                    nameCand = nameCand.substring(0, 1).toUpperCase() + nameCand.substring(1);

                    // Starting position of the other section
                    int otherPos = candidate(list[position]).length + 2;

        //            System.out.println(nameCand.getText());
        //            System.out.println(candidate(list[position]).length);


                    // moves towards a space for the others' section (2 because the
                    // first element of list is position, then the candidates.)
                    int newCandPos = otherPos;

        //            System.out.println(list[position][newCandPos] != null);

                    // moves up the other's section until it finds a new spot or finds
                    // a match.
                    while(list[position][newCandPos] != null && !(list[position][newCandPos].matches(nameCand))){
                        newCandPos++;
                    }

                    // posts the new person

        //            System.out.println(candidate(list[position]).length);

                    // new position to store the new candidate
                    list[position][newCandPos] = nameCand;

                    // adding their vote!
                    voting(newCandPos);
        //            System.out.println("This is the person's number: " + newCandPos + " with name: " + nameCand);
        //            System.out.println("This is the new person!  " + list[position][newCandPos]);


                    /*

                    int startingP = otherPos;
                    //System.out.println(list[position][startingP] != null);
                    while(list[position][startingP] != null){
                        System.out.println("All: " + list[position][startingP]);
                        startingP++;
                    }

                    */

                    // resets the name
                    jOther1.setText("other");
                } else {
                    System.out.println("I need you to leave the text box empty.");
                    jOther1.setText("other");
                }
                
            } else if(jList1.getSelectedValuesList().size() == 1) {
                // did someone vote for one person?


    //            System.out.println(jList1.getSelectedIndex());

    //            System.out.println(jList1.getSelectedValuesList().get(0));

                int cand = jList1.getSelectedIndex() + 1;
                voting(cand);
            } else {
                System.out.println("please select only one person or "
                        + "write into the box your own nomination.");
            } // else
            
        } // else
        
            
        
        
    }//GEN-LAST:event_voteMousePressed

    private void beginingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_beginingMouseClicked
        char[] pass = jPass1.getPassword();
        boolean comp = true;
        comp = matchingPass(pass);
        if(comp == true){
            
            if(starting){
                
                // let the voting begin
                
                // if you say no then the others will not work.
                if(jTextField1.getText().matches("no")){
                    others = false;
                }
                
                // no need for the text box or the label
                jLabel2.setVisible(false);
                jTextField1.setVisible(false);
                
                starting = false;
                jPass1.setText("Something long");
                begining.setText("End");

                initCand();
            } else {
                // finished voting
                System.out.println("");
                System.out.println("");
                System.out.println("");
                System.out.println("");
                System.out.println("All Done!");
                try {
                    printAll();
                } catch (IOException ex) {
                    Logger.getLogger(votingFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                setVisible(false); //you can't see me!
                dispose(); //Destroy the JFrame object
            }

        } else {
            System.out.println("That is not the correct password.");
        } // else
            
    }//GEN-LAST:event_beginingMouseClicked

    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(votingFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(votingFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(votingFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(votingFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        
        
        

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new votingFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton begining;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JList<String> jList1;
    private javax.swing.JTextField jOther1;
    private javax.swing.JPasswordField jPass1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel totalVoters;
    private javax.swing.JButton vote;
    // End of variables declaration//GEN-END:variables
}
