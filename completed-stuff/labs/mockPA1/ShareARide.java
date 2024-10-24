class ShareARide implements Service {
    private static final int magnitude = 50;
    private static final int time1 = 600;
    private static final int time2 = 900;
    private static final int surcharge = 500;

    ShareARide() {
        
    }

    public int computeFare(int dist, int noOfPass, int tos) {
        double tFare = dist * magnitude;
        if (tos >= time1 && tos <= time2) {
            tFare += surcharge;
        }
        double fare = Math.floor(tFare / noOfPass);
        return (int)fare;
    }

    @Override
    public String toString() {
        return "ShareARide";
    }
}
