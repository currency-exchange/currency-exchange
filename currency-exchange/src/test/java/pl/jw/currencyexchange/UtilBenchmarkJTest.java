package pl.jw.currencyexchange;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.MethodRule;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;
import com.carrotsearch.junitbenchmarks.annotation.AxisRange;
import com.carrotsearch.junitbenchmarks.annotation.BenchmarkHistoryChart;
import com.carrotsearch.junitbenchmarks.annotation.BenchmarkMethodChart;
import com.carrotsearch.junitbenchmarks.annotation.LabelType;

@AxisRange(min = 0, max = 1)
@BenchmarkMethodChart(filePrefix = "benchmark-lists")
@BenchmarkHistoryChart(labelWith = LabelType.CUSTOM_KEY, maxRuns = 20)
public class UtilBenchmarkJTest {
	@Rule
	public MethodRule benchmarkRun = new BenchmarkRule();

	@Before
	public void setUp() {

		System.setProperty("jub.consumers", "CONSOLE,XML");
		System.setProperty("jub.xml.file", "target/surefire-reports/jub.xml");
	}

	@BenchmarkOptions(benchmarkRounds = 10000, concurrency = BenchmarkOptions.CONCURRENCY_AVAILABLE_CORES, warmupRounds = 10)
	@Test
	public void priceToString_Float() {
		String str = Util.priceToString(BigDecimal.valueOf(1.1));
		String bla = "+@#4" + "2352345234" + "3423452345";

		for (int i = 0; i < 500; i++) {
			bla += "435345";
		}
	}
}