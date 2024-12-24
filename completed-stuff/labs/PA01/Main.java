import java.util.stream.Stream;
void main() {}

Transaction process(Stream<Transaction> transactions, Init init) {
    return transactions.reduce(init,
                               (newTsc, currTsc) -> currTsc.transact(newTsc));
}
