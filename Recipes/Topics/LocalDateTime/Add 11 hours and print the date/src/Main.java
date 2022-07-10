import java.time.LocalDateTime;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);

        LocalDateTime dt = LocalDateTime.parse(scanner.nextLine());

        System.out.println(dt.plusHours(11).toLocalDate());
    }
}