package ihm;

import gestion.Ado;
import gestion.Association;
import gestion.Banque;
import gestion.Compte;
import gestion.PersonneMorale;
import gestion.PersonnePhysique;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class AjouterCompte extends Formulaire implements ActionListener {
	private Banque laBanque;
	private JRadioButton chbPhysique;
	private JRadioButton chbMoral;
	private JRadioButton chbAdo;
	private JRadioButton chbAsso;
	private JPanel panChoixCpt;
	private Champ chProprio;
	private Champ chSolde;
	private Champ chDecouvertMax;
	private JButton btnAjouter;
	private ButtonGroup cbg;
	private ListeCpt parent;

	public AjouterCompte(ListeCpt parent, Banque laBanque) {
		setSize(700, 500);

		this.laBanque = laBanque;
		this.parent = parent;

		chbPhysique = new JRadioButton("Personne physique", false);
		chbPhysique.setSelected(true);
		chbMoral = new JRadioButton("Personne morale", false);
		chbAdo = new JRadioButton("Adolescent", false);
		chbAsso = new JRadioButton("Associatif", false);

		panChoixCpt = new JPanel(new GridLayout(1, 4));
		panChoixCpt.add(chbPhysique);
		panChoixCpt.add(chbMoral);
		panChoixCpt.add(chbAdo);
		panChoixCpt.add(chbAsso);

		cbg = new ButtonGroup();
		cbg.add(chbPhysique);
		cbg.add(chbMoral);
		cbg.add(chbAdo);
		cbg.add(chbAsso);

		add(panChoixCpt, BorderLayout.NORTH);

		JPanel panInfos = new JPanel(new GridLayout(10, 1));
		chProprio = new Champ(this, "Nom du propriétaire", false);
		chSolde = new Champ(this, "Solde", false);
		chDecouvertMax = new Champ(this, "Découvert maximum", false);
		panInfos.add(chProprio);
		panInfos.add(chSolde);
		panInfos.add(chDecouvertMax);

		add(panInfos);

		btnAjouter = new JButton("Créer le compte");
		btnAjouter.addActionListener(this);
		add(btnAjouter, BorderLayout.SOUTH);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		Compte cptTemp = null;

		if (estChiffre(chSolde.getTf())) {
			if (chbPhysique.isSelected()) {
				cptTemp = new PersonnePhysique(chProprio.getTf(),
						Float.parseFloat(chSolde.getTf()),
						Float.parseFloat(chDecouvertMax.getTf()));
			} else if (chbMoral.isSelected()) {
				cptTemp = new PersonneMorale(chProprio.getTf(),
						Float.parseFloat(chSolde.getTf()),
						Float.parseFloat(chDecouvertMax.getTf()));
			} else if (chbAdo.isSelected()) {
				cptTemp = new Ado(chProprio.getTf(), Float.parseFloat(chSolde
						.getTf()));
			} else if (chbAsso.isSelected()) {
				cptTemp = new Association(chProprio.getTf(),
						Float.parseFloat(chSolde.getTf()));
			}
			laBanque.ajouterCompte(cptTemp);
			JOptionPane.showMessageDialog(this, "Le compte a bien été ajouté",
					"Nouveau compte", JOptionPane.PLAIN_MESSAGE);
			parent.majListe(parent.getChbDecouvert().isSelected());
		}
	}

	@Override
	public void maj() {
		// TODO Auto-generated method stub

	}

	public boolean estChiffre(String s) {
		try {
			Float.parseFloat(s);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}
}
