/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

//import com.github.sarxos.webcam.Webcam;
//import com.github.sarxos.webcam.WebcamPanel;
//import com.github.sarxos.webcam.WebcamResolution;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import static data.SoundThread.getAudioFormat;
import static data.SoundThread.sendThruUDP;
import static data.SoundThread.targetDataLine;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.Port;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

//import uk.co.caprica.vlcj.player.MediaPlayerFactory;
//import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
//import uk.co.caprica.vlcj.player.embedded.videosurface.CanvasVideoSurface;

/**
 *
 * @author Administrator
 */
public class Canvas_Demo {
    
	// Create a media player factory
//	private MediaPlayerFactory mediaPlayerFactory;

	// Create a new media player instance for the run-time platform
//	private EmbeddedMediaPlayer mediaPlayer;

	public static JPanel panel;
	public static JPanel myjp;
	private Canvas canvas;
	public static JFrame frame;
	public static JTextArea ta;
	public static JTextArea txinp;
	public static int xpos = 0, ypos = 0;
        public static JLabel jl;
      
	String url = "";
        public TargetDataLine targetDataLine;
        public SoundThread s;
        boolean check=true;
        
        Webcam webcam;
        WebcamPanel camPanel;
        Camera c;
	// Constructor
	public Canvas_Demo() throws LineUnavailableException {

	
		panel = new JPanel();
		panel.setLayout(new BorderLayout());

		JPanel mypanel = new JPanel();
		mypanel.setLayout(new GridLayout(2, 1));

		
		canvas = new Canvas();
		canvas.setBackground(Color.BLACK);
		
		panel.add(canvas, BorderLayout.CENTER);
		
	

	
		frame = new JFrame("Sever Show");
	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(200, 0);
		frame.setSize(640, 640);
		frame.setAlwaysOnTop(true);

		
		mypanel.add(panel);
		frame.add(mypanel);
		frame.setVisible(true);
		xpos = frame.getX();
		ypos = frame.getY();



		myjp = new JPanel(new GridLayout(4, 1));
                JPanel nut = new JPanel(new GridLayout(1,2));
                
                    // start set nút Start Voice
                        
		Button bn = new Button("Start Voice");
                bn.setBackground(Color.DARK_GRAY);
                bn.setForeground(Color.WHITE);
                bn.setFont(new Font("Arial", 26, 26));
                nut.add(bn);
                
  // Start set nút turn on Camera              
                Button cam=new Button("Turn on Camera");
                cam.setBackground(Color.DARK_GRAY);
                cam.setForeground(Color.WHITE);
                cam.setFont(new Font("Arial", 26, 26));
                nut.add(cam);
  //thêm nut vào myjp              
                myjp.add(nut);
                
                 //Start set chức năng chat             
		
             //set khung chat nhân thông tin
                 ta = new JTextArea();
                ta.setBackground(Color.WHITE);
                ta.setForeground(Color.BLUE);
                ta.setEnabled(false);
                ta.setDisabledTextColor(Color.blue);
                 //set thanh cuộn            
		JScrollPane jpane = new JScrollPane();
		jpane.setSize(400, 200);
		// ta.setEditable(false);
		jpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jpane.add(ta);
		jpane.setViewportView(ta);
		myjp.add(jpane);
                txinp = new JTextArea();
		myjp.add(txinp);
                
                //set nút chat 
                Button sender = new Button("Chat");
                sender.setBackground(Color.DARK_GRAY);
                sender.setForeground(Color.WHITE);
                sender.setFont(new Font("Arial", 26, 26));
		myjp.add(sender);
		ta.setText("Server Running...\n");

		ta.setCaretPosition(ta.getDocument().getLength());

		mypanel.add(myjp);
		mypanel.revalidate();
		mypanel.repaint();
                
                
                Mixer.Info minfo[] = AudioSystem.getMixerInfo();
                DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, SoundThread.getAudioFormat());
                targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
                
                SoundThread s = new SoundThread(targetDataLine);
		
                
		bn.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (bn.getLabel().equals("Start Voice")) {
                        bn.setLabel("Stop Voice");

                        if (AudioSystem.isLineSupported(Port.Info.MICROPHONE)) {
                            try {
                                if (check == true) {
                                    System.out.println("opened");
                                    s.targetDataLine.open(getAudioFormat());
                                    s.targetDataLine.start();
                                    s.start();
                                    check = false;
                                } else {
                                    System.out.println("remused");
                                    s.targetDataLine.open(getAudioFormat());
                                    s.targetDataLine.start();
                                    s.resume();
                                }
                            } catch (Exception ex) {

                            }
                        }

                    } else {
                        bn.setLabel("Start Voice");
                        s.targetDataLine.close();
                        System.out.println("closed");
                        s.suspend();
                    }
                }
            });
               
                
                
                cam.addActionListener(new ActionListener() {
                
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(cam.getLabel().equals("Turn on Camera")){
                        cam.setLabel("Turn off Camera");
                        
                        webcam=Webcam.getDefault();
                        webcam.setViewSize(WebcamResolution.VGA.getSize());
                        camPanel=new WebcamPanel(webcam);
                        camPanel.setFPSDisplayed(true);
                        camPanel.setDisplayDebugInfo(true);
                        camPanel.setImageSizeDisplayed(true);
                        camPanel.setMirrored(true);
                        camPanel.setSize(400, 300);
                        c=new Camera(camPanel);
                        if(!webcam.isOpen()){
                            webcam.open();
                        }
                        if(check==true){
                            c.start();
                            check=false;
                        }    
                        else{
                            c.resume();
                            c.window.add(camPanel);
                            c.window.repaint();
                        }
                           
                    }
                    else{
                        cam.setLabel("Turn on Camera");
                        c.exit();
                        c.suspend();
                        webcam.close();
                    }
                }   
                });
                
                
                
                
                
                
                
                
                
                
                
		sender.addActionListener(new ActionListener() {

                    
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Sentencefromserver.sendingSentence = txinp.getText();
				
				Canvas_Demo.ta.append("From Myself: " + Sentencefromserver.sendingSentence + "\n");
				Canvas_Demo.myjp.revalidate();
				Canvas_Demo.myjp.repaint();
                                txinp.setText(null);
			}
		});

	}
}
