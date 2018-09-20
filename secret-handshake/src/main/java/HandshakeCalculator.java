import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HandshakeCalculator {

    private static final int REVERSE_POSITION = 4;
    private int number;
    private Signal[] signals = Signal.values();

    List<Signal> calculateHandshake(int number) {
        this.number = number;
        return indexStream().filter(this::isBitSet)
            .mapToObj(index -> signals[index])
            .collect(Collectors.toList());
    }

    IntStream indexStream() {
        IntStream range = IntStream.range(0, signals.length);
        boolean toReverse = isBitSet(REVERSE_POSITION);
        if (toReverse)
            return range.map(index -> signals.length - index - 1);
        else return range;
    }

    boolean isBitSet(int pos) {
        return ((number >> pos) & 1) == 1;
    }

}
