import java.awt.Color;
import java.awt.Font;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class modulação_e_demodulação {
	
	private static String nome;
		
	public modulação_e_demodulação(String sample_path, double frequencia) throws IOException, UnsupportedAudioFileException {
		
		 System.out.println("Classe freq: "+frequencia);
		
		 File music_input = new File(sample_path);
		 AudioInputStream song_input = AudioSystem.getAudioInputStream(music_input);
		 AudioFormat format_song_input = song_input.getFormat();
		 
         System.out.println("Formato do áudio 1: " + format_song_input);
         
         //Creating a array with all bytes of the audio
         byte[] data1 = song_input.readAllBytes();
         byte[] result = new byte[data1.length];
         System.out.println(data1.length);
         
         for (int i = 0; i < data1.length; i++) {
    		 double carrier_sample = Math.cos(frequencia * (i / 44100.0) * Math.PI * 2);
     			
             result[i] = (byte) ((data1[i] * carrier_sample));
		 }



         // Write the result to a file
         ByteArrayInputStream bais = new ByteArrayInputStream(result);
         AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
         AudioInputStream resultStream = new AudioInputStream(bais, song_input.getFormat(), result.length);
         
         //get the music name with user
         musica_nome();
         //creating a file 
         File music_output = new File(nome);
		 AudioSystem.write(resultStream, fileType, music_output);
		 System.out.println(music_output.getAbsolutePath());
		 
		 //GUI 
		 int response = JOptionPane.showConfirmDialog(null,"Deseja escutar o áudio modulado?","",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE);
			
		 if(response == JOptionPane.YES_OPTION) 
		 	{new SoundPlayer(music_output.getAbsolutePath());}
		 else 
			{
			 ImageIcon icon = new ImageIcon("res/demod/link-spin.gif");
		        JLabel label = new JLabel("Obrigado por usar nosso Software.", icon, JLabel.CENTER);
		        label.setForeground(Color.DARK_GRAY);
		        label.setFont(new Font("Helvetica",Font.BOLD, 15));

		        JPanel panel = new JPanel();
		        panel.add(label);

		        JOptionPane.showMessageDialog(
		                null,  
		                panel, 
		                "Thanks",
		                JOptionPane.PLAIN_MESSAGE); 
			 }

	}

	private void musica_nome() {
		
		JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos WAV (*.wav)", "wav");
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showSaveDialog(null);
        
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();
            
            //senao botou .wav
            if (!filePath.toLowerCase().endsWith(".wav")) {
                selectedFile = new File(filePath + ".wav");
            }
            
            nome = selectedFile.getAbsolutePath();
            
        }
	}
          
}
