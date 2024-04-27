import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class image_teste {

	public static void main(String[] args) {
		image_teste i = new image_teste();
		System.out.print(i.toString());
		ImageIcon imagem = new ImageIcon(i.getClass().getClassLoader().getResource("mod/coin.gif"));
		JLabel label = new JLabel(imagem);
		label.setSize(250,250);
		
		JFrame frame = new JFrame();
		frame.add(label);
		frame.pack();
		frame.setVisible(true);
		
	}
}
