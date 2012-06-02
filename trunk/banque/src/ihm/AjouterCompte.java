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
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import utilitaire.VerifFormat;

public class AjouterCompte extends JFrame implements ActionListener, ChangeListener {
	private Banque laBanque;
	private JRadioButton radPhysique;
	private JRadioButton radMoral;
	private JRadioButton radAdo;
	private JRadioButton radAsso;
	private JPanel panChoixCpt;
	private ChampModif chProprio;
	private ChampModif chSolde;
	private ChampModif chDecouvertMax;
	private JButton btnAjouter;
	private ButtonGroup cbg;
	private ListeCpt parent;

	public AjouterCompte(ListeCpt parent, Banque laBanque) {
		setSize(700, 400);
		setTitle("Ajouter un compte");

		this.laBanque = laBanque;
		this.parent = parent;

		radPhysique = new JRadioButton("Personne physique", false);
		radPhysique.addChangeListener(this);
		radMoral = new JRadioButton("Personne morale", false);
		radMoral.addChangeListener(this);
		radAdo = new JRadioButton("Adolescent", false);
		radAdo.addChangeListener(this);
		radAsso = new JRadioButton("Associatif", false);
		radAsso.addChangeListener(this);
		
		panChoixCpt = new JPanel(new GridLayout(1, 4));
		panChoixCpt.add(radPhysique);
		panChoixCpt.add(radMoral);
		panChoixCpt.add(radAdo);
		panChoixCpt.add(radAsso);

		cbg = new ButtonGroup();
		cbg.add(radPhysique);
		cbg.add(radMoral);
		cbg.add(radAdo);
		cbg.add(radAsso);

		add(panChoixCpt, BorderLayout.NORTH);

		JPanel panInfos = new JPanel(new GridLayout(10, 1, 0, 10));
		chProprio = new ChampModif("Nom du propriétaire");
		chSolde = new ChampModif("Solde (en €)");
		chDecouvertMax = new ChampModif("Découvert maximum (en €)");
		panInfos.add(chProprio);
		panInfos.add(chSolde);
		panInfos.add(chDecouvertMax);

		add(panInfos);

		btnAjouter = new JButton("Créer le compte");
		btnAjouter.addActionListener(this);
		add(btnAjouter, BorderLayout.SOUTH);

		radPhysique.setSelected(true);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		Compte cptTemp = null;

		if (VerifFormat.estChiffre(chSolde.getTf())) {
			if (radPhysique.isSelected()) {
				cptTemp = new PersonnePhysique(chProprio.getTf(),
						Float.parseFloat(chSolde.getTf()),
						Float.parseFloat(chDecouvertMax.getTf()));
			} else if (radMoral.isSelected()) {
				cptTemp = new PersonneMorale(chProprio.getTf(),
						Float.parseFloat(chSolde.getTf()),
						Float.parseFloat(chDecouvertMax.getTf()));
			} else if (radAdo.isSelected()) {
				cptTemp = new Ado(chProprio.getTf(), Float.parseFloat(chSolde
						.getTf()));
			} else if (radAsso.isSelected()) {
				cptTemp = new Association(chProprio.getTf(),
						Float.parseFloat(chSolde.getTf()));
			}
			laBanque.ajouterCompte(cptTemp);
			JOptionPane.showMessageDialog(this, "Le compte a bien été ajouté",
					"Nouveau compte", JOptionPane.PLAIN_MESSAGE);
			parent.majListe(parent.getChbDecouvert().isSelected());
		}
		else
		{
			JOptionPane.showMessageDialog(this, "Vous devez saisir un solde correct.",
					"Erreur de saisie", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		chDecouvertMax.setVisible(!(radAdo.isSelected() || radAsso.isSelected()));
	}
}
