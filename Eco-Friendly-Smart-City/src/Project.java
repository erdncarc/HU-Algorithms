import java.io.Serializable;
import java.util.*;

public class Project implements Serializable {
	static final long serialVersionUID = 33L;

	private final String name;
	private final List<Task> tasks;

	public Project(String name, List<Task> tasks) {
		this.name = name;
		this.tasks = tasks;
	}

	public int getProjectDuration() {
		return getEarliestSchedule()[tasks.size() - 1] + tasks.get(tasks.size() - 1).getDuration();
	}

	public int[] getEarliestSchedule() {
		int[] earliestSchedule = new int[tasks.size()];
		Arrays.fill(earliestSchedule, -1);

		tasks.stream().filter(task -> task.getDependencies().isEmpty()).forEach(task -> earliestSchedule[task.getTaskID()] = 0);

		boolean anyMiss = true;
		while (anyMiss) {
			anyMiss = false;
			for (Task task : tasks) {
				if (earliestSchedule[task.getTaskID()] == -1) {
					if (task.getDependencies().stream().allMatch(dep -> earliestSchedule[dep] != -1)) {
						int minHours = task.getDependencies().stream().mapToInt(i -> earliestSchedule[i] + tasks.get(i).getDuration()).max().orElse(0);
						earliestSchedule[task.getTaskID()] = minHours;
						anyMiss = true;
					}
				}
			}
		}

		return earliestSchedule;
	}

	public static void printlnDash(int limit, char symbol) {
		for (int i = 0; i < limit; i++) System.out.print(symbol);
		System.out.println();
	}

	public void printSchedule(int[] schedule) {
		int limit = 65;
		char symbol = '-';
		printlnDash(limit, symbol);
		System.out.println(String.format("Project name: %s", name));
		printlnDash(limit, symbol);

		// Print header
		System.out.println(String.format("%-10s%-45s%-7s%-5s", "Task ID", "Description", "Start", "End"));
		printlnDash(limit, symbol);
		for (int i = 0; i < schedule.length; i++) {
			Task t = tasks.get(i);
			System.out.println(String.format("%-10d%-45s%-7d%-5d", i, t.getDescription(), schedule[i], schedule[i] + t.getDuration()));
		}
		printlnDash(limit, symbol);
		System.out.println(String.format("Project will be completed in %d days.", tasks.get(schedule.length - 1).getDuration() + schedule[schedule.length - 1]));
		printlnDash(limit, symbol);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Project project = (Project) o;

		int equal = 0;

		for (Task otherTask : ((Project) o).tasks) {
			if (tasks.stream().anyMatch(t -> t.equals(otherTask))) {
				equal++;
			}
		}

		return name.equals(project.name) && equal == tasks.size();
	}
}