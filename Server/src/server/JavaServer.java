package server;



import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import javax.imageio.ImageIO;

import javax.swing.*;

//import com.sun.jna.NativeLibrary;
import data.Canvas_Demo;
import data.SThread;

import data.Vidthread;
import java.io.DataInputStream;




//import uk.co.caprica.vlcj.runtime.windows.WindowsRuntimeUtil;


/**
 *
 * 
 */
public class JavaServer {

	
	public static InetAddress[] inet;
	public static int[] port;
   
	public static int[] portVoice;
	public static int i;
	static int count = 0;
	public static DataInputStream[] inFromClient;
	public static DataOutputStream[] outToClient;
	public static DatagramSocket imgServer;
        public static DatagramSocket voiceServer;
	public static void main(String[] args) throws Exception
	{
		JavaServer jv = new JavaServer();
	}

	
	
	public JavaServer() throws Exception {
		
		

		JavaServer.inet = new InetAddress[30];
		port = new int[30];
                portVoice=new int[30];
		ServerSocket welcomeSocket = new ServerSocket(6782);
		Socket connectionSocket[] = new Socket[30];
		inFromClient = new DataInputStream[30];
		outToClient = new DataOutputStream[30];
		imgServer = new DatagramSocket(4321);
                voiceServer=new DatagramSocket(5678);
		byte[] buf = new byte[62000];
	
		DatagramPacket dpImage = new DatagramPacket(buf, buf.length);
                 DatagramPacket dpVoice = new DatagramPacket(buf, buf.length);
		Canvas_Demo canv = new Canvas_Demo();
		i = 0;	
		SThread[] st = new SThread[30];
		while (true) {
                    
                        //Video

			imgServer.receive(dpImage);
			buf = "starts".getBytes();
                        //Lấy địa chỉ client từ gói tin nhận được
			inet[i] = dpImage.getAddress();
			port[i] = dpImage.getPort();
                        System.out.println(port[i]);
			DatagramPacket dsend = new DatagramPacket(buf, buf.length, inet[i], port[i]);
			imgServer.send(dsend);
                        
                        
                        voiceServer.receive(dpVoice);
                        portVoice[i]=dpVoice.getPort();
                   
                        DatagramPacket dsend1 = new DatagramPacket(buf, buf.length, inet[i], portVoice[i]);
                        imgServer.send(dsend1);
			Vidthread sendvid = new Vidthread(imgServer);

                        
                        
                        //Chat
			connectionSocket[i] = welcomeSocket.accept();

			inFromClient[i] = new DataInputStream(connectionSocket[i].getInputStream());
			outToClient[i] = new DataOutputStream(connectionSocket[i].getOutputStream());
			outToClient[i].writeUTF("Connected: from Server");

			
			st[i] = new SThread(i);
			st[i].start();
			
			
//                        Sentencefromserver sen = new Sentencefromserver();
//                        sen.start();
			sendvid.start();

			i++;

			if (i == 30) {
				break;
			}
		}
	}
}




