import java.time.LocalDate;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);

        int year = scanner.nextInt();
        int month = scanner.nextInt();
        int daysTillEnd = scanner.nextInt();

        LocalDate date = LocalDate.of(year, month, 1);

        date = date.plusMonths(1).minusDays(daysTillEnd);

        System.out.println(date);

    }
}