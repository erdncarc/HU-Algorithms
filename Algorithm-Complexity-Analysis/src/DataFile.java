import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DataFile {
	public static int[] getData(String fileName) {
		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			return reader.lines().skip(1).mapToInt(line -> Integer.parseInt(line.split(",")[6])).toArray();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
}