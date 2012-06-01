package gestion;
import java.util.ArrayList;

public class Journal {
	private ArrayList<Operation> listeOp;

	public Journal() {
		this.listeOp = new ArrayList<Operation>();
	}

	public void ajouterOp(String libOp) {
		this.listeOp.add(new Operation(libOp));
	}
	
	public ArrayList<Operation> getListe() {
		return listeOp;
	}

	public String toString() {
		String s = "";

		for (Operation op : this.listeOp)
			s += op + "\n";

		return s;
	}
}
