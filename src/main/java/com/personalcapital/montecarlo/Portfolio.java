package com.personalcapital.montecarlo;

/**
 * Class to model the risk and mean of the portfolio. Once a simulation is run, it can be saved into the lastSimulation
 * instance variable
 */
public class Portfolio {
    private Double risk;
    private Double mean;
    private SimulationResult lastSimulation;

    public Portfolio(double risk, double mean) {
        this.risk = risk;
        this.mean = mean;
    }

    public Double getRisk() {
        return risk;
    }

    public void setRisk(Double risk) {
        this.risk = risk;
    }

    public Double getMean() {
        return mean;
    }

    public void setMean(Double mean) {
        this.mean = mean;
    }

    public SimulationResult getLastSimulation() {
        return lastSimulation;
    }

    public void setLastSimulation(SimulationResult lastSimulation) {
        this.lastSimulation = lastSimulation;
    }
}
