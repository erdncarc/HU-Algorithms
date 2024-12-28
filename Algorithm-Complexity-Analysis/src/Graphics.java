import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

import java.util.Arrays;

public class Graphics {
	Graphics(String name, int[] range, long[] array1, long[] array2, long[] array3, boolean isMillis) {
		try {
			Utilities.print(name, array1, array2, array3, isMillis);

			double[] x = Arrays.stream(range).asDoubleStream().toArray();
			XYChart chart = new XYChartBuilder().width(800).height(600).title(name).yAxisTitle("Time in " + (isMillis ? "Milliseconds" : "Nanoseconds")).xAxisTitle("Input Size").build();

			chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
			chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

			chart.addSeries((isMillis ? "Insertion Sort" : "Linear Search (Random Input Data)"), x, Arrays.stream(array1).asDoubleStream().toArray());
			chart.addSeries((isMillis ? "Merge Sort" : "Linear Search (Sorted Input Data)"), x, Arrays.stream(array2).asDoubleStream().toArray());
			chart.addSeries((isMillis ? "Counting Sort" : "Binary Search (Sorted Input Data)"), x, Arrays.stream(array3).asDoubleStream().toArray());

			BitmapEncoder.saveBitmap(chart, name + ".png", BitmapEncoder.BitmapFormat.PNG);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}