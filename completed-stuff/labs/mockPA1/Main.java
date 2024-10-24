import java.util.List;

void main() {}

public List<Booking> findBestBooking(Request rq, List<Driver> driverList) {
    List<Booking> bkList =
        driverList.stream()
            .flatMap(drv -> new Booking(drv, rq).potentialBookings().stream())
            .sorted()
            .toList();

    return bkList;
}
