import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HyperloopTrainNetwork implements Serializable {
	static final long serialVersionUID = 11L;

	public double averageTrainSpeed;
	public final double averageWalkingSpeed = 1000 / 6.0;
	public int numTrainLines;
	public Station startPoint;
	public Station destinationPoint;
	public List<TrainLine> lines;

	public int getIntVar(String varName, String fileContent) {
		Pattern p = Pattern.compile("[\\t ]*" + varName + "[\\t ]*=[\\t ]*([0-9]+)");
		Matcher m = p.matcher(fileContent);
		if (m.find()) return Integer.parseInt(m.group(1));
		return 0;
	}

	public String getStringVar(String varName, String fileContent) {
		Pattern p = Pattern.compile("[\\t ]*" + varName + "[\\t ]*=[\\t ]*\"([^\"]+)\"");
		Matcher m = p.matcher(fileContent);
		if (m.find()) return m.group(1);
		return "";
	}

	public Double getDoubleVar(String varName, String fileContent) {
		Pattern p = Pattern.compile("[\\t ]*" + varName + "[\\t ]*=[\\t ]*([0-9]+(\\.[0-9]+)?)");
		Matcher m = p.matcher(fileContent);
		if (m.find()) return Double.parseDouble(m.group(1));
		return 0.0;
	}

	public Point getPointVar(String varName, String fileContent) {
		Pattern p = Pattern.compile("\\b" + varName + "\\s*=\\s*\\(\\s*(\\d+)\\s*,\\s*(\\d+)\\s*\\)");
		Matcher m = p.matcher(fileContent);
		if (m.find()) {
			int x = Integer.parseInt(m.group(1));
			int y = Integer.parseInt(m.group(2));
			return new Point(x, y);
		}
		return null;
	}

	public List<TrainLine> getTrainLines(String fileContent) {
		String name = getStringVar("train_line_name", fileContent);

		Pattern p = Pattern.compile("\\(\\s*(\\d+)\\s*,\\s*(\\d+)\\s*\\)");
		Matcher m = p.matcher(fileContent);

		int index = 1;
		List<Station> trainLineStations = new ArrayList<>();
		while (m.find()) {
			int x = Integer.parseInt(m.group(1));
			int y = Integer.parseInt(m.group(2));
			trainLineStations.add(new Station(new Point(x, y), name + " Line Station " + index++));
		}

		lines.add(new TrainLine(name, trainLineStations));
		return lines;
	}

	public void readInput(String file) {
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			String extraLine = "";
			lines = new ArrayList<>();

			while ((line = br.readLine()) != null) {
				if (line.contains("num_train_lines")) {
					numTrainLines = getIntVar("num_train_lines", line);

				} else if (line.contains("starting_point")) {
					startPoint = new Station(getPointVar("starting_point", line), "Starting Point");

				} else if (line.contains("destination_point")) {
					destinationPoint = new Station(getPointVar("destination_point", line), "Final Destination");

				} else if (line.contains("average_train_speed")) {
					averageTrainSpeed = (getDoubleVar("average_train_speed", line) * 1000) / 60;

				} else if (line.contains("train_line_name")) {
					extraLine = line;

				} else if (line.contains("train_line_stations")) {
					lines = getTrainLines(extraLine + line);

				}
			}
		} catch (Exception ignored) {
		}
	}
}