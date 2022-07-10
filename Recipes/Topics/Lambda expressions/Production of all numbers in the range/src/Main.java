import java.util.function.*;


class Operator {

    public static LongBinaryOperator binaryOperator = (l1, l2) -> {
        long r = 1;

        for (long i = l1; i <= l2; i++) {
            r = r * i;
        }
        return r;
    };
}