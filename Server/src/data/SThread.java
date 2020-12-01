/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import server.JavaServer;

/**
 *
 * @author Administrator
 */
public class SThread extends Thread {

	public static String clientSentence;
	int srcid;
        DataInputStream inFromClient;
	DataOutputStream outToClient[] = JavaServer.outToClient;

	public SThread(int a) {
		srcid = a;
                inFromClient = JavaServer.inFromClient[srcid];
	}

	public void run() {
		while (true) {
			try {
                        
			clientSentence = inFromClient.readUTF();
                        
			Canvas_Demo.ta.append("From Client " + srcid + ": " + clientSentence + "\n");
				
			for(int i=0; i<JavaServer.i; i++){
                    
                            if(i!=srcid)
                                JavaServer.outToClient[i].writeUTF("Client "+srcid+": "+clientSentence );	
                        }
				
                            Canvas_Demo.myjp.revalidate();
                            Canvas_Demo.myjp.repaint();

			} catch (Exception e) {
                            
			}

		}
	}
}
