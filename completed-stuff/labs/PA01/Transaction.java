abstract class Transaction {
    private final Seating plan;
    private final String log;

    Transaction(Seating plan, String log) {
        this.plan = plan;
        this.log = log;
    }

    Transaction(Transaction tsc, String log) {
        this.plan = tsc.plan;
        this.log = tsc.log + "\n" + log;
    }

    Transaction(Transaction tsc, Pair<Integer, Integer> rowOfSeats,
                String log) {
        this.plan = tsc.plan.book(rowOfSeats);
        this.log = tsc.log + "\n" + log;
    }

    public boolean isRowBookable(Pair<Integer, Integer> rowOfSeats) {
        return this.plan.isAvailable(rowOfSeats);
    }

    public Transaction transact(Transaction tsc) {
        return this;
    }

    @Override
    public String toString() {
        return this.log + "\n" + this.plan.toString();
    }
}
