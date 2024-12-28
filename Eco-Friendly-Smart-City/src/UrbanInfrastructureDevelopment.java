import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import java.io.File;
import java.io.Serializable;
import java.util.*;

public class UrbanInfrastructureDevelopment implements Serializable {
	static final long serialVersionUID = 88L;

	public void printSchedule(List<Project> list) {
		for (Project p : list) {
			p.printSchedule(p.getEarliestSchedule());
		}
	}

	public List<Project> readXML(String file) {
		List<Project> list = new ArrayList<>();

		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new File(file));
			doc.getDocumentElement().normalize();

			NodeList projectList = doc.getElementsByTagName("Project");
			for (int i = 0; i < projectList.getLength(); i++) {
				Node projectNode = projectList.item(i);
				if (projectNode.getNodeType() == Node.ELEMENT_NODE) {
					Element projectElement = (Element) projectNode;
					String projectName = projectElement.getElementsByTagName("Name").item(0).getTextContent();

					NodeList taskList = projectElement.getElementsByTagName("Task");
					List<Task> tasks = new ArrayList<>();
					for (int j = 0; j < taskList.getLength(); j++) {
						Element taskElement = (Element) taskList.item(j);
						int taskID = Integer.parseInt(taskElement.getElementsByTagName("TaskID").item(0).getTextContent());
						String description = taskElement.getElementsByTagName("Description").item(0).getTextContent();
						int duration = Integer.parseInt(taskElement.getElementsByTagName("Duration").item(0).getTextContent());

						List<Integer> dependencies = new ArrayList<>();
						NodeList dependenciesList = taskElement.getElementsByTagName("DependsOnTaskID");
						for (int k = 0; k < dependenciesList.getLength(); k++) {
							String dependency = dependenciesList.item(k).getTextContent();
							if (!dependency.isEmpty()) {
								dependencies.add(Integer.parseInt(dependency));
							}
						}
						tasks.add(new Task(taskID, description, duration, dependencies));
					}
					list.add(new Project(projectName, tasks));
				}
			}
		} catch (Exception ignored) {
		}

		return list;
	}
}