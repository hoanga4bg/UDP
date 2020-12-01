/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.IOException;
import java.lang.annotation.Target;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.Port;
import javax.sound.sampled.TargetDataLine;
import server.JavaServer;

/**
 *
 * @author Administrator
 */
public class SoundThread extends Thread{
     private static final String IP_TO_STREAM_TO = "localhost";
     private static final int PORT_TO_STREAM_TO = 1234;
     public static TargetDataLine targetDataLine;
     private static InetAddress inet;
     private boolean check=true;
     static int i=0;
     public SoundThread(TargetDataLine targetDataLine) {
       this.targetDataLine=targetDataLine;
     }
     
     @Override
     public void run(){
        byte tempBuffer[] =new byte[10000];
        while (true) {          
            int cnt=targetDataLine.read(tempBuffer, 0, tempBuffer.length);
            if(cnt>0){
                sendThruUDP(tempBuffer);
            }
        }            
     }
    public static AudioFormat getAudioFormat() {
        float sampleRate = 22050.0F;
        //8000,11025,16000,22050,44100
        int sampleSizeInBits = 16;
        //8,16
        int channels = 1;
        //1,2
        boolean signed = true;
        //true,false
        boolean bigEndian = false;
        //true,false
        return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
    }

    public static void sendThruUDP(byte soundpacket[]) {
        try {
            
            DatagramSocket sock;
            try {
                sock = new DatagramSocket();
                for(int i=0;i<JavaServer.i;i++){
                    DatagramPacket dp;
                    dp = new DatagramPacket(soundpacket, soundpacket.length,JavaServer.inet[i], JavaServer.portVoice[i]);
                    System.out.println(JavaServer.inet);
                    sock.send(dp);
                }
                System.out.println("Gui thanh cong"+(i++));
                sock.close();
            } catch (SocketException ex) {
                Logger.getLogger(SoundThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            
        } catch (Exception e) {
            System.out.println("Ko gui dc");
        }

    }
}
