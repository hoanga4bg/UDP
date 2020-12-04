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
        start();
    }

    public void run() {
        String mysent;
        try {
            while (true) {
                    mysent = inFromServer.readUTF();
                    
                    Vidshow.ta.append(mysent+"\n");
                    Vidshow.ta.setCaretPosition(Vidshow.ta.getDocument().getLength());
                    Vidshow.panel1.revalidate();
                    Vidshow.panel1.repaint();
                    Vidshow.panel1.revalidate();
                    Vidshow.panel1.repaint();
                    sentence = null;
    
            }
        } catch (Exception e) {
        }
    }
}
