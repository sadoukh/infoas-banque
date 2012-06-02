package ihm;

import gestion.Banque;
import gestion.Compte;
import gestion.Operation;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import utilitaire.VerifFormat;

public class DetailCli extends Formulaire implements ActionListener {
	private Compte cptActuel;
	private ChampConsult chNumCpt;
	private ChampConsult chTypeCpt;
	private ChampBouton chNomCli;
	private ChampBouton chDecMax;
	private JList listeOp;
	private DefaultListModel listModel;
	private JTextField tfSomme;
	private JButton btnCrediter;
	private JButton btnDebiter;
	private JLabel lblFact;
	private JButton btnFacturerDecou;
	private ChampConsult chSolde;

	public DetailCli(Banque laBanque, int numCpt) {
		cptActuel = laBanque.getCompte(numCpt);

		setSize(700, 500);
		setTitle("Détail du compte n°" + numCpt);

		JPanel panGestion = new JPanel(new GridLayout(10, 1, 0, 10));
		chNumCpt = new ChampConsult("Numéro de compte", numCpt);
		panGestion.add(chNumCpt);

		chSolde = new ChampConsult("Solde", cptActuel.getSolde() + "€");
		panGestion.add(chSolde);

		chTypeCpt = new ChampConsult("Type de compte", cptActuel.getTypeCpt());
		panGestion.add(chTypeCpt);

		chNomCli = new ChampBouton(this, "Propriétaire", cptActuel.getProprio());
		panGestion.add(chNomCli);

		// Certains comptes ne doivent pas avoir la possibilité de changer leur
		// découvert max
		String typeActuel = cptActuel.getTypeCpt();
		if (typeActuel != "Adolescent" && typeActuel != "Associatif") {
			chDecMax = new ChampBouton(this, "Découvert maximum (en €)",
					cptActuel.getDecouvertMax());
			panGestion.add(chDecMax);
		}

		JPanel panFactDecou = new JPanel(new GridLayout(1, 3, 10, 0));
		panFactDecou.add(new JLabel("À facturer"));
		lblFact = new JLabel(cptActuel.getFactureVirtuelle() + "€");
		panFactDecou.add(lblFact);
		btnFacturerDecou = new JButton("Facturer");
		btnFacturerDecou.addActionListener(this);
		panFactDecou.add(btnFacturerDecou);

		panGestion.add(panFactDecou);

		JPanel panCredDeb = new JPanel(new GridLayout(1, 4, 10, 0));
		panCredDeb.add(new JLabel("Somme (en €)"));
		tfSomme = new JTextField();
		panCredDeb.add(tfSomme);
		btnDebiter = new JButton("Débiter");
		btnDebiter.addActionListener(this);
		panCredDeb.add(btnDebiter);
		btnCrediter = new JButton("Créditer");
		btnCrediter.addActionListener(this);
		panCredDeb.add(btnCrediter);

		panGestion.add(panCredDeb);

		add(panGestion);

		listModel = new DefaultListModel();
		listeOp = new JList();
		JScrollPane scrollPane = new JScrollPane(listeOp);
		listeOp.setModel(listModel);
		majListe();

		add(scrollPane, BorderLayout.SOUTH);

		setVisible(true);
	}

	private void majListe() {
		listModel.removeAllElements();
		ArrayList<Operation> alOp = cptActuel.getJournal();
		for (Operation op : alOp)
			listModel.addElement(op);
	}

	public void maj() {
		cptActuel.setProprio(chNomCli.getTf());
		cptActuel.setDecouvertMax(Float.parseFloat(chDecMax.getTf()));
		majListe();
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		Object objSource = ae.getSource();

		if (objSource == btnCrediter || objSource == btnDebiter) {
			if (VerifFormat.estChiffre(tfSomme.getText())) {
				float somme = Float.parseFloat(tfSomme.getText());
				
				if(objSource == btnCrediter)
					cptActuel.crediter(somme);
				else
					cptActuel.debiter(somme);
			}
			else
				JOptionPane.showMessageDialog(this,
						"Vous devez saisir une somme correcte.",
						"Erreur de saisie", JOptionPane.ERROR_MESSAGE);
		}
	}
}
