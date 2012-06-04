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

public class AjouterCompte extends JFrame implements ActionListener,
		ChangeListener {
	private IHMBanque parent;
	private Banque laBanque;
	private JRadioButton radPhysique;
	private JRadioButton radMoral;
	private JRadioButton radAdo;
	private JRadioButton radAsso;
	private ChampModif chProprio;
	private ChampModif chSolde;
	private ChampModif chDecouvertMax;

	public AjouterCompte(IHMBanque parent, Banque laBanque) {
		setSize(700, 400);
		setTitle("Ajouter un compte");

		this.parent = parent;
		this.laBanque = laBanque;

		radPhysique = new JRadioButton("Personne physique", false);
		radPhysique.addChangeListener(this);
		radMoral = new JRadioButton("Personne morale", false);
		radMoral.addChangeListener(this);
		radAdo = new JRadioButton("Adolescent", false);
		radAdo.addChangeListener(this);
		radAsso = new JRadioButton("Associatif", false);
		radAsso.addChangeListener(this);

		JPanel panChoixCpt = new JPanel(new GridLayout(1, 4));
		panChoixCpt.add(radPhysique);
		panChoixCpt.add(radMoral);
		panChoixCpt.add(radAdo);
		panChoixCpt.add(radAsso);

		ButtonGroup cbg = new ButtonGroup();
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

		JButton btnAjouter = new JButton("Créer le compte");
		btnAjouter.addActionListener(this);
		add(btnAjouter, BorderLayout.SOUTH);

		radPhysique.setSelected(true);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		boolean soldeOk = VerifFormat.estChiffre(chSolde.getTf());
		Compte cptTemp = null;
		String sErreur = null;

		if (radPhysique.isSelected() || radMoral.isSelected()) {
			if (soldeOk && VerifFormat.estChiffre(chDecouvertMax.getTf())) {
				if (radPhysique.isSelected()) {
					cptTemp = new PersonnePhysique(chProprio.getTf(),
							Float.parseFloat(chSolde.getTf()),
							Float.parseFloat(chDecouvertMax.getTf()));
				} else {
					cptTemp = new PersonneMorale(chProprio.getTf(),
							Float.parseFloat(chSolde.getTf()),
							Float.parseFloat(chDecouvertMax.getTf()));
				}
			} else {
				sErreur = "Vous devez saisir un solde et un découvert max correct.";
			}
		} else {
			if (soldeOk) {
				if (radAdo.isSelected()) {
					cptTemp = new Ado(chProprio.getTf(),
							Float.parseFloat(chSolde.getTf()));
				} else {
					cptTemp = new Association(chProprio.getTf(),
							Float.parseFloat(chSolde.getTf()));
				}
			} else {
				sErreur = "Vous devez saisir un solde correct.";
			}
		}

		if (sErreur == null) {
			laBanque.ajouterCompte(cptTemp);
			JOptionPane.showMessageDialog(this, "Le compte a bien été ajouté",
					"Nouveau compte", JOptionPane.PLAIN_MESSAGE);
			parent.majListe(parent.getChbDecouvert().isSelected());
			dispose();
		} else {
			JOptionPane.showMessageDialog(this, sErreur, "Erreur de saisie",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		chDecouvertMax
				.setVisible(radPhysique.isSelected() || radMoral.isSelected());
	}
}
