import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        LocalDateTime a = LocalDateTime.parse(scanner.nextLine());
        LocalDateTime b = LocalDateTime.parse(scanner.nextLine());

        System.out.println(a.until(b, ChronoUnit.HOURS));
    }
}