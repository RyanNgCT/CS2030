class TakeACab implements Service {
    private static final int magnitude = 33;
    private static final int surcharge = 200;

    TakeACab() {
        
    }

    public int computeFare(int dist, int noOP, int tos) {
        int fare = dist * magnitude + surcharge;
        return fare;
    }

    @Override
    public String toString() {
        return "TakeACab";
    }
}
