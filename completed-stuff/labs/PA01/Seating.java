import java.util.List;
import java.util.stream.IntStream;

class Seating {
    private final int capacity;
    private final List<Seat> seatList;

    Seating(int capacity) {
        this.capacity = capacity;
        this.seatList = IntStream.rangeClosed(0, capacity - 1)
                            .boxed()
                            .<Seat>map(ele -> new Available())
                            .toList();
    }

    Seating(int capacity, List<Seat> seatList) {
        this.capacity = capacity;
        this.seatList = seatList;
    }

    boolean isAvailable(Pair<Integer, Integer> rowOfSeats) {
        int startIdx = rowOfSeats.t();
        int stopIdx = rowOfSeats.t() + rowOfSeats.u() - 1;

        if (startIdx < 0 || stopIdx > this.capacity - 1) {
            return false;
        }
        return this.seatList.stream()
            .filter(seat
                    -> (this.seatList.indexOf(seat) >= startIdx &&
                        this.seatList.indexOf(seat) <= stopIdx))
            .noneMatch(seat -> seat.isBooked());
    }

    Seating book(Pair<Integer, Integer> rowOfSeats) {
        if (this.isAvailable(rowOfSeats)) {
            List<Seat> updatedSList =
                this.seatList.stream()
                    .map(ele
                         -> (this.seatList.indexOf(ele) >= rowOfSeats.t() &&
                             this.seatList.indexOf(ele) <=
                                 rowOfSeats.t() + rowOfSeats.u() - 1)
                                ? new Booked()
                                : ele)
                    .toList();

            return new Seating(this.capacity, updatedSList);
        }
        return this;
    }

    @Override
    public String toString() {
        return this.seatList.stream()
            .map(st -> st.toString())
            .reduce("", (x, y) -> x + y);
    }
}
