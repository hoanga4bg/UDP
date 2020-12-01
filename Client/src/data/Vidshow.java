/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import client.JavaClient;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.net.DatagramPacket;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
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
    public static JFrame jf = new JFrame();
    public static JPanel jp = new JPanel(new GridLayout(2,1));
    public static JPanel half = new JPanel(new GridLayout(3,1));
    JLabel jl = new JLabel();
    public static JTextArea ta,tb;
    
    byte[] receiveData = new byte[699999];
    
    DatagramPacket dp = new DatagramPacket(receiveData, receiveData.length);
    BufferedImage bf;
    ImageIcon imc;
    
    
    public Vidshow() throws Exception {
       
        jf.setSize(840,840);
        jf.setTitle("Client Show");
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setAlwaysOnTop(true);
        jf.setLayout(new BorderLayout());
        jf.setVisible(true);
        jl.setSize(jp.getWidth(),jp.getHeight());
        jp.add(jl);
        jp.add(half);
        jf.add(jp);
        
        
        JScrollPane jpane = new JScrollPane();
    
        ta = new JTextArea();
        tb = new JTextArea();
        ta.setBackground(Color.WHITE);
                ta.setForeground(Color.BLUE);
                ta.setEnabled(false);
                ta.setDisabledTextColor(Color.blue);
        jpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jpane.add(ta);
        jpane.setViewportView(ta);
        half.add(jpane);
        half.add(tb);
        ta.setText("Begins\n");
        
        
    }

    @Override
    public void run() {

        try {
         
            do {
          
                JavaClient.ds.receive(dp);
                
                ByteArrayInputStream in = new ByteArrayInputStream(receiveData);
                
                bf = ImageIO.read(in);

                if (bf != null) {
                    
                    imc = new ImageIcon(bf);
                   
                    jl.setIcon(imc);
                    
                    Thread.sleep(5);
                }
                jf.revalidate();
                jf.repaint();
                

            } while (true);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
