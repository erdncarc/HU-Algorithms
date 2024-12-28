import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
	public static void main(String[] args) throws IOException {

		// MISSION POWER GRID OPTIMIZATION BELOW \\

		BufferedReader br = new BufferedReader(new FileReader(args[0]));
		ArrayList<Integer> energyDemands = new ArrayList<>();
		for (String str : br.readLine().split(" ")) {
			energyDemands.add(Integer.parseInt(str));
		}

		PowerGridOptimization pgo = new PowerGridOptimization(energyDemands);
		OptimalPowerGridSolution pgs = pgo.getOptimalPowerGridSolutionDP();

		int demanded = 0;
		for (int num : energyDemands) demanded += num;
		int satisfied = pgs.getmaxNumberOfSatisfiedDemands();
		ArrayList<Integer> hours = pgs.getHoursToDischargeBatteriesForMaxEfficiency();

		System.out.println("##MISSION POWER GRID OPTIMIZATION##");
		System.out.println("The total number of demanded gigawatts: " + demanded);
		System.out.println("Maximum number of satisfied gigawatts: " + satisfied);
		System.out.print("Hours at which the battery bank should be discharged: ");
		for (int i = 0; i < hours.size(); i++) System.out.print(hours.get(i) + ((i < hours.size() - 1) ? ", " : ""));
		System.out.println("\nThe number of unsatisfied gigawatts: " + (demanded - satisfied));
		System.out.println("##MISSION POWER GRID OPTIMIZATION COMPLETED##");

		// MISSION ECO-MAINTENANCE BELOW \\

		br = new BufferedReader(new FileReader(args[1]));
		energyDemands = new ArrayList<>();
		String[] features = br.readLine().split(" ");
		for (String str : br.readLine().split(" ")) {
			energyDemands.add(Integer.parseInt(str));
		}

		OptimalESVDeploymentGP esv = new OptimalESVDeploymentGP(energyDemands);
		int minNum = esv.getMinNumESVsToDeploy(Integer.parseInt(features[0]), Integer.parseInt(features[1]));

		System.out.println("##MISSION ECO-MAINTENANCE##");
		System.out.println(minNum == -1 ? "Warning: Mission Eco-Maintenance Failed." : "The minimum number of ESVs to deploy: " + minNum);
		for (int i = 1; i < minNum + 1; i++) System.out.println("ESV " + i + " tasks: " + esv.getMaintenanceTasksAssignedToESVs().get(i - 1));
		System.out.println("##MISSION ECO-MAINTENANCE COMPLETED##");
	}
}