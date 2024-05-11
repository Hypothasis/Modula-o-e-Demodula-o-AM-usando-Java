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

public class De_ModulatorExport implements Runnable  {
	
	private static String MusicAbsolutePathName;
	private static String MusicOnlyName;
	private static String SamplePath;
	private static double Frequency;
		
	public De_ModulatorExport(String AudioPath, double FrequencyFromMain) {
		 SamplePath = AudioPath; 
		 Frequency = FrequencyFromMain;	 
	}

	@Override
	public void run() {
		try {
			Export();
			SoundPlayerAsk();
		} catch (UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
		}
	}

	private void Export() throws UnsupportedAudioFileException, IOException {
		File SampleFile = new File(SamplePath);
	    AudioInputStream SongInput = AudioSystem.getAudioInputStream(SampleFile);
	    AudioFormat AudioFormatSong = SongInput.getFormat();
	    
	    //Para poder modular temos que transformar em bytes nossas musicas, que serao Arrays
        byte[] DataSongInput = SongInput.readAllBytes();
        byte[] DataSongOutput = new byte[DataSongInput.length];
        
        for (int i = 0; i < DataSongInput.length; i++) {
   		 double SampleCarrier = Math.cos(Frequency * (i / 44100.0) * Math.PI * 2);
    			
            DataSongOutput[i] = (byte) ((DataSongInput[i] * SampleCarrier));
		 }
		
        //Criando um arquivo wav
        ByteArrayInputStream ByteArray = new ByteArrayInputStream(DataSongOutput);
        AudioFileFormat.Type FileType = AudioFileFormat.Type.WAVE;
        AudioInputStream OutputStream = new AudioInputStream(ByteArray, AudioFormatSong, DataSongOutput.length);
        
        //Adicionando nome Atraves do GUI
        AddMusicName();
        
        //Criando Arquivo .wav
        File MusicOutput = new File(MusicAbsolutePathName);
		AudioSystem.write(OutputStream, FileType, MusicOutput);
	}

	private void AddMusicName() {

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
			
			MusicAbsolutePathName = selectedFile.getAbsolutePath();
			MusicOnlyName = selectedFile.getName();
		}
	}
	
	private void SoundPlayerAsk() {
		//GUI
		int response = JOptionPane.showConfirmDialog(null,"Deseja escutar o áudio modulado?","",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE);
		if(response == JOptionPane.YES_OPTION) {
			Thread SoundPlayerThread = new Thread(new SoundPlayer(MusicAbsolutePathName, MusicOnlyName));
			SoundPlayerThread.start();
		} 
		else {
		ImageIcon Image = new ImageIcon(getClass().getClassLoader().getResource("mod/link-spin.gif"));
		JLabel Label = new JLabel("Obrigado por usar nosso Software.", Image, JLabel.CENTER);
        Label.setForeground(Color.DARK_GRAY);
        Label.setFont(new Font("Helvetica",Font.BOLD, 15));
 
        JPanel Panel = new JPanel();
        Panel.add(Label);
 
        JOptionPane.showMessageDialog(
                null,  
                Panel, 
                "Thanks",
                JOptionPane.PLAIN_MESSAGE); 
		}

	}
		
}
