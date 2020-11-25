/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Udp;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
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
public class UdpClient {
    public static void main(String[] args) {
        try {
            byte[] sendBuff=new byte[1024];
            byte[] rcBuff=new byte[1024];
            DatagramSocket ds=new DatagramSocket();
            
            
            //gui chuoi msv
            sendBuff="B17DCCN264".getBytes();
            DatagramPacket sendPk;      
            sendPk = new DatagramPacket(sendBuff,sendBuff.length,InetAddress.getByName("localhost"),1107);
            ds.send(sendPk);
            
            
            //nhan object
            DatagramPacket receivePk;
            receivePk=new DatagramPacket(rcBuff, rcBuff.length);
            ds.receive(receivePk);
            ByteArrayInputStream bis=new ByteArrayInputStream(rcBuff);
            ObjectInput in=new ObjectInputStream(bis);
            Student s=(Student) in.readObject();
            
            
            System.out.println(s.toString());
           
        } catch (SocketException ex) {
            Logger.getLogger(UdpClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(UdpClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UdpClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UdpClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
