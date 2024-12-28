public class FunctionTimer {
	long getTime(Runnable function, boolean isMillis) {
		long startTime = System.nanoTime();
		function.run();
		long endTime = System.nanoTime();

		return (isMillis ? ((endTime - startTime) / 1_000_000) : endTime - startTime);
	}
}