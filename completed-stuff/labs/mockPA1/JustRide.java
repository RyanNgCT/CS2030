class JustRide implements Service {
    private static final int magnitude = 22;
    private static final int time1 = 600;
    private static final int time2 = 900;
    private static final int surcharge = 500;

    JustRide() {
        
    }

    public int computeFare(int distance, int noOfPassengers,
                           int timeOfService) {
        int fare = distance * magnitude;
        if (timeOfService >= time1 && timeOfService <= time2) {
            fare += surcharge;
        }
        return fare;
    }

    @Override
    public String toString() {
        return "JustRide";
    }
}
