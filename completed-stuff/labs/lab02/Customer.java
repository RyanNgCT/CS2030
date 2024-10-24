class Customer {
    private final int custId;
    private final double arrivalTime;
    // public static final double SERVICE_TIME = 1.0;

    Customer(int custId, double arrivalTime) {
        this.custId = custId;
        this.arrivalTime = arrivalTime;
    }

    public boolean canBeServed(double time) {
        return this.arrivalTime >= time;
    }

    public double serveTill(double serviceTime) {
        return this.arrivalTime + serviceTime;
    }

    @Override
    public String toString() {
        return "customer " + this.custId;
    }
}
