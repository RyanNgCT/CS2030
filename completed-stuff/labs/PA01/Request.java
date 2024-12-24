class Request extends Transaction {
    private final Pair<Integer, Integer> rowOfSeats;
    private final int billing;
    private final Bank bank;

    Request(Seating plan, Pair<Integer, Integer> rowOfSeats, int billing,
            Bank bank) {
        super(plan, "Requesting");
        this.rowOfSeats = rowOfSeats;
        this.billing = billing;
        this.bank = bank;
    }

    @Override
    public Transaction transact(Transaction tsc) {
        if (tsc.isRowBookable(rowOfSeats)) {
            if (this.bank.test(this.billing)) {
                return new Approve(tsc, rowOfSeats, billing);
            }
            return new Reject(tsc, billing);
        }
        return tsc;
    }

    @Override
    public String toString() {
        return "REQUEST:\n" + super.toString();
    }
}
