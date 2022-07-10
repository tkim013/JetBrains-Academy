import java.time.LocalDateTime;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        LocalDateTime a = LocalDateTime.parse(scanner.nextLine());
        int hours = scanner.nextInt();
        int mins = scanner.nextInt();

        System.out.println(a.minusHours(hours).plusMinutes(mins));
    }
}