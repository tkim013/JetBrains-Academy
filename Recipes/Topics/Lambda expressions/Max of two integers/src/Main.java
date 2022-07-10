import java.util.function.IntBinaryOperator;

class Operator {

    public static IntBinaryOperator binaryOperator = (n1, n2) -> {
        return n1 > n2 ? n1 : n2;
    };
}