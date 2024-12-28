import java.util.*;
import java.util.stream.Collectors;

public class MissionSynthesis {

	private final List<MolecularStructure> humanStructures;
	private final ArrayList<MolecularStructure> diffStructures;

	public MissionSynthesis(List<MolecularStructure> humanStructures, ArrayList<MolecularStructure> diffStructures) {
		this.humanStructures = humanStructures;
		this.diffStructures = diffStructures;
	}

	public List<Bond> synthesizeSerum() {
		List<Molecule> list = humanStructures.stream().map(MolecularStructure::getMoleculeWithWeakestBondStrength).collect(Collectors.toList());
		System.out.println("Typical human molecules selected for synthesis: " + list);

		List<Molecule> tempList = diffStructures.stream().map(MolecularStructure::getMoleculeWithWeakestBondStrength).collect(Collectors.toList());
		System.out.println("Vitales molecules selected for synthesis: " + tempList);

		list.addAll(tempList);
		List<Bond> serumV1 = new ArrayList<>();
		for (int f = 0; f < list.size(); f++) {
			for (int t = f + 1; t < list.size(); t++) {
				serumV1.add(new Bond(list.get(t), list.get(f), (double) (list.get(t).getBondStrength() + list.get(f).getBondStrength()) / 2));
			}
		}
		serumV1.sort(Comparator.comparingDouble(Bond::getWeight));

		List<Bond> serum = new ArrayList<>();
		for (Bond bond : serumV1) {
			if (checkBondStrength(bond.getFrom(), bond.getTo(), serum)) serum.add(bond);
			for (Bond b : serum) b.marked = false;
		}
		return serum;
	}

	public boolean checkBondStrength(Molecule m1, Molecule m2, List<Bond> serum) {
		for (Bond bond : serum) {
			if (bond.marked) continue;
			Molecule from = bond.getFrom();
			Molecule to = bond.getTo();

			if (from.equals(m1) || to.equals(m1)) {
				if (from.equals(m2) || to.equals(m2)) {
					return false;
				} else if (from.equals(m1)) {
					bond.marked = true;
					if (!checkBondStrength(to, m2, serum)) return false;
				} else if (to.equals(m1)) {
					bond.marked = true;
					if (!checkBondStrength(from, m2, serum)) return false;
				}
			}
		}
		return true;
	}

	public void printSynthesis(List<Bond> serum) {
		System.out.println("Synthesizing the serum...");
		double total = 0;
		for (Bond bond : serum) {
			System.out.println("Forming a bond between " + bond.getFrom() + " - " + bond.getTo() + " with strength " + String.format("%.2f", bond.getWeight()));
			total += bond.getWeight();
		}
		System.out.println("The total serum bond strength is " + String.format("%.2f", total));
	}
}