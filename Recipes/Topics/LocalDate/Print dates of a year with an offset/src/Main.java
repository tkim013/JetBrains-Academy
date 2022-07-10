import java.time.LocalDate;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);

        LocalDate date = LocalDate.parse(scanner.nextLine());

        int offset = scanner.nextInt();
        int nextYear = date.getYear() + 1;

        while (date.getYear() != nextYear) {
            System.out.println(date);
            date = date.plusDays(offset);
        }
    }
}