package gestion;

import java.util.HashMap;
import java.util.Set;

public class Banque {
	private static int nbCpt = 0;
	private String nom;
	private HashMap<Integer, Compte> listeComptes;

	public Banque(String nom) {
		assert nom.length() > 0;

		this.nom = nom;
		listeComptes = new HashMap<Integer, Compte>();
	}

	@Override
	public String toString() {
		String s = "Liste des comptes de la banque " + nom + "\n";

		for (Integer i : listeComptes.keySet())
			s += "\nCompte numero " + i + listeComptes.get(i) + "\n";

		return s;
	}

	public HashMap<Integer, Compte> listeDecouverts() {
		Set<Integer> ks = listeComptes.keySet();
		HashMap<Integer, Compte> hmTemp = new HashMap<Integer, Compte>();
		
		for (int k : ks) {
			Compte cptCourant = listeComptes.get(k);
			if (cptCourant.soldeEstNegatif())
				hmTemp.put(k, cptCourant);
		}

		return hmTemp;
	}

	public void ajouterCompte(Compte cpt) {
		nbCpt += 1;
		listeComptes.put(nbCpt, cpt);
	}

	public String getNom() {
		return nom;
	}

	public HashMap<Integer, Compte> getListeCompte() {
		return listeComptes;
	}

	public boolean supprimerCompte(int numCpt) {
		boolean ok = listeComptes.get(numCpt).soldeEstNul();

		if (ok)
			listeComptes.remove(numCpt);

		return ok;
	}

	public Compte getCompte(int numCpt) {
		return listeComptes.get(numCpt);
	}
}
