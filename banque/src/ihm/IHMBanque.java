package ihm;

import gestion.Banque;
import gestion.Compte;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Set;

import javax.swing.*;

public class IHMBanque extends JFrame implements ActionListener {
        private static Banque maBanque;
        private JList listCli;
        private JButton btnOK;
        private JButton btnAjouter;
        private JCheckBox chbDecouvert;
        private DefaultListModel listModel;

        public IHMBanque(Banque uneBanque) {
                setTitle("Comptes client de la banque " + uneBanque.getNom());
                setLocation(50, 50);
                setSize(400, 400);
                setDefaultCloseOperation(IHMBanque.EXIT_ON_CLOSE);

                listModel = new DefaultListModel();
                listCli = new JList();
                listCli.setModel(listModel);
                JScrollPane scrollPane = new JScrollPane(listCli);

                majListe(false);

                JPanel pGrid = new JPanel(new BorderLayout());

        		pGrid.add(scrollPane);
        		chbDecouvert = new JCheckBox(
        				"Afficher uniquement les comptes à découvert");
        		chbDecouvert.addActionListener(this);
        		pGrid.add(chbDecouvert, BorderLayout.SOUTH);

        		add(pGrid);

        		JPanel pGridBtn = new JPanel(new GridLayout(1, 2, 10, 0));
        		btnAjouter = new JButton("Ajouter");
        		btnAjouter.addActionListener(this);
        		pGridBtn.add(btnAjouter);
        		btnOK = new JButton("Modifier");
        		btnOK.addActionListener(this);
        		pGridBtn.add(btnOK);
        		
        		add(pGridBtn, BorderLayout.SOUTH);

        		setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
                Object objSource = ae.getSource();
                if (objSource == btnOK) {
                        if (listCli.getSelectedIndex() != -1)
                                new DetailCompte(this, maBanque, Integer.parseInt(listCli
                                                .getSelectedValue().toString().split(" - ")[0]));
                        else
                                JOptionPane.showMessageDialog(this,
                                                "Vous devez sélectionner un compte dans la liste.",
                                                "Erreur", JOptionPane.ERROR_MESSAGE);
                } else if (objSource == chbDecouvert) {
                        listModel.removeAllElements();
                        majListe(chbDecouvert.isSelected());
                        listCli.setModel(listModel);
                } else if (objSource == btnAjouter) {
                        new AjouterCompte(this, maBanque);
                }
        }

        public void majListe(boolean aDecouvert) {
                listModel.removeAllElements();
                HashMap<Integer, Compte> listeCpt = aDecouvert ? maBanque
                                .listeDecouverts() : maBanque.getListeCompte();
                Set<Integer> ks = listeCpt.keySet();
                for (int k : ks) {
                        Compte cptCourant = listeCpt.get(k);
                        listModel.addElement(k + " - " + cptCourant.getProprio());
                }
        }

        public JCheckBox getChbDecouvert() {
                return chbDecouvert;
        }

        public static void main(String[] args) {

                maBanque = new Banque("BNP");
                new IHMBanque(maBanque);
        }
}

