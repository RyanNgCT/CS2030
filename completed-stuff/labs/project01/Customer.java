class Customer implements Comparable<Customer> {
    private final int custId;
    private final double arrivalTime;

    Customer(int custId, double arrivalTime) {
        this.custId = custId;
        this.arrivalTime = arrivalTime;
    }

    public double calcDifference(double newTime) {
        return newTime - this.arrivalTime;
    }

    @Override
    public int compareTo(Customer otherCust) {
        if (this.arrivalTime < otherCust.arrivalTime) {
            return -1;
        } else if (this.arrivalTime > otherCust.arrivalTime) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "customer " + this.custId;
    }
}
