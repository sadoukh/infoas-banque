package gestion;

import java.util.ArrayList;

public abstract class Compte implements Nommable {
	protected String proprio;
	protected float solde;
	protected float decouvertMax;
	protected float factureVirtuelle;
	protected Journal monJournal;

	protected Compte(String proprio, float decouvertMax) {
		this(proprio, 0, decouvertMax);
	}

	protected Compte(String proprio, float solde, float decouvertMax) {
		this.proprio = proprio;
		this.solde = solde;
		this.decouvertMax = decouvertMax;
		monJournal = new Journal();
		monJournal.ajouterOp("Création du compte");
	}

	@Override
	public String toString() {
		return "\nProprietaire : " + proprio + "\nSolde actuel : " + solde
				+ "€\nDecouvert maximum autorise : " + decouvertMax + "€";
	}

	public boolean soldeEstNul() {
		return this.solde == 0;
	}

	public boolean soldeEstNegatif() {
		return this.solde < 0;
	}

	public float getSolde() {
		return solde;
	}

	public float getDecouvertMax() {
		return decouvertMax;
	}

	public float getFactureVirtuelle() {
		return factureVirtuelle;
	}

	public boolean crediter(float somme) {
		assert somme > 0;
		boolean ok = somme > 0;

		if (ok) {
			solde += somme;
			monJournal.ajouterOp("Le compte a été crédité de " + somme + "€");
		}

		return ok;
	}

	public boolean debiter(float somme) {
		assert somme > 0;

		boolean ok = solde - somme >= -decouvertMax;

		if (ok) {
			solde -= somme;
			monJournal.ajouterOp("Le compte a ete debite de " + somme + "€");
		} else {
			monJournal
					.ajouterOp("Débit refusé. La somme demandée dépasse le découvert maximum authorisé.");
		}

		return ok;
	}

	public String getProprio() {
		return proprio;
	}

	public void setProprio(String proprio) {
		// Pour éviter d'exécuter la méthode et de remplir le journal si rien ne
		// s'est passé
		if (!this.proprio.equals(proprio)) {
			this.proprio = proprio;
			monJournal
					.ajouterOp("Le compte a changé de propriétaire. Le nouveau propriétaire est "
							+ proprio);
		}
	}

	public void setDecouvertMax(float decouvertMax) {
		assert decouvertMax > 0;

		if (this.decouvertMax != decouvertMax) {
			if (decouvertMax > 0) {
				this.decouvertMax = decouvertMax;
				monJournal
						.ajouterOp("Le découvert maximum a été changé. Le nouveau découvert maximum est "
								+ decouvertMax);
			}
		}
	}

	// public boolean facturer() {
	// assert factureVirtuelle <= 0;
	//
	// boolean ok = factureVirtuelle > 0;
	//
	// if (ok) {
	// solde -= factureVirtuelle;
	// monJournal
	// .ajouterOp("Le compte a été facturé pour un decouvert.");
	// }
	//
	// return ok;
	// }

	public boolean facturer() {
		assert factureVirtuelle > 0;

		boolean ok = factureVirtuelle > 0;

		if (ok) {
			solde -= factureVirtuelle;
			monJournal.ajouterOp("Le compte a été facturé pour un decouvert.");
		}

		return ok;
	}

	public ArrayList<Operation> getJournal() {
		return monJournal.getListe();
	}
}
