/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import client.JavaClient;
import static data.CThread.sentence;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.net.DatagramPacket;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

/**
 *
 * @author Administrator
 */
public class Vidshow extends Thread {

    public static JFrame frame = new JFrame();
    public static JPanel panel1 = new JPanel();
    public static JPanel panel2 = new JPanel();

    public static JLabel jl;

    public static JTextArea ta, tb;
    public static JButton chat;

    byte[] receiveData = new byte[699999];

    DatagramPacket dp = new DatagramPacket(receiveData, receiveData.length);

  
    DataOutputStream outToServer;

    public Vidshow(DataOutputStream outToServer) throws Exception {
        this.outToServer = outToServer;
        frame.setTitle("Client");
        frame.setSize(1020, 540);
        frame.setBackground(Color.gray);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setAlwaysOnTop(true);
        ta = new JTextArea();
        JScrollPane jpane = new JScrollPane();
        tb = new JTextArea();

        chat = new JButton();

        frame.setLayout(null);

        panel1.setBounds(0, 0, 800, 500);
        panel1.setBackground(Color.gray);

        panel2.setBounds(800, 0, 200, 500);
        panel2.setBackground(Color.gray);

        frame.add(panel1);
        frame.add(panel2);
        
        jl = new JLabel();
        jl.setBounds(0, 0, 800, 500);

        panel1.add(jl);

        panel2.setLayout(null);
        ta.setBounds(0, 0, 200, 300);
        ta.setBorder(BorderFactory.createLineBorder(Color.black));
        ta.setFont(new Font("ARIAL", 0, 14));
        ta.setText("Begins\n");
        ta.setBackground(Color.white);
        jpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jpane.add(ta);
        jpane.setViewportView(ta);
        
        ta.setEnabled(false);
        ta.setDisabledTextColor(Color.BLUE);
        tb.setBounds(0, 305, 200, 150);
        tb.setBorder(BorderFactory.createLineBorder(Color.black));

        chat.setBounds(0, 455, 200, 50);
        chat.setText("Chat");
        chat.setForeground(Color.black);
        chat.setFont(new Font("Arial", 0, 20));
        panel2.add(jpane);
        panel2.add(ta);
        panel2.add(tb);
        panel2.add(chat);

        frame.setVisible(true);

        chat.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                sentence = Vidshow.tb.getText();
                if (sentence.length() > 0) {
                    ta.append("From myself: " + sentence + "\n");
                    try {
                        outToServer.writeUTF(sentence);
                    } catch (Exception E) {
                    }
                    Vidshow.tb.setText(null);
                }
            }
        });

    }

    @Override
    public void run() {

        try {

            do {
                
                
                JavaClient.ds.receive(dp);
                
                ByteArrayInputStream in = new ByteArrayInputStream(receiveData);
                
                BufferedImage bf = ImageIO.read(in);

                if (bf != null) {
               
//                    BufferedImage outputImage = new BufferedImage(panel1.getWidth(),
//                            panel1.getHeight(), bf.getType());
//                    Graphics2D g2d = outputImage.createGraphics();
//                    g2d.drawImage(bf, 0, 0, panel1.getWidth(), panel1.getHeight(), null);
//                    g2d.dispose();
                    ImageIcon imc = new ImageIcon(bf);
                    jl.setIcon(imc);
                    
                    Thread.sleep(15);
                }
                frame.revalidate();
                frame.repaint();
                

            } while (true);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
