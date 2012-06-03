package gestion;

public class Association extends PersonneMorale implements Nommable {
	public Association(String proprio, float solde) {
		super(proprio, solde, 0);
	}

	@Override
	public String getTypeCpt() {
		return "Association";
	}

	public void setDecouvertMax(float decouvertMax) {
		if (decouvertMax != 0)
			System.out
					.println("Vous ne pouvez pas modifier votre découvert maximum car vous avez un compte associatif.");
	}
}
