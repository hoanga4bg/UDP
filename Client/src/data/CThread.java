/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class CThread extends Thread {

    DataInputStream inFromServer;
    Button sender = new Button("Chat");
    DataOutputStream outToServer;
    public static String sentence;
  

    public CThread(DataInputStream in, DataOutputStream out) {
        inFromServer = in;
        outToServer = out;
       
        
            Vidshow.half.add(sender);
            sender.setBackground(Color.DARK_GRAY);
                sender.setForeground(Color.WHITE);
                sender.setFont(new Font("Arial", 26, 26));
            sender.addActionListener(new ActionListener() {
                
            @Override
            public void actionPerformed(ActionEvent e) {
               
                sentence = Vidshow.tb.getText();
                if(sentence.length()>0){
                    Vidshow.ta.append("From myself: "+sentence+"\n");
                    try{
                        outToServer.writeUTF(sentence );
                    }
                    catch(Exception E){                
                    }
                    Vidshow.tb.setText(null);
                }
            }
        });
        
        start();
    }

    public void run() {
        String mysent;
        try {
            while (true) {
                
                    mysent = inFromServer.readUTF();
                    
                    Vidshow.ta.append(mysent+"\n");
                    Vidshow.ta.setCaretPosition(Vidshow.ta.getDocument().getLength());
                    Vidshow.half.revalidate();
                    Vidshow.half.repaint();
                    Vidshow.jp.revalidate();
                    Vidshow.jp.repaint();
                    
                    
                    
                    System.out.println("From : " + sentence);
                    sentence = null;
                    
                
            }
        } catch (Exception e) {
        }
    }
}
