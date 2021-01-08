
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrator
 */
public class Main {
    public static void main(String[] args) {
        try {
            DatagramSocket ds=new DatagramSocket();
            byte[] a1=new byte[1024];
            byte[] a2=new byte[1024];
            byte[] a3=new byte[1024];
            
            //Gui ma sinh vien
            a1="B17DCCN264".getBytes();
            DatagramPacket dp1;
            dp1 = new DatagramPacket(a1,a1.length,InetAddress.getByName("localhost"),1077);
            ds.send(dp1);
            
            
            //Nhan lai 1 Student
            DatagramPacket dp2=new DatagramPacket(a2, a2.length);
            ds.receive(dp2);
            ByteArrayInputStream bais=new ByteArrayInputStream(dp2.getData());
            ObjectInputStream in=new ObjectInputStream(bais);
            Student s=(Student) in.readObject();
            System.out.println(s.toString());
            
            //Chuan hoa ten tu Student nhan duoc
            String name=s.getName().trim();
            String[] words=name.split("\\s+");
            String newName="";
            for(String s1:words){
                s1=s1.toLowerCase();
                String temp=s1.substring(0, 1).toUpperCase()+s1.substring(1, s1.length());
                newName+=temp+" ";
                
            }
            
            s.setName(newName.trim());
            s.setId("1");
            System.out.println(s.toString());
            
            
            //Gui Student sau khi chuan hoa ten
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            ObjectOutputStream out=new ObjectOutputStream(baos);
            out.writeObject(s);
            out.flush();
            a3=baos.toByteArray();
            DatagramPacket dp3=new DatagramPacket(a3, a3.length,InetAddress.getByName("localhost"),1077);
            ds.send(dp3);
            
            
        } catch (SocketException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
