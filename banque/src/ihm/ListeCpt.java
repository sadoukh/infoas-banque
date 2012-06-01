package ihm;

import gestion.Banque;
import gestion.Compte;
import gestion.PersonneMorale;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Set;

import javax.swing.*;

public class ListeCpt extends JFrame implements ActionListener {
	private static Banque maBanque;
	private JList listCli;
	private JButton btnOK;
	private JButton btnAjouter;
	private JCheckBox chbDecouvert;
	private DefaultListModel listModel;

	public ListeCpt(Banque uneBanque) {
		setTitle("Comptes client de la banque " + uneBanque.getNom());
		setLocation(50, 50);
		setSize(400, 400);

		listModel = new DefaultListModel();
		listCli = new JList();
		listCli.setModel(listModel);

		creerListe(false);

		btnAjouter = new JButton("Ajouter un compte");
		btnAjouter.addActionListener(this);
		add(btnAjouter, BorderLayout.NORTH);
		add(listCli);

		JPanel pGrid = new JPanel(new GridLayout(2, 1));
		chbDecouvert = new JCheckBox(
				"Afficher uniquement les comptes à découvert");
		chbDecouvert.addActionListener(this);
		btnOK = new JButton("OK");
		btnOK.addActionListener(this);
		pGrid.add(chbDecouvert);
		pGrid.add(btnOK);
		add(pGrid, BorderLayout.SOUTH);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		Object objSource = ae.getSource();
		if (objSource == btnOK) {
			if (listCli.getSelectedIndex() != -1)
				new DetailCli(maBanque, Integer.parseInt(listCli
						.getSelectedValue().toString().split(" - ")[0]));
			else
				JOptionPane.showMessageDialog(this,
						"Vous devez sélectionner un compte dans la liste.",
						"Erreur", JOptionPane.ERROR_MESSAGE);
		} else if (objSource == chbDecouvert) {
			listModel.removeAllElements();
			creerListe(chbDecouvert.isSelected());
			listCli.setModel(listModel);
		} else if (objSource == btnAjouter) {
			new AjouterCompte();
		}
	}

	public void creerListe(boolean aDecouvert) {
		HashMap<Integer, Compte> listeCpt = aDecouvert ? maBanque
				.listeDecouverts() : maBanque.getListeCompte();
		Set<Integer> ks = listeCpt.keySet();
		for (int k : ks) {
			Compte cptCourant = listeCpt.get(k);
			listModel.addElement(k + " - " + cptCourant.getProprio());
		}
	}

	public static void main(String[] args) {
		maBanque = new Banque("BNP");
		maBanque.ajouterCompte(new PersonneMorale("compte de test", 12));
		PersonneMorale pm2 = new PersonneMorale("compte de test 2", 12);
		maBanque.ajouterCompte(pm2);
		pm2.debiter(10);
		new ListeCpt(maBanque);
	}
}
