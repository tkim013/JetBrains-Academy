import java.time.LocalDateTime;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();

        String[] ar = s.split(" ");

        LocalDateTime dt = LocalDateTime.parse(ar[0]);
        int days = Integer.parseInt(ar[1]);
        int hours = Integer.parseInt(ar[2]);

        System.out.println(dt.plusDays(days).minusHours(hours));
    }
}