import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // start coding here
        final int INTERVAL = 3;

        int a = scanner.nextInt();
        int b = scanner.nextInt();

        int sum = 0;
        int count = 0;

        for (int i = a; i <= b;) {
            if (i % INTERVAL != 0) {
                i++;
                continue;
            }

            sum += i;
            count++;
            i += INTERVAL;
        }

        System.out.println((double) sum / count);
    }
}