import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SoundPlayer implements Runnable {
	private static String musicPathName;
	private static String musicName;
	private static Clip clip;
	private static long pause_moment;
	private static boolean pause_actived;
	
	public SoundPlayer(String MusicPath, String MusicName) {
		musicPathName = MusicPath;
		musicName = MusicName;
	}
	
	@Override
	public void run() {

		/* Implementar modelos de botoes diferentes, aprender JComboBox
		 * saber como implementar o audio, rodar todos os botoes*/
		
		
		/* Image icon to the Frame*/
		ImageIcon image1 = new ImageIcon(getClass().getClassLoader().getResource("mod/SoundPlayer Icon.png")); // uma classe, que usa set and get
		
		/* Declarations to components of the GUI*/
		JFrame frame = new JFrame();
		JPanel panel =  new JPanel();
		JButton play = new JButton("Play");
		JButton pause = new JButton("Pause");
		JButton loop = new JButton("Loop");
		JButton stop = new JButton("Stop");
		JComboBox<String> playlist = new JComboBox<String>();
		JLabel text = new JLabel("Choose to play the selected sound file.");			
		
		//add music from De_ModulatorExport.java
		playlist.addItem(musicName);
		
		//Play List - JComboBox
		playlist.setBounds(10,20,95,25);
		playlist.setToolTipText("Choose a file to play");
		
		//Play Button
		play.setBounds(110,20,70,25);
		play.setHorizontalAlignment(JButton.CENTER);
		play.setToolTipText("Use to play the stop.");
		play.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Play(musicPathName);		
			}
		});
		
		//Pause button
		
		pause.setBounds(185,20,70,25);
		pause.setHorizontalAlignment(JButton.CENTER);
		pause.setToolTipText("Use to pause the song.");
		pause.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				Pause();			
			}
		});
		
		//Loop Button
		loop.setBounds(260,20,70,25);
		loop.setToolTipText("Use to restart the song");
		loop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Loop();
			}
		});
		
		//Stop Button
		stop.setBounds(335,20,70,25);
		stop.setToolTipText("Use to stop the song");
		stop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Stop();
			}
		});
		
		//Text messenger - JLabel
		text.setFont(new Font("Arial",Font.ITALIC,15));
		text.setHorizontalAlignment(JLabel.CENTER);
		text.setVerticalAlignment(JLabel.BOTTOM);
	
		//Panel - JPanel
		
		panel.add(text);
		panel.setSize(430,130);
		panel.setBackground(new Color(0xdb2a68));
		
		//SoundPlayer Window - JFrame
		frame.setTitle("SoundPlayer Java Edition");
		frame.setSize(430,130);
		frame.setIconImage(image1.getImage());
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	if(clip.isRunning()) {
					clip.stop();
				}
            }
        });
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.add(playlist);
		frame.add(pause);
		frame.add(play);
		frame.add(loop);
		frame.add(stop);
		frame.add(text);
		frame.setVisible(true);
	}
	
    private static void Play(String caminhoMusica) {
    	if(clip != null && clip.isRunning()) {
	       System.out.println("***Alerta de delay previnido***");
    	} else {
    		 try {
 	        	/* Arquivo para musica*/
 	            File Music = new File(caminhoMusica);
 	            /* Music para song, tranforma um file em uma song*/
 	            AudioInputStream song = AudioSystem.getAudioInputStream(Music);
 	
 	            clip = AudioSystem.getClip();
 	            clip.open(song);
 	            
 	            if(pause_actived == false) {
 	            	clip.start();
 	            } else {
 	            	clip.setMicrosecondPosition(pause_moment);
 	            	clip.start();
 	            }
 	            
 	        }
 	        	catch (Exception e) {
 	        		e.printStackTrace();
     	}
    	}
        
    }
    
    private static void Pause() {
    	if(clip != null && clip.isRunning()) {
    		pause_moment = clip.getMicrosecondPosition();
    		pause_actived = true;
    		clip.stop();
    	}
    }
    
    private static void Loop() {
    	if(clip != null && clip.isRunning()) {
    		clip.setMicrosecondPosition(0);
    		pause_moment = 0;
    		pause_actived = false;
    	}
    }
    
    private static void Stop() {
    	/* se a classe clip nao for vazia e o clipe estiver rodando  */
    	if(clip != null && clip.isRunning()) {
    		clip.stop();
    		clip.setFramePosition(0);
    		pause_moment = 0;
    		pause_actived = false;
    	}
    }
     
}
