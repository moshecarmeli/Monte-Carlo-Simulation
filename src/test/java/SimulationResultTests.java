import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This test suite verifies that the position selected for median/best/worst are what we expect
 */
public class SimulationResultTests {
    @Test
    void verifyMedianBestAndWorst() {
        List<Double> simulatedBalances = IntStream.range(0,10).asDoubleStream().boxed().collect(Collectors.toList());
        SimulationResult simulationResult = new SimulationResult(simulatedBalances, 10);
        assertEquals(4.5, simulationResult.getMedian()); // values 0 through 9, the median is (4 + 5)/2 = 4.5
        assertEquals(8, simulationResult.getTop10()); // index 8 corresponds to 9th element out of 10 total
        assertEquals(0, simulationResult.getWorst10()); // index 0 corresponds to element 1 out of 10 total
    }

    @Test
    void verifyMedianOddVsEven() {
        // Even Case
        List<Double> simulatedBalances = IntStream.range(0,10).asDoubleStream().boxed().collect(Collectors.toList());
        SimulationResult simulationResult = new SimulationResult(simulatedBalances, 10);
        assertEquals(4.5, simulationResult.getMedian()); // values 0 through 10, the median is (4 + 5)/2 = 4.5

        // Odd Case
        List<Double> simulatedBalances2 = IntStream.range(0,9).asDoubleStream().boxed().collect(Collectors.toList());
        SimulationResult simulationResult2 = new SimulationResult(simulatedBalances2, 9);
        assertEquals(4, simulationResult2.getMedian()); // values 0 through 8, the median is 4
    }
}
