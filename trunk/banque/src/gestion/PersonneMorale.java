package gestion;

public class PersonneMorale extends Compte implements Nommable {
	public PersonneMorale(String proprio, float decouvertMax) {
		super(proprio, decouvertMax);
	}

	public PersonneMorale(String proprio, float solde, float decouvertMax) {
		super(proprio, solde, decouvertMax);
	}

	public boolean debiter(float somme) {
		boolean ok = super.debiter(somme);
		
		if (ok && solde < 0) {
			float fact = (float) (0.1 * -solde);
			super.factureVirtuelle += fact;
			super.monJournal.ajouterOp(fact
					+ "€ ajoutés à la future facture pour cause de découvert.");
		}

		return ok;
	}

	@Override
	public String getTypeCpt() {
		return "Personne morale";
	}
}