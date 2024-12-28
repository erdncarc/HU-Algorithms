import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class OptimalESVDeploymentGP {
	private final ArrayList<Integer> energyDemands;
	private final ArrayList<ArrayList<Integer>> assignedESVs = new ArrayList<>();

	public OptimalESVDeploymentGP(ArrayList<Integer> energyDemands) {
		this.energyDemands = energyDemands;
	}

	public ArrayList<ArrayList<Integer>> getMaintenanceTasksAssignedToESVs() {
		return assignedESVs;
	}

	public int getMinNumESVsToDeploy(int availableESVs, int capacityESVs) {
		energyDemands.sort(Collections.reverseOrder());

		int[] remainingCapacities = new int[availableESVs];
		Arrays.fill(remainingCapacities, capacityESVs);

		for (int energyDemand : energyDemands) {
			if (energyDemand > capacityESVs) return -1;
			boolean taskFits = false;

			for (int i = 0; i < availableESVs; i++) {
				if (remainingCapacities[i] >= energyDemand) {
					if (remainingCapacities[i] == capacityESVs) assignedESVs.add(new ArrayList<>(Collections.singletonList(energyDemand)));
					else assignedESVs.get(i).add(energyDemand);

					remainingCapacities[i] -= energyDemand;
					taskFits = true;
					break;
				}
			}

			if (!taskFits) return -1;
		}

		return assignedESVs.size();
	}
}