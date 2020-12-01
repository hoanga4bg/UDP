/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

//import com.github.sarxos.webcam.Webcam;
//import com.github.sarxos.webcam.WebcamPanel;
//import com.github.sarxos.webcam.WebcamResolution;
import com.github.sarxos.webcam.WebcamPanel;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Administrator
 */
public class Camera extends Thread{
   
 private WebcamPanel camPanel;
    JFrame window;
    
    public Camera(WebcamPanel camPanel){   
        
       this.camPanel=camPanel;
       window=new JFrame("Camera");
     
        window.setResizable(true);
       window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setSize(400, 300);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
   
    
    
    public void exit(){
        window.dispose();
    }
    @Override
    public void run(){
        window.add(camPanel);
        window.repaint();
          
       
    }
}
