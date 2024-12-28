public class Bond {

	private final Molecule to;
	private final Molecule from;
	private final Double weight;
	boolean marked = false;

	public Bond(Molecule to, Molecule from, Double weight) {
		if (from.compareTo(to) > 0) {
			Molecule temp = from;
			from = to;
			to = temp;
		}
		this.to = to;
		this.from = from;
		this.weight = weight;
	}

	public Molecule getTo() {
		return to;
	}

	public Molecule getFrom() {
		return from;
	}

	public Double getWeight() {
		return weight;
	}
}