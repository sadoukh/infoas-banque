package ihm;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ChampBouton extends ChampModif implements ActionListener {
	private Formulaire frm;
	private JButton btn;
	
	public ChampBouton(Formulaire frm, String texteLbl, float valeurTf) {
		this(frm, texteLbl, "" + valeurTf, "Modifier");
	}
	
	public ChampBouton(Formulaire frm, String texteLbl, String texteTf) {
		this(frm, texteLbl, texteTf, "Modifier");
	}
	
	public ChampBouton(Formulaire frm, String texteLbl, String texteTf, String texteBtn) {
		super(texteLbl, texteTf);
		setLayout(new GridLayout(1, 3, 10, 0));
		
		this.frm = frm;
		btn = new JButton(texteBtn);
		btn.addActionListener(this);
		add(btn);
	}

	public void setTf(String texte) {
		super.tf.setText(texte);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		frm.maj();
	}
}
