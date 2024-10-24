class Customer {
    private final int custId;
    private final double arrivalTime;
    private final double serviceTime;

    Customer(int custId, double arrivalTime, double serviceTime) {
        this.custId = custId;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    public boolean canBeServed(double time) {
        return this.arrivalTime >= time;
    }

    public double serveTill() {
        return this.arrivalTime + this.serviceTime;
    }

    @Override
    public String toString() {
        return "customer " + this.custId;
    }
}
