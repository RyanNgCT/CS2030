class Init extends Transaction {
    Init(Seating plan) {
        super(plan, "Initializing");
    }

    @Override
    public String toString() {
        return "INIT:\n" + super.toString();
    }
}
