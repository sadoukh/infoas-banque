package gestion;
public class Ado extends PersonnePhysique  implements Nommable {
	public Ado(String proprio, float solde) {
		super(proprio, solde, 0);
	}
	
	public void setDecouvertMax(float decouvertMax) {
		if(decouvertMax != 0)		
			System.out.println("Vous ne pouvez pas modifier votre découvert maximum car vous avez un compte adolescent.");
	}

	@Override
	public String getTypeCpt() {
		return "Adolescent";
	}
}
