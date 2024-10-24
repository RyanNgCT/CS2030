abstract class Event implements Comparable<Event> {
    protected final double eventTime;
    protected final double arrivalTime;

    Event(double eventTime) {
        this.eventTime = eventTime;
        this.arrivalTime = eventTime;
    }

    Event(double eventTime, double arrivalTime) {
        this.eventTime = eventTime;
        this.arrivalTime = arrivalTime;
    }

    abstract Pair<Event, Shop> next(Shop shp);

    @Override
    public int compareTo(Event evt) {
        if (this.eventTime < evt.eventTime) {
            return -1;
        } else if (this.eventTime > evt.eventTime) {
            return 1;
        } else {
            if (this.arrivalTime < evt.arrivalTime) {
                return -1;
            } else if (this.arrivalTime > evt.arrivalTime) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
