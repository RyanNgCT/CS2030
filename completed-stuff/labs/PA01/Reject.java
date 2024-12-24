class Reject extends Transaction {
    private final int billing;

    Reject(Transaction tsc, int billing) {
        super(tsc, "not billed " + billing);
        this.billing = billing;
    }

    @Override
    public String toString() {
        return "REJECTED:\n" + super.toString();
    }
}
