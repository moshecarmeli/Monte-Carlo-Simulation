import org.junit.jupiter.api.Test;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This test suite verifies that given a predictable Rate of Return that the calculation performed is what we expect
 * The gaussian distributed is additionally verified using histograms in resources folder
 */
public class MonteCarloSimulatorTests {
    @Test
    void verifyPredictableResults() {
        Portfolio riskyPortfolio = new Portfolio(0, 9.4324);
        Portfolio conservativePortfolio = new Portfolio( 0, 6.189);
        ArrayList<Portfolio> portfolios = new ArrayList<>();
        portfolios.add(riskyPortfolio);
        portfolios.add(conservativePortfolio);

        MonteCarloSimulator monteCarloSimulator = new MonteCarloSimulator();
        monteCarloSimulator.comparePortfolios(10000, 20, 100000, portfolios);

        // Want to limit decimal places since we're comparing against a manual calculation
        DecimalFormat truncateDecimal = new DecimalFormat("#.##");
        truncateDecimal.setRoundingMode(RoundingMode.FLOOR);

        // Since standard deviation is 0, all results will be equal to ((1 + 0.094324)/(1 + 0.035)) ** 20 * 100000 = 304867.74
        assertEquals(Double.parseDouble(truncateDecimal.format(riskyPortfolio.getLastSimulation().getMedian())), 304867.74);
        assertEquals(Double.parseDouble(truncateDecimal.format(riskyPortfolio.getLastSimulation().getTop10())), 304867.74);
        assertEquals(Double.parseDouble(truncateDecimal.format(riskyPortfolio.getLastSimulation().getWorst10())), 304867.74);

        // Since standard deviation is 0, all results will be equal to ((1 + 0.06189)/(1 + 0.035)) ** 20 * 100000 = 167025.82
        assertEquals(Double.parseDouble(truncateDecimal.format(conservativePortfolio.getLastSimulation().getMedian())), 167025.82);
        assertEquals(Double.parseDouble(truncateDecimal.format(conservativePortfolio.getLastSimulation().getTop10())), 167025.82);
        assertEquals(Double.parseDouble(truncateDecimal.format(conservativePortfolio.getLastSimulation().getWorst10())), 167025.82);
    }
}
