package ihm;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ChampModif extends JPanel {
	private JLabel lbl;
	protected JTextField tf;
	
	public ChampModif(String texteLbl) {
		this(texteLbl, "");
	}
	
	public ChampModif(String texteLbl, String texteTf) {
		setLayout(new GridLayout(1, 2, 10, 0));
		
		lbl = new JLabel(texteLbl);
		tf = new JTextField(texteTf);

		add(lbl);
		add(tf);
	}
	
	public String getTf() {
		return tf.getText();
	}

	public void setVisible(boolean isVisible) {
		lbl.setVisible(isVisible);
		tf.setVisible(isVisible);
	}
}
