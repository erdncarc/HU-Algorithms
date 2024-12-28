import java.util.HashSet;
import java.util.List;

public class Molecule implements Comparable {

	private final String id;
	private final int bondStrength;
	private final List<String> bonds;

	public Molecule(String id, int bondStrength, List<String> bonds) {
		this.id = id;
		this.bondStrength = bondStrength;
		this.bonds = bonds;
	}

	public String getId() {
		return id;
	}

	public int getBondStrength() {
		return bondStrength;
	}

	public List<String> getBonds() {
		return bonds;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Molecule molecule = (Molecule) o;
		return bondStrength == molecule.bondStrength && id.equals(molecule.id) && new HashSet<>(bonds).equals(new HashSet<>(((Molecule) o).getBonds()));
	}

	@Override
	public String toString() {
		return id;
	}

	@Override
	public int compareTo(Object o) {
		Integer ownId = Integer.parseInt(this.getId().substring(1));
		Integer oId = Integer.parseInt(((Molecule) o).getId().substring(1));
		return ownId.compareTo(oId);
	}
}