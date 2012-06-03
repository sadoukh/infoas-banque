package gestion;

public class PersonnePhysique extends Compte implements Nommable {
	public PersonnePhysique(String proprio, float decouvertMax) {
		super(proprio, decouvertMax);
	}

	public PersonnePhysique(String proprio, float solde, float decouvertMax) {
		super(proprio, solde, decouvertMax);
	}

	public boolean debiter(float somme) {
		boolean ok = super.debiter(somme);

		if (ok && solde < 0) {
			super.factureVirtuelle += 10;
			super.monJournal.ajouterOp(super.factureVirtuelle
					+ "€ ajoutés à la future facture pour cause de découvert.");
		}

		return ok;
	}

	@Override
	public String getTypeCpt() {
		return "Personne physique";
	}
}
