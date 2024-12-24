class SimulationStatistics {
    private final double avgWaitTime;
    private final int custServed;
    private final int custNotServed;

    SimulationStatistics() {
        this.avgWaitTime = 0.0;
        this.custServed = 0;
        this.custNotServed = 0;
    }

    SimulationStatistics(double avgWaitTime, int custServed,
                         int custNotServed) {
        this.avgWaitTime = avgWaitTime;
        this.custServed = custServed;
        this.custNotServed = custNotServed;
    }

    public SimulationStatistics updateNotServed() {
        return new SimulationStatistics(this.avgWaitTime, this.custServed,
                                        this.custNotServed + 1);
    }

    public SimulationStatistics updateAvgTime(double timeDiff) {
        int customersServed = this.custServed + 1;

        // add back the "original" time diff
        double avgTime = (timeDiff + (this.avgWaitTime * this.custServed)) / customersServed;
        return new SimulationStatistics(avgTime, customersServed,
                                        this.custNotServed);
    }

    @Override
    public String toString() {
        return "[" + String.format("%.3f", this.avgWaitTime) + " " +
            this.custServed + " " + this.custNotServed + "]";
    }
}
