package ihm;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Champ extends JPanel implements ActionListener {
	private JLabel lbl;
	private JTextField tfTexte;
	private JLabel lblTexte;
	private JButton btnModif;
	private Formulaire frm;

	public Champ(Formulaire frm, String nom, boolean lectureSeule) {
		this(frm, nom, "", lectureSeule);
	}
	
	public Champ(Formulaire frm, String nom, int valeur, boolean lectureSeule) {
		this(frm, nom, "" + valeur, lectureSeule);
	}

	public Champ(Formulaire frm, String nom, float valeur, boolean lectureSeule) {
		this(frm, nom, "" + valeur, lectureSeule);
	}

	public Champ(Formulaire frm, String nom, String texte, boolean lectureSeule) {
		setLayout(new GridLayout(1, 3, 10, 0));
		this.frm = frm;

		lbl = new JLabel(nom);
		add(lbl);
		
		if (lectureSeule) {
			lblTexte = new JLabel(texte);
			tfTexte = null;
			btnModif = null;
			add(lblTexte);
		}
		else {
			tfTexte = new JTextField(texte);
			tfTexte.addActionListener(this);
			lblTexte = null;
			btnModif = new JButton("Modifier");
			btnModif.addActionListener(this);
			add(tfTexte);
			add(btnModif);
		}
	}

	public String getTf() {
		return tfTexte.getText();
	}

	public void setTf(String texte) {
		tfTexte.setText(texte);
	}
	
	public void desactiver() {
		this.tfTexte.setVisible(false);
	}
	
	public void activer() {
		this.tfTexte.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		frm.maj();
	}
}
