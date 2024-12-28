import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.List;

public class MissionGenesis {

	private MolecularData molecularDataHuman;
	private MolecularData molecularDataVitales;

	public MolecularData getMolecularDataHuman() {
		return molecularDataHuman;
	}

	public MolecularData getMolecularDataVitales() {
		return molecularDataVitales;
	}

	public void readXML(String file) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(file);
			document.getDocumentElement().normalize();

			molecularDataHuman = new MolecularData(processMolecules(document.getElementsByTagName("HumanMolecularData")));
			molecularDataVitales = new MolecularData(processMolecules(document.getElementsByTagName("VitalesMolecularData")));

		} catch (Exception ignored) {
		}
	}

	private List<Molecule> processMolecules(NodeList molecules) {
		ArrayList<Molecule> list = new ArrayList<>();

		for (int i = 0; i < molecules.getLength(); i++) {
			Node node = molecules.item(i);
			Element element = (Element) node;

			NodeList moleculeList = element.getElementsByTagName("Molecule");
			for (int m = 0; m < moleculeList.getLength(); m++) {
				Element molecule = (Element) moleculeList.item(m);
				String moleculeId = molecule.getElementsByTagName("ID").item(0).getTextContent();
				String bondStrength = molecule.getElementsByTagName("BondStrength").item(0).getTextContent();

				List<String> bondList = new ArrayList<>();
				NodeList bondsList = molecule.getElementsByTagName("MoleculeID");
				for (int b = 0; b < bondsList.getLength(); b++) {
					Element bond = (Element) bondsList.item(b);
					String bondId = bond.getTextContent();
					bondList.add(bondId);
				}

				Molecule particle = new Molecule(moleculeId, Integer.parseInt(bondStrength), bondList);
				list.add(particle);
			}
		}
		return list;
	}
}