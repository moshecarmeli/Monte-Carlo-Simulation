import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Class that drives the heavy processing for this operation
 */
public class MonteCarloSimulator {
    private static final Random randomGenerator = new Random();
    private static final Double INFLATION_RATE = 0.035;

    /**
     * Runs the simulation for each portfolio and then prints out the results
     */
    public void comparePortfolios(int numberSimulations, int numberYears, double initialValue, Portfolio portfolio1, Portfolio portfolio2) {
        List<Double> portfolio1Results = runSimulation(numberSimulations, numberYears, initialValue, portfolio1.getMean(), portfolio1.getRisk());
        portfolio1.setLastSimulation(new SimulationResult(portfolio1Results, numberSimulations));
        Double median1 = portfolio1.getLastSimulation().getMedian();
        Double best10P1 = portfolio1.getLastSimulation().getTop10();
        Double worst10P1 = portfolio1.getLastSimulation().getWorst10();
        System.out.println(String.format("Results for Portfolio1:\n\tMedian: %.2f\n\tBest 10: %.2f\n\tWorst 10: %.2f\n\tHistoric Risk: %.4f\n\tMean %.4f",
                median1, best10P1, worst10P1, portfolio1.getRisk(), portfolio1.getMean()));

        List<Double> portfolio2Results = runSimulation(numberSimulations, numberYears, initialValue, portfolio2.getMean(), portfolio2.getRisk());
        portfolio2.setLastSimulation(new SimulationResult(portfolio2Results, numberSimulations));
        Double median2 = portfolio2.getLastSimulation().getMedian();
        Double best10P2 = portfolio2.getLastSimulation().getTop10();
        Double worst10P2 = portfolio2.getLastSimulation().getWorst10();
        System.out.println(String.format("Results for Portfolio2:\n\tMedian: %.2f\n\tBest 10: %.2f\n\tWorst 10: %.2f\n\tHistoric Risk: %.4f\n\tMean %.4f",
                median2, best10P2, worst10P2, portfolio2.getRisk(), portfolio2.getMean()));
    }

    /**
     * Performs the mathematical operations involved for each simulation.
     * Goes through years desired and calculates new balance each year (inflation adjusted)
     */
    private List<Double> runSimulation(int numberSimulations, int numberYears, Double initialValue, Double mean, Double risk) {
        return IntStream
                .range(0, numberSimulations)
                .asDoubleStream()
                .parallel()
                .map(number -> {
                    // Monte carlo to simulate portfolio performance for a given year
                    // the given year's performance will be equal to a random rate of return from the described distribution
                    // and then adjusted for inflation each year ((1 + ROI)/(1 + inflation)) - 1
                    Double portfolioValue = initialValue;
                    for (int i = 0; i < numberYears; i++) {
                        Double nextGaussian = this.randomGenerator.nextGaussian();
                        Double returnOnInvestment = ((nextGaussian * risk) + mean) / 100;
                        returnOnInvestment = ((1 + returnOnInvestment) / (1 + INFLATION_RATE)) - 1;
                        portfolioValue = (1 + returnOnInvestment) * portfolioValue;
                    }
                    return portfolioValue;
                })
                .sorted() // Sort so that we can later get the top/bottom 10% as well as the median easily
                .boxed()
                .collect(Collectors.toList());
    }
}
