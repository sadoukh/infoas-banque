package gestion;

import java.util.ArrayList;

public class Journal {
	private ArrayList<Operation> listeOp;

	public Journal() {
		this.listeOp = new ArrayList<Operation>();
	}

	@Override
	public String toString() {
		String s = "";

		for (Operation op : this.listeOp)
			s += op + "\n";

		return s;
	}

	public ArrayList<Operation> getListe() {
		return listeOp;
	}

	public void ajouterOp(String libOp) {
		this.listeOp.add(new Operation(libOp));
	}
}
