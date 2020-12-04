/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;


import data.CThread;
import data.SoundThread;
import data.Vidshow;
import java.io.BufferedReader;
import java.io.DataInputStream;

import java.io.DataOutputStream;

import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author imran
 */
public class JavaClient {
    public static DatagramSocket ds;
    public static DatagramSocket dsVoice;
    public static void main(String[] args) throws Exception {
              ds = new DatagramSocket();
           dsVoice = new DatagramSocket();
       
        byte[] init = new byte[1024];
        init = "givedata".getBytes();
        
//        InetAddress addr = InetAddress.getByName("192.168.0.104");
         InetAddress addr  =InetAddress.getLocalHost();
        DatagramPacket dp = new DatagramPacket(init,init.length,addr,4321);    
        ds.send(dp);
        DatagramPacket rcv = new DatagramPacket(init, init.length);
        ds.receive(rcv);
        System.out.println("Ok");
        
        
        byte[] buff = new byte[1024];
        buff = "givedata".getBytes();
        System.out.println("Ok");
        DatagramPacket dpVoice = new DatagramPacket(buff,buff.length,addr,5678);
        dsVoice.send(dpVoice);
          System.out.println("Ok");
        DatagramPacket rcvVoice = new DatagramPacket(buff,buff.length);
        dsVoice.receive(rcvVoice);
        
       
       
        
        SoundThread s=new SoundThread();
        s.start();
        String modifiedSentence;

//        InetAddress inetAddress =  InetAddress.getByName("192.168.0.104");
        InetAddress inetAddress =  InetAddress.getLocalHost();
        System.out.println(inetAddress);

        Socket clientSocket = new Socket(inetAddress, 6782);
        DataOutputStream outToServer =
                new DataOutputStream(clientSocket.getOutputStream());
        
        DataInputStream inFromServer=new DataInputStream(clientSocket.getInputStream());
        outToServer.writeUTF("Đã tham gia!");
        
        
        Vidshow vd = new Vidshow(outToServer);
        vd.start();
        CThread read = new CThread(inFromServer, outToServer);
        read.join();
        clientSocket.close();
    }
}


