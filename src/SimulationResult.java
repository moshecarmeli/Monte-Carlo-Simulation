import java.util.List;

/**
 * Class modeling the results of a simulation. This consists of a list of final balances for each of the simulations
 * run.
 */
public class SimulationResult {
    private List<Double> simulationResults;
    private Integer numberOfSimulations;

    public SimulationResult(List<Double> simulationResults, Integer numberOfSimulations) {
        this.simulationResults = simulationResults;
        this.numberOfSimulations = numberOfSimulations;
    }

    /**
     * Function to get the result representing the worst of the top 10% outcomes
     */
    public Double getTop10() {
        return this.simulationResults.get(Math.round(this.numberOfSimulations * 9/10) - 1);
    }

    /**
     * Function to get the result representing the best of the worst 10% outcomes
     */
    public Double getWorst10() {
        return this.simulationResults.get(Math.round(this.numberOfSimulations * 1/10) - 1);
    }

    /**
     * Function to get median of result set.
     * This will be the middle element, if the list has an odd number of elements.
     * Otherwise this will be the average of the two middle elements.
     */
    public Double getMedian() {
        if (numberOfSimulations % 2 == 0) {
            return this.simulationResults.get((int) Math.floor(this.numberOfSimulations/2));
        } else {
            return (this.simulationResults.get((int)Math.floor(this.numberOfSimulations/2) - 1) +
                    this.simulationResults.get((int)Math.floor(this.numberOfSimulations/2)))/2;
        }
    }
}
