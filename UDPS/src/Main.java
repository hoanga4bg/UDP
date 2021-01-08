
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
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
            DatagramSocket ds=new DatagramSocket(1077);
            byte[] a1=new byte[1024];
            byte[] a2=new byte[1024];
            byte[] a3=new byte[1024];
            
            
            //Nhan ma sinh vien
            DatagramPacket dp1=new DatagramPacket(a1, a1.length);
            ds.receive(dp1);
            System.out.println(new String(dp1.getData()));
            
            String code=new String(dp1.getData()).trim();
            
            //Gui ve 1 Student cho client
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            ObjectOutputStream out=new ObjectOutputStream(baos);
            Student s=new Student("", code, "ABC xyz   DEf");
            out.writeObject(s);
            out.flush();
            a2=baos.toByteArray();
            
            DatagramPacket dp2=new DatagramPacket(a2, a2.length,dp1.getAddress(), dp1.getPort());
            ds.send(dp2);
            System.out.println("send..");
            
            
            //Nhan 1 Student da chuan hoa tu client
            DatagramPacket dp3=new DatagramPacket(a3, a3.length);
            ds.receive(dp3);
            ByteArrayInputStream bais=new ByteArrayInputStream(dp3.getData());
            ObjectInputStream in=new ObjectInputStream(bais);
            Student s1=(Student) in.readObject();
            System.out.println(s1.toString());
            
        } catch (SocketException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
