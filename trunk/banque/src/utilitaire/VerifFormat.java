package utilitaire;

public class VerifFormat {

	//méthode utilitaire -> class spéciale
	public static boolean estChiffre(String s) {
		try {
			Float.parseFloat(s);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}
}
