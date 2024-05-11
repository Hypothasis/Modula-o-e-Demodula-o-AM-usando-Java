import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;


public class MainProgram {

	private static ArrayList <String> MusicPath = new ArrayList<String>();
	private static JComboBox ComboBox_Music = new JComboBox<>();
	private static JTextField  TextFieldFrequency;
	private static JLabel ExportIndicator = new JLabel(); 
	private static AudioFormat SampleFormat;
	private static ImageIcon BackgroundImage;
	private static ImageIcon AgreeExport;
	private static ImageIcon DisagreeExport;
	private static ImageIcon IconFrame;
	private static ImageIcon TitleImage;
	private static ImageIcon PlayImage;
	private static ImageIcon LoopImage;
	private static ImageIcon InfoImage;
	private static ImageIcon ExportImage;
	private static ImageIcon CreditsImage;
	private static JButton SelectSampleButton;
	private static JButton SelectFrequencyButton;
	private static JButton CreditsButton;
	private static JButton PlayButton;
	private static JButton LoopButton;
	private static JButton InfoButton;
	private static JButton ExportButton;
	private static double Frequency = 0.0;
	
	private static Clip clip;
	private static long pause_moment;
	private static boolean pause_actived;
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				Star_Program();
				Instanciar_Imagem();
				Criar_ComboBox();
				Criar_JTextField_Frequency();
				Criar_Botoes();
				Criar_Janela();
				CheckingIndicator();
			}
		});
	}

	private static void Star_Program() {
		
		ImageIcon Gif = new ImageIcon(MainProgram.class.getClassLoader().getResource("mod/gif.gif"));
		JLabel Text = new JLabel("Seja bem vindo ao modulador AM Java Edition", Gif, JLabel.CENTER);
		Text.setForeground(Color.DARK_GRAY);
		Text.setFont(new Font("Helvetica",Font.BOLD, 15));
		
		JPanel Painel = new JPanel();
		Painel.add(Text);
		
		JOptionPane.showMessageDialog(
				null,  
				Painel, 
				"Modulador AM Java Edition",
				JOptionPane.PLAIN_MESSAGE 
				);
		
	}

	private static void Instanciar_Imagem(){
		
		MainProgram Instance = new MainProgram();

		BackgroundImage= new ImageIcon(Instance.getClass().getClassLoader().getResource("mod/base_modulacao.jpg"));
		AgreeExport = new ImageIcon(Instance.getClass().getClassLoader().getResource("mod/green_light_50_50.jpg"));
		DisagreeExport = new ImageIcon(Instance.getClass().getClassLoader().getResource("mod/not_50_50.jpg"));
		IconFrame = new ImageIcon(Instance.getClass().getClassLoader().getResource("mod/icone.jpg"));
		TitleImage = new ImageIcon(Instance.getClass().getClassLoader().getResource("mod/title_200_71.jpg"));
		PlayImage = new ImageIcon(Instance.getClass().getClassLoader().getResource("mod/play_50_50.jpg"));
		LoopImage = new ImageIcon(Instance.getClass().getClassLoader().getResource("mod/loop.jpg"));
		InfoImage = new ImageIcon(Instance.getClass().getClassLoader().getResource("mod/info_50_50.jpg"));
		ExportImage = new ImageIcon(Instance.getClass().getClassLoader().getResource("mod/export_160_50.jpg"));
		/*redimensionando imagem de acordo com o Frame */
		BackgroundImage.setImage(BackgroundImage.getImage().getScaledInstance(400,700,1));
		CreditsImage = new ImageIcon(Instance.getClass().getClassLoader().getResource("mod/mario_star (1).gif"));
	}
	
	private static void Criar_ComboBox() {
		/*
		MainProgram Instance = new MainProgram();
		
		URL sample1 = Instance.getClass().getResource("samples/sample1.wav");
		URL sample2 = Instance.getClass().getResource("samples/sample2.wav");
		URL sample3 = Instance.getClass().getResource("samples/sample3.wav");
		
		MusicPath.add(sample1.getPath());
		MusicPath.add("sample1.wav");
		MusicPath.add(sample2.getPath());
		MusicPath.add("sample2.wav");
		MusicPath.add(sample3.getPath());
		MusicPath.add("sample3.wav");
		
		ComboBox_Music.addItem("sample1.wav");
		ComboBox_Music.addItem("sample2.wav");
		ComboBox_Music.addItem("sample3.wav");
		*/
		
		ComboBox_Music.setBounds(115,130,110,30);
	}
	
	private static void Criar_JTextField_Frequency() {
		TextFieldFrequency = new JTextField();
		TextFieldFrequency.setText("Digite hertz");
		TextFieldFrequency.setBounds(180, 220, 180, 30);
		TextFieldFrequency.setToolTipText("Seleciona a frequência da portadora.");
		TextFieldFrequency.setFont(new Font("Arial",Font.CENTER_BASELINE,15));
		TextFieldFrequency.setBackground(Color.white);
		TextFieldFrequency.setForeground(Color.DARK_GRAY);
		TextFieldFrequency.setCaretColor(Color.red);
	}
	
	private static void Criar_Botoes() {
		SelectSampleButton = new JButton("WAV File");
		SelectSampleButton.setBounds(10,130,100,30);
		SelectSampleButton.setToolTipText("Seleciona o arquivo da música");
		SelectSampleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AddSample();
			}});
		
		SelectFrequencyButton = new JButton("Select Frequency");
		SelectFrequencyButton.setBounds(10,220,165,30);
		SelectFrequencyButton.setToolTipText("Seleciona a frequência");
		SelectFrequencyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Frequencia: "+TextFieldFrequency.getText()+" Hertz");
				Frequency = Double.parseDouble(TextFieldFrequency.getText());

			}});
		
		CreditsButton = new JButton(CreditsImage);
		CreditsButton.setBounds(10,10,40,40);
		CreditsButton.setBackground(new Color(158, 193, 231));
		CreditsButton.setToolTipText("Credits");
		CreditsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,
						"Desenvolvido por Gabriel Oliveira dos Santos \nGitHub: Hypothasis",
						"Créditos",
						JOptionPane.PLAIN_MESSAGE);
				
				System.out.println("-Tamanho da lista: "+ MusicPath.size());
				for(int i=0; i < MusicPath.size();i++) {
					System.out.println(MusicPath.get(i));
				}
				System.out.println("");
			}});
		
		PlayButton = new JButton(PlayImage);
		PlayButton.setBounds(230,130,50,50);
		PlayButton.setToolTipText("Toca a música");
		PlayButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String MusicPath = GetAbsoluteMusicPath();
				if(isOpenAudio(MusicPath)) {
					Play(MusicPath);				
				}
			}});
		
		LoopButton = new JButton(LoopImage);
		LoopButton.setBounds(280,130,50,50);
		LoopButton.setToolTipText("Pause e recomeça a música");
		LoopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Loop();	
			}});
		
		InfoButton = new JButton(InfoImage);
		InfoButton.setBounds(330,130,50,50);
		InfoButton.setToolTipText("Formatação da música");
		InfoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String SamplePath = GetAbsoluteMusicPath();
				if(!isComboboxEmpty()) {
					isAudioFormatCorrect(SamplePath);
				}
			}});
		
		ExportButton = new JButton(ExportImage);
		ExportButton.setBounds(125,400,150,60);
		ExportButton.setToolTipText("Use para Exportar o sinal");
		ExportButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Loop();
				String SamplePath = GetAbsoluteMusicPath();
				if(isAudioFormatCorrect(SamplePath)) {
					ExportAudio(SamplePath);	
				}
					
			}});
		
	}

	protected static void Criar_Janela() {
		JLabel Title = new JLabel(TitleImage);
		Title.setBounds(100,5,200,71);
		Title.setToolTipText("Main Title");
		
		
		ExportIndicator.setIcon(DisagreeExport);
		ExportIndicator.setBounds(70,405,50,50);
		ExportIndicator.setToolTipText("Indicator da exportação");
		
		
		JLabel base = new JLabel();
		base.setIcon(BackgroundImage);
		base.add(PlayButton);
		base.add(LoopButton);
		base.add(InfoButton);
		base.add(ComboBox_Music);
		base.add(SelectFrequencyButton);
		base.add(TextFieldFrequency);
		base.add(SelectSampleButton);
		base.add(ExportButton);
		base.add(CreditsButton);
		base.add(Title);
		base.add(ExportIndicator);
		
		
		final JFrame window = new JFrame("Modulador AM Java Edition");
		window.setIconImage(IconFrame.getImage());
		window.setSize(400,700);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocationRelativeTo(null);
		window.setResizable(false);
		window.setVisible(true);
				
		window.add(base);
		//window.pack();
		
	}
	
	protected static void AddSample() {
		JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos WAV", "wav");
        fileChooser.setFileFilter(filter);
        
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String MusicName = selectedFile.getName();
            if (MusicName.toLowerCase().endsWith(".wav")) {
            	MusicPath.add(selectedFile.getPath());
            	MusicPath.add(selectedFile.getName());
                ComboBox_Music.addItem(MusicName);
            } else {
                JOptionPane.showMessageDialog(null, "Selecione um arquivo .wav válido.","Error Audio File", JOptionPane.ERROR_MESSAGE);
            }
        }
		
	}

	protected static boolean isComboboxEmpty() {
		if(ComboBox_Music.getSelectedItem() == null || ComboBox_Music.getItemCount() == 0) {
			JOptionPane.showMessageDialog(null,"Nenhum áudio selecionado ou ComboBox Vazio","Info unmod", JOptionPane.INFORMATION_MESSAGE);
			return true;
		} else {
			return false;
		}
		
	}
	
	protected static boolean isOpenAudio(String musicPath2) {
		File AudioFile = new File(musicPath2);
		AudioInputStream Audio;
		try {
			Audio = AudioSystem.getAudioInputStream(AudioFile);
			AudioFormat AudioFormat = Audio.getFormat();
			SampleFormat= AudioFormat;
			clip = AudioSystem.getClip();
			clip.open(Audio);
		
			return true;
		} catch (LineUnavailableException e) {
			JOptionPane.showMessageDialog(null,"LineUnavailableException: Erro no formato de áudio\nEscolha um arquivo WAV PCM_SIGNED 16Bits?","Info Song", JOptionPane.INFORMATION_MESSAGE);
			e.printStackTrace();
			return false;
		} catch (UnsupportedAudioFileException e) {
			JOptionPane.showMessageDialog(null,"UnsupportedAudioFileException: Error audio Unsupported","Info Song", JOptionPane.INFORMATION_MESSAGE);
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,"IOException: Erro de path de audio, path errado","Info Song", JOptionPane.INFORMATION_MESSAGE);
			e.printStackTrace();
			return false;
		}
		
	}

	protected static boolean isAudioFormatCorrect(String SamplePath) {
		File AudioFile = new File(SamplePath);
		AudioInputStream Audio;
		try {
			Audio = AudioSystem.getAudioInputStream(AudioFile);
			AudioFormat AudioFormat = Audio.getFormat();
			SampleFormat= AudioFormat;
			clip = AudioSystem.getClip();
			clip.open(Audio);
			JOptionPane.showMessageDialog(null,"Formato do audio: "+AudioFormat,"Info Song", JOptionPane.INFORMATION_MESSAGE);
			
			return true;
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			JOptionPane.showMessageDialog(null,"UnsuportedAudioFileException: Erro no formato de áudio\nVocê escolheu um arquivo Wav PCM_SIGNED 16Bits?","Info Song", JOptionPane.INFORMATION_MESSAGE);
			e.printStackTrace();
			return false;
		}		
	}

	protected static void ExportAudio(String SamplePath) {
		
		//Chama a Classe como uma Thread
	
		Thread Modulator = new Thread(new De_ModulatorExport(SamplePath,Frequency));
		Modulator.start();
		
		TextFieldFrequency.setText("");
		Frequency = 0.0;
	} 
	
	protected static String GetAbsoluteMusicPath() {
		String SamplePath = null;					
		for(int i = 0; i < MusicPath.size();i++) {
			if(MusicPath.get(i).equals(ComboBox_Music.getSelectedItem())) {
				SamplePath = MusicPath.get(i-1);
				break;
				}
			}
		return SamplePath;
	}
	
	protected static void Play(String SongPath) {
		if(clip != null && clip.isRunning()) {
		       System.err.println("***Alerta de delay previnido***");
		       
	    	} else {
    		 try {
 	        	/* Arquivo para musica*/
 	            File Music = new File(SongPath);
 	            /* Music para song, tranforma um file em uma song*/
 	            AudioInputStream song = AudioSystem.getAudioInputStream(Music);
 	            System.out.println(song.markSupported());
 	            System.out.println(song.getFormat());
 	            clip = AudioSystem.getClip();
 	            System.out.println(clip.getFormat());
 	            clip.open(song);
 	            
 	            if(pause_actived == false) {
 	            	clip.start();
 	            } else {
 	            	clip.setMicrosecondPosition(pause_moment);
 	            	clip.start();
 	            }
 	            
 	        }catch (Exception e) {
 	        	e.printStackTrace();
 	        }
    	}
	}
	
	protected static void Loop() {
		if(clip != null) {
			clip.setMicrosecondPosition(0);
			pause_moment = 0;
			pause_actived = false;
			clip.stop();
		}	
	}

	protected static void CheckingIndicator() {
		Thread checking = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true) {
					if(ComboBox_Music.getSelectedItem() != null && Frequency != 0.0) {
						ExportIndicator.setIcon(AgreeExport);
					}else {
						ExportIndicator.setIcon(DisagreeExport);
					}
				}
				
			}
		});
		checking.start();
	}
}
