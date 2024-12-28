import java.util.ArrayList;

public class OptimalPowerGridSolution {
	private int maxNumberOfSatisfiedDemands;
	private ArrayList<Integer> hoursToDischargeBatteriesForMaxEfficiency;

	public OptimalPowerGridSolution(int maxNumberOfSatisfiedDemands, ArrayList<Integer> hoursToDischargeBatteriesForMaxEfficiency) {
		this.maxNumberOfSatisfiedDemands = maxNumberOfSatisfiedDemands;
		this.hoursToDischargeBatteriesForMaxEfficiency = hoursToDischargeBatteriesForMaxEfficiency;
	}

	public OptimalPowerGridSolution() {
	}

	public int getmaxNumberOfSatisfiedDemands() {
		return maxNumberOfSatisfiedDemands;
	}

	public void setmaxNumberOfSatisfiedDemands(int number) {
		maxNumberOfSatisfiedDemands = number;
	}

	public ArrayList<Integer> getHoursToDischargeBatteriesForMaxEfficiency() {
		return hoursToDischargeBatteriesForMaxEfficiency;
	}

	public void setHoursToDischargeBatteriesForMaxEfficiency(ArrayList<Integer> numbers) {
		hoursToDischargeBatteriesForMaxEfficiency = numbers;
	}
}