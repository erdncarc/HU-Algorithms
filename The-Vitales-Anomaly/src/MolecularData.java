import java.util.*;
import java.util.stream.Collectors;

public class MolecularData {

	private final List<Molecule> molecules;
	ArrayList<String> inStructure = new ArrayList<>();

	public MolecularData(List<Molecule> molecules) {
		this.molecules = molecules;
	}

	public List<Molecule> getMolecules() {
		return molecules;
	}

	public List<MolecularStructure> identifyMolecularStructures() {
		ArrayList<MolecularStructure> structures = new ArrayList<>();
		molecules.forEach(molecule -> {
			if (!inStructure.contains(molecule.getId())) {
				boolean bondsIn = structures.stream().filter(bond -> molecule.getBonds().stream().anyMatch(bond::hasMolecule)).findFirst().map(ms -> {
					addStructures(ms, molecule);
					return true;
				}).orElse(false);

				if (!bondsIn) {
					MolecularStructure ms = new MolecularStructure();
					addStructures(ms, molecule);
					structures.add(ms);
				}
			}
		});
		return structures;
	}

	private void addStructures(MolecularStructure ms, Molecule molecule) {
		ms.addMolecule(molecule);
		inStructure.add(molecule.getId());
		for (String bond : molecule.getBonds()) {
			if (!inStructure.contains(bond)) {
				molecules.stream().filter(m -> bond.equals(m.getId())).findFirst().ifPresent(m -> addStructures(ms, m));
			}
		}
	}

	public void printMolecularStructures(List<MolecularStructure> molecularStructures, String species) {
		System.out.println(molecularStructures.size() + " molecular structures have been discovered in " + species + ".");
		for (int i = 0; i < molecularStructures.size(); i++) {
			System.out.println("Molecules in Molecular Structure " + (i+1) + ": " + molecularStructures.get(i));
		}
	}

	public static ArrayList<MolecularStructure> getVitalesAnomaly(List<MolecularStructure> sourceStructures, List<MolecularStructure> targetStructures) {
		return targetStructures.stream().filter(targetStructure -> sourceStructures.stream().noneMatch(targetStructure::equals)).collect(Collectors.toCollection(ArrayList::new));
	}

	public void printVitalesAnomaly(List<MolecularStructure> molecularStructures) {
		System.out.println("Molecular structures unique to Vitales individuals:");
		molecularStructures.forEach(System.out::println);
	}
}