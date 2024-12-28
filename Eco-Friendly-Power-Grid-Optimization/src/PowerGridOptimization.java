import java.util.ArrayList;
import java.util.Arrays;

public class PowerGridOptimization {
	private final ArrayList<Integer> energyDemands;

	public PowerGridOptimization(ArrayList<Integer> energyDemands) {
		this.energyDemands = energyDemands;
	}

	public ArrayList<Integer> getAmountOfEnergyDemandsArrivingPerHour() {
		return energyDemands;
	}

	public OptimalPowerGridSolution getOptimalPowerGridSolutionDP() {
		int n = energyDemands.size();
		OptimalPowerGridSolution[] pgs = new OptimalPowerGridSolution[n];
		Arrays.fill(pgs, new OptimalPowerGridSolution());

		pgs[0].setmaxNumberOfSatisfiedDemands(0);
		pgs[0].setHoursToDischargeBatteriesForMaxEfficiency(new ArrayList<>());

		for (int j = 1; j < n; j++) {
			pgs[j] = createOptimalPowerGridSolution(pgs, j);
		}

		return createOptimalPowerGridSolution(pgs, n);
	}

	public OptimalPowerGridSolution createOptimalPowerGridSolution(OptimalPowerGridSolution[] pgs, int number) {
		int maxSatisfiedDemands = 0;
		ArrayList<Integer> optimalHours = new ArrayList<>();

		for (int i = 0; i < number; i++) {
			int currentSatisfiedDemands = pgs[i].getmaxNumberOfSatisfiedDemands() + Math.min((number - i) * (number - i), energyDemands.get(number - 1));
			if (currentSatisfiedDemands > maxSatisfiedDemands) {
				maxSatisfiedDemands = currentSatisfiedDemands;
				optimalHours = new ArrayList<>(pgs[i].getHoursToDischargeBatteriesForMaxEfficiency());
				optimalHours.add(number);
			}
		}

		return new OptimalPowerGridSolution(maxSatisfiedDemands, optimalHours);
	}
}