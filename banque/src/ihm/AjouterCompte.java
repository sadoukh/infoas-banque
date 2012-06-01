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
import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class AjouterCompte extends Formulaire implements ActionListener {
	private Banque laBanque;
	private JCheckBox chbPhysique;
	private JCheckBox chbMoral;
	private JCheckBox chbAdo;
	private JCheckBox chbAsso;
	private JPanel panChoixCpt;
	private Champ chProprio;
	private Champ chSolde;
	private Champ chDecouvertMax;
	private JButton btnAjouter;
	private ButtonGroup cbg;

	public AjouterCompte() {
		setSize(700, 500);

		chbPhysique = new JCheckBox("Personne physique", false);
		chbPhysique.setSelected(true);
		chbMoral = new JCheckBox("Personne morale", false);
		chbAdo = new JCheckBox("Adolescent", false);
		chbAsso = new JCheckBox("Associatif", false);

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
		JCheckBox chbTemp = (JCheckBox) cbg.getSelection();

		if (chbTemp == chbPhysique) {
			cptTemp = new PersonnePhysique(chProprio.getTf(),
					Float.parseFloat(chSolde.getTf()),
					Float.parseFloat(chDecouvertMax.getTf()));
		} else if (chbTemp == chbMoral) {
			cptTemp = new PersonneMorale(chProprio.getTf(),
					Float.parseFloat(chSolde.getTf()),
					Float.parseFloat(chDecouvertMax.getTf()));
		} else if (chbTemp == chbAdo) {
			cptTemp = new Ado(chProprio.getTf(),
					Float.parseFloat(chSolde.getTf()));
		} else if (chbTemp == chbAsso) {
			cptTemp = new Association(chProprio.getTf(),
					Float.parseFloat(chSolde.getTf()));
		}
		laBanque.ajouterCompte(cptTemp);
	}

	@Override
	public void maj() {
		// TODO Auto-generated method stub

	}
}
