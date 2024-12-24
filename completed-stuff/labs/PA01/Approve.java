class Approve extends Transaction {
    private final int billing;

    Approve(Transaction tsc, Pair<Integer, Integer> rowOfSeats, int billing) {
        super(
            tsc, rowOfSeats,
            "billed " + billing + "; booked " +
                new Pair<>(rowOfSeats.t(), rowOfSeats.t() + rowOfSeats.u() - 1)
                    .toString());
        this.billing = billing;
    }

    @Override
    public String toString() {
        return "APPROVED:\n" + super.toString();
    }
}
