/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import data.Canvas_Demo;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.io.File;
import server.JavaServer;

/**
 *
 * @author Administrator
 */
public class Vidthread extends Thread{
        int clientno;

	DatagramSocket dataSoc;

	Robot rb = new Robot();
	
	byte[] outbuff = new byte[699999];

	BufferedImage mybuf;
	ImageIcon img;
	Rectangle rc;
	
        
	public Vidthread(DatagramSocket ds) throws Exception {
		dataSoc = ds;
	}

  
	public void run() {
		while (true) {
			try {
				int num = JavaServer.i;
                                Rectangle capture =  
                                    new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()); 
                                
				mybuf = rb.createScreenCapture(capture); 
                                
                                
                                BufferedImage outputImage = new BufferedImage(840,
                                        420, mybuf.getType());
                                Graphics2D g2d = outputImage.createGraphics();
                                g2d.drawImage(mybuf, 0, 0, 840,420, null);
                                g2d.dispose();
                                
				img = new ImageIcon(mybuf);				
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
	
				ImageIO.write(outputImage, "jpg", baos);
				
				outbuff = baos.toByteArray();

				for (int j = 0; j < num; j++) {
					DatagramPacket dp = new DatagramPacket(outbuff, outbuff.length, JavaServer.inet[j],
							JavaServer.port[j]);
					
					dataSoc.send(dp);
					baos.flush();
				}
				Thread.sleep(5);

				
			} catch (Exception e) {

			}
		}

	}
}
