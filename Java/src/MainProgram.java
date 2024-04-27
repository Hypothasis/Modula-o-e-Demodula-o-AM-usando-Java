import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
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
	/*
	Thread SoundPlayer = new Thread(new SoundPlayer(""));
	SoundPlayer.start();
	*/
	
	private static ArrayList <String> MusicPath = new ArrayList<String>();
	private static JComboBox<String> ComboBox_Music = new JComboBox<>();
	private static JTextField  TextFieldFrequency;
	private static JLabel ExportIndicator = new JLabel();
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
	private static AudioFormat SampleFormat;
	private static double Frequency = 0.0;
	private static JButton SelectSampleButton;
	private static JButton SelectFrequencyButton;
	private static JButton CreditsButton;
	private static JButton PlayButton;
	private static JButton LoopButton;
	private static JButton InfoButton;
	private static JButton ExportButton;
	
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
			}
		});
	}

	private static void Star_Program() {
		
		ImageIcon Gif = new ImageIcon("res/demod/gif.gif");
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
		ComboBox_Music.setBounds(120,130,110,30);	
	}
	
	private static void Criar_JTextField_Frequency() {
		TextFieldFrequency = new JTextField();
		TextFieldFrequency.setText("Digite hertz");
		TextFieldFrequency.setBounds(150, 220, 150, 30);
		TextFieldFrequency.setToolTipText("Seleciona a frequência da portadora.");
		TextFieldFrequency.setFont(new Font("Arial",Font.CENTER_BASELINE,15));
		TextFieldFrequency.setBackground(Color.white);
		TextFieldFrequency.setForeground(Color.DARK_GRAY);
		TextFieldFrequency.setCaretColor(Color.red);
	}
	
	private static void Criar_Botoes() {
		SelectSampleButton = new JButton("Select File");
		SelectSampleButton.setBounds(10,130,100,30);
		SelectSampleButton.setToolTipText("Seleciona o arquivo da música");
		SelectSampleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("File sample");
				//declarando e filtrando
				JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos WAV", "wav");
                fileChooser.setFileFilter(filter);
                
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String Musica = selectedFile.getName();
                    if (Musica.toLowerCase().endsWith(".wav")) {
                    	MusicPath.add(selectedFile.getPath());
                    	MusicPath.add(selectedFile.getName());
                        ComboBox_Music.addItem(Musica);
                    } else {
                        JOptionPane.showMessageDialog(null, "Selecione um arquivo .wav válido.","Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
			}});
		
		SelectFrequencyButton = new JButton("Select Frequency");
		SelectFrequencyButton.setBounds(10,220,135,30);
		SelectFrequencyButton.setToolTipText("Seleciona a frequência");
		SelectFrequencyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				System.out.println("Frequencia: "+TextFieldFrequency.getText()+" Hertz");
				
				Frequency = Double.parseDouble(TextFieldFrequency.getText());

			}});
		
		CreditsButton = new JButton(CreditsImage);
		CreditsButton.setBounds(10,10,35,35);
		CreditsButton.setBackground(new Color(158, 193, 231));
		CreditsButton.setToolTipText("Credits");
		//credits.setContentAreaFilled(false);
        //credits.setBorderPainted(false);
		CreditsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,"Desenvolvido por Gabriel Oliveira dos Santos"
						+ " GitHub: Hypothasis.","Creditos", JOptionPane.PLAIN_MESSAGE);
				System.out.println("-Tamanho da list unmod: "+ MusicPath.size());
				for(int i=0; i < MusicPath.size();i++) {
					System.out.println(MusicPath.get(i));
				}
				System.out.println("");
				
				System.err.println("Format_sample :" + SampleFormat);
			}});
		
		PlayButton = new JButton(PlayImage);
		PlayButton.setBounds(240,130,50,50);
		PlayButton.setToolTipText("Toca a música");
		PlayButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("play unmod");
				String selecao = (String) ComboBox_Music.getSelectedItem();
				System.out.println(selecao);
				
				 for (int i = 0; i < MusicPath.size(); i++) {
			            //System.out.println(CaminhoMusica.get(i));
					 if(MusicPath.get(i).equals(selecao)) {
						 //acha o nome da musica. i-1 = endereço da musica
						 System.out.println(MusicPath.get(i-1));
						 Play(MusicPath.get(i-1));
						 break;
					 }
			        }
			}});
		
		LoopButton = new JButton(LoopImage);
		LoopButton.setBounds(290,130,50,50);
		LoopButton.setToolTipText("Pause e recomeça a música");
		LoopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("loop unmod");
				String selecao = (String) ComboBox_Music.getSelectedItem();
				System.out.println(selecao);
				
				 for (int i = 0; i < MusicPath.size(); i++) {
			            //System.out.println(CaminhoMusica.get(i));
					 if(MusicPath.get(i).equals(selecao)) {
						 //acha o nome da musica. i-1 = endereço da musica
						 System.out.println(MusicPath.get(i-1));
						 Loop();
						 break;
					 }
			        }			
			}});
		
		InfoButton = new JButton(InfoImage);
		InfoButton.setBounds(340,130,50,50);
		InfoButton.setToolTipText("Formatação da música");
		InfoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(ComboBox_Music.getSelectedItem());
				System.out.println("tamanho da combobox unmod: "+ComboBox_Music.getItemCount());
				
				//se o info nao tiver audio
				if(ComboBox_Music.getSelectedItem() == null) {
					JOptionPane.showMessageDialog(null,"Nenhum áudio selecionado","Info unmod", JOptionPane.INFORMATION_MESSAGE);
				}
				
				for(int i = 0; i < MusicPath.size();i++) {
					if(MusicPath.get(i).equals(ComboBox_Music.getSelectedItem())) {
						System.out.print("caminho musica: "+MusicPath.get(i-1));
						File unmod_info_music = new File(MusicPath.get(i-1));
						try {
							AudioInputStream song = AudioSystem.getAudioInputStream(unmod_info_music);
							AudioFormat format_song = song.getFormat();
							SampleFormat= format_song;
							JOptionPane.showMessageDialog(null,"Formato do audio: "+format_song,"Info unmod", JOptionPane.INFORMATION_MESSAGE);
							SampleFormat = null;
						} catch (UnsupportedAudioFileException | IOException e1) {
							System.err.println("Error: format song unmod");
							e1.printStackTrace();
						}
						break;
					}
				}				
			}});
		
		ExportButton = new JButton(ExportImage);
		ExportButton.setBounds(125,400,150,60);
		ExportButton.setToolTipText("Use para Exportar o sinal");
		ExportButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Export");
				if(MusicPath.size() != 0 && Frequency > 0)
				{						
					//Adequirindo o path dos arquivos selecionados
				String sample_path = null;
				
				for(int i = 0; i < MusicPath.size();i++) {
					if(MusicPath.get(i).equals(ComboBox_Music.getSelectedItem())) {
						sample_path = MusicPath.get(i-1);
						break;
					}
				}
				
				
				//adquirindo o format audio do audio
				
				
				for(int i = 0; i < MusicPath.size();i++) {
					if(MusicPath.get(i).equals(ComboBox_Music.getSelectedItem())) {
						System.out.print("caminho musica: "+MusicPath.get(i-1));
						File mod_info_music = new File(MusicPath.get(i-1));
						try {
							AudioInputStream song = AudioSystem.getAudioInputStream(mod_info_music);
							AudioFormat format_song = song.getFormat();
							SampleFormat = format_song;
						} catch (UnsupportedAudioFileException | IOException e1) {
							e1.printStackTrace();
						}
						break;
					}
				}
				
				//Secando se esta certo para exportar
				
				System.err.println("Caminho do sample: "+ sample_path);
				System.err.println("formato do sample: "+ SampleFormat);
				
				ExportIndicator.setIcon(AgreeExport);
				try {
					
					//Chamando a classe para exportar
					new modulação_e_demodulação(sample_path, Frequency);
				
				} catch (IOException | UnsupportedAudioFileException e1) {
					e1.printStackTrace();
				}
				
				ExportIndicator.setIcon(DisagreeExport);
				} else {
					JOptionPane.showMessageDialog(null,"                                                      "
							+ "ERRO NA EXPORTAÇÃO!"
							+ "\nCheque se o áudio foi selecionado e "
							+ "você apertou o borão de selecionar a frequência."
							+ "","Exportation", JOptionPane.WARNING_MESSAGE);
				}
			}});
		
	}

	private static void Criar_Janela() {
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
		window.pack();
		
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
	 	            
	 	        }
	 	        	catch (Exception e) {
	 	        		e.printStackTrace();
	     	}
	    	}
		
	}
	
	protected static void Loop() {
		if(clip != null && clip.isRunning() ) {
    		clip.setMicrosecondPosition(0);
    		pause_moment = 0;
    		pause_actived = false;
    		clip.stop();
    	}
    	else {
    		clip.setMicrosecondPosition(0);
    		pause_moment = 0;
    		pause_actived = false;
    		clip.stop();
    	}
		
	}

}
