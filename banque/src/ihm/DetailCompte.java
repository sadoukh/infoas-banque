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

public class DetailCompte extends JFrame implements ActionListener {
	private IHMBanque parent;
	private Banque laBanque;
	private Compte cptActuel;
	private ChampConsult chNumCpt;
	private ChampConsult chSolde;
	private ChampConsult chTypeCpt;
	private ChampBouton chNomCli;
	private ChampBouton chDecMax;
	private DefaultListModel listModel;
	private JTextField tfSomme;
	private JLabel lblFact;
	private JButton btnCrediter;
	private JButton btnDebiter;
	private JButton btnFacturerDecou;
	private JButton btnSupprimer;
	private int numCpt;
	private boolean decouvertAutorise;

	public DetailCompte(IHMBanque parent, Banque laBanque, int numCpt) {
		cptActuel = laBanque.getCompte(numCpt);
		this.laBanque = laBanque;
		this.numCpt = numCpt;
		this.parent = parent;

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
		if (decouvertAutorise = (cptActuel.getTypeCpt() != "Adolescent" && cptActuel
				.getTypeCpt() != "Association")) {
			chDecMax = new ChampBouton(this, "Découvert maximum (en €)",
					cptActuel.getDecouvertMax());
			panGestion.add(chDecMax);

			JPanel panFactDecou = new JPanel(new GridLayout(1, 3, 8, 0));
			panFactDecou.add(new JLabel("À facturer"));
			lblFact = new JLabel(cptActuel.getFactureVirtuelle() + "€");
			panFactDecou.add(lblFact);
			btnFacturerDecou = new JButton("Facturer");
			btnFacturerDecou.addActionListener(this);
			panFactDecou.add(btnFacturerDecou);

			panGestion.add(panFactDecou);
		} else {
			chDecMax = null;
			lblFact = null;
			btnFacturerDecou = null;
		}

		JPanel panCredDeb = new JPanel(new GridLayout(1, 4, 10, 0));
		panCredDeb.add(new JLabel("Somme (en €)"));

		tfSomme = new JTextField();
		panCredDeb.add(tfSomme);

		btnCrediter = new JButton("Créditer");
		btnCrediter.addActionListener(this);
		panCredDeb.add(btnCrediter);

		btnDebiter = new JButton("Débiter");
		btnDebiter.addActionListener(this);
		panCredDeb.add(btnDebiter);

		panGestion.add(panCredDeb);

		add(panGestion);

		listModel = new DefaultListModel();
		JList listeOp = new JList();
		JScrollPane scrollPane = new JScrollPane(listeOp);
		listeOp.setModel(listModel);
		majListe();

		btnSupprimer = new JButton("Supprimer le compte");
		btnSupprimer.addActionListener(this);
		panGestion.add(btnSupprimer);

		add(scrollPane, BorderLayout.SOUTH);

		setVisible(true);
	}

	private void majListe() {
		listModel.removeAllElements();
		ArrayList<Operation> alOp = cptActuel.getJournal();

		for (int cpt = alOp.size() - 1; cpt >= 0; cpt--)
			listModel.addElement(alOp.get(cpt));
	}

	private void majSolde() {
		chSolde.setLblTexte(cptActuel.getSolde() + "€");
	}

	public void maj() {
		cptActuel.setProprio(chNomCli.getTf());
		if (decouvertAutorise)
			cptActuel.setDecouvertMax(Float.parseFloat(chDecMax.getTf()));
		majListe();
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		Object objSource = ae.getSource();

		if (objSource == btnSupprimer) {
			if (JOptionPane.showConfirmDialog(this,
					"Voulez-vous supprimer ce compte ?",
					"Confirmation de suppression", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				if (laBanque.supprimerCompte(numCpt)) {
					parent.majListe(parent.getChbDecouvert().isSelected());
					dispose();
				} else {
					JOptionPane
							.showMessageDialog(
									this,
									"Vous ne pouvez supprimer un compte que si son solde est nul",
									"Erreur", JOptionPane.ERROR_MESSAGE);
				}
			}
		}

		if (objSource == btnCrediter || objSource == btnDebiter) {
			if (VerifFormat.estChiffre(tfSomme.getText())) {
				float somme = Float.parseFloat(tfSomme.getText());

				if (objSource == btnCrediter)
					cptActuel.crediter(somme);
				else
					cptActuel.debiter(somme);

			} else
				JOptionPane.showMessageDialog(this,
						"Vous devez saisir une somme correcte.",
						"Erreur de saisie", JOptionPane.ERROR_MESSAGE);
		}

		if (objSource == btnFacturerDecou) {
			cptActuel.facturer();
		}

		if (decouvertAutorise) {
			lblFact.setText(cptActuel.getFactureVirtuelle() + "€");
		}

		majSolde();
		majListe();
	}
}
