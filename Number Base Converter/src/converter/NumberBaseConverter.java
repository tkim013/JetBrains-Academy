package converter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NumberBaseConverter {

    private final static int PRECISION = 5;
    private final static List<Character> baseChars = List.of('0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F',
            'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z');

    public static void main(String[] args) {

        State state = State.INPUT;

        int sourceBase = 0;
        int targetBase = 0;

        String option;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            switch (state) {

                case INPUT: {

                    String[] input;

                    System.out.print("Enter two numbers in format: {source base} {target base} (To quit type /exit) ");
                    option = scanner.nextLine();

                    if (option.equals("/exit")) {
                        System.exit(0);
                    }

                    try {

                        input = option.split(" ");
                        sourceBase = Integer.parseInt(input[0]);
                        targetBase = Integer.parseInt(input[1]);

                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                        e.printStackTrace();
                        System.out.println("Enter numbers separated by a space: \"xxx xxx\" or \"/exit\"");
                        break;
                    }

                    if (sourceBase > 36 || targetBase > 36 || sourceBase < 2 || targetBase < 2) {
                        System.out.println("source base and target base must be in range of 2 - 36");
                        break;
                    }

                    state = State.CONVERT;

                    break;
                }

                case CONVERT: {

                    String[] input;
                    BigInteger result;

                    System.out.printf("Enter number in base %d to convert to base %d (To go back type /back) ",
                            sourceBase, targetBase);
                    option = scanner.nextLine();

                    if(option.equals("/back")) {
                        state = State.INPUT;
                        break;
                    }

                    try {
                        if (Integer.parseInt(option) == 0) {
                            System.out.printf("Conversion result: 0%n%n");
                        }
                    } catch (NumberFormatException ignored) {
                    }

                    if (option.contains(".") && option.length() > 1) {

                        StringBuilder sb = new StringBuilder();
                        String s;

                        input = option.split("\\.");

                        result = baseToDecimal(sourceBase, input[0].toUpperCase());
                        input[1] = fractionalBaseToDecimal(sourceBase, input[1].toUpperCase());

                        sb.append(decimalToBase(targetBase, result));
                        sb.append(".");
                        s = fractionalDecimalToBase(targetBase, input[1]);
                        if (s.length() > PRECISION) {
                            s = s.substring(0, PRECISION);
                        }
                        sb.append(s);

                        System.out.printf("Conversion result: " + sb + "%n%n");

                    } else {

                        String s;

                        result = baseToDecimal(sourceBase, option);
                        s = decimalToBase(targetBase, result);

                        System.out.printf("Conversion result: " + s + "%n%n");
                    }
                    break;
                }
            }
        }
    }

    enum State {
        INPUT, CONVERT
    }

    public static String decimalToBase(int targetBase, BigInteger decimal) {

        BigInteger base = BigInteger.valueOf(targetBase);
        List<Integer> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        while(decimal.compareTo(BigInteger.ZERO) > 0){
            list.add(decimal.mod(base).intValue());
            decimal = decimal.divide(base);
        }

        for(int i = list.size() - 1; i >= 0; i--) {
            sb.append(baseChars.get(list.get(i)));
        }
        return sb.toString();
    }

    private static BigInteger baseToDecimal(int sourceBase, String number) {

        BigInteger value = BigInteger.ZERO;
        BigInteger base = BigInteger.valueOf(sourceBase);

        number = number.toUpperCase();

        for (int i = 0; i < number.length(); i++)
        {
            char c = number.charAt(i);
            int d = baseChars.indexOf(c);
            value = value.multiply(base).add(BigInteger.valueOf(d));
        }

        return value;
    }

    public static String fractionalBaseToDecimal(int sourceBase, String fractional) {

        try {
            if (Integer.parseInt(fractional) == 0) {
                return "0".repeat(PRECISION);
            }
        } catch (NumberFormatException ignore) {

        }

        BigDecimal result = BigDecimal.ZERO;
        BigDecimal base = BigDecimal.valueOf(sourceBase);
        BigDecimal p = base;

        try {
            for (int i = 0; i < fractional.length(); i++) {
                result = result.add(BigDecimal.valueOf(baseChars.indexOf(fractional.charAt(i)))
                        .divide(base, PRECISION, RoundingMode.HALF_DOWN));
                base = base.multiply(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return String.valueOf(result).substring(String.valueOf(result).indexOf("."));
    }

    public static String fractionalDecimalToBase(int targetBase, String fractional) {

        BigDecimal value = new BigDecimal(fractional);
        List<Integer> result = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        BigDecimal b = BigDecimal.valueOf(targetBase);
        while (value.compareTo(BigDecimal.ZERO) > 0) {
            result.add(value.multiply(b).intValue());
            value = value.multiply(b).subtract(BigDecimal.valueOf(result.get(result.size() - 1)));

            if (result.size() == PRECISION) {
                break;
            }
        }

        for (Integer i : result) {
            sb.append(baseChars.get(i));
        }

        if (sb.length() < PRECISION) {
            sb.append("0".repeat(Math.max(0, PRECISION - sb.length())));
        }

        return sb.toString();
    }
}
