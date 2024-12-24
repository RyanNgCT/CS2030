class Available implements Seat {
    Available(){}

    public final boolean isBooked(){
        return false;
    }

    @Override
    public String toString(){
        return ".";
    }
}