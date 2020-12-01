/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import client.JavaClient;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

/**
 *
 * @author Administrator
 */
public class SoundThread extends Thread {
    private static final String IP_TO_STREAM_TO = "192.168.0.102";
    private static final int PORT_TO_STREAM_TO = 1234;
    private static int cnt;
    /**
     * Creates a new instance of RadioReceiver
     */
    @Override
    public void run() {
     
        while (cnt!=-1) {
            byte b[] = new byte[10000];
            b = receiveThruUDP();
            toSpeaker(b);
  
        }
    }
    
  
    public static byte[] receiveThruUDP() {
        try {
            
            byte soundpacket[] = new byte[10000];

            DatagramPacket datagram = new DatagramPacket(soundpacket, soundpacket.length);
            JavaClient.dsVoice.receive(datagram);
            JavaClient.dsVoice.close();
            return datagram.getData(); // soundpacket ;
        } catch (Exception e) {
            System.out.println(" Unable to send soundpacket using UDP ");
            return null;
        }
      

    }

    public static void toSpeaker(byte soundbytes[]) {

        try {
            InputStream byteInputStream = new ByteArrayInputStream(soundbytes);
            AudioFormat adFormat = getAudioFormat();
            AudioInputStream InputStream = new AudioInputStream(byteInputStream, adFormat, soundbytes.length / adFormat.getFrameSize());
            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, getAudioFormat());
            SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
            sourceDataLine.open(getAudioFormat());
            sourceDataLine.start();
            cnt = InputStream.read(soundbytes, 0, soundbytes.length);
            if(cnt>0){
                sourceDataLine.write(soundbytes, 0, cnt);        
            }    
            
//            sourceDataLine.drain();
//            sourceDataLine.close();
        } catch (Exception e) {
            System.out.println("not working in speakers ");
        }

    }

    public static AudioFormat getAudioFormat() {
        float sampleRate = 16000.0F;
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
}
