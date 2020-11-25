/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Udp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class UdpServer {
    public static void main(String[] args) {
        try {
            
            
            byte[] sendBuff=new byte[1024];
            byte[] rcBuff=new byte[1024];
            DatagramSocket ds=new DatagramSocket(1107);
            
            
            //Nhan chuoi ma sinh vien
            DatagramPacket receivePk;
            receivePk=new DatagramPacket(rcBuff, rcBuff.length);
            ds.receive(receivePk);
            String data=new String(receivePk.getData());
            System.out.println(data);
            
            InetAddress inet=receivePk.getAddress();
            
            
            //gui object
            Student s=new Student("Hoang",3.5f,"");
            ByteArrayOutputStream bos=new ByteArrayOutputStream();
            ObjectOutputStream out=null;
            out=new ObjectOutputStream(bos);
            out.writeObject(s);
            out.flush();
            sendBuff=bos.toByteArray();
            DatagramPacket sendPk;
            sendPk = new DatagramPacket(sendBuff,sendBuff.length,inet,receivePk.getPort());
            ds.send(sendPk);
            
          
        } catch (UnknownHostException ex) {
            Logger.getLogger(UdpServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SocketException ex) {
            Logger.getLogger(UdpServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UdpServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
