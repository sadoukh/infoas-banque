package ihm;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ChampBouton extends ChampModif implements ActionListener {
	private DetailCompte dc;
	private JButton btn;
	
	public ChampBouton(DetailCompte dc, String texteLbl, float valeurTf) {
		this(dc, texteLbl, "" + valeurTf, "Modifier");
	}
	
	public ChampBouton(DetailCompte dc, String texteLbl, String texteTf) {
		this(dc, texteLbl, texteTf, "Modifier");
	}
	
	public ChampBouton(DetailCompte dc, String texteLbl, String texteTf, String texteBtn) {
		super(texteLbl, texteTf);
		setLayout(new GridLayout(1, 3, 10, 0));
		
		this.dc = dc;
		btn = new JButton(texteBtn);
		btn.addActionListener(this);
		add(btn);
	}

	public void setTf(String texte) {
		super.tf.setText(texte);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		dc.maj();
	}
}
