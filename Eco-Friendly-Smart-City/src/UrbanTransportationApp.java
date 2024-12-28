import java.io.Serializable;
import java.util.*;

class UrbanTransportationApp implements Serializable {
	static final long serialVersionUID = 99L;

	private final HashMap<Station, List<Edge>> edges = new HashMap<>();
	private double trainSpeed;
	private double walkSpeed;

	public HyperloopTrainNetwork readHyperloopTrainNetwork(String file) {
		HyperloopTrainNetwork hyperloopTrainNetwork = new HyperloopTrainNetwork();
		hyperloopTrainNetwork.readInput(file);
		return hyperloopTrainNetwork;
	}

	public List<RouteDirection> getFastestRouteDirections(HyperloopTrainNetwork network) {
		trainSpeed = network.averageTrainSpeed;
		walkSpeed = network.averageWalkingSpeed;
		List<Station> stations = new ArrayList<>();

		stations.add(network.startPoint);
		stations.add(network.destinationPoint);
		for (TrainLine trainLine : network.lines) {
			stations.addAll(trainLine.trainLineStations);
		}

		for (int i = 0; i < stations.size(); i++) {
			for (int j = i + 1; j < stations.size(); j++) {
				String[] iString = stations.get(i).description.split(" ");
				String[] jString = stations.get(j).description.split(" ");
				if (iString.length == 1 || jString.length == 1) {
					addEdge(stations.get(i), stations.get(j));
				} else if (!iString[0].equals(jString[0]) || Integer.parseInt(iString[iString.length - 1]) - Integer.parseInt(jString[jString.length - 1]) == -1) {
					addEdge(stations.get(i), stations.get(j));
				}
			}
		}

		for (Station station : stations) {
			station.time = Double.MAX_VALUE;
		}
		network.startPoint.time = 0.0;

		HashMap<Station, Double> check = new HashMap<>();
		check.put(network.startPoint, 0.0);
		while (!check.isEmpty()) {
			Station current = check.entrySet().stream().min(Map.Entry.comparingByValue()).orElseThrow().getKey();
			check.remove(current);

			if (current.equals(network.destinationPoint)) break;

			for (Edge edge : edges.get(current)) {
				double newTime = current.time + edge.time;
				if (newTime < edge.target.time) {
					edge.target.time = newTime;
					check.put(edge.target, newTime);
					edge.target.previous= current;
				}
			}
		}

		List<Station> path = new ArrayList<>();
		for (Station station = network.destinationPoint; station != null; station = station.previous) {
			path.add(station);
		}
		Collections.reverse(path);

		List<RouteDirection> routeDirections = new ArrayList<>();
		for (int i = 1; i < path.size(); i++) {
			Station previousStation = path.get(i - 1);
			double duration = edges.get(path.get(i)).stream().filter(edge -> edge.target.equals(previousStation)).mapToDouble(edge -> edge.time).findFirst().orElse(0);
			boolean trainRide = previousStation.description.split(" ")[0].equals(path.get(i).description.split(" ")[0]);
			routeDirections.add(new RouteDirection(previousStation.description, path.get(i).description, duration, trainRide));
		}
		return routeDirections;
	}

	public void addEdge(Station s1, Station s2) {
		double speed = (s1.description.split(" ")[0].equals(s2.description.split(" ")[0]) ? trainSpeed : walkSpeed);
		double time = Math.sqrt(Math.pow(s1.coordinates.x - s2.coordinates.x, 2) + Math.pow(s1.coordinates.y - s2.coordinates.y, 2)) / speed;

		if (!edges.containsKey(s1)) edges.put(s1, new ArrayList<>());
		edges.get(s1).add(new Edge(s2, time));

		if (!edges.containsKey(s2)) edges.put(s2, new ArrayList<>());
		edges.get(s2).add(new Edge(s1, time));
	}

	public void printRouteDirections(List<RouteDirection> directions) {
		double totalTime = 0;
		for (RouteDirection direction : directions) {
			totalTime += direction.duration;
		}

		System.out.println("The fastest route takes " + Math.round(totalTime) + " minute(s).");

		System.out.println("Directions\n----------");

		int index = 1;
		for (RouteDirection direction : directions) {
			System.out.print(index++);
			if (direction.trainRide) {
				System.out.println(". Get on the train from \"" + direction.startStationName + "\" to \"" + direction.endStationName + "\" for " + String.format("%.2f", direction.duration) + " minutes.");
			} else {
				System.out.println(". Walk from \"" + direction.startStationName + "\" to \"" + direction.endStationName + "\" for " + String.format("%.2f", direction.duration) + " minutes.");
			}
		}
	}

	private static class Edge {
		Station target;
		double time;

		public Edge(Station target, double time) {
			this.target = target;
			this.time = time;
		}
	}
}