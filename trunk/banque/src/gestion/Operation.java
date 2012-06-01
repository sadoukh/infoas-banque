package gestion;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Operation {
	private Date dateOp;
	private String libOp;
	private SimpleDateFormat sdf;

	public Operation(String libOp) {
		this.dateOp = new Date();
		this.libOp = libOp;
		this.sdf = new SimpleDateFormat("dd/MM/yyyy à HH:mm:ss");
	}

	public String toString() {
		return "Le " + sdf.format(this.dateOp) + " : " + this.libOp;
	}
}
