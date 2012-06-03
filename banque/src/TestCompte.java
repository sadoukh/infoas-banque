import gestion.Banque;
import gestion.PersonneMorale;
import gestion.PersonnePhysique;

public class TestCompte {
	public static void main(String[] arg) {
		float decouvertMax = 100;

		PersonneMorale pm = new PersonneMorale("Roger", decouvertMax);
		System.out.println("-> etat initial du cpt pers morale\n" + pm + "\n");

		System.out.println(pm.crediter(1000));
		System.out.println("-> crediter 1000\n" + pm + "\n");

		System.out.println(pm.debiter(1100));
		System.out.println("-> debiter 1100\n" + pm + "\n");

		System.out.println(pm.debiter(3000));
		System.out.println("-> debiter 3000\n" + pm + "\n");

		pm.facturer();
		System.out.println("-> facturation du decouvert\n" + pm + "\n");

		System.out.println(pm.getJournal());

		PersonnePhysique pp = new PersonnePhysique("Robert", 10000,
				decouvertMax * 20);
		System.out
				.println("-> etat initial du cpt pers physique\n" + pp + "\n");

		System.out.println(pp.crediter(1000));
		System.out.println("-> crediter 1000\n" + pp + "\n");

		System.out.println(pp.debiter(1100));
		System.out.println("-> debiter 1100\n" + pp + "\n");

		pp.facturer();
		System.out.println("-> facturation du decouvert\n" + pp + "\n");

		System.out.println(pp.getJournal());

		Banque bnp = new Banque("BNP");
		bnp.ajouterCompte(pp);
		bnp.ajouterCompte(pm);
		System.out.println(bnp);

		bnp.supprimerCompte(2);
		System.out.println(bnp);
	}
}
