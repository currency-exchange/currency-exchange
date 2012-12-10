package pl.jw.currencyexchange;

import java.math.BigDecimal;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.MethodRule;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;

public class UtilBenchmarkJTest {
	@Rule
	public MethodRule benchmarkRun = new BenchmarkRule();

	@BenchmarkOptions(benchmarkRounds=10000, concurrency=BenchmarkOptions.CONCURRENCY_AVAILABLE_CORES, warmupRounds=10)
	@Test
	public void priceToString_Float() {
		String str = Util.priceToString(BigDecimal.valueOf(1.1));
		String bla = "+@#4"+"2352345234"+"3423452345";

		for (int i = 0; i < 2122; i++) {
			bla +="435345";
		}
	}
}