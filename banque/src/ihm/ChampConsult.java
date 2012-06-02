package ihm;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ChampConsult extends JPanel {
	private JLabel lblLegende;
	private JLabel lblTexte;
	

	public ChampConsult(String legende, float valeur) {
		this(legende, "" + valeur);
	}

	public ChampConsult(String legende, int valeur) {
		this(legende, "" + valeur);
	}

	public ChampConsult(String legende, String texte) {
		setLayout(new GridLayout(1, 2, 10, 0));

		lblLegende = new JLabel(legende);
		lblTexte = new JLabel(texte);

		add(lblLegende);
		add(lblTexte);

		setVisible(true);
	}
	
	public void setLblTexte(String lblTexte) {
		this.lblTexte.setText(lblTexte);
	}
}